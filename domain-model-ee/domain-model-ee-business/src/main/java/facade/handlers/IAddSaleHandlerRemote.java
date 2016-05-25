package facade.handlers;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IAddSaleHandlerRemote {

	public void addSale(int customerVAT) throws ApplicationException;
	
}
