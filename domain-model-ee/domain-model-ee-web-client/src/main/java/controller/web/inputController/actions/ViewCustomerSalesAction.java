package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Customer;
import business.persistence.entities.Sale;
import facade.handlers.IGetCustomersHandlerRemote;
import facade.handlers.IViewCustomerSalesHandlerRemote;
import presentation.web.model.ViewCustomerSalesModel;

@Stateless
public class ViewCustomerSalesAction extends Action{

	@EJB private IGetCustomersHandlerRemote customersHandler;
	
	@EJB private IViewCustomerSalesHandlerRemote getSalesHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			System.out.println("TESTE 1");
			int customerVAT = Integer.parseInt(request.getParameter("vat"));
			
			System.out.println("TESTE 2");
			Customer customer = customersHandler.getCustomerByVat(customerVAT);
			
			
			System.out.println("TESTE 5");
			ViewCustomerSalesModel model = new ViewCustomerSalesModel(customer.getVatNumber(), customer.getId(), customer.getDesignation(), getSalesHandler);
			
			System.out.println("TESTE 6");
			request.setAttribute("model", model);
			
			request.getRequestDispatcher("/viewSales/viewSales.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
