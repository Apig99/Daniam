package daniamproject

import grails.gorm.transactions.Transactional

@Transactional

class ProductController {

    ProductService productService

    def index() {
        [productList: productService.listProducts()]
    }

    def show(Long id) {
        def product = productService.getProduct(id)
        if (!product) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        [product: product]
    }

    def create() {
        [product: new Product(params)]
    }

    def save() {
        def existingProduct = productService.findByName(params.name)
        if (existingProduct) {
            flash.message = "Product with the same name already exists"
            render(view: "create", model: [product: new Product(params), error: "Product with the same name already exists"])
            return
        }
        def product = new Product(params)
        if (!productService.saveProduct(product)) {
            render(view: "create", model: [product: product])
            return
        }
        flash.message = "Product created successfully"
        redirect(action: "show", id: product.id)
    }

    def update(Long id) {
        def product = productService.getProduct(id)
        if (!product) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        product.properties = params
        if (!productService.saveProduct(product)) {
            render(view: "edit", model: [product: product])
            return
        }
        flash.message = "Product updated successfully"
        redirect(action: "show", id: product.id)
    }

    def delete(Long id) {
        if (!productService.deleteProduct(id)) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        flash.message = "Product deleted successfully"
        redirect(action: "index")
    }
}
