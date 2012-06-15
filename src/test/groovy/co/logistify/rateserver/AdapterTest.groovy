package co.logistify.rateserver

import co.logistify.rateserver.adapter.*
import groovy.util.GroovyTestCase

class AdapterTest extends GroovyTestCase {

    void testAdapterFactory() {
        def cnwy = AdapterFactory.getAdapter('CNWY')

        assert cnwy != null
    }

}

