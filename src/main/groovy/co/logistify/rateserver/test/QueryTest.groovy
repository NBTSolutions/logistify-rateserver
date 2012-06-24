package co.logistify.rateserver.test

import co.logistify.rateserver.dao.*
import co.logistify.rateserver.*
import com.googlecode.objectify.ObjectifyService

class QueryTest implements Test {
    public void run(def log) {
        log.print('QueryTest')
        testZipCode(log)
    }

    void testZipCode(def log) {
        log.print('testZipCode\n')

        def zip = Query.getZip('23188')
        assert zip != null
        assert zip.city == 'WILLIAMSBURG'
    }
}

