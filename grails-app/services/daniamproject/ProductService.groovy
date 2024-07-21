package daniamproject

import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    Product getProduct(Long id) {
        Product.get(id)
    }

    List<Product> listProducts() {
        Product.list()
    }

    boolean saveProduct(Product product) {
        product.save(flush: true)
    }

    boolean deleteProduct(Long id) {
        def product = Product.get(id)
        if (product) {
            product.delete(flush: true)
            return true
        }
        return false
    }

    Product findByName(String name) {
        Product.findByName(name)
    }
}
