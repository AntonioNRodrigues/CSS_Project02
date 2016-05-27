package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.handlers.IGetCustomersHandlerRemote;

@Stateless
public class ViewSalesAction extends Action{

	@EJB private IGetCustomersHandlerRemote getCustomerHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
	}

}
