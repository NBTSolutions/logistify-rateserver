package co.logistify.rateserver.adapter

import co.logistify.rateserver.Util
import co.logistify.rateserver.Rate
import co.logistify.rateserver.Request
import groovy.xml.MarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

public class Conway implements Adapter {

    @Override
    public Rate getRate(Request r) {
        return parseXmlResponse(requestRate(r))
    }

    String requestRate(Request r) {
        Util.httpPost(
            'https://www.con-way.com/XMLj/X-Rate',
            'RateRequest',
            buildRequestBody(r),
            r.login,
            r.pass
        )
    }

    Rate parseXmlResponse(String xml) {
        def quote = new XmlParser().parseText(xml)
        new Rate([
            note        : quote.Error?.text() ?: "OK",
            netCharge   : (quote.NetCharge.text() ?: null)?.toFloat(),
        ])
    }

    String buildRequestBody(Request r) {

        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.RateRequest() {
            OriginZip(country : getCountryCode(r.fromZip), r.fromZip)
            DestinationZip(country : getCountryCode(r.toZip), r.toZip)
            if (r.acct != null) CustNmbr(r.acct)
            ChargeCode(getTerms(r.terms))
            EffectiveDate(r.date)

            r.freight.each { final item ->
                Item() {
                    CmdtyClass(convertClass(item.cls))
                    Weight(unit: 'lbs', item.weight)
                }
            }
        }

        writer.toString()
    }

    String getTerms(String terms) {
        def map = [
            'PP' : 'P',
            'CC' : 'C',
            'TP' : null
        ]
        terms in map.keySet() ? map[terms] : terms
    }

    String convertClass(String cls) {
        def map = [
            '77.5'  : '775',
            '77'    : '775'
        ]
        cls in map.keySet() ? map[cls] : cls
    }

    static String getCountryCode(String zip) {
        if (zip =~ /^[ABCEGHJKLMNPRSTVXY]{1}\d{1}[A-Z]{1} *\d{1}[A-Z]{1}\d{1}$/) return 'CN'
        if (zip =~ /^\d{5}(-\d{4})?$/) return 'US'
    }
}

