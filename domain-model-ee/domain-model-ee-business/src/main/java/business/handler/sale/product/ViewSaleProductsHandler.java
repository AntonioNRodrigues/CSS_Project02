package business.handler.sale.product;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import business.handler.sale.GetSaleHandler;
import business.persistence.entities.SaleProduct;
import facade.exceptions.ApplicationException;
import facade.handlers.IViewSaleProductsHandlerRemote;

@Stateless
@WebService
public class ViewSaleProductsHandler implements IViewSaleProductsHandlerRemote {

	@Override
	public List<SaleProduct> viewSaleProducts(int saleID) throws ApplicationException {
		return new GetSaleHandler().getSale(saleID).getSaleProducts();
	}

}
