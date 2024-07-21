package daniamproject

import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class RepositoryController {
    static scaffold = Repository

    RepositoryService repositoryService
    ProductService productService

    def index() {
        [repositoryList: repositoryService.listRepositories(), productList: productService.listProducts()]
    }

    def show(Long id) {
        def repository = repositoryService.getRepository(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        [repository: repository, productList: productService.listProducts(), repositoryList: repositoryService.listRepositories() - repository]
    }

    def save() {
        def existingRepository = repositoryService.findByName(params.name)
        if (existingRepository){
            flash.message = "Repository with the same name already exists"
            render(view: "create", model: [repository: new Repository(params), error: "Repository with the same name already exists"])
            return
        }
        def repository = new Repository(params)
        if (!repositoryService.saveRepository(repository)) {
            render(view: "create", model: [repository: repository])
            return
        }
        flash.message = "Repository created successfully"
        redirect(action: "show", id: repository.id)
    }

    def edit(Long id) {
        def repository = repositoryService.getRepository(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        [repository: repository]
    }

    def update(Long id) {
        def repository = repositoryService.getRepository(id)
        if (!repository) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        repository.properties = params
        if (!repositoryService.saveRepository(repository)) {
            render(view: "edit", model: [repository: repository])
            return
        }
        flash.message = "Repository updated successfully"
        redirect(action: "show", id: repository.id)
    }

    def delete(Long id) {
        if (!repositoryService.deleteRepository(id)) {
            flash.message = "Repository not found"
            redirect(action: "index")
            return
        }
        flash.message = "Repository deleted successfully"
        redirect(action: "index")
    }

    def addProduct() {
        def repository = repositoryService.getRepository(params.repositoryId.toLong())
        def product = productService.getProduct(params.productId.toLong())

        if (repository && product) {
            repositoryService.addProductToRepository(repository, product, params.int('quantity'))
        }
        redirect(action: "show", id: params.repositoryId.toLong())
    }

    def removeProduct() {
        def repository = repositoryService.getRepository(params.repositoryId.toLong())
        def product = productService.getProduct(params.productId.toLong())
        if (repository && product) {
            repositoryService.removeProductFromRepository(repository, product)
        }
        redirect(action: "show", id: params.repositoryId.toLong())
    }

    def moveProduct() {
        def sourceRepository = repositoryService.getRepository(params.sourceRepositoryId.toLong())
        def targetRepository = repositoryService.getRepository(params.targetRepositoryId.toLong())
        def product = productService.getProduct(params.productId.toLong())
        if (sourceRepository && targetRepository && product) {
            repositoryService.moveProductBetweenRepositories(sourceRepository, targetRepository, product, params.int('quantity'))
        }
        redirect(action: "show", id: params.sourceRepositoryId.toLong())
    }

    def getProductsForRepository(Long id) {
        def repository = repositoryService.getRepository(id)
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
