package presentation;

import java.util.List;

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
	
	public List<String> getSaleProducts(int saleId) throws ApplicationException{
		return saleTS.getSaleProducts(saleId);
	}
	
	public void closeSale(int sale) throws ApplicationException{
		this.saleTS.closeSale(sale);
	}
	
	//TODO -> CHANGE THIS TO A PROPOER SERVICE
	public List<String> getTransactionDetails(int transactionId)
		throws ApplicationException
	{
		return saleTS.getTransactionDetails(transactionId);
	}
	
	public List<String> getAllSales() throws ApplicationException{
		return saleTS.getAllSales();
	}
	
}
