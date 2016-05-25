package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Customer;
import facade.handlers.IGetCustomersHandlerRemote;
import presentation.web.model.NewSaleModel;

@Stateless
public class NewSaleAction extends Action{

	@EJB private IGetCustomersHandlerRemote getCustomersHandler; 
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			// prepare view model
			NewSaleModel model = new NewSaleModel();
			model.setCustomers(getCustomersHandler.getCustomers());
			
			// set model available to request scope
			request.setAttribute("model", model);
			
			// send it to view
			request.getRequestDispatcher("/newSale/newSale.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
			//request.getRequestDispatcher("/unknownAction.jsp").forward(request, response);
		}
		
	}

}
