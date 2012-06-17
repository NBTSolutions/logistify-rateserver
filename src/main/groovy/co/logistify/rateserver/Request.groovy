package co.logistify.rateserver

import co.logistify.rateserver.adapter.AdapterFactory

class Request {
    String scac
    String fromZip
    String toZip
    Freight[] freight
    String terms
    String date
    String[] accessorials

    String login
    String pass
    String acct

    Request validate() {

        if (AdapterFactory.getMap()[scac] == null)
            throw new Exception("scac code $scac not currently supported")
        if (Util.nullOrEmpty(scac))    
            throw new Exception('scac code cannot be empty')
        if (Util.nullOrEmpty(fromZip))
            throw new Exception('fromZip cannot be empty')
        if (Util.nullOrEmpty(toZip))
            throw new Exception('toZip cannot be empty')
        if (!(Util.isAmericanZip(toZip) || Util.isCanadianZip(toZip)))
            throw new Exception("toZip '$toZip' not recognized as a valid zip")
        if (!(Util.isAmericanZip(fromZip) || Util.isCanadianZip(fromZip)))
            throw new Exception("fromZip '$fromZip' not recognized as a valid zip")

        freight.each { it.validate() }

        accessorials.each { acc ->
            if (!(acc in Util.supportedAccessorials.keySet()))
                throw new Exception("The Accessorial '$acc' is not curently supported")
        }

        return this
    }

}
