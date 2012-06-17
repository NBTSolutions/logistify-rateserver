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
        if (o instanceof List) 
            o?.size() == 0
        else if (o instanceof String)
            o?.length() == 0
        else
            o == null
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

    public static boolean isCanadianZip(String zip) {
        return zip =~ /^[ABCEGHJKLMNPRSTVXY]{1}\d{1}[A-Z]{1} *\d{1}[A-Z]{1}\d{1}$/
    }
    public static boolean isAmericanZip(String zip) {
        return zip =~ /^\d{5}(-\d{4})?$/
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

    static final def supportedAccessorials = [
        'PED' : 'Destination exhibition sites delivery',
        'PEO' : 'Origin exhibition sites pickup',
        'ELS' : 'Excessive length',
        'ZHM' : 'Hazardous Materials',
        'OIP' : 'Inside pickup',
        'DID' : 'Inside delivery',
        'DLG' : 'Lift gate service',
        'RSD' : 'Destination residential delivery',
        'RSO' : 'Origin residential pickup',
        'OSS' : 'Origin sort and segregate',
        'SRT' : 'Destination sort and segregate',
    ]
    static final String[] supportedClasses = [
        '50', '55', '60', '65', '70', '77', '77.5', '85', '92', '92.5',
        '100', '110', '125', '150', '175', '200', '250', '300', '400', '500'
    ]
}
