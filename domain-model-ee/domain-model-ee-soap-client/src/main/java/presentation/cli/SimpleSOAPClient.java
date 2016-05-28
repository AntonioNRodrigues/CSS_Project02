package presentation.cli;

import business.handler.AddCustomerHandler;
import business.handler.AddCustomerHandlerService;
import business.handler.ApplicationException_Exception;
import business.handler.sale.AddSaleHandler;
import business.handler.sale.AddSaleHandlerService;

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
		
		// Make services
	    AddCustomerHandlerService addCustomerHandlerService = new AddCustomerHandlerService();
	    AddSaleHandlerService addSaleHandlerService = new AddSaleHandlerService();

	    // Now use the service to get a stub which implements the SDI.
	    AddCustomerHandler addCustomerHandler = addCustomerHandlerService.getAddCustomerHandlerPort();
	    AddSaleHandler addSaleHandler = addSaleHandlerService.getAddSaleHandlerPort();

	    // Make the actual call
	    try {
			int vat = 233299053;
//			addCustomerHandler.addCustomer(vat, "Simão", 217500255, 1);

			int saleId = addSaleHandler.addSale(vat);
			System.out.println("SaleId: " + saleId);

		} catch (business.handler.sale.ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar sale");
			e.printStackTrace();
		}


	}
}

