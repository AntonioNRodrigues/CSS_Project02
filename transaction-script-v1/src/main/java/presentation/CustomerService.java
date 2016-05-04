package presentation;

import business.ApplicationException;
import business.CustomerTransactionScripts;
import business.DiscountType;
import domain.Account;


public class CustomerService {

	private CustomerTransactionScripts customerTS;

	public CustomerService(CustomerTransactionScripts customerTS) {
		this.customerTS = customerTS;
	}
	
	public void addCustomer(int vat, String denomination, int phoneNumber, 
			DiscountType discountType) throws ApplicationException {
		customerTS.addCustomer(vat, denomination, phoneNumber, discountType);
	}
	
	public Account getAccount(int vat) throws ApplicationException{
		return this.customerTS.getAccountInfo(vat);
	}
}
