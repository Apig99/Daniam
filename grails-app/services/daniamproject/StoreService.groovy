package daniamproject

import grails.gorm.transactions.Transactional

@Transactional
class StoreService {

    Store getStore(Long id) {
        Store.get(id)
    }

    List<Store> listStores() {
        Store.list()
    }

    boolean saveStore(Store store) {
        store.save(flush: true)
    }

    boolean deleteStore(Long id) {
        def store = Store.get(id)
        if (store) {
            store.delete(flush: true)
            return true
        }
        return false
    }

    Store findByAddress(String address) {
        Store.findByAddress(address)
    }

    void sellProductFromStore(Store store, Product product, int quantity) {
        def storeProduct = store.products.find { it.product.id == product.id }
        if (storeProduct && storeProduct.quantity >= quantity) {
            storeProduct.quantity -= quantity
            if (storeProduct.quantity == 0) {
                store.removeFromProducts(storeProduct)
                storeProduct.delete(flush: true)
            } else {
                storeProduct.save(flush: true)
            }
            store.save(flush: true)
        }
    }

    void returnProductToRepository(Store store, Repository repository, Product product) {
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

    void addProductFromRepositoryToStore(Store store, Repository repository, Product product, int quantity) {
        def repositoryProduct = repository.products.find { it.product.id == product.id }
        if (repositoryProduct && repositoryProduct.quantity >= quantity) {
            def storeProduct = store.products.find { it.product.id == product.id }
            if (storeProduct) {
                storeProduct.quantity += quantity
                storeProduct.save(flush: true)
            } else {
                def newStoreProduct = new StoreProduct(product: product, quantity: quantity)
                store.addToProducts(newStoreProduct)
            }
            repositoryProduct.quantity -= quantity
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

    void checkExpiredProducts() {
        def stores = Store.list()
        def currentDate = new Date()
        stores.each { store ->
            store.products.each { storeProduct ->
                if (storeProduct.product.expirationDate && storeProduct.product.expirationDate.before(currentDate)) {
                    def repository = Repository.list().find { it.products.contains(storeProduct.product) }
                    if (repository) {
                        returnProductToRepository(store, repository, storeProduct.product)
                    }
                }
            }
        }
    }
}
