package facade.interfaces;

import business.Customer;
import facade.exceptions.ApplicationException;


public interface ISale {

	public double total();

	public double eligibleDiscountTotal();
	
	public Customer getCustomer();
	
	public double discount () throws ApplicationException;
}