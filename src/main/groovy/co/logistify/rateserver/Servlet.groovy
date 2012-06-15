package co.logistify.rateserver

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import co.logistify.rateserver.adapter.AdapterFactory
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory

public class Servlet extends HttpServlet {
    //final Logger log = LoggerFactory.getLogger(Servlet.class)

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean debug = request.getParameter('debug') == 'true'

        def result = null
        Request r = null

        try {
            r = validateAndConvertRequest(request.getParameter('r'))
            //log.debug(r.toString())
            //log.debug(r.scac)
            result = AdapterFactory.getAdapter(r.scac).getRate(r)
            //log.debug(result)
        } catch (Exception e) {
            //log.error(e)
            result = [ error: e.message ]
        }
        def json = new groovy.json.JsonBuilder(result).toString()
        //log.debug(json)
        response.getWriter().print(json)
    }

    /**
     * Validate the request. If valid, return a Request object. Throw
     * exception otherwise.
     */
    public Request validateAndConvertRequest(String req) throws Exception {
        Request r = new groovy.json.JsonSlurper().parseText(req)
        return r.validate()
    }
}
