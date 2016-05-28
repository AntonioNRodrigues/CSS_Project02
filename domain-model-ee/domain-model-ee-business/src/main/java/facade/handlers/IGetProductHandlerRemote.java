package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.persistence.entities.Product;
import facade.exceptions.ApplicationException;

@Remote
public interface IGetProductHandlerRemote {

	/**
	 * Gets all available products according to it's qty 
	 *  
	 * @throws ApplicationException
	 */
	public List<Product> getAvailableProducts() throws ApplicationException;
	
}
