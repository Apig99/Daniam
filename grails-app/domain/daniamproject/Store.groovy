package daniamproject

class Store {

    String name
    String address
    static hasMany = [products: StoreProduct]

    static constraints = {
        name blank: false
        address blank: false
    }
}
