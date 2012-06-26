package co.logistify.rateserver.adapter

import java.util.logging.Logger
import java.net.URLEncoder
import co.logistify.rateserver.dao.Query
import co.logistify.rateserver.dao.Zip
import co.logistify.rateserver.Util
import co.logistify.rateserver.Rate
import co.logistify.rateserver.Request
import groovy.xml.MarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

public class PittOhio extends Adapter {
    static final Logger log = Logger.getLogger(PittOhio.class.getName())

    public PittOhio() {
        setTermsMap([
            'PP' : 'P',
            'CC' : 'I',
            'TP' : '3'
        ])
        setClassMap(new HashMap())
        setAccessorialMap([
            'RSD' : 'Acc_RDEL',
            'RSO' : 'Acc_REP',
            'DLG' : 'Acc_LGD',
            'CSD' : 'Acc_CSD',
            'DID' : 'Acc_IDL',
            'DNC' : 'Acc_MNC',
            'ZHM' : 'Acc_HAZ'
        ])
    }

    @Override
    public Rate getRate(Request r) {
        parseXmlResponse(Util.httpGet(buildRequestString(r)))
    }

    String buildRequestString(Request r) {
        Zip from = Query.getZip(r.fromZip)
        Zip to = Query.getZip(r.toZip)

        def base = 'http://works.pittohio.com/mypittohio/b2bratecalc.asp?'
        def argv = [
            'UID'       : r.login,
            'PWD'       : r.pass,
            'ShipCity'  : from.city,
            'ShipState' : from.state,
            'ShipZIP'   : from.zip,
            'ConsCity'  : to.city,
            'ConsState' : to.state,
            'ConsZip'   : to.zip,
            'Terms'     : mapTerms(r.terms)
        ]
        r.acc.each { acc ->
            argv[mapAccessorial(acc)] = 'Y'
        }
        r.freight.eachWithIndex { f, _i, i = _i + 1 ->
            argv["Class$i"] = f.cls
            argv["Weight$i"] = f.weight
        }
        base + argv.collect { k, _v, v = URLEncoder.encode(_v, 'UTF-8') ->
            "$k=$v"
        }.join('&')
    }

    Rate parseXmlResponse(String xml) {
        def quote = new XmlParser().parseText(xml)
        new Rate([
            note        : quote.NUMERRORS.text() != '0' ? quote.ERROR.ERRORMESSAGE.text() : 'OK',
            netCharge   : (quote.CHARGE.text() ?: null)?.toFloat(),
        ])
    }
}
