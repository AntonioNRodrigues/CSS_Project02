package business.handler.sale.product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.catalog.ProductCatalog;
import business.catalog.SaleCatalog;
import business.persistence.entities.Product;
import business.persistence.entities.Sale;
import business.persistence.entities.SaleProduct;
import facade.exceptions.ApplicationException;
import facade.handlers.IInsertSaleProductHandlerRemote;

@Stateless
@WebService
public class InsertSaleProductHandler implements IInsertSaleProductHandlerRemote{

	@EJB private SaleCatalog saleCatalog;
	
	@EJB private ProductCatalog productCatalog;
	
	@Override
	public void insertSaleProduct(int saleId, int prodCod) throws ApplicationException{
		
		try {
			// obtain product by prodCod
			Product product = productCatalog.getProductByCod(prodCod);
			
			// obtain sale by id
			saleCatalog.addProductToSale(product, saleId);
			
		} catch (Exception e) {
			
			throw new ApplicationException("Handler: Erro ao adicionar product to sale", e);
			
		}
		
	}
	
}
