package controller.web.inputController.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.handlers.IGetCustomersHandlerRemote;
import presentation.web.model.ViewCustomersModel;

@Stateless
public class ViewCustomersAction extends Action{

	@EJB private IGetCustomersHandlerRemote getCustomersHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// obtem customers
			ViewCustomersModel model = 
					new ViewCustomersModel(getCustomersHandler.getCustomers());
			
			// disponiliza para a view
			request.setAttribute("model", model);
			
			// encaminha para view dispatcher
			request.getRequestDispatcher("/viewCustomers/viewCustomers.jsp")
			.forward(request, response);
			
			
		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.write("error");
		}
		
	}

}
