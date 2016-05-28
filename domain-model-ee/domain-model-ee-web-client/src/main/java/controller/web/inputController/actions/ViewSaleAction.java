package controller.web.inputController.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Sale;
import facade.handlers.ISaleHandlerRemote;
import presentation.web.model.ViewSaleModel;

public class ViewSaleAction extends Action {
	@EJB
	private ISaleHandlerRemote getSaleHandler;
	
	private static final String path = "/getSale/getSale.jsp";

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			int idSale = Integer.parseInt(request.getParameter("id"));
			Sale s = getSaleHandler.getSale(idSale);
			request.setAttribute("model", new ViewSaleModel(s.getId(), s.getDiscountValue(), s.getTotalValue()));

			request.getRequestDispatcher(path).forward(request, response);

		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.write("error");
		}

	}

}
