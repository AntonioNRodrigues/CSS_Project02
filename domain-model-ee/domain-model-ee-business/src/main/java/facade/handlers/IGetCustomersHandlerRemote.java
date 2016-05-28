package facade.handlers;

import java.util.List;
import javax.ejb.Remote;

import business.persistence.entities.Customer;
import facade.exceptions.ApplicationException;

@Remote
public interface IGetCustomersHandlerRemote {

	public List<Customer> getCustomers() throws ApplicationException;
	
	public Customer getCustomerByVat(int customerVAT) throws ApplicationException;
	
	public Customer getCustomerById(int customerID) throws ApplicationException;
	
}
