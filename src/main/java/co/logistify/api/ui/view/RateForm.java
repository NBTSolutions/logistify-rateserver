package co.logistify.api.ui.view;

import co.logistify.api.shared.*;
import com.google.gwt.editor.client.*;
import javax.validation.*;
import com.github.gwtbootstrap.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;

/**
 * @author Travis Webb (travis@traviswebb.com)
 *
 *  Form that builds a rate request.
 */
public class RateForm extends Composite implements Editor<RateRequest> {

    private RateRequest request;
    
    private ValidatorFactory factory;

    public RateForm() {
        factory = Validation.buildDefaultValidatorFactory();
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        driver.edit(new RateRequest());
    }

    public RateRequest getObject() {
        return driver.flush();
    }

    interface Driver extends SimpleBeanEditorDriver<RateRequest, RateForm> { }
    Driver driver = GWT.create(Driver.class);

    @UiTemplate("RateForm.ui.xml")
    interface RateFormUiBinder extends UiBinder<Widget, RateForm> { }
    private static RateFormUiBinder uiBinder = GWT.create(RateFormUiBinder.class);
}
