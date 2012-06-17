package co.logistify.rateserver.adapter

import co.logistify.rateserver.Request
import co.logistify.rateserver.Rate

public abstract class Adapter {
    static def termsMap
    static def classMap
    static def accessorialMap

    /**
     * Call from servlet to request and parse a rate.
     *
     * @returns the rate
     */
    abstract Rate getRate(Request r)

    String mapTerms(String terms) {
        terms in termsMap.keySet() ? termsMap[terms] : terms
    }

    String mapClass(String cls) {
        cls in classMap.keySet() ? classMap[cls] : cls
    }

    String mapAccessorial(String acc) {
        acc in accessorialMap.keySet() ? accessorialMap[acc] : acc
    }
}
