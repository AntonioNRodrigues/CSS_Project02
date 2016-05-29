package facade.handlers;

import javax.ejb.Remote;

import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;

@Remote
public interface IViewSaleHandlerRemote {
	public Sale getSale(int id) throws ApplicationException ;
}
