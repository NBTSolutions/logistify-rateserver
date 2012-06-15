package co.logistify.rateserver.adapter

class AdapterFactory {
    private static def map = [
        CNWY : Conway.class,
    ]

    static Adapter getAdapter(String scac) {
        map[scac]?.newInstance()
    }
}
