package daniamproject

import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class RepositoryController {
    static scaffold = Repository

    def index() {
        [repositoryList: Repository.list(), productList: Product.list()]
    }

    def show(Long id) {
        def repository = Repository.get(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        [repository: repository, productList: Product.list(), repositoryList: Repository.list() - repository]
    }

//    def create() {
//        [repository: new Repository(params)]
//    }

    def save() {
        def existingRepository = Repository.findByName(params.name)
        if (existingRepository){
            flash.message = "Repository with the same name already exists"
            render(view: "create", model: [repository: new Repository(params), error: "Repository with the same name already exists"])
            return
        }
        def repository = new Repository(params)
        if (!repository.save(flush: true)) {
            render(view: "create", model: [repository: repository])
            return
        }
        flash.message = "Repository created successfully"
        redirect(action: "show", id: repository.id)
    }

    def edit(Long id) {
        def repository = Repository.get(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        [repository: repository]
    }

    def update(Long id) {
        def repository = Repository.get(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        repository.properties = params
        if (!repository.save(flush: true)) {
            render(view: "edit", model: [repository: repository])
            return
        }
        flash.message = "Repository updated successfully"
        redirect(action: "show", id: repository.id)
    }

    def delete(Long id) {
        def repository = Repository.get(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        repository.delete(flush: true)
        flash.message = "Repository deleted successfully"
        redirect(action: "index")
    }

    def addProduct() {
        def repository = Repository.get(params.repositoryId)
        def product = Product.get(params.productId)

        if (repository && product) {
            def existingProduct = repository.products.find { it.product.id == product.id }
            if (existingProduct) {
                existingProduct.quantity += params.int('quantity')
                existingProduct.save(flush: true)
            } else {
                def repositoryProduct = new RepositoryProduct(product: product, quantity: params.int('quantity'))
                repository.addToProducts(repositoryProduct)
            }
            repository.save(flush: true)
        }
        redirect(action: "show", id: params.repositoryId)
    }

    def removeProduct() {
        def repository = Repository.get(params.repositoryId)
        def product = Product.get(params.productId)
        if (repository && product) {
            def repositoryProduct = repository.products.find { it.product.id == product.id }
            if (repositoryProduct) {
                repository.removeFromProducts(repositoryProduct)
                repositoryProduct.delete(flush: true)
            }
            repository.save(flush: true)
        }
        redirect(action: "show", id: params.repositoryId)
    }

    def moveProduct() {
        def sourceRepository = Repository.get(params.sourceRepositoryId)
        def targetRepository = Repository.get(params.targetRepositoryId)
        def product = Product.get(params.productId)
        if (sourceRepository && targetRepository && product) {
            def sourceProduct = sourceRepository.products.find { it.product.id == product.id }
            if (sourceProduct && sourceProduct.quantity >= params.int('quantity')) {
                def targetProduct = targetRepository.products.find { it.product.id == product.id }
                if (targetProduct) {
                    targetProduct.quantity += params.int('quantity')
                    targetProduct.save(flush: true)
                } else {
                    def newProduct = new RepositoryProduct(product: product, quantity: params.int('quantity'))
                    targetRepository.addToProducts(newProduct)
                }
                sourceProduct.quantity -= params.int('quantity')
                if (sourceProduct.quantity == 0) {
                    sourceRepository.removeFromProducts(sourceProduct)
                    sourceProduct.delete(flush: true)
                }
                sourceProduct.save(flush: true)
                targetRepository.save(flush: true)
//                if (sourceProduct.quantity > params.int('quantity')) {
//                    sourceProduct.quantity -= params.int('quantity')
//                    sourceProduct.save(flush: true)
//                } else {
//                    sourceRepository.removeFromProducts(sourceProduct)
//                    sourceProduct.delete(flush: true)
//                }
//                sourceRepository.save(flush: true)
//                targetRepository.save(flush: true)
            }
        }
        redirect(action: "show", id: params.sourceRepositoryId)
    }

    def getProductsForRepository(Long id) {
        def repository = Repository.get(id)
        if (repository) {
            def products = repository.products.collect {
                [id: it.product.id, name: it.product.name]
            }
            render products as JSON
        } else {
            render status: 404
        }
    }

}
