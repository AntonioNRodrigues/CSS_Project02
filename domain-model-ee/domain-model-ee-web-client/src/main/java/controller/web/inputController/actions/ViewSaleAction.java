package controller.web.inputController.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Sale;
import facade.handlers.IGetSaleHandlerRemote;
import facade.handlers.IInsertSaleProductHandlerRemote;
import presentation.web.model.ViewSaleModel;

@Stateless
public class ViewSaleAction extends Action {
	@EJB
	private IGetSaleHandlerRemote getSaleHandler;
	
	@EJB private IInsertSaleProductHandlerRemote getProductFromSale;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			int idSale = Integer.parseInt(request.getParameter("sale"));

			Sale s = getSaleHandler.getSale(idSale);
			
			request.setAttribute("model", new ViewSaleModel(s.getId(), s.getDiscountValue(), s.getTotalValue(), s.getSaleProdutcs()));
			
			request.getRequestDispatcher("/getSale/getSale.jsp").forward(request, response);

		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.write("error");
		}

	}

}
