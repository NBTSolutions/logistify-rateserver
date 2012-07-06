package co.logistify.api

import groovy.util.GroovyTestCase

/**
 * Test the methods in Util
 */
class UtilTest extends GroovyTestCase {

    /**
     * Tests:
     *  Util.isCanadianZip
     *  Util.isAmericanZip
     */
    void testZipCountryDetection() {
        String usZip = '23188'
        String caZip = 'A0E2Z0'
        String noZip = '3jxij59'

        assert Util.isAmericanZip(usZip)
        assert !Util.isAmericanZip(caZip)
        assert !Util.isAmericanZip(noZip)

        assert Util.isCanadianZip(caZip)
        assert !Util.isCanadianZip(usZip)
        assert !Util.isCanadianZip(noZip)
    }

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

