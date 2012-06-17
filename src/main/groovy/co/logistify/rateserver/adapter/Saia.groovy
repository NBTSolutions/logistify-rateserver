package co.logistify.rateserver.adapter

import co.logistify.rateserver.Util
import co.logistify.rateserver.Rate
import co.logistify.rateserver.Request
import groovy.xml.MarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

public class Saia {
    static {
        setTermsMap([
            'PP' : 'Prepaid',
            'CC' : 'Collect'
        ])
        setClassMap([
            '77'    : '77.5',
            '92'    : '92.5'
        ])
    }
}
