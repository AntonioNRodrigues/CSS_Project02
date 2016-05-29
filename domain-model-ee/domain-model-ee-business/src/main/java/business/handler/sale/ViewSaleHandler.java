package business.handler.sale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.catalog.SaleCatalog;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.IViewSaleHandlerRemote;

@Stateless
public class ViewSaleHandler implements IViewSaleHandlerRemote{
	@EJB
	private SaleCatalog salecat;
	
	public Sale getSale(int id) throws ApplicationException {
		return salecat.getSale(id);
	}
	
}
