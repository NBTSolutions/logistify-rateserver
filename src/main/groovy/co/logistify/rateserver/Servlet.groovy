package co.logistify.rateserver

import javax.servlet.ServletException
import javax.servlet.ServletConfig
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import co.logistify.rateserver.adapter.AdapterFactory
import co.logistify.rateserver.test.*

public class Servlet extends HttpServlet {

    public void init(ServletConfig config) {
        super.init(config)
        Util.init();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter('test') == 'true') {
            runTests(response.getWriter())
            return
        }

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

    void runTests(def log) {
        log.print('starting tests...<br/>')
        TestSuite.getTests().each { test ->
            try {
                test.run(log)
            }
            catch (Exception e) {
                log.print(e.getMessage())
                //e.printStackTrace()
            }
        }
        log.println('<br/>tests complete.')
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
