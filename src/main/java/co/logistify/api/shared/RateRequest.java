package co.logistify.api.shared;

import java.util.*;

public interface RateRequest {
    String getScac();
    String getFromZip();
    String getToZip();
    List<FreightItem> getFreight();
    String getTerms();
    String getDate();
    List<String> getAccessorials();
    String getLogin();
    String getPass();
    String getAcct();

    void setScac(String scac);
    void setFromZip(String fromZip);
    void setToZip(String toZip);
    void setFreight(List<FreightItem> freight);
    void setTerms(String terms);
    void setDate(String date);
    void setAccessorials(List<String> accessorials);
    void setLogin(String login);
    void setPass(String pass);
    void setAcct(String acct);
}
