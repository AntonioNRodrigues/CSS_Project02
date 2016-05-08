package business;

import java.util.Date;
import java.util.List;

import business.entities.Account;
import business.entities.AccountCatalog;
import business.entities.Transation;
import business.entities.TransationCatalog;

/**
 * Handles use case process sale (version with two operations: newSale followed
 * by an arbitray number of addProductToSale)
 * 
 * @author fmartins
 *
 */
public class ProcessSaleHandler {

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
	private Sale currentSale;

	private AccountCatalog accountCatalog;

	/**
	 * Creates a handler for the process sale use case given the sale, customer,
	 * and product catalogs.
	 * 
	 * @param saleCatalog
	 *            A sale's catalog
	 * @param customerCatalog
	 *            A customer's catalog
	 * @param productCatalog
	 *            A product's catalog
	 */
	public ProcessSaleHandler(SaleCatalog saleCatalog, CustomerCatalog customerCatalog, ProductCatalog productCatalog,
			TransationCatalog tc, AccountCatalog accountCatalog) {
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
		this.productCatalog = productCatalog;
		this.transationCatalog = tc;
		this.accountCatalog = accountCatalog;
	}

	/**
	 * Creates a new sale
	 * 
	 * @param vatNumber
	 *            The customer's VAT number for the sale
	 * @throws ApplicationException
	 *             In case the customer is not in the repository
	 */
	public void newSale(int vatNumber) throws ApplicationException {
		Customer customer = customerCatalog.getCustomer(vatNumber);
		currentSale = saleCatalog.newSale(customer);
	}

	/**
	 * Adds a product to the current sale
	 * 
	 * @param prodCod
	 *            The product code to be added to the sale
	 * @param qty
	 *            The quantity of the product sold
	 * @throws ApplicationException
	 *             When the sale is closed, the product code is not part of the
	 *             product's catalog, or when there is not enough stock to
	 *             proceed with the sale
	 */
	public void addProductToSale(int prodCod, double qty) throws ApplicationException {
		Product product = productCatalog.getProduct(prodCod);
		currentSale = saleCatalog.addProductToSale(currentSale, product, qty);
	}

	/**
	 * @return The sale's discount, according to the customer discount type
	 * @throws ApplicationException
	 *             When some persistence error occurs
	 */
	public double getSaleDiscount() throws ApplicationException {
		return currentSale.discount();
	}

	/**
	 * @return The sale's total, before discount.
	 * @throws ApplicationException
	 *             When some persistence error occurs
	 */
	public double getSaleTotal() {
		return currentSale.total();
	}

	/**
	 * method to close the sale
	 * @return  id of the closed Sale or -1 for errors
	 * @throws ApplicationException
	 */
	public int closeSale(int vat) throws ApplicationException {

		currentSale.setSatus(SaleStatus.CLOSED);
		
		Customer customer = customerCatalog.getCustomer(vat);

		Account account = customer.getAccount();

		Transation transation = null;
		
		try {
			// new Transation
			transation = Transation.factory(
					"debit", (getSaleTotal() - getSaleDiscount()), new Date(), currentSale);
			
			transationCatalog.addTransation(transation);

			currentSale.addTransationSale(transation);
			
			account.addTransation(transation);
			
			accountCatalog.updateAccount(account);

			saleCatalog.updateSale(currentSale);

			customerCatalog.updateCustomer(customer);

			return currentSale.getIdSale();
		
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		return -1;

	}

	/**
	 * method to pay the sale 
	 * @param idSale 
	 * @return true is is payed and false if is not
	 * @throws ApplicationException
	 */
	public boolean paySale(int idSale, int vat) throws ApplicationException {
		Sale sale = saleCatalog.getSale(idSale);

		if (sale.getStatusPayment() == PaymentStatus.NOT_PAYDED) {
			sale.setStatusPayment(PaymentStatus.PAYED);

			Transation transation = 
					Transation.factory("credit", getSaleTotal() - getSaleDiscount(), new Date(), sale);
		
			transationCatalog.addTransation(transation);

			sale.addTransationSale(transation);

			saleCatalog.updateSale(sale);

			Customer c = customerCatalog.getCustomer(vat);
			
			Account account = c.getAccount();

			account.addTransation(transation);

			accountCatalog.updateAccount(account);

		} else {
			System.out.println("Sale already payed");
		}

		return true;
	}
}
