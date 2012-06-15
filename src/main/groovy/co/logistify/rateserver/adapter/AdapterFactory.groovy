package co.logistify.rateserver.adapter

class AdapterFactory {
    static def map = [
        'CNWY'  : Conway.class
    ]

    static Adapter getAdapter(String scac) {
        return map[scac].newInstance()
    }
}
