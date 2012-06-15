package co.logistify.rateserver

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import co.logistify.rateserver.adapter.AdapterFactory

public class Servlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean debug = request.getParameter('debug') == 'true'

        def result = null
        Request r = null

        try {
            r = validateAndConvertRequest(request.getParameter('r'))
            result = AdapterFactory.getAdapter(r.scac).getRate(r)
        } catch (Exception e) {
            result = [ error: e.message ]
        }
        def json = new groovy.json.JsonBuilder(result).toString()
        response.getWriter().print(json)
    }

    /**
     * Validate the request. If valid, return a Request object. Throw
     * exception otherwise.
     */
    public Request validateAndConvertRequest(String req) throws Exception {
        Request r = new groovy.json.JsonSlurper().parseText(req)
        r.validate()
    }
}
