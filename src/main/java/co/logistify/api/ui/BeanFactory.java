package co.logistify.api.ui;

import com.google.web.bindery.autobean.shared.*;
import co.logistify.api.shared.*;

public interface BeanFactory extends AutoBeanFactory {
    AutoBean<FreightItem> freightItem();
}

