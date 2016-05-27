package business;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

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
			sale.setStatus(SaleStatus.PAYED);
			saleCatalog.update(sale);
			
			// anota a sale com paga
			double transactionAmount = sale.getTotalValue() - sale.getDiscountValue();
			CreditTransaction transaction = 
					new CreditTransaction(
							sale, sale.getCustomer().getAccount(), 
							transactionAmount, new Date());
			transactionCatalog.addTransaction(transaction);
			
		} catch (Exception e) {
			
			throw new ApplicationException("Erro ao fechar venda", e);
			
		}
		
	}
	
}
