package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.Sale;
import facade.exceptions.ApplicationException;

@Remote
public interface IGetCustomerSalesHandlerRemote {

	public List<Sale> getCustomerSales(int customerID) throws ApplicationException;
	
}
