package daniamproject

import grails.gorm.transactions.Transactional


@Transactional
class StoreController {
    static scaffold = Store

    def index() {
        [storeList: Store.list(), productList: Product.list(), repositoryList: Repository.list()]
    }

    def show(Long id) {
        def store = Store.get(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        [store: store, productList: Product.list(), repositoryList: Repository.list()]
    }

    def create() {
        [store: new Store(params)]
    }

    def save() {
        def existingStore = Store.findByAddress(params.address)
        if (existingStore){
            flash.message = "Store with the same address already exists"
            render(view: "create", model: [store: new Store(params), error: "Store with the same address already exists"])
            return
        }

        def store = new Store(params)
        if (!store.save(flush: true)) {
            render(view: "create", model: [store: store])
            return
        }
        flash.message = "Store created successfully"
        redirect(action: "show", id: store.id)
    }

    def edit(Long id) {
        def store = Store.get(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        [store: store]
    }

    def update(Long id) {
        def store = Store.get(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        store.properties = params
        if (!store.save(flush: true)) {
            render(view: "edit", model: [store: store])
            return
        }
        flash.message = "Store updated successfully"
        redirect(action: "show", id: store.id)
    }

    def delete(Long id) {
        def store = Store.get(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        store.delete(flush: true)
        flash.message = "Store deleted successfully"
        redirect(action: "index")
    }

    def sellProduct() {
        def store = Store.get(params.storeId)
        def product = Product.get(params.productId)
        if (store && product) {
            def storeProduct = store.products.find { it.product.id == product.id }
            if (storeProduct) {
                if (storeProduct.quantity >= params.int('quantity')) {
                    storeProduct.quantity -= params.int('quantity')
                    if (storeProduct.quantity == 0) {
                        store.removeFromProducts(storeProduct)
                        storeProduct.delete(flush: true)
                    } else {
                        storeProduct.save(flush: true)
                    }
                    store.save(flush: true)
                } else {
                    flash.message = "Not enough quantity in store to sell."
                }
            }
        }
        redirect(action: "show", id: params.storeId)
    }

    def returnProduct() {
        println("Params: ${params}")  // Add this line to log the parameters
        def store = Store.get(params.storeId)
        def repository = Repository.get(params.repositoryId)
        def product = Product.get(params.productId)
        if (store && repository && product) {
            def storeProduct = store.products.find { it.product.id == product.id }
            if (storeProduct) {
                def repositoryProduct = repository.products.find { it.product.id == product.id }
                if (repositoryProduct) {
                    repositoryProduct.quantity += storeProduct.quantity
                    repositoryProduct.save(flush: true)
                } else {
                    def newProduct = new RepositoryProduct(product: product, quantity: storeProduct.quantity)
                    repository.addToProducts(newProduct)
                }
                store.removeFromProducts(storeProduct)
                storeProduct.delete(flush: true)
                store.save(flush: true)
                repository.save(flush: true)
            }
        }
        redirect(action: "show", id: params.storeId)
    }

    def addProductFromRepository() {
        def store = Store.get(params.storeId)
        def repository = Repository.get(params.repositoryId)
        def product = Product.get(params.productId)
        if (store && repository && product) {
            def repositoryProduct = repository.products.find { it.product.id == product.id }
            if (repositoryProduct && repositoryProduct.quantity >= params.int('quantity')) {
                def storeProduct = store.products.find { it.product.id == product.id }
                if (storeProduct) {
                    storeProduct.quantity += params.int('quantity')
                    storeProduct.save(flush: true)
                } else {
                    def newStoreProduct = new StoreProduct(product: product, quantity: params.int('quantity'))
                    store.addToProducts(newStoreProduct)
                }
                repositoryProduct.quantity -= params.int('quantity')
                if (repositoryProduct.quantity == 0) {
                    repository.removeFromProducts(repositoryProduct)
                    repositoryProduct.delete(flush: true)
                } else {
                    repositoryProduct.save(flush: true)
                }
                store.save(flush: true)
                repository.save(flush: true)
            }
        }
        redirect(action: "show", id: params.storeId)
    }

    def checkExpiredProducts() {
        def stores = Store.list()
        def currentDate = new Date()
        stores.each { store ->
            store.products.each { storeProduct ->
                if (storeProduct.product.expirationDate && storeProduct.product.expirationDate.before(currentDate)) {
                    def repository = Repository.list().find { it.products.contains(storeProduct.product) }
                    if (repository) {
                        returnProduct(store.id, repository.id, storeProduct.product.id, storeProduct.quantity)
                    }
                }
            }
        }
    }
}
