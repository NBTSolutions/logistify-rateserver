package co.logistify.api

import co.logistify.api.adapter.Conway
import groovy.util.GroovyTestCase

class SaiaTest extends GroovyTestCase {

    void testRequest() {
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
            login   : 'logistify1',
            pass    : 'pass123'
        ])
    }
}

