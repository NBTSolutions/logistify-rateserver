package co.logistify.api.ui.widget;

import java.util.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.text.shared.*;

public class EditorListBox<T> extends ValueListBox<T> {

    public EditorListBox(Renderer<T> renderer) {
        super(renderer);
    }

    public void setAcceptableValuesClean(Collection<T> values) {
        ListBox l = (ListBox)getWidget();
        super.setAcceptableValues(values);
        l.removeItem(l.getItemCount() - 1);
    }

}
