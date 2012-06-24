package co.logistify.rateserver.adapter

import co.logistify.rateserver.Request
import co.logistify.rateserver.Rate

public abstract class Adapter {
    def termsMap
    def classMap
    def accessorialMap

    /**
     * Call from servlet to request and parse a rate.
     *
     * @returns the rate
     */
    abstract Rate getRate(Request r)

    String mapTerms(String terms) {
        terms.toUpperCase() in termsMap.keySet() ? termsMap[terms] : terms.toUpperCase()
    }

    String mapClass(String cls) {
        cls.toUpperCase() in classMap.keySet() ? classMap[cls] : cls.toUpperCase()
    }

    String mapAccessorial(String acc) {
        acc.toUpperCase() in accessorialMap.keySet() ? accessorialMap[acc] : acc.toUpperCase()
    }
}
