package co.logistify.api.ui;

import com.google.web.bindery.autobean.shared.*;
import com.google.gwt.core.client.GWT;
import co.logistify.api.shared.*;

public abstract class BeanFactoryProvider {
    static BeanFactory fac = GWT.create(BeanFactoryProvider.BeanFactory.class);

    public interface BeanFactory extends AutoBeanFactory {
        AutoBean<FreightItem> freightItem();
        AutoBean<RateRequest> rateRequest();
    }

    public static BeanFactoryProvider.BeanFactory get() {
        return fac;
    }
}


