package daniamproject

class RepositoryProduct {
    Product product
    int quantity

    static belongsTo = [repository: Repository]

    static constraints = {
        product nullable: false
        quantity min: 0
    }
}
