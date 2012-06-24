package co.logistify.rateserver.dao

import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.Key

abstract class Query {
    public static Zip getZip(String zipCode) {
        def ofy = ObjectifyService.begin()
        return ofy.get(Zip.class, zipCode)
    }
}
