package daniamproject

class Repository {
    String name
    static hasMany = [products: RepositoryProduct]

    static constraints = {
        name blank: false
    }
}
