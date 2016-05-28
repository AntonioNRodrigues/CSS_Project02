package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;

@Remote
public interface IViewCustomerSalesHandlerRemote {

	public List<Sale> getCustomerSales(int customerID) throws ApplicationException;
	
}
