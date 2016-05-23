package facade.handlers;

import javax.ejb.Remote;

import business.Discount;
import facade.exceptions.ApplicationException;

@Remote
public interface IAddCustomerHandlerRemote {

	public void addCustomer (int vat, String denomination, 
			int phoneNumber, int discountType) 
			throws ApplicationException;
	
	public Iterable<Discount> getDiscounts() throws ApplicationException;

}
