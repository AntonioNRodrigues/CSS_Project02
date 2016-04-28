package presentation;

import java.util.List;

import business.ApplicationException;
import business.CustomerTransactionScripts;
import business.DiscountType;
import dataaccess.CustomerRowDataGateway;


public class CustomerService {

	private CustomerTransactionScripts customerTS;

	public CustomerService(CustomerTransactionScripts customerTS) {
		this.customerTS = customerTS;
	}
	
	public void addCustomer(int vat, String denomination, int phoneNumber, 
			DiscountType discountType) throws ApplicationException {
		customerTS.addCustomer(vat, denomination, phoneNumber, discountType);
	}
	
	public List<String> getAllCustomers() throws ApplicationException{
		return customerTS.getAllCustomers();
	}
	
	public List<String> getSaleTransactions(int vat) throws ApplicationException{
		return customerTS.getAllTransactions(vat);
	}
	
	public void makePayment(int saleId, double amount) throws ApplicationException{
		this.customerTS.makePayment(saleId, amount);
	}
}
