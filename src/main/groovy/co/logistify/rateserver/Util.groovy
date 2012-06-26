package co.logistify.rateserver

import co.logistify.rateserver.dao.Zip

import com.google.appengine.api.urlfetch.*
import java.util.logging.Logger
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.commons.codec.binary.Base64
import com.googlecode.objectify.ObjectifyService

abstract class Util {
    static final Logger log = Logger.getLogger(Util.class.getName())

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

	static String httpGet(String urlString) {
		httpGet(urlString, null, null, null, null)
	}

    static String httpGet(String urlString, def keys, def values,
            String user, String pass) {
        try {
            def payload = new StringBuilder()
            if (keys == null && values != null && values.size() > 0) {
                String encodedValue = URLEncoder.encode(values[0], "UTF-8")
                payload.append("$encodedValue")
            }
			else if (keys == null && values == null) {
				// do nothing
			}
            else [ keys, values ].transpose().eachWithIndex { row, i ->
                def key = row[0], value = row[1]
                String encodedValue = URLEncoder.encode(value, "UTF-8")
                payload.append(i == 0 ? '?' : '&')
                payload.append("$key=$encodedValue")
            }

            log.info('request payload: '+ new String(payload.toString()))

            URL url = new URL(urlString + payload.toString())

            HTTPRequest request = new HTTPRequest(url, HTTPMethod.GET)

            if (user != null && pass != null) {
                String nameAndPassword = user +":"+ pass
                String authorizationString = "Basic " + Base64.encodeBase64String(nameAndPassword.getBytes())
                request.addHeader(new HTTPHeader("Authorization", authorizationString))
            }

            HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request)
            String s = new String(response.getContent())

            log.info('api response: '+ s)

            return s

        } catch (MalformedURLException e) {
            'httpPost: '+ e.getMessage()
        } catch (IOException e) {
            'httpPost: '+ e.getMessage()
        }
    }

    /**
     * Make a http POST request using Basic Auth.
     * This is fugly because groovy uses an unsupported class to make SSL requests
     * so I had to implement this function myself.
     */
    static String httpPost(String urlString, def keys, def values,
            String user, String pass) {
        try {
            URL url = new URL(urlString)

            HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST)

            if (user != null && pass != null) {
                String nameAndPassword = user +":"+ pass
                String authorizationString = "Basic " + Base64.encodeBase64String(nameAndPassword.getBytes())
                request.addHeader(new HTTPHeader("Authorization", authorizationString))
            }

            def payload = new StringBuilder()
            if (keys == null && values != null && values.size() > 0) {
                String encodedValue = URLEncoder.encode(values[0], "UTF-8")
                payload.append("$encodedValue")
            }
            else [ keys, values ].transpose().each {
                def key = it[0], value = it[1]
                String encodedValue = URLEncoder.encode(value, "UTF-8")
                payload.append("$key=$encodedValue")
            }
            request.setPayload(payload.toString().getBytes())

            log.info('request payload: '+ new String(request.getPayload()))

            HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request)
            String s = new String(response.getContent())

            log.info('api response: '+ s)

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

    public static void init() {
        ObjectifyService.register(Zip.class)
    }

    // TODO move to the database
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
