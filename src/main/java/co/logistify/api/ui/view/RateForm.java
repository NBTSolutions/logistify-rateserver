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
import com.google.gwt.core.client.GWT;
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
    @UiField(provided=true)
    ValueListBox<String> scac = 
        new ValueListBox<String>(new AbstractRenderer<String>() {
            @Override
            public String render(String s) {
                if (s == null) return "Choose a Carrier";
                return s;
            }
        });
    @UiField TextBox login;
    @UiField TextBox pass;
    @UiField TextBox acct;
    @UiField DateBox date;
    @UiField @Ignore ControlGroup fromControl;
    @UiField @Ignore HelpInline fromCaption;
    @UiField TextBox fromZip;
    @UiField @Ignore ControlGroup toControl;
    @UiField @Ignore HelpInline toCaption;
    @UiField TextBox toZip;
    @UiField(provided=true) ValueListBox<String> terms =
        new ValueListBox<String>(new AbstractRenderer<String>() {
            @Override
            public String render(String object) {
                return object;
            }
        });

    @UiField @Ignore CodeBlock requestJson;
    @UiField @Ignore CodeBlock responseJson;

    List<AccessorialField> accessorials;
    List<LineItemForm> freight;

    public RateForm() {
        initWidget(uiBinder.createAndBindUi(this));
        List<String> scacList = new ArrayList<String>();
        scacList.add("CNWY");
        scacList.add("SAIA");
        scacList.add("PITD");
        scac.setAcceptableValues(scacList);
        scac.setValue("default");
        /*
                  <g:item value='default'>Select a Carrier</g:item>
                  <g:item value='CNWY'>Con-way</g:item>
                  <g:item value='SAIA'>Saia</g:item>
                  <g:item value='PITD'>Pitt Ohio</g:item>
                  */

        /*
         *
                  <g:item>Prepaid</g:item>
                  <g:item>Collect</g:item>
                  <g:item>Third Party</g:item>
                  */


        driver.initialize(this);
        driver.edit(BeanFactoryProvider.get().rateRequest().as());

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
        if (Util.isAmericanZip(zip) || Util.isCanadianZip(zip)) return;

        fromControl.setType(ControlGroupType.ERROR);
        fromCaption.setText("This does not look like a valid zip code");
        updateRequestJson();
    }
    @UiHandler("toZip")
    void onToZipFocus(FocusEvent e) {
        toControl.setType(ControlGroupType.NONE);
        toCaption.setText(null);
    }
    @UiHandler("toZip")
    void onToZipBlur(BlurEvent e) {
        String zip = toZip.getText();
        if (Util.isAmericanZip(zip) || Util.isCanadianZip(zip)) return;

        toControl.setType(ControlGroupType.ERROR);
        toCaption.setText("This does not look like a valid zip code");
        updateRequestJson();
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

    public void bind() {
        // freight item add handler

        // acc select handler --> show add btn
        // acc add handler

    }

    public void updateRequestJson() {
        RateRequest r = (RateRequest)driver.flush();
        String json = AutoBeanCodex.encode(AutoBeanUtils.getAutoBean(r)).getPayload();
        requestJson.setText(json);
    }

    @UiTemplate("RateForm.ui.xml")
    interface RateFormUiBinder extends UiBinder<Widget, RateForm> { }
    private static RateFormUiBinder uiBinder = GWT.create(RateFormUiBinder.class);

    interface Driver extends SimpleBeanEditorDriver<RateRequest, RateForm> { }
    Driver driver = GWT.create(Driver.class);
}
