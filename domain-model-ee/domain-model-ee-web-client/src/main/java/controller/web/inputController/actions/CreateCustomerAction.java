package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewCustomerModel;
import facade.exceptions.ApplicationException;
import facade.handlers.IAddCustomerHandlerRemote;

/**
 * Handles the criar cliente event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 * 
 * @author fmartins
 *
 */
@Stateless
public class CreateCustomerAction extends Action {
	
	@EJB private IAddCustomerHandlerRemote addCustomerHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewCustomerModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			try {
				addCustomerHandler.addCustomer(intValue(model.getVATNumber()), 
						model.getDesignation(), intValue(model.getPhoneNumber()), intValue(model.getDiscountType()));
				model.clearFields();
				model.addMessage("Customer successfully added.");
			} catch (ApplicationException e) {
				model.addMessage("Error adding customer: " + e.getMessage());
			}
		} else
			model.addMessage("Error validating customer data");
		
		request.getRequestDispatcher("/addCustomer/newCustomer.jsp").forward(request, response);
	}

	
	private boolean validInput(NewCustomerModel model) {
		
		// check if designation is filled
		boolean result = isFilled(model, model.getDesignation(), "Designation must be filled.");
		
		// check if VATNumber is filled and a valid number
		result &= isFilled(model, model.getVATNumber(), "VAT number must be filled")
				 			&& isInt(model, model.getVATNumber(), "VAT number with invalid characters");
		
		// check in case phoneNumber is filled if it contains a valid number
		if (!model.getPhoneNumber().equals(""))
			result &= isInt(model, model.getPhoneNumber(), "Phone number contains invalid characters");

		// check if discount type is filled and a valid number
		result &= isFilled(model, model.getDiscountType(), "Discount type must be filled") 
					&& isInt(model, model.getDiscountType(), "Discount type with invalid characters");

		return result;
	}


	private NewCustomerModel createHelper(HttpServletRequest request) {
		// Create the object model
		NewCustomerModel model = new NewCustomerModel();
		model.setAddCustomerHandler(addCustomerHandler);

		// fill it with data from the request
		model.setDesignation(request.getParameter("designacao"));
		model.setVATNumber(request.getParameter("npc"));
		model.setPhoneNumber(request.getParameter("telefone"));
		model.setDiscountType(request.getParameter("desconto"));
		
		return model;
	}	
}
