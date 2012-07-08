package co.logistify.api.ui.widget;

import java.util.*;
import co.logistify.api.shared.*;
import co.logistify.api.ui.view.*;
import co.logistify.api.ui.*;
import com.google.gwt.text.shared.*;
import com.google.gwt.editor.client.*;
import com.google.gwt.event.dom.client.*;
import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.base.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.text.shared.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.event.logical.shared.*;

public class LineItemForm extends Composite implements Editor<FreightItem> {

    private FreightItem freight;

    @UiField @Ignore HelpBlock freightPrompt;
    @UiField @Ignore HelpInline freightCaption;
    @UiField IntegerBox weight;
    @UiField ListBox cls;
    @UiField @Ignore Button addBtn;

    public LineItemForm() {
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        driver.edit(BeanFactoryProvider.get().freightItem().as());
    }

    public FreightItem getFreightItem() {
        return driver.flush();
    }

    @UiHandler("addBtn")
    void onAddClick(ClickEvent e) {
        freightPrompt.setVisible(false);
    }
    @Ignore
    public Integer getWeight() {
        return weight.getValue();
    }
    @Ignore
    public String getCls() {
        return cls.getValue();
    }
    @Ignore
    public HelpBlock getFreightPrompt() {
        return freightPrompt;
    }
    @Ignore
    public HelpInline getFreightCaption() {
        return freightCaption;
    }
    @Ignore
    public Button getAddButton() {
        return addBtn;
    }

    public void bind (final RateForm form) {
        addBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                form.addLineItem();
            }
        });
        cls.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent e) {
                form.updateRequestJson();
                weight.setFocus(true);
            }
        });
        weight.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent e) {
                form.updateRequestJson();
            }
        });

    }

    @UiTemplate("LineItemForm.ui.xml")
    interface LineItemFormUiBinder extends UiBinder<Form, LineItemForm> { }
    private static LineItemFormUiBinder uiBinder = GWT.create(LineItemFormUiBinder.class);

    interface Driver extends SimpleBeanEditorDriver<FreightItem, LineItemForm> { }
    Driver driver = GWT.create(Driver.class);
}
