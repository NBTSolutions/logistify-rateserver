
package co.logistify.api.test

import co.logistify.api.adapter.*
import co.logistify.api.dao.*
import co.logistify.api.*
import com.googlecode.objectify.ObjectifyService

class AdapterTest implements Test {
    public void run(def log) {
        log.print('AdapterTest\n')
        testSaia(log)
    }

    Request createRequest(String scac, String login, String pass, String acct) {
        def request = new Request([
            date    : new Date().format('MM/dd/yy'),
            fromZip : '23188',
            toZip   : '24060',
            terms   : 'PP',
            freight : [
                new Freight([ cls: '50', weight: '1000' ]),
                new Freight([ cls: '77', weight: '2000' ])
            ],
            scac    : scac,
            login   : login,
            pass    : pass,
            acct    : acct
        ])
    }

    void testSaia(def log) {
        log.print('testSaia\n')

        def request = createRequest('SAIA', 'logistify', 'pass123', '0925521')
        def saia = new Saia()
        def rate = saia.getRate(request)

        assert rate != null
        assert rate.netCharge != null
        assert rate.netCharge > 0

        //log.print(rate.toString())
    }

    void testPittOhio() {
        log.print('testPittOhio')
        def request = createRequest('PITD', 'travis@traviswebb.com', 'lkmsdf934', null)

        def pitt = new PittOhio()
        def rate = pitt.getRate(request)

        assert rate != null
        assert rate.note != null
    }
}

