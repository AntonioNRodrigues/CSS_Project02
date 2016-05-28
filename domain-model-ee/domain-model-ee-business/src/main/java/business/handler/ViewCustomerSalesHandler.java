package business.handler;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.catalog.SaleCatalog;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.IViewCustomerSalesHandlerRemote;

@Stateless
@WebService
public class ViewCustomerSalesHandler implements IViewCustomerSalesHandlerRemote{

	@EJB private SaleCatalog saleCatalog;
	
	public List<Sale> getCustomerSales(int customerID) throws ApplicationException{
		
		try {
			
			// obter sales por id de customer
			List<Sale> sales = saleCatalog.getCustomerSalesByID(customerID);
			return sales;
			
		} catch (Exception e) {
			throw new ApplicationException("Catalog: erro ao obter sales de customer", e);
		}
		
	}
	
}
