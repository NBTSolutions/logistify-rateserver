package co.logistify.rateserver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.google.appengine.api.urlfetch.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.commons.codec.binary.Base64

abstract class Util {
    static final Logger log = LoggerFactory.getLogger(Util.class)

    /**
     * Return true if the argument is 'empty' or null, and false otherwise.
     */
    static boolean nullOrEmpty(def o) {
        if (o instanceof List) return o?.size() == 0
        if (o instanceof String) return o?.length() == 0
        else return o == null
    }

    static String httpPost(String urlString, String key, String value, String user, String pass) {
        log.debug('httpPost')
        String encodedValue = URLEncoder.encode(value, "UTF-8")
        try {
            URL url = new URL(urlString)

            String nameAndPassword = user +":"+ pass
            String authorizationString = "Basic " + Base64.encodeBase64String(nameAndPassword.getBytes())

            HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST)
            request.addHeader(new HTTPHeader("Authorization", authorizationString))
            request.setPayload("$key=$encodedValue".getBytes())

            log.info('request payload: {}', new String(request.getPayload()))

            HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request)
            String s = new String(response.getContent())

            log.info('api response: {}', s)

            return s

        } catch (MalformedURLException e) {
            'httpPost: '+ e.getMessage()
        } catch (IOException e) {
            'httpPost: '+ e.getMessage()
        }
    }

    /**
     * Preemptively set the Authorization header to use Basic Auth.
     * @param connection The HTTP connection
     * @param username Username
     * @param password Password
     */
    public static void setBasicAuth(HttpURLConnection connection,
                String username, String password) {
        StringBuilder buf = new StringBuilder(username)
        buf.append(':')
        buf.append(password)
        byte[] bytes = null
        try {
                bytes = buf.toString().getBytes("ISO-8859-1")
        } catch (java.io.UnsupportedEncodingException uee) {
                assert false
        }

        String header = "Basic " + Base64.encodeBase64String(bytes)
        connection.setRequestProperty("Authorization", header)
    }
}
