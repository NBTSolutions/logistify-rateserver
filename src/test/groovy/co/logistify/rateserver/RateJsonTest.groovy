package co.logistify.rateserver

import groovy.util.GroovyTestCase

class RateJsonTest extends GroovyTestCase {
    /**
     * More than anything, this was a test to help me learn how JsonBuilder 
     * works.
     */
    void testGroovyToJson() {
        def rate = new Rate([
            netCharge   : 50,
            grossCharge : 100,
            note        : 'hello',
            lineItems   : [ new LineItem([
                description : 'nothing',
                grossCharge : 10,
                netCharge   : 20
            ]) ]
        ])
        assert new groovy.json.JsonBuilder(rate).toString() == '{"note":"hello","lineItems":[{"description":"nothing","netCharge":20.0,"grossCharge":10.0}],"netCharge":50.0,"grossCharge":100.0}'
    }

    void testJsonToGroovy() {
        def request = new Request([
            fromZip : '23188',
            toZip   : '24060',
            classes : [
                '50', '75'
            ],
            weights : [
                1000, 2000
            ],
            scac    : 'CNWY'
        ])
        // convert from object to json string
        String requestStr = new groovy.json.JsonBuilder(request).toString()

        // convert string to Request object
        Request r = new groovy.json.JsonSlurper().parseText(requestStr)

        assert r.fromZip == '23188'
        assert r.toZip == '24060'
        assert r.classes == [ '50', '75' ]
        assert r.weights == [ 1000, 2000 ]
        assert r.login == null
        assert r.pass == null
        assert r.acct == null
        assert r.scac == 'CNWY'

        assert new groovy.json.JsonBuilder(r).toString() == requestStr
    }
}
