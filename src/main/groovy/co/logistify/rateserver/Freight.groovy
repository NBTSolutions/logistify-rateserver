package co.logistify.rateserver

class Freight {
    String cls
    String weight

    Freight validate() {
        if (!(cls in Util.supportedClasses))
            throw new Exception("Freight class $cls not supported")
        if (weight.toInteger() < 0)
            throw new Exception('Weight must be positive')

        return this
    }
}
