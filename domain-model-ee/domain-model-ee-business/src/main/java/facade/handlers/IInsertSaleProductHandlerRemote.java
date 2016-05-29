package facade.handlers;

import javax.ejb.Remote;

import facade.exceptions.ApplicationException;

@Remote
public interface IInsertSaleProductHandlerRemote {

	public void insertSaleProduct(int saleId, int prodCode) throws ApplicationException;
	
	
	
}
