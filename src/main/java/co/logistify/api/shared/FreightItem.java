package co.logistify.api.shared;

public interface FreightItem {
    String getCls();
    int getWeight();

    void setCls(String cls);
    void setWeight(int weight);
}
