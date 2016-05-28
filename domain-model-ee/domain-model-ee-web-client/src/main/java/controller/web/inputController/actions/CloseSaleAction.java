package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Customer;
import facade.handlers.ICloseSaleHandlerRemote;
import facade.handlers.IGetCustomersHandlerRemote;
import facade.handlers.IViewCustomerSalesHandlerRemote;
import presentation.web.model.ViewCustomerSalesModel;

@Stateless
public class CloseSaleAction extends Action{

	@EJB private ICloseSaleHandlerRemote closeSaleHandler;
	
	@EJB private IViewCustomerSalesHandlerRemote getCustomerSalesHandler;
	
	@EJB private IGetCustomersHandlerRemote getCustomerHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// obtem sale id e customer vat
			int saleId = Integer.parseInt(request.getParameter("sale"));
			int customerVAT = Integer.parseInt(request.getParameter("customerVAT"));
			
			// close sale
			closeSaleHandler.closeSale(saleId);
			
			// get customer
			Customer customer = getCustomerHandler.getCustomerByVat(customerVAT);
			
			ViewCustomerSalesModel model = new ViewCustomerSalesModel(customer.getVATNumber(), customer.getId(), customer.getDesignation(), getCustomerSalesHandler);
			model.addMessage("Sale closed!");
			
			request.setAttribute("model", model);
			
			request.getRequestDispatcher("/viewSales/viewSales.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
