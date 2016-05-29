package facade.handlers;

import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;

import javax.ejb.Remote;

@Remote
public interface IGetSaleHandlerRemote {
    public Sale getSale(int saleId) throws ApplicationException;
}
