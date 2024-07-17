package daniamproject

import grails.gorm.transactions.Transactional

class ProductController {
    static scaffold = Product

    @Transactional
    def save() {
        def existingProduct = Product.findByName(params.name)
        if (existingProduct) {
            flash.message = "Product with the same name already exists"
            render(view: "create", model: [product: new Product(params), error: "Product with the same name already exists"])
            return
        }

        def product = new Product(params)
        if (!product.save(flush: true)) {
            render(view: "create", model: [product: product])
            return
        }
        flash.message = "Product created successfully"
        redirect(action: "show", id: product.id)
    }

    @Transactional
    def update(Long id) {
        def product = Product.get(id)
        if (!product) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        product.properties = params
        if (!product.save(flush: true)) {
            render(view: "edit", model: [product: product])
            return
        }
        flash.message = "Product updated successfully"
        redirect(action: "show", id: product.id)
    }

    @Transactional
    def delete(Long id) {
        def product = Product.get(id)
        if (!product) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        product.delete(flush: true)
        flash.message = "Product deleted successfully"
        redirect(action: "index")
    }

    def show(Long id) {
        def product = Product.get(id)
        if (!product) {
            flash.message = "Product not found"
            redirect(action: "index")
            return
        }
        [product: product]
    }

    def index() {
        [productList: Product.list()]
    }


}
