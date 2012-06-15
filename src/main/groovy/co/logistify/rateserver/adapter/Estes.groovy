package co.logistify.rateserver.adapter

import co.logistify.rateserver.Rate
import co.logistify.rateserver.Request

public class Estes implements Adapter {

    @Override
    public Rate getRate(Request r) {
        String uri = buildUri(r)

        return null
    }

    Rate parseXml(Request r) {
        return null
    }

    String buildUri(Request r) {
        return null
    }

    static {
        AdapterFactory.map['EXLA'] = Estes.class
    }
}
