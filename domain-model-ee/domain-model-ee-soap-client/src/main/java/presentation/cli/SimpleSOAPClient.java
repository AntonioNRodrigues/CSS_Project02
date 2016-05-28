package presentation.cli;

import business.handler.AddCustomerHandler;
import business.handler.AddCustomerHandlerService;
import business.handler.ApplicationException_Exception;
import business.handler.sale.AddSaleHandler;
import business.handler.sale.AddSaleHandlerService;
import business.handler.sale.CloseSaleHandler;
import business.handler.sale.CloseSaleHandlerService;
import business.handler.sale.product.InsertSaleProductHandler;
import business.handler.sale.product.InsertSaleProductHandlerService;

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
		InsertSaleProductHandlerService insertSaleProductHandlerService = new InsertSaleProductHandlerService();
		CloseSaleHandlerService closeSaleHandlerService = new CloseSaleHandlerService();

	    // Now use the service to get a stub which implements the SDI.
	    AddCustomerHandler addCustomerHandler = addCustomerHandlerService.getAddCustomerHandlerPort();
	    AddSaleHandler addSaleHandler = addSaleHandlerService.getAddSaleHandlerPort();
	    InsertSaleProductHandler insertSaleProductHandler = insertSaleProductHandlerService.getInsertSaleProductHandlerPort();
	    CloseSaleHandler closeSaleHandler = closeSaleHandlerService.getCloseSaleHandlerPort();

	    // Make the actual call
	    try {
			int vat = 233299053;
//			addCustomerHandler.addCustomer(vat, "Simão", 217500255, 1);

			int saleId = addSaleHandler.addSale(vat);
			System.out.println("SaleId: " + saleId);

			insertSaleProductHandler.insertSaleProduct(saleId, 123);
			insertSaleProductHandler.insertSaleProduct(saleId, 123);
			insertSaleProductHandler.insertSaleProduct(saleId, 124);
			System.out.println("Inseriu sale products");

			closeSaleHandler.closeSale(saleId);
			System.out.println("Close sale successfull!");


		} catch (business.handler.sale.ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar sale");
			e.printStackTrace();
		} catch (business.handler.sale.product.ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar um product a uma sale");
			e.printStackTrace();
		}


	}
}

