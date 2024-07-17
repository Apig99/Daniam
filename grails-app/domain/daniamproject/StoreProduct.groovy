package daniamproject

class StoreProduct {

    Product product
    int quantity

    static belongsTo = [store: Store]

    static constraints = {
        quantity min: 0
    }
}
