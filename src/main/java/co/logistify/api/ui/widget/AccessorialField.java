package co.logistify.api.ui.widget;

import java.util.*;
import co.logistify.api.shared.*;
import co.logistify.api.ui.*;
import co.logistify.api.ui.view.*;
import com.google.gwt.editor.client.*;
import com.google.gwt.event.dom.client.*;
import com.github.gwtbootstrap.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.uibinder.client.*;

public class AccessorialField extends Composite {

    @UiField HelpBlock accPrompt;
    @UiField ListBox accField;
    @UiField Button addBtn;

    public AccessorialField() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("addBtn")
    void onAddButtonClick(ClickEvent e) {
        accPrompt.setVisible(false);
    }

    public String getValue() {
        return accField.getValue();
    }

    public void bind(final RateForm form) {
        accField.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent e) {
                form.updateRequestJson();
            }
        });
        addBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent e) {
                form.addAccessorial();
            }
        });
    }

    @UiTemplate("AccessorialField.ui.xml")
    interface AccessorialFieldUiBinder extends UiBinder<Controls, AccessorialField> { }
    private static AccessorialFieldUiBinder uiBinder = GWT.create(AccessorialFieldUiBinder.class);
}
