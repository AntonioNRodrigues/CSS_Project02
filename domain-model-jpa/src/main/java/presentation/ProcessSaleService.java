package presentation;

import business.ApplicationException;
import business.ProcessSaleHandler;
import business.Sale;

public class ProcessSaleService {

	
	
	private ProcessSaleHandler saleHandler;

	public ProcessSaleService(ProcessSaleHandler saleHandler) {
		this.saleHandler = saleHandler;
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
		saleHandler.closeSale();
	}
	
	public Sale getCurrentSale(){
		return this.saleHandler.getCurrentSale();
	}
}
