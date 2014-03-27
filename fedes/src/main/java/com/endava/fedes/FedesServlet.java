package com.endava.fedes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Base servlet for Federated Search.
 *
 * @author <a href="mailto:roxana.paduraru@endava.com">Roxana PADURARU</a>
 * @since 3/24/14
 */
public class FedesServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("searchTerms");

		//do the search here

		request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
	}
}
