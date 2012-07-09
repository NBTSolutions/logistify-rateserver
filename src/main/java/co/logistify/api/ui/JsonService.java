package co.logistify.api.ui;

import co.logistify.api.ui.view.*;
import com.google.gwt.jsonp.client.*;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.json.client.*;

public class JsonService {

    public static void requestRate(final String r, final RateForm form) {
        String url = "http://api.logistify.co/?r=" + r;
        new JsonpRequestBuilder().requestObject(url, new AsyncCallback<JavaScriptObject>() {
            public void onSuccess(JavaScriptObject obj) {
                String json = new JSONObject(obj).toString();
                form.updateResponseJson(json);
            }
            public void onFailure(Throwable e) {
                // throw a fit
            }
        });

    }
}
