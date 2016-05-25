package controller.web.inputController.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Customer;
import facade.handlers.IGetCustomersHandlerRemote;
import presentation.web.model.ViewCustomerProfileModel;

@Stateless
public class ViewCustomerProfileAction extends Action{

	@EJB private IGetCustomersHandlerRemote getCustomersHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// fetch customer
			int customerVAT = Integer.parseInt(request.getParameter("vat"));
			Customer customer = getCustomersHandler.getCustomerByVat(customerVAT);
			
			// pass it to request scope
			request.setAttribute("model", new ViewCustomerProfileModel(customer));
			
			// pass control to view dispatcher
			request.getRequestDispatcher("/viewCustomers/viewCustomerProfile.jsp")
			.forward(request, response);
			
		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.write("Error...");
		}
		
	}

}
