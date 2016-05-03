package presentation;

import business.ApplicationException;
import business.ProcessSaleHandler;

public class ProcessSaleService {

	private ProcessSaleHandler saleHandler;

	public ProcessSaleService(ProcessSaleHandler saleHandler) {
		this.saleHandler = saleHandler;
	}

	public void newSale(int vat) throws ApplicationException {
		saleHandler.newSale(vat);
	}

	public void addProductToSale(int productCode, int qty) throws ApplicationException {
		saleHandler.addProductToSale(productCode, qty);
	}

	public double getSaleDiscount() throws ApplicationException {
		return saleHandler.getSaleDiscount();
	}

	public boolean closeSale(int vat) throws ApplicationException {
		return saleHandler.closeSale(vat);

	}
	public boolean paymentSale(int idSale) throws ApplicationException{
		return saleHandler.paymentSale(idSale);
	}
}
