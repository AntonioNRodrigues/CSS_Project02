package facade.interfaces;

import business.persistence.entities.Customer;
import facade.exceptions.ApplicationException;


public interface ISale {

	public double total();

	public double eligibleDiscountTotal();
	
	public Customer getCustomer();
	
	public double discount () throws ApplicationException;
	
	public String toString();
}