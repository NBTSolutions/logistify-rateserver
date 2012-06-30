package co.logistify.api

import groovy.util.GroovyTestCase

/**
 * Tests to help me better understand some new Groovy concepts.
 */
class UtilTest extends GroovyTestCase {

    /**
     * Test parallel iteration using transpose()
     */
    void testListTranspose() {
        Integer[] list1 = [1, 2, 3]
        Integer[] list2 = [4, 5, 6]

        def result = [list1, list2].transpose().collect { it }
        assert result == [[1, 4], [2, 5], [3, 6]]
    }

    void testXmlListTranspose() {
        Integer[] list1 = [1, 2, 3]
        Integer[] list2 = [4, 5, 6]

        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)

        xml.items() {
            [list1, list2].transpose().each { final value ->
                item() {
                    a(value[0])
                    b(value[1])
                }
            }
        }

        assert writer.toString() != null
    }
}

