package business;

import business.entities.Account;
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

	public CurrentAccountHandler(SaleCatalog saleCatalog, CustomerCatalog customerCatalog,
			ProductCatalog productCatalog, TransationCatalog tc) {
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
		this.productCatalog = productCatalog;
		this.transationCatalog = tc;
	}
	

}
