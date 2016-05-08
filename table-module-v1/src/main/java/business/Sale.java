package business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dataaccess.Persistence;
import dataaccess.PersistenceException;
import dataaccess.TableData;
import dataaccess.TableData.Row;

public class Sale extends TableModule {

	/**
	 * Constructs a sale module given the persistence repository
	 * 
	 * @param persistence The persistence repository
	 */
	public Sale (Persistence persistence) {
		super(persistence);
	}
	
	/**
	 * Add a new sale.
	 * Notice the interaction with the Customer module. We use the
	 * customer module to get the customer id of the customer with
	 * a given VAT number. 
	 * 
	 * @param vat The VAT number of the customer the sale belongs to
	 * @return The internal sale id so that products may be added to the sale
	 * @throws ApplicationException When there is no customer with the given
	 * VAT number or when there is an unexpected error add the sale.
	 */
	public int newSale (int vat) throws ApplicationException {
		try {
			Customer customer = new Customer(persistence);
			int customerId = customer.getCustomerId(vat);
			return persistence.saleTableGateway.newSale(customerId);
		} catch (PersistenceException e) {
			throw new ApplicationException("There was an internal error adding the sale", e);
		}
	}
	
	/**
	 * @param saleId The sale id to check if it is closed
	 * @return If the sale is closed
	 * @throws ApplicationException When the sale does not exist or some obscure
	 * error has occurred.
	 */
	public boolean isClosed(int saleId) throws ApplicationException {
		try {
			TableData td = getSale (saleId);
			return !persistence.saleTableGateway.readStatus(td.iterator().next()).equals(SaleStatus.OPEN);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error checking if sale with id " + saleId + " is closed", e);
		}
	}
	
	/**
	 * Add a product to a sale.
	 * Mind the usage of the Product module to get the product id from the product code
	 * and to further update its stock existence.
	 * 
	 * @param saleId The sale id to add a product to
	 * @param productCode The product to be added to the sale
	 * @param qty The quantity sold of the product
	 * @throws ApplicationException When the sale is closed, the product code does not
	 * exist or some internal error occurred while saving the data.
	 */
	public void addProductToSale(int saleId, int productCode, double qty) 
			throws ApplicationException {
		// Business rule: products can be only added to open sales
		if (isClosed (saleId))
			throw new ApplicationException("Cannot add products to a closed sale.");		
		// take units from the stock
		int productId = 0;
		try {
			persistence.beginTransaction();
			Product product = new Product(persistence);
			productId = product.getProductId(productCode);
			product.takeFromStock (productId, qty);
			// add the product to the sale
			new SaleProduct(persistence).addProductToSale(saleId, productId, qty);
			persistence.commit();
		} catch (PersistenceException e) {
			try {
				persistence.rollback();
			} catch (PersistenceException e1) {
				throw new ApplicationException("Internal error with selling product id " + productId, e);			
			}
			throw new ApplicationException("Internal error with selling product id " + productId, e);			
		}
	}
	
	/**
	 * @param saleId The sale to compute the discount.
	 * @return Compute the discount of the sale. 
	 * @throws ApplicationException When some referential integrity problem occurs. This
	 * might happen if a foreign key is not found, for instance.
	 */
	public double getSaleDiscount (int saleId) throws ApplicationException {
		// If the sale is closed, the discount is already computed
		if (isClosed (saleId)) 
			return getDiscount(saleId);

		try {
			TableData td = persistence.saleProductTableGateway.getSaleProducts(saleId);
			// compute the sale discount
			return computeDiscount(saleId, td);
		} catch (PersistenceException | SQLException e) {
			throw new ApplicationException("Internal referential integrity error when computing "
				+ "the discount for sale " + saleId, e);			
		}
	}

	/**
	 * @param saleId The sale id to compute the discount
	 * @param td The result set containing the information about the product of the sale
	 * @return The discount based on the customer information
	 * @throws ApplicationException If the customer does not exist 
	 * @throws SQLException or PersistenceException If some obscure problem occurs. This exception is not going to be
	 * sent to the application layer. Instead it will be trapped by the public method.
	 */
	private double computeDiscount(int saleId, TableData td) throws ApplicationException, SQLException, PersistenceException  {
		Customer customer = new Customer(persistence);
		switch (customer.getDiscountType(getCustomerId(saleId))) {
		case SALE_AMOUNT:
			return discountOnSaleAmount (td);
		case ELIGIBLE_PRODUCTS:
			return discountOnEligibleProducts (td);
		default:
			return 0;
		}
	}

