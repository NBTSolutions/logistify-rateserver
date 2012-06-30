package co.logistify.api

import groovy.util.GroovyTestCase

class RateJsonTest extends GroovyTestCase {
    /**
     * More than anything, this was a test to help me learn how JsonBuilder 
     * works.
     */
    void testGroovyToJson() {
        def rate = new Rate([
            netCharge   : 50,
            note        : 'OK'
        ])
        assert new groovy.json.JsonBuilder(rate).toString() == '{"note":"OK","netCharge":50.0}'
    }

    void testJsonToGroovy() {
        def request = new Request([
            date    : new Date().format('MM/dd/yy'),
            fromZip : '23188',
            toZip   : '24060',
            terms   : 'PP',
            freight : [
                new Freight([ cls: '50', weight: '1000' ]),
                new Freight([ cls: '77', weight: '2000' ])
            ],
            scac    : 'CNWY',
            login   : 'logistify1',
            pass    : 'pass123'
        ])
        // convert from object to json string
        String requestStr = new groovy.json.JsonBuilder(request).toString()

        // convert string to Request object
        Request r = new groovy.json.JsonSlurper().parseText(requestStr)

        assert r.fromZip == '23188'
        assert r.toZip == '24060'
        assert r.scac == 'CNWY'

        assert new groovy.json.JsonBuilder(r).toString() == requestStr
    }
}
