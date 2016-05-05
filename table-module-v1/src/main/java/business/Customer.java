package business;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;

/**
 * The table module of a customer. I choose to implement it as a regular instance,
 * but it could be implemented as a singleton, instead. The singleton implementation
 * could be simpler but may complicate things further down has things get more
 * complex.Please check Martin Fowler's enterprise pattern book for details on 
 * other plausible implementations. 
 * 
 * @author fmartins
 * @version 1.2 (2/10/2015)
 *
 */
public class Customer extends TableModule {
	
	/**
	 * Constructs a customer module given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public Customer (Persistence persistence) {
		super(persistence);
	}

	/**
	 * Creates a new customer given its VAT number, its designation, phone contact, and discount type.
	 * 
	 * @param vat The customer's VAT number
	 * @param designation The customer's designation
	 * @param phoneNumber The customer's phone number
	 * @param discountType The customer's discount id
	 * @throws ApplicationException When VAT number is invalid, or the customer exists, or there is
	 * an internal referential integrity error.
	 */
	public void addCustomer (int vat, String designation, 
			int phoneNumber, DiscountType discountType) throws ApplicationException {
		// Checks if it is a valid VAT number
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);

		// Checks that there is no other customer with the same VAT number 
		if (existsCustomer(vat))
			throw new ApplicationException ("Customer with VAT number " + vat + " already exists!");

		try {
			// If the customer does not exists, add it to the database 
			persistence.customerTableGateway.addCustomer(vat, designation, phoneNumber, discountType);
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error adding a custumer", e);
		}

	}

	/**
	 * @param vat The VAT number of the customer to check if it exists
	 * @return If a customer exists
	 */
	private boolean existsCustomer(int vat) {
		try {
			return !persistence.customerTableGateway.findWithVATNumber(vat).isEmpty();
		} catch (PersistenceException e) {
			return false;
		} 
	}
	
	/**
	 * @param vat The VAT number of the customer to get its internal id number
	 * @return The customerId of the customer with the vat number 
	 * @throws ApplicationException When the customer is not found or when some 
	 * obscure database internal error occurs (not in the this version of the 
	 * example) 
	 */
	public int getCustomerId (int vat) throws ApplicationException {
		try {
			TableData td = persistence.customerTableGateway.findWithVATNumber(vat);
			if (!td.isEmpty())
				return persistence.customerTableGateway.readID(td.iterator().next());
			else 
				throw new ApplicationException("Customer with VAT number " + vat + " does not exist.");
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error obtaining customer with VAT number " + vat, e);
		} 
	}

	
	/**
	 * @param customerId The customerId to get the discount type of
	 * @return The DiscountType associated with the customer with customerId id
	 * @throws ApplicationException When the customer with customerId is not found or when there is 
	 * some obscure internal error (not on this example version; wait until a real database comes
	 * into play).
	 */
	public DiscountType getDiscountType (int customerId) throws ApplicationException {
		try {
			TableData td = persistence.customerTableGateway.find(customerId);
			if (!td.isEmpty())
				return persistence.customerTableGateway.readDiscountType(td.iterator().next());
			else 
				throw new ApplicationException("Internal error obtaining customer with id " + customerId + ".");
		} catch (PersistenceException e) {
			throw new ApplicationException ("Internal error obtaining customer with id " + customerId + ".", e);
		} 
	}
	

	/**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat The VAT number to checked.
	 * @return Whether the VAT number is valid. 
	 */
	public boolean isValidVAT(int vat) {
		// If the number of digits is not 9, error!
		if (vat < 100000000 || vat > 999999999)
			return false;

		// If the first number is not 1, 2, 5, 6, 8, 9, error!
		int firstDigit = vat / 100000000;
		if (firstDigit != 1 && firstDigit != 2 && 
				firstDigit != 5 && firstDigit != 6 &&
				firstDigit != 8 && firstDigit != 9)
			return false;

		// Checks the congruence modules 11.
		int sum = 0;
		int checkDigit = vat % 10;
		vat /= 10;

		for (int i = 2; i < 10 && vat != 0; i++) {
			sum += vat % 10 * i;
			vat /= 10;
		}

		int checkDigitCalc = 11 - sum % 11;
		if (checkDigitCalc == 10)
			checkDigitCalc = 0;
		return checkDigit == checkDigitCalc;
	}
}
