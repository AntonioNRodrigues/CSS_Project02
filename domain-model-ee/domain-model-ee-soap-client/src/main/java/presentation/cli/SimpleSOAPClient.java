package presentation.cli;

import business.handler.AddCustomerHandler;
import business.handler.AddCustomerHandlerService;
import business.handler.ApplicationException_Exception;

/**
 * A simple application client that uses both services.
 *	
 * @author fmartins
 * @version 1.2 (11/02/2015)
 * 
 */
public class SimpleSOAPClient {

	/**
	 * A simple interaction with the application services
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {
		
		// Make a service
	    AddCustomerHandlerService service = new AddCustomerHandlerService();

	    // Now use the service to get a stub which implements the SDI.
	    AddCustomerHandler customerHandler = service.getAddCustomerHandlerPort();

	    // Make the actual call
	    try {
			int vat = 233299053;
			customerHandler.addCustomer(vat, "Simão", 217500255, 1);
			

		} catch (ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar cliente.");
			System.out.println("Causa:");
			e.printStackTrace();
		}

	}
}
