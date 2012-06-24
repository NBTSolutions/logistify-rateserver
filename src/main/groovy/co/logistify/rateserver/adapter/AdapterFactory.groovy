package co.logistify.rateserver.adapter

class AdapterFactory {
    static def map = [
        CNWY : Conway.class,
        SAIA : Saia.class,
    ]

    static Adapter getAdapter(String scac) {
        map[scac]?.newInstance()
    }
}
