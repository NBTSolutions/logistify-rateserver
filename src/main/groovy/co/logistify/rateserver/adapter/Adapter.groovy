package co.logistify.rateserver.adapter

import co.logistify.rateserver.Request
import co.logistify.rateserver.Rate

public interface Adapter {
    Rate getRate(Request r)
}
