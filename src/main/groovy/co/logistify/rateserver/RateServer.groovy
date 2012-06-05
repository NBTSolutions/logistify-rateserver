package co.logistify.rateserver

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import co.logistify.rateserver.adapter.AdapterFactory

public class RateServer extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        validateRequest(request.getParameter('scac'))
        response.getWriter().print(
            new groovy.json.JsonBuilder(
                AdapterFactory.getAdapter(request.getParameter('scac')).getRate(request)
            ).toString()
        )
    }
}
