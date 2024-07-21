package daniamproject

import grails.gorm.transactions.Transactional

@Transactional
class StoreController {
    static scaffold = Store

    StoreService storeService
    RepositoryService repositoryService
    ProductService productService

    def index() {
        [storeList: storeService.listStores(), productList: productService.listProducts(), repositoryList: repositoryService.listRepositories()]
    }

    def show(Long id) {
        def store = storeService.getStore(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        [store: store, productList: productService.listProducts(), repositoryList: repositoryService.listRepositories()]
    }

    def create() {
        [store: new Store(params)]
    }

    def save() {
        def existingStore = storeService.findByAddress(params.address)
        if (existingStore){
            flash.message = "Store with the same address already exists"
            render(view: "create", model: [store: new Store(params), error: "Store with the same address already exists"])
            return
        }

        def store = new Store(params)
        if (!storeService.saveStore(store)) {
            render(view: "create", model: [store: store])
            return
        }
        flash.message = "Store created successfully"
        redirect(action: "show", id: store.id)
    }

    def edit(Long id) {
        def store = storeService.getStore(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        [store: store]
    }

    def update(Long id) {
        def store = storeService.getStore(id)
        if (!store) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        store.properties = params
        if (!storeService.saveStore(store)) {
            render(view: "edit", model: [store: store])
            return
        }
        flash.message = "Store updated successfully"
        redirect(action: "show", id: store.id)
    }

    def delete(Long id) {
        if (!storeService.deleteStore(id)) {
            flash.message = "Store not found"
            redirect(action: "index")
            return
        }
        flash.message = "Store deleted successfully"
        redirect(action: "index")
    }

    def sellProduct() {
        def store = storeService.getStore(params.storeId.toLong())
        def product = productService.getProduct(params.productId.toLong())
        if (store && product) {
            storeService.sellProductFromStore(store, product, params.int('quantity'))
        }
        redirect(action: "show", id: params.storeId)
    }

    def returnProduct() {
        def store = storeService.getStore(params.storeId.toLong())
        def repository = repositoryService.getRepository(params.repositoryId.toLong())
        def product = productService.getProduct(params.productId.toLong())
        if (store && repository && product) {
            storeService.returnProductToRepository(store, repository, product)
        }
        redirect(action: "show", id: params.storeId)
    }

    def addProductFromRepository() {
        def store = storeService.getStore(params.storeId.toLong())
        def repository = repositoryService.getRepository(params.repositoryId.toLong())
        def product = productService.getProduct(params.productId.toLong())

        if (store && repository && product) {
            storeService.addProductFromRepositoryToStore(store, repository, product, params.int('quantity'))
        }
        redirect(action: "show", id: params.storeId)
    }

    def checkExpiredProducts() {
        storeService.checkExpiredProducts()
    }
}
