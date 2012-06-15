package co.logistify.rateserver

import co.logistify.rateserver.adapter.Conway
import groovy.util.GroovyTestCase

class ConwayTest extends GroovyTestCase {

    void testRequest() {
        def request = new Request([
            date    : new Date().format('MM/dd/yy'),
            fromZip : '23188',
            toZip   : '24060',
            terms   : 'PP',
            freight : [
                new Freight([ cls: '50', weight: '1000' ]),
                new Freight([ cls: '77', weight: '2000' ])
            ],
            scac    : 'CNWY',
            login   : 'logistify1',
            pass    : 'pass123'
        ])

        //def rate = new Conway().requestRate(request)

        //assert rate == null
    }

    void testXmlParse() {
        String xml = '''
            <RateQuote xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://www.con-way.com/XML/RateQuote.xsd" schemaVersion="1.0">
                <OriginZip country="US">97006</OriginZip>
                <DestinationZip country="US">33179</DestinationZip>
                <ChargeCode>P</ChargeCode>
                <Item>
                    <CmdtyClass>775</CmdtyClass>
                    <Weight unit="lbs">667</Weight>
                    <Rate currency="USD">316.02</Rate>
                    <Charge currency="USD">2107.85</Charge>
                </Item>
                <Item>
                    <CmdtyClass>100</CmdtyClass>
                    <Weight unit="lbs">555</Weight>
                    <Rate currency="USD">407.77</Rate>
                    <Charge currency="USD">2263.12</Charge>
                </Item>
                <ItemTotal>
                    <TotalWeight unit="lbs">1222</TotalWeight>
                    <TotalCharge currency="USD">4370.97</TotalCharge>
                </ItemTotal>
                <AccessorialCharges>
                    <OtherAccessorialCharges code="GUR" name="CON-WAY GUARANTEED!">437.10</OtherAccessorialCharges>
                    <OtherAccessorialCharges code="DNC" name="DEST NOTIFICATION">37.00</OtherAccessorialCharges>
                    <FuelSurcharge code="FSC" name="FUEL SURCHARGE 29.30%" rate="29.30">640.35</FuelSurcharge>
                    <TotalAccessorialCharges currency="USD">1114.45</TotalAccessorialCharges>
                </AccessorialCharges>
                <Discount rate="50.0">2185.49</Discount>
                <NetCharge currency="USD">3299.93</NetCharge>
                <TransitTime>5</TransitTime>
                <EffectiveDate>06/14/12</EffectiveDate>
                <Disclaimer>
                    This Con-way shipping price quote is based on information entered into this query including pickup and delivery locations, commodities, weights, appropriate discounts, and accessorial services. Routing by a different freight company may result in different charges. This quote may not include additional accessorial service charges (e.g. Inside Delivery, Liftgate Truck, etc.) that could be required at delivery. Con-way authorizes you to use Con-way rate quote and tracking systems solely for the purpose of conducting business with Con-way and for no other purpose. Your use shall be limited to only those shipments in which you are the shipper, consignee or authorized agent. Any other use of the Con-way systems and information is strictly prohibited. (c) 2012 Con-way. All Rights Reserved. Con-way's logos, trademarks and company names may not be used without prior permission. Email us at info@con-way.com. Terms and Conditions at www.con-way.com/Help/terms.html. Privacy Policy at www.con-way.com/Help/privacy.html?dest=privacy.
                </Disclaimer>
                <OriginState>OR</OriginState>
                <DestinationState>FL</DestinationState>
                <EstDeliverDate>06/21/12</EstDeliverDate>
            </RateQuote>
        '''

        def rate = new Conway().parseXmlResponse(xml)

        assert rate.netCharge.toFloat() == 3299.93.toFloat()
    }
}

