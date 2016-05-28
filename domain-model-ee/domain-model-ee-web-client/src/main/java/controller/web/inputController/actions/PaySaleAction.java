package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Customer;
import facade.handlers.IViewCustomerSalesHandlerRemote;
import facade.handlers.IGetCustomersHandlerRemote;
import facade.handlers.IPaySaleHandlerRemote;
import presentation.web.model.ViewCustomerSalesModel;

@Stateless
public class PaySaleAction extends Action{

	@EJB private IPaySaleHandlerRemote paySaleHandler;
	
	@EJB private IViewCustomerSalesHandlerRemote getCustomerSales;
	
	@EJB private IGetCustomersHandlerRemote getCustomers;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// get customer and sale id
			int saleId = Integer.parseInt(request.getParameter("sale"));
			int customerVAT = Integer.parseInt(request.getParameter("customerVAT")); 
						
			// pay sale
			paySaleHandler.paySale(saleId);
			
			// get customer
			Customer c = getCustomers.getCustomerByVat(customerVAT);
			ViewCustomerSalesModel model = new ViewCustomerSalesModel(c.getVATNumber(), c.getId(), c.getDesignation(), getCustomerSales);
			model.addMessage("Sale payed!");
			
			// set it available to view
			request.setAttribute("model", model);
			
			// pass control to view dispatcher
			request.getRequestDispatcher("/viewSales/viewSales.jsp").forward(request, response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	
	
}
