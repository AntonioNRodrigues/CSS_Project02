package business.handler;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.catalog.ProductCatalog;
import business.persistence.entities.Product;
import facade.exceptions.ApplicationException;
import facade.handlers.IGetProductHandlerRemote;

@Stateless
@WebService
public class GetProductHandler implements IGetProductHandlerRemote{

	@EJB private ProductCatalog productsCatalog;
	
	@Override
	public List<Product> getAvailableProducts() throws ApplicationException {
		
		try {
			
			List<Product> list = productsCatalog.getAvailableProducts();
			return list;
		} catch (Exception e) {
			throw new ApplicationException("Erro ao obter produtos disponiveis", e);
		}
		
	}

	
	
}
