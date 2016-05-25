package facade.handlers;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface ICloseSaleHandlerRemote {

	public void closeSale(int saleId) throws ApplicationException;
	
}
