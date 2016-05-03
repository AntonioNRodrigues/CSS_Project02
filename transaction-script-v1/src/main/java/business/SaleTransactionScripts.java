package business;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;

import dataaccess.ConfigurationRowDataGateway;
import dataaccess.CustomerRowDataGateway;
import dataaccess.DataSource;
import dataaccess.PersistenceException;
import dataaccess.ProductRowDataGateway;
import dataaccess.RecordNotFoundException;
import dataaccess.SaleProductRowDataGateway;
import dataaccess.SaleRowDataGateway;
import dataaccess.SaleTransactionRowDataGateway;

/**
 * Handles sales' transactions. 
 * Each public method implements a transaction script.	
 *	
 * Notes:
 * 1. Check the transaction control in the addProductToSale method.
 * I need to make sure that both tables were updated consistently: products table (the product stock)
 * and the sales table (insert the sales record). If some error occurs, the transaction 
 * is rolled back and an exception is thrown wrapping the underlying error 
 * for debugging purposes. See the catch 6 lines below the throw that wraps the
 * specific exception in a more general message that can be shown to the end user.
 * This way, the end user is spared of low-level details that only make sense
 * to a specialized user. 
 *	 
 * @author fmartins
 * @version 1.3 (1/04/2016)
 *
 */
public class SaleTransactionScripts {
	
	/**
	 * Starts a new sale for a given customer identified by its VAT number
	 * 
	 * @param vat The VAT number of the customer the sale belongs to
	 * @return The id of the new sale. This id is useful for adding products 
	 * to the sale and for computing the sale's total discount, etc.
	 * @throws ApplicationException When the customer does not exist or 
	 * when an unexpected SQL error occurs.
	 */
	public int newSale (int vat) throws ApplicationException {
		
		try {
			// gets the customer number having its vat number
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerByVATNumber (vat);
			// creates the sale
			SaleRowDataGateway newSale = new SaleRowDataGateway(customer.getCustomerId(), new Date());
			newSale.insert();
			return newSale.getId();
		} catch (RecordNotFoundException e) {
			throw new ApplicationException("There is no customer with the given VAT number " + vat);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error adding sale", e);
		}
	}

	
	/**
	 * Add a product to an open sale.
	 * 
	 * @param saleId The sale's id.
	 * @param productCode The product's code.
	 * @param qty The amount to sell.
	 * @throws ApplicationException When the sale is closed, the product code does not exist,
	 * there's not enough amount of product to be sold, or when same unexpected SQL error occurs. 
	 * 
	 * @pre: qty > 0
	 */
	public void addProductToSale(int saleId, int productCode, double qty) throws ApplicationException {
		try {
			// Checks if a sale with saleId exists and its status is still open
			SaleRowDataGateway sale = getSale(saleId);
			if (sale.getStatus() == SaleStatus.CLOSED)
				throw new ApplicationException("Products cannot be added to closed sales.");

			// gets the product id from its code
			ProductRowDataGateway product = getProduct(productCode);
			
			// if it has available units in stock...
			// Introduces a race condition, but we will solve it using JPA. Stay tuned.
			// A race condition?!? what is that?! 
			// A method has a race condition when its correctness depends on the order 
			// of execution of the multiple threads/processes that are running it.
			// In this case, suppose that there are two customers adding the same product
			// to (maybe different) sales. Furthermore, imagine that the stock for the 
			// product is 100 units and both customers want to buy 90 of them. At a first 
			// glance we may argue that this is impossible due to the following if statement, 
			// but picture the following scenario: 
			// 1. the first customer executes the getProduct method and creates the 
			// row data gateway in memory where there is information about 100 units 
			// of the product
			// 2. then the second customer also executes the getProduct method (before
			// the first updates the product's stock) and creates its own copy of the
			// row data gateway in memory also with information about 100 units of the
			// product
			// 3. Of course the product.getQty() >= qty guard is going to true in 
			// both threads and both will decrease the stock of the product ending 
			// in a negative value
			// Notice that this method can work correctly as long as the update occurs 
			// before the next getProduct for the same product, which might mean that
			// it can work correctly for years and then exhibit an error. 
			if (product.getQty() >= qty) {
				try {
					// JDBC Begin transaction
					DataSource.INSTANCE.beginTransaction();
					decreaseStockValue(product, qty);
					addProductToSale(product, sale, qty);
					// JDBC commit
					DataSource.INSTANCE.commit();
				} catch (PersistenceException e) {
					DataSource.INSTANCE.rollback();
					throw e;
				}
			} else
				throw new ApplicationException("Product with code " + productCode + " has ("  + 
					product.getQty() + ") units, which is insufficient for the request of " + 
					qty + " units.");
		} catch (PersistenceException e) {
				throw new ApplicationException("Internal error selling product with code " + productCode, e);
		}		
	}
	
