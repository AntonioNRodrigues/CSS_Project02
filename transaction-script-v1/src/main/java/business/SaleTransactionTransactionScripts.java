package business;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dataaccess.PersistenceException;
import dataaccess.ProductRowDataGateway;
import dataaccess.SaleProductRowDataGateway;
import dataaccess.SaleTransactionRowDataGateway;
import domain.CreditTransaction;
import domain.DebitTransaction;
import domain.SaleProduct;
import domain.Transaction;

/**
 * This class is a transaction scripts that handles sale transaction operations
 * 
 * @author JoaoR
 *
 */
public class SaleTransactionTransactionScripts {
	
	/**
	 * Gets a transaction by id
	 * 
	 * @param transactionId, transaction by id
	 * @return the corresponding transaction
	 * 
	 * @throws ApplicationException
	 */
	public <T extends Transaction> T getTransactionDetails(int transactionId) 
		throws ApplicationException{
		
		try{
			
			// gets transaction by id
			SaleTransactionRowDataGateway st = SaleTransactionRowDataGateway.getTransactionById(transactionId);
			
			// if is a credit transaction
			if(st.getType() == TransactionType.CREDIT)
				return (T) convertToCreditTransaction(st);
			// if is a debit transaction
			else
				return (T) convertToDebitTransaction(st);
			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error getting transaction details.", e);
		}
	}
	
	/**
	 * Converts a sale transaction into a credit transaction
	 * 
	 * @param st, sale transaction to be considered
	 * @return corresponding credit transaction
	 */
	private CreditTransaction convertToCreditTransaction(SaleTransactionRowDataGateway st){
		return new CreditTransaction(st);
	}
	
	/**
	 * Converts a sale transaction into a debit transaction
	 * 
	 * @param st, sale transaction to be considered
	 * @return corresponding debit transaction wiht all sale products
	 * 
	 * @throws ApplicationException
	 */
	private DebitTransaction convertToDebitTransaction(SaleTransactionRowDataGateway st)
		throws ApplicationException{
		
		try{			
			// get sale products
			Set<SaleProductRowDataGateway> saleProducts = SaleProductRowDataGateway.getSaleProducts(st.getSaleId());
			
			
			// get sale products
			List<SaleProduct> products = new ArrayList<>();
			for(SaleProductRowDataGateway s : saleProducts){				
				ProductRowDataGateway p = new ProductRowDataGateway().getProductById(s.getProductId());
				SaleProduct sp = new SaleProduct(s, p.getDescription(), (p.getFaceValue() * s.getQty()));
				products.add(sp);
			}
			
			return new DebitTransaction(st, products);

			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error converting to debit transaction", e);
		}
		
	}
	

}
