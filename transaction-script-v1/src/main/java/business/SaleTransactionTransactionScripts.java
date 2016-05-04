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

public class SaleTransactionTransactionScripts {

	public <T extends Transaction> T getTransactionDetails(int transactionId) 
		throws ApplicationException{
		
		try{
			
			SaleTransactionRowDataGateway st = SaleTransactionRowDataGateway.getTransactionById(transactionId);
			
			// if is a credit transaction
			if(st.getType() == TransactionType.CREDIT)
				return (T) convertToCreditTransaction(st);
			else
				return (T) convertToDebitTransaction(st);
			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error getting transaction details.", e);
		}
	}
	
	private CreditTransaction convertToCreditTransaction(SaleTransactionRowDataGateway st){
		return new CreditTransaction(st.getId(), st.getSaleId(), st.getValue(), st.getCreatedAt());
	}
	
	private DebitTransaction convertToDebitTransaction(SaleTransactionRowDataGateway st)
		throws ApplicationException{
		
		try{			
			// get sale products
			Set<SaleProductRowDataGateway> saleProducts = SaleProductRowDataGateway.getSaleProducts(st.getSaleId());
			
			
			// get sale products
			List<SaleProduct> products = new ArrayList<>();
			for(SaleProductRowDataGateway s : saleProducts){				
				ProductRowDataGateway p = new ProductRowDataGateway().getProductById(s.getProductId());
				SaleProduct sp = new SaleProduct(p.getProductId(), s.getQty(), p.getDescription(), (p.getFaceValue() * s.getQty()));
				products.add(sp);
			}
			
			return new DebitTransaction(st.getId(), st.getSaleId(), st.getValue(), st.getCreatedAt(), products);

			
		} catch (PersistenceException e) {
			throw new ApplicationException("Error converting to debit transaction", e);
		}
		
	}
	

}
