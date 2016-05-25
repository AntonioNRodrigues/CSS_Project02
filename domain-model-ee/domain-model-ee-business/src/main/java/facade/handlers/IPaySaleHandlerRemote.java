package facade.handlers;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IPaySaleHandlerRemote {

	public void paySale(int saleId) throws ApplicationException;
	
}
