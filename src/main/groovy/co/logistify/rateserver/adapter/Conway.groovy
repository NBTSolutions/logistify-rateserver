package co.logistify.rateserver.adapter

import java.util.logging.Logger
import co.logistify.rateserver.Util
import co.logistify.rateserver.Rate
import co.logistify.rateserver.Request
import groovy.xml.MarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

public class Conway extends Adapter {
    static final Logger log = Logger.getLogger(Conway.class.getName())

    public Conway() {
        setTermsMap([
            'PP' : 'P',
            'CC' : 'C',
        ])
        setClassMap([
            '77.5'  : '775',
            '77'    : '775'
        ])
        // special case since accessorial mappings are based on Conway's
        setAccessorialMap(new HashMap())
    }

    @Override
    public Rate getRate(Request r) {
        parseXmlResponse(requestRate(r))
    }

    String requestRate(Request r) {
        Util.httpPost(
            'https://www.con-way.com/XMLj/X-Rate',
            ['RateRequest'],
            [buildRequestBody(r)],
            r.login,
            r.pass
        )
    }

    Rate parseXmlResponse(String xml) {
        def quote = new XmlParser().parseText(xml)
        new Rate([
            note        : quote.Error?.text() ?: 'OK',
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
            ChargeCode(mapTerms(r.terms))
            EffectiveDate(r.date)

            r.freight.each { final item ->
                Item() {
                    CmdtyClass(mapClass(item.cls))
                    Weight(unit: 'lbs', item.weight)
                }
            }
            r.accessorials.each { final acc -> 
                Accessorial(mapAccessorial(acc))
            }
        }
        writer.toString()
    }

    static String getCountryCode(String zip) {
        if (Util.isAmericanZip(zip)) 'US'
        else if (Util.isCanadianZip(zip)) 'CN'
        else throw new Exception("error parsing zip code $zip")
    }
}

