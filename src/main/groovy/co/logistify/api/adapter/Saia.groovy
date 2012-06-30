package co.logistify.api.adapter

import java.util.logging.Logger
import co.logistify.api.dao.Query
import co.logistify.api.dao.Zip
import co.logistify.api.Util
import co.logistify.api.Rate
import co.logistify.api.Request
import groovy.xml.MarkupBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

public class Saia extends Adapter {
    static final Logger log = Logger.getLogger(Saia.class.getName())

    public Saia() {
        setTermsMap([
            'PP' : 'Prepaid',
            'CC' : 'Collect'
        ])
        setClassMap([
            '77'    : '77.5',
            '92'    : '92.5'
        ])
        /* acc not implemented:
            ArrivalNotice/Appointment
            Marking/Tagging
            LimitedAccessLocationPU
            LimitedAccessLocation
            LiftGateServicePU
            InBond
            BorderCrossing
            TradeShowPickup
            TradeShowDelivery
        */
        setAccessorialMap([
            'SSC' : 'SingleShipment',
            'DLG' : 'LiftGateService',
            'OIP' : 'InsidePickup',
            'DID' : 'InsideDelivery',
            'ELS' : 'ExcessiveLength',
            'ZHM' : 'Hazardous',
            'RSO' : 'ResidentialPickup',
            'RSD' : 'ResidentialDelivery',
            'OSS' : 'Sorting/Segregating',
            'SRT' : 'Sorting/Segregating',
        ])
    }

    @Override
    public Rate getRate(Request r) {
        parseXmlResponse(
            Util.httpGet(
                'http://www.saiasecure.com/webservice/ratequote/xml.aspx',
                ['ProcessXML'],
                [buildRequestBody(r)],
                null, null
            )
        )
    }

    String buildRequestBody(Request r) {
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        Zip from = Query.getZip(r.fromZip)
        Zip to = Query.getZip(r.toZip)

        xml.Create() {
            UserID(r.login)
            Password(r.pass)
            TestMode('Y')
            BillingTerms(mapTerms(r.terms))
            AccountNumber(r.acct)
            Application('ThirdParty')
            OriginCity(from.city)
            OriginState(from.state)
            OriginZipCode(from.zip)
            DestinationCity(to.city)
            DestinationState(to.state)
            DestinationZip(to.zip)
            Details() {
                r.freight.each { final item ->
                    DetailItem() {
                        Weight(item.weight)
                        Class(mapClass(item.cls))
                    }
                }
            }
            Accessorials() {
                r.accessorials.each { final acc ->
                    AccessorialItem() {
                        Code(mapAccessorial(acc))
                    }
                }
            }
        }
        writer.toString()
    }

    Rate parseXmlResponse(String xml) {
        def quote = new XmlParser().parseText(xml)
        new Rate([
            note        : quote.Code?.text() ? quote.Message.text() : 'OK',
            netCharge   : (quote.TotalInvoice.text() ?: null)?.toFloat(),
        ])
    }
}
