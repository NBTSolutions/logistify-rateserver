package co.logistify.api.shared;

public interface FreightItem {
    String getCls();
    Integer getWeight();

    void setCls(String cls);
    void setWeight(Integer weight);
}
