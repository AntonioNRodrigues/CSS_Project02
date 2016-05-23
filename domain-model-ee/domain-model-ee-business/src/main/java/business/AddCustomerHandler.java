package business;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import facade.exceptions.ApplicationException;
import facade.handlers.IAddCustomerHandlerRemote;

/**
 * Handles the add customer use case. This represents a very 
 * simplified use case with just one operation: addCustomer.
 * 
 * @author fmartins
 * @version 1.1 (17/04/2015)
 *
 */
@Stateless
@WebService 
public class AddCustomerHandler implements IAddCustomerHandlerRemote {
	
	/**
	 * The customer's catalog
	 */
	@EJB
	private CustomerCatalog customerCatalog;
	
	/**
	 * The discount's catalog 
	 */
	@EJB
	private DiscountCatalog discountCatalog;
	
	/**
	 * Adds a new customer with a valid Number. It checks that there is no other 
	 * customer in the database with the same VAT.
	 * 
	 * @param vat The VAT of the customer
	 * @param denomination The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable to the customer
	 * @throws ApplicationException When the VAT number is invalid (we check it according 
	 * to the Portuguese legislation).
	 */
	public void addCustomer (int vat, String denomination, 
			int phoneNumber, int discountType) 
			throws ApplicationException {
		Discount discount = discountCatalog.getDiscount(discountType);
		try {
			customerCatalog.addCustomer(vat, denomination, phoneNumber, discount);
		} catch (Exception e) {
			throw new ApplicationException ("Error adding customer with VAT " + vat, e);
		}
	}	
	
	public List<Discount> getDiscounts() throws ApplicationException {
		return new LinkedList<>(discountCatalog.getDiscounts());
	}

}
