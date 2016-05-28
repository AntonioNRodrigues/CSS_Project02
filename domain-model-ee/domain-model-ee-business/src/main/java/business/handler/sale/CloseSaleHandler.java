package business.handler.sale;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.SaleStatus;
import business.catalog.SaleCatalog;
import business.catalog.TransactionCatalog;
import business.persistence.entities.Account;
import business.persistence.entities.Customer;
import business.persistence.entities.DebitTransaction;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.ICloseSaleHandlerRemote;

@Stateless
@WebService
public class CloseSaleHandler implements ICloseSaleHandlerRemote{
	
	@EJB private SaleCatalog saleCatalog;
	
	@EJB private TransactionCatalog transactionCatalog;
	
	@Override
	public void closeSale(int saleId) throws ApplicationException {
		
		try{
			
			// obtem sale 
			Sale sale = saleCatalog.getSale(saleId);
			double saleTotal = sale.calculateTotal();
			double saleDiscount = sale.discount();
			
			
			// get customer
			Customer customer = sale.getCustomer();
			Account account = customer.getAccount();
			
			// generate debit transaction
			DebitTransaction transaction = 
					new DebitTransaction(
							sale, account, 
							saleTotal - saleDiscount, 
							new Date());
			transactionCatalog.addTransaction(transaction);
			
			
			
			// set sale status to closed
			sale.setStatus(SaleStatus.CLOSED);
			sale.setTotalValue(saleTotal);
			sale.setDiscountValue(saleDiscount);
			
			// update sale
			saleCatalog.update(sale);
			
		} catch (Exception e) {
			throw new ApplicationException("Error closing sale", e);
		}
		
	}

	
	
}
