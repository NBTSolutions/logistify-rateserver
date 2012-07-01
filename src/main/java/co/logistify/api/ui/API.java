package co.logistify.api.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import co.logistify.api.ui.view.*;

public class API implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new AppHeader());
        RootPanel.get().add(new RateForm());
    }
}
