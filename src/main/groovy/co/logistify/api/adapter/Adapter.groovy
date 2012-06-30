package co.logistify.api.adapter

import co.logistify.api.Request
import co.logistify.api.Rate

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

    final String mapTerms(String terms) {
        terms.toUpperCase() in termsMap.keySet() ? termsMap[terms] : terms.toUpperCase()
    }

    final String mapClass(String cls) {
        cls.toUpperCase() in classMap.keySet() ? classMap[cls] : cls.toUpperCase()
    }

    final String mapAccessorial(String acc) {
        acc.toUpperCase() in accessorialMap.keySet() ? accessorialMap[acc] : acc.toUpperCase()
    }
}