	/**
	 * Updates product stock.
	 * 
	 * @param product The product being sold
	 * @param qty The number of units to be sale
	 * @throws PersistenceException When there is an error updating the stock value.
	 */
	private void decreaseStockValue(ProductRowDataGateway product, double qty) 
			throws PersistenceException {
		product.setQty(product.getQty() - qty);
		product.updateStockValue();
	}

	
	/**
	 * Associates a product to a sale. 
	 * 
	 * @param product The product being sold
	 * @param sale The sale to associate the product sale with
	 * @throws PersistenceException When there is an error inserting the sale product into the database
	 */
	private void addProductToSale(ProductRowDataGateway product, SaleRowDataGateway sale, double qty) throws PersistenceException {
		// adds product to sale 
		SaleProductRowDataGateway saleProduct = new SaleProductRowDataGateway(sale.getId(), 
						product.getProductId(), qty);
		saleProduct.insert();
	}

	
	/**
	 * Gets a SaleRowGateway object for a given sales id, or raises a 
	 * RecordNotFoundException otherwise.
	 * 
	 * @param saleId The sale id to get the SaleRowGateway object
	 * @return The SaleRowGateway object associated with the given saleId 
	 * @throws ApplicationException When there is no sale with saleId 
	 */
	private SaleRowDataGateway getSale(int saleId) throws ApplicationException {
		try {
			return SaleRowDataGateway.getSaleById(saleId);
		} catch (PersistenceException e) {
			throw new ApplicationException("Sale with id: " + saleId + " does not exist.");
		}
	}
	
	/**
	 * Gets a ProductRowGateway object for a given productCode, or raises a 
	 * RecordNotFoundException otherwise.
	 * 
	 * @param productCode The product code to get the ProductRowGateway object
	 * @return The ProductRowGateway object associated with the give productCode
	 * @throws ApplicationException When there is no product with the given productCode
	 */
	private ProductRowDataGateway getProduct(int productCode)
			throws ApplicationException {
		try {
			return ProductRowDataGateway.getProductByProdCod(productCode);
		} catch (PersistenceException e) {
			throw new ApplicationException("Product with code " + productCode + " does not exist.");
		}
	}

		
	/**
	 * Computes the discount amount for a sale (based on the discount type of the customer).
	 * 
	 * @param saleId The sale's id
	 * @return The discount amount
	 * @throws ApplicationException When the sale does not exist or when an unexpected 
	 * referential integrity problem occurs.
	 */
	public double getSaleDiscount (int saleId) throws ApplicationException {
		try {
			SaleRowDataGateway sale = getSale(saleId);
			// If the sale is closed, the discount is already computed
			if (sale.getStatus() == SaleStatus.CLOSED) 
				return sale.getDiscount();
			
			// Get customer associated with the sale. 
			// The customer always exists due to the referential integrity enforced by the database
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerById(sale.getClientId());
					
			// Get sale's products
			Iterable<SaleProductRowDataGateway> saleProducts = SaleProductRowDataGateway.getSaleProducts(saleId);
		
			// Compute the sale discount 
			return computeDiscount(customer, saleProducts);	
		} catch (PersistenceException e) { 
			throw new ApplicationException("Internal referential integrity error when computing "
					+ "the discount for sale " + saleId, e);			
		}
	}

	/**
	 * Closes a sale and generates a debit transaction
	 * to the created sale
	 * 
	 * @param saleId, sale's id to be closed
	 * 
	 * @throws ApplicationException
	 */
	public void closeSale(int saleId) throws ApplicationException{
		
		try{
			// verify if sale is created
			SaleRowDataGateway sale = SaleRowDataGateway.getSaleById(saleId);
			
			// obtain customer associated to sale
			CustomerRowDataGateway customer = CustomerRowDataGateway.getCustomerById(sale.getClientId());
			
			// obtain all sale products
			Set<SaleProductRowDataGateway> saleProducts = SaleProductRowDataGateway.getSaleProducts(saleId);
			
			// compute total sale discount value
			double discount = this.computeDiscount(customer, saleProducts);
			
			// compute total sale value
			double total = this.computeSaleTotal(saleProducts, p -> true);
			
			// update sale values
			sale.setDiscount(discount);
			sale.setStatus(SaleStatus.CLOSED);
			sale.setTotal(total);
			sale.update();
			
			// generate credit transaction
			SaleTransactionRowDataGateway st = 
					new SaleTransactionRowDataGateway(saleId, TransactionType.DEBIT, 
							sale.getTotal() - sale.getDiscount());
			st.insert();
			
			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error closing sale", e);
		}
		
	}
	
