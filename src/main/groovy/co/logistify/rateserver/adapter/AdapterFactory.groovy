package co.logistify.rateserver.adapter

class AdapterFactory {
    static final def map = [:]

    public Adapter getAdapter(String scac) {
        return map[scac].newInstance();
    }
}
