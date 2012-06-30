package co.logistify.api.ui.view;

import com.github.gwtbootstrap.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;

public class AppHeader extends Composite {

    public AppHeader() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiTemplate("AppHeader.ui.xml")
    interface AppHeaderUiBinder extends UiBinder<Navbar, AppHeader> { }
    static AppHeaderUiBinder uiBinder = GWT.create(AppHeaderUiBinder.class);
}
