package co.logistify.api

import groovy.util.GroovyTestCase

class RateServerTest extends GroovyTestCase {

    void testValidateRequest() {
        def request = new Request([
            fromZip : '23188',
            toZip   : '24060',
            freight : [
                new Freight([ cls: '50', weight: '1000' ]),
                new Freight([ cls: '77', weight: '2000' ])
            ],
            scac    : 'CNWY'
        ])
        // convert from object to json string
        String requestStr = new groovy.json.JsonBuilder(request).toString()

        //def rateServer = new Servlet()
        //Request r = rateServer.validateAndConvertRequest(requestStr)

        //assert r != null
    }

    void testNullOrEmpty() {
        def a = [ 1, 2, 3]
        def b = [ ]
        def c = null

        assert !Util.nullOrEmpty(a)
        assert Util.nullOrEmpty(b)
        assert Util.nullOrEmpty(c)

        def d = ''
        def e = null
        def f = 'f'

        assert Util.nullOrEmpty(d)
        assert Util.nullOrEmpty(e)
        assert !Util.nullOrEmpty(f)
    }
}

