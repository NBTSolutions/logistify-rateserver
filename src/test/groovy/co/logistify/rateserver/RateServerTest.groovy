package co.logistify.rateserver

import groovy.util.GroovyTestCase

class RateServerTest extends GroovyTestCase {

    void testValidateRequest() {
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

        def rateServer = new RateServer()

        Request r = rateServer.validateAndConvertRequest(requestStr)

        assert r != null
    }

    void testNullOrEmpty() {
        def a = [ 1, 2, 3]
        def b = [ ]
        def c = null

        assert !RateServer.nullOrEmpty(a)
        assert RateServer.nullOrEmpty(b)
        assert RateServer.nullOrEmpty(c)

        def d = ''
        def e = null
        def f = 'f'

        assert RateServer.nullOrEmpty(d)
        assert RateServer.nullOrEmpty(e)
        assert !RateServer.nullOrEmpty(f)
    }
}

