package presentation;

import business.ApplicationException;
import business.SaleTransactionScripts;


public class SaleService {

	private SaleTransactionScripts saleTS;

	public SaleService(SaleTransactionScripts saleTS) {
		this.saleTS = saleTS;
	}

	public int newSale(int vat) throws ApplicationException {
		return saleTS.newSale(vat);
	}

	public void addProductToSale(int saleId, int productCode, int qty) 
			throws ApplicationException {
		saleTS.addProductToSale(saleId, productCode, qty);
	}

	public double getSaleDiscount(int saleId) throws ApplicationException {
		return saleTS.getSaleDiscount(saleId);
	}
	
	public void closeSale(int saleId) throws ApplicationException{
		this.saleTS.closeSale(saleId);
	}
	
	public void makePayment(int saleId, double amount) throws ApplicationException{
		this.saleTS.makePayment(saleId, amount);
	}
	
}
