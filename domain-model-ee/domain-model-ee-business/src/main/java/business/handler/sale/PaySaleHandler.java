package business.handler.sale;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.SaleStatus;
import business.TransactionType;
import business.catalog.SaleCatalog;
import business.catalog.TransactionCatalog;
import business.persistence.entities.Sale;
import business.persistence.entities.Transaction;
import facade.exceptions.ApplicationException;
import facade.handlers.IPaySaleHandlerRemote;

@Stateless
@WebService
public class PaySaleHandler implements IPaySaleHandlerRemote{

	@EJB private SaleCatalog saleCatalog;
	
	@EJB private TransactionCatalog transactionCatalog;
	
	public void paySale(int saleId) throws ApplicationException{
		
		try {
			
			// paga sale
			Sale sale = saleCatalog.getSale(saleId);
			
			// validate sale status
			if(!sale.isClosed())
				throw new ApplicationException("A Sale já foi paga...");
			
			sale.setStatus(SaleStatus.PAYED);
			saleCatalog.update(sale);
			
			// anota a sale com paga
			double transactionAmount = sale.getTotalValue() - sale.getDiscountValue();
			Transaction transaction = 
					new Transaction(
							sale, TransactionType.CREDIT, sale.getCustomer().getAccount(), 
							transactionAmount, new Date());
			transactionCatalog.addTransaction(transaction);
			
		} catch (Exception e) {
			
			throw new ApplicationException("Erro ao fechar venda", e);
			
		}
		
	}
	
}
