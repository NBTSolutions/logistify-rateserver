package co.logistify.api.ui.view;

import java.util.*;
import co.logistify.api.shared.*;
import co.logistify.api.ui.*;
import co.logistify.api.ui.widget.*;
import com.google.gwt.text.shared.*;
import com.google.web.bindery.autobean.shared.*;
import com.google.gwt.editor.client.*;
import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.*;
import com.github.gwtbootstrap.datepicker.client.ui.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.*;

/**
 * @author Travis Webb (travis@traviswebb.com)
 *
 *  Form that builds a rate request.
 */
public class RateForm extends Composite implements Editor<RateRequest> {

    @UiField @Ignore ControlGroup carrierControl;
    @UiField(provided=true) EditorListBox<String> scac = 
        new EditorListBox<String>(new AbstractRenderer<String>() {
            @Override
            public String render(String s) {
                if (s == null) return "Choose a Carrier";

                if ("CNWY".equals(s)) return "Conway";
                if ("PITD".equals(s)) return "Pitt Ohio";
                if ("SAIA".equals(s)) return "Saia";

                return null;
            }
        });
    @UiField TextBox login;
    @UiField TextBox pass;
    @UiField TextBox acct;
    @UiField @Ignore DateBox date;
    @UiField @Ignore ControlGroup fromControl;
    @UiField @Ignore HelpInline fromCaption;
    @UiField TextBox fromZip;
    @UiField @Ignore ControlGroup toControl;
    @UiField @Ignore HelpInline toCaption;
    @UiField TextBox toZip;
    @UiField(provided=true) EditorListBox<String> terms =
        new EditorListBox<String>(new AbstractRenderer<String>() {
            @Override
            public String render(String s) {
                if (s == null) return "Choose Billing Terms";
                if ("PP".equals(s)) return "Prepaid";
                if ("CC".equals(s)) return "Collect";
                if ("TP".equals(s)) return "Third Party";

                return null;
            }
        });

    @UiField @Ignore CodeBlock requestJson;
    @UiField @Ignore CodeBlock responseJson;
    @UiField @Ignore ControlGroup freightControl;
    @UiField @Ignore ControlGroup accControl;

    List<AccessorialField> accessorials;
    List<LineItemForm> freight;

    public RateForm() {
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
        driver.edit(BeanFactoryProvider.get().rateRequest().as());

        freight = new ArrayList<LineItemForm>();
        accessorials = new ArrayList<AccessorialField>();

        List<String> scacList = new ArrayList<String>();
        scacList.add(null);
        scacList.add("CNWY");
        scacList.add("SAIA");
        scacList.add("PITD");
        scac.setAcceptableValues(scacList);

        List<String> termsList = new ArrayList<String>();
        termsList.add(null);
        termsList.add("PP");
        termsList.add("CC");
        termsList.add("TP");
        terms.setAcceptableValues(termsList);

        date.setValue(new Date());

        bind();
    }

    public RateRequest getRequest() {
        return (RateRequest)driver.flush();
    }

    @UiHandler("fromZip")
    void onFromZipFocus(FocusEvent e) {
        fromControl.setType(ControlGroupType.NONE);
        fromCaption.setText(null);
    }
    @UiHandler("fromZip")
    void onFromZipBlur(BlurEvent e) {
        String zip = fromZip.getText();

        if (!(Util.isAmericanZip(zip) || Util.isCanadianZip(zip))) {
            fromControl.setType(ControlGroupType.ERROR);
            fromCaption.setText("This does not look like a valid zip code");
        }
        else {
            updateRequestJson();
        }
    }
    @UiHandler("toZip")
    void onToZipFocus(FocusEvent e) {
        toControl.setType(ControlGroupType.NONE);
        toCaption.setText(null);
    }
    @UiHandler("toZip")
    void onToZipBlur(BlurEvent e) {
        String zip = toZip.getText();

        if (!(Util.isAmericanZip(zip) || Util.isCanadianZip(zip))) {
            toControl.setType(ControlGroupType.ERROR);
            toCaption.setText("This does not look like a valid zip code");
        }
        else {
            updateRequestJson();
        }
    }

    @UiHandler("login")
    void onUserBlur(BlurEvent e) {
        updateRequestJson();
    }
    @UiHandler("pass")
    void onPassBlur(BlurEvent e) {
        updateRequestJson();
    }
    @UiHandler("acct")
    void onAccBlur(BlurEvent e) {
        updateRequestJson();
    }

    @UiHandler("date")
    void onDateChange(ChangeEvent e) {
        updateRequestJson();
    }

    public void bind() {
        scac.addValueChangeHandler(new ValueChangeHandler() {
            @Override
            public void onValueChange(ValueChangeEvent e) {
                updateRequestJson();
            }
        });
        terms.addValueChangeHandler(new ValueChangeHandler() {
            @Override
            public void onValueChange(ValueChangeEvent e) {
                updateRequestJson();
            }
        });

        addLineItem();
        addAccessorial();
        updateRequestJson();
    }

    public void addAccessorial() {
        AccessorialField acc = new AccessorialField();
        accessorials.add(acc);
        accControl.add(acc);

        acc.bind(this);
    }

    public void addLineItem() {
        LineItemForm li = new LineItemForm();
        freight.add(li);
        freightControl.add(li);

        li.bind(this);
    }

    void flushLineItemData(RateRequest r) {
        List<FreightItem> lineItems = new ArrayList<FreightItem>();
        for (LineItemForm li : freight) {
            String cls = li.getCls();
            Integer weight = li.getWeight();
            if (cls == null || weight == null) continue;

            FreightItem freightItem = BeanFactoryProvider.get().freightItem().as();
            freightItem.setCls(cls);
            freightItem.setWeight(weight);
            lineItems.add(freightItem);
        }
        r.setFreight(lineItems);
    }
    void flushAccessorialData(RateRequest r) {
        List<String> accList = new ArrayList<String>();
        for (AccessorialField accField : accessorials) {
            String value = accField.getValue();
            if (value.equals("None")) continue;

            accList.add(value);
        }
        r.setAccessorials(accList);
    }

    void flushDate(RateRequest r) {
        r.setDate(date.getOriginalValue());
    }

    public void updateRequestJson() {
        RateRequest r = (RateRequest)driver.flush();
        flushDate(r);
        flushLineItemData(r);
        flushAccessorialData(r);
        String json = AutoBeanCodex.encode(AutoBeanUtils.getAutoBean(r)).getPayload();

        VerticalPanel p = (VerticalPanel)requestJson.getParent();
        int index = p.getWidgetIndex(requestJson);
        requestJson.removeFromParent();
        requestJson = new CodeBlock(prettify(json));
        p.insert(requestJson, index);
        //requestJson.setText(prettify(json));
    }

    private native String prettify(String json) /*-{
        return $wnd.JSON.stringify(eval('(' + json + ')'), undefined, 4);
    }-*/;

    @UiTemplate("RateForm.ui.xml")
    interface RateFormUiBinder extends UiBinder<Widget, RateForm> { }
    private static RateFormUiBinder uiBinder = GWT.create(RateFormUiBinder.class);

    interface Driver extends SimpleBeanEditorDriver<RateRequest, RateForm> { }
    Driver driver = GWT.create(Driver.class);
}