	public void makePayment(int saleId, double amount) throws ApplicationException{
		
		try{
			// verify if sale is created and closed
			SaleRowDataGateway sale = SaleRowDataGateway.getSaleById(saleId);
			if(sale.getStatus() != SaleStatus.CLOSED)
				throw new ApplicationException("A sale must be closed before "
						+ "it can be payed. Please close it.");
			
			// generates a credit transaction to that sale
			SaleTransactionRowDataGateway st = 
					new SaleTransactionRowDataGateway(saleId, TransactionType.CREDIT, amount);
			st.insert();
			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error making payment.", e);
		}
		
	}
	
	/**
	 * Computes the discount for the products of a sale to a customer
	 * 
	 * @param customer The customer the sale is made to
	 * @param saleProducts The products being sold
	 * @return The discount amount based on the discount type associated to the customer
	 * @throws PersistenceException When there is an internal referential integrity problem
	 */
	private double computeDiscount(CustomerRowDataGateway customer, 
			Iterable<SaleProductRowDataGateway> saleProducts) throws PersistenceException {
		switch (customer.getDiscountType()) {
		case SALE_AMOUNT:
			return discountOnSaleAmount (saleProducts);
		case ELIGIBLE_PRODUCTS:
			return discountOnEligibleProducts (saleProducts);
		default:
			return 0;
		}
	}

	
	/**
	 * Computes a discount based on the sale's amount.
	 * 
	 * @param saleProducts The products on which to compute the discount
	 * @return The discount value
	 * @throws PersistenceException When there is an internal referential integrity problem
	 */
	private double discountOnSaleAmount(Iterable<SaleProductRowDataGateway> saleProducts) 
			throws PersistenceException {
		// Determines the sale's total. Mind the Java 8 lambda function.
		// In this case it selects all products.
		double saleTotal = computeSaleTotal(saleProducts, p -> true);
		
		// get configuration info.
		ConfigurationRowDataGateway config = ConfigurationRowDataGateway.getConfiguration();
			
		return saleTotal > config.getAmountThreshold() ? saleTotal * config.getAmountThresholdPercentage() : 0;
	}

	/**
	 * Computes a discount based on the amount of eligible products of the sale.
	 * 
	 * @param saleProducts The set with the sold products 
	 * @return The discount value
	 * @throws PersistenceException When some unexpected referential integrity problem occurs.
	 */
	private double discountOnEligibleProducts(Iterable<SaleProductRowDataGateway> saleProducts) 
			throws PersistenceException {
		// Mind the lambda expression. In the case computeSaleTotal filters 
		// the products that are eligible for discount.
		double saleEligibleTotal = computeSaleTotal(saleProducts, p -> p.isEligibleForDiscount());
		
		// get configuration info.
		ConfigurationRowDataGateway config = ConfigurationRowDataGateway.getConfiguration();

		return saleEligibleTotal * config.getEligiblePercentage();
	}

	
	/**
	 * Computes the sale's total of eligible products, or RecordNotFoundException in case there is
	 * a referential integrity problem (the sales refers to a product that does not exits). 
	 * 
	 * Notice the Predicate<...> for receiving the lambda expression and 
	 * the if statement applies the filter passed as a parameter. Available Since Java 8.
	 * 
	 * @param saleProducts The products associated with the sale
	 * @return sale's total of eligible products
	 * @throws PersistenceException When an error occurs fetching the data from the database
	 */
	private double computeSaleTotal(Iterable<SaleProductRowDataGateway> saleProducts,
			Predicate<ProductRowDataGateway> filter)
			throws PersistenceException {
		double saleEligibleTotal = 0;		
		for (SaleProductRowDataGateway saleProduct : saleProducts) {
			// Get product info. Always exists, unless there is an referential integrity problem.
			ProductRowDataGateway product = new ProductRowDataGateway().getProductById(saleProduct.getProductId());
			if (filter.test(product))
				saleEligibleTotal += saleProduct.getQty() * product.getFaceValue();
		}
		return saleEligibleTotal;
	}
}
