package business.handler.sale;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.catalog.CustomerCatalog;
import business.catalog.SaleCatalog;
import business.persistence.entities.Customer;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.IAddSaleHandlerRemote;

@Stateless
@WebService
public class AddSaleHandler implements IAddSaleHandlerRemote{

	@EJB private SaleCatalog saleCatalog;
	
	@EJB private CustomerCatalog customerCatalog;
	
	@Override
	public int addSale(int customerVAT) throws ApplicationException {
		
		try{
			// obtain customer by vat
			Customer customer = customerCatalog.getCustomer(customerVAT);
			
			System.out.println("Customer obtido: " + customer);
			
			// create new sale
			Sale sale = new Sale(new Date(), customer);
			System.out.println("Sale criada: " + sale);
			saleCatalog.addSale(sale);
			System.out.println("Sale adicionada");
			return sale.getId();
			
		} catch (Exception e) {
			throw new ApplicationException("Error creating sale", e);
		}
		
	}

}