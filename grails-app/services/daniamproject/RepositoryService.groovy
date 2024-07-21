package daniamproject

import grails.gorm.transactions.Transactional

@Transactional
class RepositoryService {

    Repository getRepository(Long id) {
        Repository.get(id)
    }

    List<Repository> listRepositories() {
        Repository.list()
    }

    boolean saveRepository(Repository repository) {
        repository.save(flush: true)
    }

    boolean deleteRepository(Long id) {
        def repository = Repository.get(id)
        if (repository) {
            repository.delete(flush: true)
            return true
        }
        return false
    }

    Repository findByName(String name) {
        Repository.findByName(name)
    }

    void addProductToRepository(Repository repository, Product product, int quantity) {
        def existingProduct = repository.products.find { it.product.id == product.id }
        if (existingProduct) {
            existingProduct.quantity += quantity
            existingProduct.save(flush: true)
        } else {
            def repositoryProduct = new RepositoryProduct(product: product, quantity: quantity)
            repository.addToProducts(repositoryProduct)
        }
        repository.save(flush: true)
    }

    void removeProductFromRepository(Repository repository, Product product) {
        def repositoryProduct = repository.products.find { it.product.id == product.id }
        if (repositoryProduct) {
            repository.removeFromProducts(repositoryProduct)
            repositoryProduct.delete(flush: true)
        }
        repository.save(flush: true)
    }

    void moveProductBetweenRepositories(Repository sourceRepository, Repository targetRepository, Product product, int quantity) {
        def sourceProduct = sourceRepository.products.find { it.product.id == product.id }
        if (sourceProduct && sourceProduct.quantity >= quantity) {
            def targetProduct = targetRepository.products.find { it.product.id == product.id }
            if (targetProduct) {
                targetProduct.quantity += quantity
                targetProduct.save(flush: true)
            } else {
                def newProduct = new RepositoryProduct(product: product, quantity: quantity)
                targetRepository.addToProducts(newProduct)
            }
            sourceProduct.quantity -= quantity
            if (sourceProduct.quantity == 0) {
                sourceRepository.removeFromProducts(sourceProduct)
                sourceProduct.delete(flush: true)
            } else {
                sourceProduct.save(flush: true)
            }
            sourceRepository.save(flush: true)
            targetRepository.save(flush: true)
        }
    }
}
