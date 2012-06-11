package co.logistify.rateserver

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import co.logistify.rateserver.adapter.AdapterFactory

public class RateServer extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        def result = null
        Request r = null

        try {
            r = validateAndConvertRequest(getPostBody(request))
            result = AdapterFactory.getAdapter(r.scac).getRate(r)
        } catch (Exception e) {
            result = e
        }

        response.getWriter().print(new groovy.json.JsonBuilder(result).toString())
    }

    /**
     * Validate the request. If valid, return a Request object. Throw
     * exception otherwise.
     */
    public Request validateAndConvertRequest(String req)
            throws Exception {
        Request r = new groovy.json.JsonSlurper().parseText(req)

        if (nullOrEmpty(r.scac))    throw new Exception('scac code cannot be empty')
        if (nullOrEmpty(r.fromZip)) throw new Exception('fromZip cannot be empty')
        if (nullOrEmpty(r.toZip))   throw new Exception('toZip cannot be empty')
        if (nullOrEmpty(r.classes)) throw new Exception('classes cannot be empty')
        if (nullOrEmpty(r.weights)) throw new Exception('weights cannot be empty')

        return r
    }

    /**
     * Return the body of the post request as a string.
     */
    private String getPostBody(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer()
        String line = null
        try {
            BufferedReader reader = request.getReader()
            while ((line = reader.readLine()) != null)
                sb.append(line)

            return sb.toString()
        } catch (Exception e) {
            // TODO report problem
        }
    }

    /**
     * Return true if the argument is 'empty' or null, and false otherwise.
     */
    public static boolean nullOrEmpty(def o) {
        if (o instanceof List) return o?.size() == 0
        if (o instanceof String) return o?.length() == 0
        else return o == null
    }
}
