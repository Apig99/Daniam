//package daniamproject
//
//import grails.gorm.transactions.Transactional
//
//@Transactional
//class ProductService {
//
//    // Method to add a product to a repository
//    void addProductToRepository(Long repositoryId, Long productId, int quantity) {
//        Repository repository = Repository.get(repositoryId)
//        Product product = Product.get(productId)
//        if (repository && product) {
//            repository.addProduct(product, quantity)
//        } else {
//            throw new IllegalStateException("Repository or Product not found")
//        }
//    }
//    // Method to remove a product from a repository
//    void removeProductFromRepository(Long repositoryId, Long productId, int quantity) {
//        Repository repository = Repository.get(repositoryId)
//        Product product = Product.get(productId)
//        if (repository && product) {
//            repository.removeProduct(product, quantity)
//        } else {
//            throw new IllegalStateException("Repository or Product not found")
//        }
//    }
//
//    // Method to ship a product from repository to store
//    void shipProductToStore(Long repositoryId, Long productId, int quantity, Long storeId) {
//        Repository repository = Repository.get(repositoryId)
//        Store store = Store.get(storeId)
//        Product product = Product.get(productId)
//        if (repository && store && product) {
//            repository.shipProductToStore(product, quantity, store)
//        } else {
//            throw new IllegalStateException("Repository, Store, or Product not found")
//        }
//    }
//
//    // Method to return expired products from store to repository
//    void returnExpiredProducts(Long storeId, Long repositoryId) {
//        Store store = Store.get(storeId)
//        Repository repository = Repository.get(repositoryId)
//        if (store && repository) {
//            store.returnExpiredProducts(repository)
//        } else {
//            throw new IllegalStateException("Store or Repository not found")
//        }
//    }
//}
