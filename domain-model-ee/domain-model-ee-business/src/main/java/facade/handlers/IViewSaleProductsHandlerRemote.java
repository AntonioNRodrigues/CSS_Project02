package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.persistence.entities.SaleProduct;
import facade.exceptions.ApplicationException;

@Remote
public interface IViewSaleProductsHandlerRemote {

	public List<SaleProduct> viewSaleProducts(int saleID) throws ApplicationException;

}
