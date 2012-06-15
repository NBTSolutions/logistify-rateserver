package co.logistify.rateserver

class Request {
    String fromZip
    String toZip
    Freight[] freight
    String terms
    String date

    String scac
    String login
    String pass
    String acct

    Request validate() {
        if (Util.nullOrEmpty(scac))    throw new Exception('scac code cannot be empty')
        if (Util.nullOrEmpty(fromZip)) throw new Exception('fromZip cannot be empty')
        if (Util.nullOrEmpty(toZip))   throw new Exception('toZip cannot be empty')

        return this
    }
}
