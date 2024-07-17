package daniamproject

class Product {
    String name
    BigDecimal price
    Date productionDate
    Date expirationDate

    static constraints = {
        name nullable: false, blank: false
        price nullable: false, validator: { val, obj ->
            if (val == null)
                return ['nullable']
            else if (val < 0.0)
                return ['min', 0.0]

        }
        productionDate nullable: false
        expirationDate nullable: true, validator: { val, obj ->
            if (val && obj.productionDate && val.before(obj.productionDate)) {
                return ['invalidExpirationDate']
            }
        }
    }
}