	/**
	 * @param saleId The sale to get the customer id from
	 * @return The customer id of the sale
	 * @throws ApplicationException In case an error occurs when retrieving the
	 * information from the database.
	 */
	public int getCustomerId(int saleId) throws ApplicationException {
		try {
			TableData td = getSale(saleId);
			return persistence.saleTableGateway.readCustomerId(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error when trying to get the customer id of sale id " + saleId, e);
		}
	}

	public double getSaleTotal(int saleId) throws ApplicationException {
		// If the sale is closed, the total is already computed
		if (isClosed (saleId))
			return getTotal(saleId);

		try {
			TableData td = persistence.saleProductTableGateway.getSaleProducts(saleId);
			// compute the sale total
			double totalSum = 0;
			Product product = new Product(persistence);
			for (Row r : td) {
				int productId = persistence.saleProductTableGateway.readProductId(r);
				double quantity = persistence.saleProductTableGateway.readQuantity(r);
				totalSum += product.getFaceValue(productId) * quantity;
			}
			return totalSum;

		} catch (PersistenceException e) {
			throw new ApplicationException("Internal referential integrity error when computing "
					+ " the total of the sale with id " + saleId, e);
		}
	}

	/**
	 * @param saleId The sale id to obtain the total amount. When the sale is closed
	 * the total is computed and stored in an attribute so it need not to be computed
	 * every time.
	 * @return The discount of the sale (when closed)
	 * @throws ApplicationException When some persistence error occurs.
	 */
	private double getTotal(int saleId) throws ApplicationException {
		try {
			TableData td = getSale(saleId);
			return persistence.saleTableGateway.readTotal(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error when trying to get the total of a sale with id " + saleId, e);
		}
	}


	/**
	 * Computes the type 1 discount
	 * @param td The result set with the sold products
	 * @return The discount value
	 * @throws SQLException When some unexpected error occurs.
	 * @throws ApplicationException
	 * @throws PersistenceException
	 */
	private double discountOnSaleAmount(TableData td) 
				throws SQLException, ApplicationException, PersistenceException {
		double saleTotal = 0;
		Product product = new Product(persistence);
		for (Row r : td) 
			saleTotal +=  persistence.saleProductTableGateway.readQuantity(r) * 
					product.getFaceValue(persistence.saleProductTableGateway.readProductId(r));
		
		ApplicationSettings appSettings = new ApplicationSettings(persistence);
		return saleTotal > appSettings.getAmountThreshold() ? 
					saleTotal * appSettings.getAmountThresholdPercentage() : 0;
	}

	/**
	 * Computes the type 2 discount
	 * @param td The result set with the sold products 
	 * @return The discount value
	 * @throws SQLException When some unexpected error occurs.
	 * @throws ApplicationException 
	 * @throws PersistenceException 
	 */
	private double discountOnEligibleProducts(TableData td) 
					throws SQLException, ApplicationException, PersistenceException {
		// If the sale is closed, the discount is already computed
		double saleEligibleTotal = 0;
		Product product = new Product(persistence);
		for (Row r : td) {
			int productId = persistence.saleProductTableGateway.readProductId(r);
			if (product.isEligibleForDiscount(productId))
				saleEligibleTotal += persistence.saleProductTableGateway.readQuantity(r) * 
					product.getFaceValue(productId);
		}

		ApplicationSettings appSettings = new ApplicationSettings(persistence);
		return saleEligibleTotal * appSettings.getEligiblePercentage();
	}

	/**
	 * @param saleId The sale id to obtain the discount amount. When the sale is closed
	 * the discount is computed and stored in an attribute so it need not to be computed 
	 * every time. 
	 * @return The discount of the sale (when closed)
	 * @throws ApplicationException When some persistence error occurs.
	 */
	private double getDiscount(int saleId) throws ApplicationException {
		try {
			TableData td = getSale(saleId);
			return persistence.saleTableGateway.readDiscount(td.iterator().next());
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error obtaining the discount for sale with id " + saleId, e);
		}
	}

	/**
	 * A service method to get a sale by id.
	 * 
	 * @param saleId The sale id of the sale to be fetched
	 * @return The result set with the sale matching the sale id
	 * @throws ApplicationException The sale does not exist or some 
	 * database error occurs.
	 * @ensures !TableData.isEmpty()
	 */
	public TableData getSale(int saleId) throws ApplicationException {
		try {
			TableData td = persistence.saleTableGateway.find(saleId);
			if (td.isEmpty()) 
				throw new ApplicationException("Sale with internal id " + saleId + " does not exist.");
			return td;
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error when retrieving sale with internal id " + saleId, e);
		} 
	}

	/**
	 * Operation where a new Transaction payment is made
	 * Validates if sale is closed and not payed
	 * @param saleId    The Id of the sale we want to create a Transaction for
	 * @return the id of the new Transaction that was made
	 * @throws ApplicationException
     */
	public int makePayment(int saleId) throws ApplicationException {
		// Verifica que a venda não foi paga
		PaymentTransaction paymentTransaction = new PaymentTransaction(persistence);
		if (!isClosed(saleId))
			throw new ApplicationException("Sale not yet closed!");
		if (isPayed(saleId))
			throw new ApplicationException("Sale already payed!");

		// Marca a sale como paga
		double total = getTotal(saleId);
		double discount = getDiscount(saleId);
		updateSale(saleId, SaleStatus.PAYED, total, discount);

		try {
			// Gera uma transacção do cliente, incluindo valor, data, descrição e id da sale
			int transactionId = paymentTransaction.newTransaction(saleId, total - discount);
			return transactionId;
		} catch (PersistenceException e) {
			throw new ApplicationException("There was an error making a payment transaction", e);
		}
	}

	/**
	 * Checks if the sale with saleId has been payed
	 * @param saleId Id of the sale we want to check
	 * @return True if the sale has PAYED status
	 * @throws ApplicationException
     */
	private boolean isPayed(int saleId) throws ApplicationException {
		try {
			TableData td = getSale (saleId);
			return persistence.saleTableGateway.readStatus(td.iterator().next()).equals(SaleStatus.PAYED);
		} catch (PersistenceException e) {
			throw new ApplicationException("Internal error checking if sale with id " + saleId + " is payed", e);
		}
	}

	/**
	 * Updates the sale with saleId with a new total, discount and status
	 * @param saleId The sale Id of the sale we want to update
	 * @param status New status
	 * @param total New total
	 * @param discount New discount
	 * @return True is the sale was successfully updated, false otherwise
	 * @throws ApplicationException
     */
	public boolean updateSale(int saleId, SaleStatus status, double total, double discount) throws ApplicationException {
		try {
			return persistence.saleTableGateway.updateSale(saleId, status, total, discount);
		} catch (PersistenceException e) {
			throw new ApplicationException("There was an internal error updating the sale with id " + saleId, e);
		}
	}

	/**
	 * Close Sale operation, where it calculates the values of the total and discount
	 * and creates a new Transaction of the DEBIT type
	 * @param saleId The sale Id of the sale we want to close
	 * @return id of the new Transaction created
	 * @throws ApplicationException
     */
	public int closeSale(int saleId) throws ApplicationException {
		// Calcular valor final atraves da soma do valor dos produtos da venda e aplicar desconto
		double finalTotal = getSaleTotal(saleId);
		double finalDiscount = getSaleDiscount(saleId);

		// Marcar sale como closed e actualizar valores
		updateSale(saleId, SaleStatus.CLOSED, finalTotal, finalDiscount);

		// Gerar transaction de debito, que o cliente tem a pagar
		DebitTransaction debitTransaction = new DebitTransaction(persistence);
		try {
			return debitTransaction.newTransaction(saleId, finalTotal - finalDiscount);
		} catch (PersistenceException e) {
			throw new ApplicationException("There was an error creating a DebitTransaction in closing a sale", e);
		}
	}

	/**
	 * Get all the sale Ids from a Customer, from it's ID
	 *
	 * @param customerId Id of the customer that has the sales we want
	 * @return List of int that represent the sale Ids of the Customer
	 * @throws ApplicationException
     */
	public List<Integer> getAllSaleIdsFromCustomer(int customerId) throws ApplicationException {
		try {
			List<Integer> list = new ArrayList<>();
			TableData td = persistence.saleTableGateway.getAllSalesFromCustomer(customerId);
			Iterator<Row> iter = td.iterator();
			while (iter.hasNext()) {
				Row row = iter.next();
				list.add(persistence.saleTableGateway.readId(row));
			}
			return list;
		} catch (PersistenceException e) {
			throw new ApplicationException("There was an error creating a DebitTransaction in closing a sale", e);
		}
	}

	/**
	 * Textual representation of a Sale
	 * @param row Row that has all the data from a Sale
	 * @return String that represents the Sale
	 * @throws PersistenceException
     */
	public String print(TableData.Row row) throws PersistenceException {
		return this.persistence.saleTableGateway.readId(row) + " | "
				+ this.persistence.saleTableGateway.readDate(row) + " | "
				+ (this.persistence.saleTableGateway.readTotal(row) - this.persistence.saleTableGateway.readDiscount(row));
	}
}
