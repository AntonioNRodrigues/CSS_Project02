package business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import facade.exceptions.ApplicationException;
import facade.handlers.IGetCustomersHandlerRemote;

@Stateless
@WebService
public class GetCustomersHandler implements IGetCustomersHandlerRemote{

	@EJB private CustomerCatalog customerCatalog;
	
	@Override
	public List<Customer> getCustomers() throws ApplicationException{
		
		try {
			
			return customerCatalog.getAll();
			
		} catch (Exception e) {
			throw new ApplicationException("CATALOG: Error getting customers", e);
		}
		
	}
	
	@Override
	public Customer getCustomerByVat(int customerVAT) throws ApplicationException{
		
		try {
			
			return customerCatalog.getCustomer(customerVAT);
			
		} catch (Exception e) {
			throw new ApplicationException("Error getting customers", e);
		}
		
	}

	@Override
	public Customer getCustomerById(int customerID) throws ApplicationException {
		
		try {
			
			return customerCatalog.getCustomerById(customerID);
			
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter customer por id", e);
		}
		
	}
	
}
