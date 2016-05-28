package facade.handlers;

import business.persistence.entities.Sale;

import javax.ejb.Remote;

@Remote
public interface IGetSaleHandlerRemote {
    public Sale getSale();
}
