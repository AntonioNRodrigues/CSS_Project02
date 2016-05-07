package presentation;

import business.ApplicationException;
import business.ProcessSaleHandler;
import business.Sale;

public class ProcessSaleService {

	/**
	 * aditional service
	 * this service is the transactions service
	 * because it may be using an external service to regist transactions
	 * so system has the hability to decide
	 */
	private RegistTransactionService transactionService;
	
	
	private ProcessSaleHandler saleHandler;

	public ProcessSaleService(ProcessSaleHandler saleHandler, RegistTransactionService transactionService) {
		this.saleHandler = saleHandler;
		this.transactionService = transactionService;
	}

	public Sale newSale(int vat) throws ApplicationException {
		return saleHandler.newSale(vat);
	}

	public void addProductToSale(int productCode, int qty) 
			throws ApplicationException {
		saleHandler.addProductToSale(productCode, qty);
	}

	public double getSaleDiscount() throws ApplicationException {
		return saleHandler.getSaleDiscount();
	}
	
	public void closeSale() throws ApplicationException{
		saleHandler.closeSale(transactionService);
	}
	
	public Sale getCurrentSale(){
		return this.saleHandler.getCurrentSale();
	}
	
	public void makePayment(){
		this.saleHandler.makePayment(transactionService);
	}
}
