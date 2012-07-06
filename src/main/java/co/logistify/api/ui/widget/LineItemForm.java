package co.logistify.api.ui.widget;

import co.logistify.api.shared.*;
import co.logistify.api.ui.*;
import com.google.gwt.editor.client.*;
import com.google.gwt.event.dom.client.*;
import com.github.gwtbootstrap.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.uibinder.client.*;

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
    public IntegerBox getWeightField() {
        return weight;
    }
    @Ignore
    public ListBox getClassField() {
        return cls;
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

    @UiTemplate("LineItemForm.ui.xml")
    interface LineItemFormUiBinder extends UiBinder<Form, LineItemForm> { }
    private static LineItemFormUiBinder uiBinder = GWT.create(LineItemFormUiBinder.class);

    interface Driver extends SimpleBeanEditorDriver<FreightItem, LineItemForm> { }
    Driver driver = GWT.create(Driver.class);
}
