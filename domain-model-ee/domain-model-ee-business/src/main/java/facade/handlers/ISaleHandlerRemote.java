package facade.handlers;

import javax.ejb.Remote;

import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;

@Remote
public interface ISaleHandlerRemote {
	public Sale getSale(int id) throws ApplicationException;

}
