package org.eclipse.om2m.webapp.resourcesbrowser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CIMAAdministrationServlet extends HttpServlet {
    /** Serial Version UID */
    private static final long serialVersionUID = 1L;
      @Override
      protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
          //httpServletRequest.getRequestDispatcher("/webapps/index.html").forward (httpServletRequest, httpServletResponse);
          httpServletResponse.sendRedirect("/config/ManualConfiguration.html");
      }
}
