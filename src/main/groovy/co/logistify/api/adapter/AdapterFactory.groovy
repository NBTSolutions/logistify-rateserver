package co.logistify.api.adapter

class AdapterFactory {
    static def map = [
        CNWY : Conway.class,
        SAIA : Saia.class,
        PITD : PittOhio.class,
    ]

    static Adapter getAdapter(String scac) {
        map[scac]?.newInstance()
    }
}
