package presentation.cli;

import business.AddCustomerHandler;
import business.AddCustomerHandlerService;
import business.ApplicationException_Exception;

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
			customerHandler.addCustomer(168027852, "Customer 1", 217500255, 1);
			System.out.println("Cliente adiciondo com sucesso.");
		} catch (ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar cliente.");
			System.out.println("Causa:");
			e.printStackTrace();
		}

	}
}
