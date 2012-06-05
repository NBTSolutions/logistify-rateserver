package co.logistify.rateserver

import co.logistify.rateserver.Rate
import groovy.util.GroovyTestCase

class RateJsonTest extends GroovyTestCase {
    /**
     * More than anything, this was a test to help me learn how JsonBuilder 
     * works.
     */
    void testJsonOutput() {
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
}
