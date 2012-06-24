
package co.logistify.rateserver.test

import co.logistify.rateserver.adapter.*
import co.logistify.rateserver.dao.*
import co.logistify.rateserver.*
import com.googlecode.objectify.ObjectifyService

class AdapterTest implements Test {
    public void run(def log) {
        log.print('AdapterTest\n')
        testSaia(log)
    }

    void testSaia(def log) {
        log.print('testSaia\n')

        def request = new Request([
            date    : new Date().format('MM/dd/yy'),
            fromZip : '23188',
            toZip   : '24060',
            terms   : 'PP',
            freight : [
                new Freight([ cls: '50', weight: '1000' ]),
                new Freight([ cls: '77', weight: '2000' ])
            ],
            scac    : 'SAIA',
            login   : 'logistify',
            pass    : 'pass123',
            acct    : '0925521'
        ])

        def saia = new Saia()
        def rate = saia.getRate(request)

        assert rate != null
        assert rate.netCharge != null
        log.print(rate.toString())
    }
}

