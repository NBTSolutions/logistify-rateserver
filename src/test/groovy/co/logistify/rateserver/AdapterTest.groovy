package co.logistify.api

import co.logistify.api.adapter.*
import groovy.util.GroovyTestCase

class AdapterTest extends GroovyTestCase {

    void testAdapterFactory() {
        def cnwy = AdapterFactory.getAdapter('CNWY')

        assert cnwy != null
    }

}

