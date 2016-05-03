package business;

import java.util.Date;

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
			TransationCatalog tc) {
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
		this.productCatalog = productCatalog;
		this.transationCatalog = tc;
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
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public boolean closeSale(int vat) throws ApplicationException {
		double value = getSaleTotal();
		double discount = getSaleDiscount();

		currentSale.setSatus(SaleStatus.CLOSED);
		Customer customer = customerCatalog.getCustomer(vat);

		Transation transation = null;
		try {
			// new Transation
			transation = Transation.factory("debit", value, new Date());
			// associate the tansation to the current sale
			currentSale.setTransation(transation);
			// get the account of the current user
			Account account = customer.getAccount();
			// associate the transation to the account

			account.addTransation(transation);
			
			transationCatalog.addTransation(transation);
			customerCatalog.updateCustomer(customer);

			return true;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 
	 * @param idSale
	 * @return true is is payed and false if is not
	 * @throws ApplicationException
	 */
	public boolean paymentSale(int vat) throws ApplicationException {
		if (currentSale.getStatusPayment() == PaymentStatus.NOT_PAYDED) {
			currentSale.setStatusPayment(PaymentStatus.PAYED);
		}
		
		
		return true;
	}
}
