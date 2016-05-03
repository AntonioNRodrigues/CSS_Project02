package business;

import java.util.List;

import business.entities.Account;
import business.entities.AccountCatalog;
import business.entities.Transation;
import business.entities.TransationCatalog;

public class CurrentAccountHandler {

	/**
	 * The sale's catalog
	 */
	private SaleCatalog saleCatalog;

	/**
	 * The customer's catalog
	 */
	private CustomerCatalog customerCatalog;

	/**
	 * The product's catalog
	 */
	private ProductCatalog productCatalog;
	/**
	 * the transtion catalog
	 */
	private TransationCatalog transationCatalog;
	/**
	 * The current sale
	 */
	private Account currentAccount;
	private AccountCatalog accountCatalog;

	public CurrentAccountHandler(SaleCatalog saleCatalog, CustomerCatalog customerCatalog,
			ProductCatalog productCatalog, TransationCatalog transationCatalog, AccountCatalog accountCatalog) {
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
		this.productCatalog = productCatalog;
		this.transationCatalog = transationCatalog;
		this.accountCatalog = accountCatalog;
	}

	public Customer getCustomer(int vat) throws ApplicationException {
		return customerCatalog.getCustomer(vat);
	}

	public Account getAccount(int id_account) throws ApplicationException{
		return accountCatalog.getAccount(id_account);
	}
	public List<Transation> getAllTransations(){
		return null;
	}
}
