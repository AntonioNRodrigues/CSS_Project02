package business;

import java.util.ArrayList;
import java.util.List;

import dataaccess.CustomerRowDataGateway;
import dataaccess.PersistenceException;
import dataaccess.SaleRowDataGateway;
import dataaccess.SaleTransactionRowDataGateway;
import domain.Account;
import domain.CreditTransaction;
import domain.DebitTransaction;
import domain.Transaction;

/**
 * Handles customer transactions. 
 * Each public method implements a transaction script.
 * 
 * @author fmartins
 * @version 1.3 (1/04/2016)
 *
 */
public class CustomerTransactionScripts {
	
	/**
	 * Adds a new customer. It checks that there is no other customer in the system
	 * with the same VAT.
	 * 
	 * @param vat The VAT number of the customer
	 * @param denomination The customer's name
	 * @param phoneNumber The customer's phone 
	 * @param discountType The type of discount applicable for the customer
	 * @throws ApplicationException When the VAT number is already in the database, when the VAT number is
	 * invalid (we check according to the Portuguese legislation), or when there is some unexpected
	 * SQL error.
	 */
	public void addCustomer (int vat, String denomination, int phoneNumber, DiscountType discountType) 
			throws ApplicationException {
		// Checks if it is a valid VAT number
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
		
		// Checks that denomination and phoneNumber and filled in
	    if (!isFilled (denomination) || phoneNumber == 0)
	      throw new ApplicationException(
	             "Both denomination and phoneNumber must be filled");

		// Tries to stores the customer in the database. There is a 
		// unique key constraint associated with the VAT number, so if the 
		// VAT number already exists in the database a persistence exception will
		// be thrown. In this simple application I'm not distinguishing an 
		// internal SQL error from the duplicate key error, but it could be done
		// because the JDBC throws different exception. For now, we keep it simple.
		// This is the proper way, in this case, to handle unique values in a concurrent 
		// environment, called the optimistic approach. Since I'm not expecting many 
		// insertions of customers with the same VAT number, I simple assume that the number 
		// is not in the database. In situations where a high number of collisions is expected, 
		// the best way is to follow a pessimistic approach, which relies in locking 
		// the table, checking insertion success, and unlocking the table at the end of the
		// operation. This topic will be discussed in greater detail in the context of object 
		// relational mapping (ORM).
		// Anyway, be careful that testing for the existence and then inserting 
		// (without locking or other suitable method for handling concurrency) introduces a 
		// race condition in the application.
		try {
			CustomerRowDataGateway newCustomer = new CustomerRowDataGateway (vat, denomination, phoneNumber, discountType);
			newCustomer.insert();
		} catch (PersistenceException e) {
			// Another important this to notice is exception wrapping. Notice that the persistence 
			// exception thrown by the underlying JDBC API is not discarded; instead, it is wrapped 
			// under an application exception. This way we still know the precise problem that occurred 
			// but prevent from showing low-level exceptions to the end users. It is not pleasant at
			// all for a user to see an SQL exception in her browser window.
				throw new ApplicationException ("Error inserting the customer into the database", e);
		}
	}

	
	/**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat The number to be checked.
	 * @return Whether the VAT number is valid. 
	 */
	private boolean isValidVAT(int vat) {
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
	
	/**
	 * Checks is a string is filled
	 * 
	 * @param value The String to check
	 * @return true if the string is not empty (and not null!)
	 */
	private boolean isFilled(String value) {
		return value != null && !value.isEmpty();
	}

	/**
	 * Gets customer account transactions
	 * by customer's vat
	 * 
	 * @param vat, customer's vat
	 * @return a list with customer transactions
	 * 
	 * @throws ApplicationException
	 */
	public Account getAccountInfo(int vat) throws ApplicationException{
		
		try {
			// get customer by id
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerByVATNumber(vat);
			
			// get customer sales
			List<SaleRowDataGateway> customerSales = 
					SaleRowDataGateway.getSalesByCustomerId(customer.getCustomerId());
			
			// get customer sales transactions
			List<Transaction> transactions = new ArrayList<>();
			for(SaleRowDataGateway s : customerSales)
			{
				// for each sale get it's transactions
				List<SaleTransactionRowDataGateway> l = 
						SaleTransactionRowDataGateway.getSaleTransactionsBySaleId(s.getId());
				for(SaleTransactionRowDataGateway t : l)					
					if(t.getType() == TransactionType.CREDIT)
					transactions.add(
							new CreditTransaction(
									t.getId(), t.getSaleId(), 
									t.getValue(), 
									t.getCreatedAt()));
					else
						transactions.add(
								new DebitTransaction(
										t.getId(), t.getSaleId(), 
										t.getValue(), 
										t.getCreatedAt()));
				
			}
			
			// transactions
			return new Account(customer.getCustomerId(), transactions);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new ApplicationException("Error getting customer balance");
		}
	}
	
	
	private static double computeCustomerBalance(
			List<SaleTransactionRowDataGateway> transactions){
		
		double res = 0;
		
		for(SaleTransactionRowDataGateway st : transactions)
			if(st.getType() == TransactionType.CREDIT)
				res += st.getValue();
			else
				res -= st.getValue();
		
		
		return res;
		
	}
	
}
