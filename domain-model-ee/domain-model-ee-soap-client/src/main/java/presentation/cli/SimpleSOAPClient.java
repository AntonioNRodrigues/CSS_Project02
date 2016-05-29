package presentation.cli;

import business.handler.AddCustomerHandler;
import business.handler.AddCustomerHandlerService;
import business.handler.ApplicationException_Exception;
import business.handler.sale.*;
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
	 * @throws ApplicationException_Exception 
	 */
	public static void main(String[] args) throws ApplicationException_Exception {
		
		// Make services
	    AddCustomerHandlerService addCustomerHandlerService = new AddCustomerHandlerService();
	    AddSaleHandlerService addSaleHandlerService = new AddSaleHandlerService();
		InsertSaleProductHandlerService insertSaleProductHandlerService = new InsertSaleProductHandlerService();
		CloseSaleHandlerService closeSaleHandlerService = new CloseSaleHandlerService();
		PaySaleHandlerService paySaleHandlerService = new PaySaleHandlerService();
		GetSaleHandlerService getSaleHandlerService = new GetSaleHandlerService();

	    // Now use the service to get a stub which implements the SDI.
	    AddCustomerHandler addCustomerHandler = addCustomerHandlerService.getAddCustomerHandlerPort();
	    AddSaleHandler addSaleHandler = addSaleHandlerService.getAddSaleHandlerPort();
	    InsertSaleProductHandler insertSaleProductHandler = insertSaleProductHandlerService.getInsertSaleProductHandlerPort();
	    CloseSaleHandler closeSaleHandler = closeSaleHandlerService.getCloseSaleHandlerPort();
	    PaySaleHandler paySaleHandler = paySaleHandlerService.getPaySaleHandlerPort();
	    GetSaleHandler getSaleHandler = getSaleHandlerService.getGetSaleHandlerPort();

	    // Make the actual call
	    try {
			int vat = 233299053;
			// addCustomerHandler.addCustomer(vat, "Simão", 217500255, 1);

			int saleId = addSaleHandler.addSale(vat);
			System.out.println("SaleId: " + saleId);

			insertSaleProductHandler.insertSaleProduct(saleId, 123);
			insertSaleProductHandler.insertSaleProduct(saleId, 123);
			insertSaleProductHandler.insertSaleProduct(saleId, 124);
			System.out.println("Inseriu sale products");

			closeSaleHandler.closeSale(saleId);
			System.out.println("Close sale successfull!");

			paySaleHandler.paySale(saleId);
			System.out.println("Sale payed successfully!");
			
			System.out.println("Sale Transactions: ");
			Sale sale = getSaleHandler.getSale(saleId);
			for (Transaction transaction : sale.getTransactions()) {
				System.out.println(transaction);
			}
			
			System.out.println("Sale Products: ");
			for (SaleProduct product : sale.getSaleProducts()) {
				System.out.println(product);
			}
			System.out.println("Sale owner is: " + sale.getCustomer().getDesignation());
			System.out.println("Sale total is: " + sale.getTotalValue());
			System.out.println("Sale discount is: " + sale.getDiscountValue());

			System.out.println("Reached the end!");


		} catch (business.handler.sale.ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar sale");
			e.printStackTrace();
		} catch (business.handler.sale.product.ApplicationException_Exception e) {
			System.out.println("Erro ao adicionar um product a uma sale");
			e.printStackTrace();
		}


	}
}

