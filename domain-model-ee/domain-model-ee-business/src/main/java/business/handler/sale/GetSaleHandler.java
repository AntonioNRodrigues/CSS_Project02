package business.handler.sale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import business.catalog.SaleCatalog;
import business.persistence.entities.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.IGetSaleHandlerRemote;

@Stateless
@WebService
public class GetSaleHandler implements IGetSaleHandlerRemote {

    /**
     * The sale's catalog
     */
    @EJB
    private SaleCatalog saleCatalog;

    public Sale getSale(int saleId) throws ApplicationException {
        try {
            Sale sale = saleCatalog.getSale(saleId);
//            sale.setTransactions();
            return sale;
        } catch (Exception e) {
            throw new ApplicationException("Error while searching Sale with id " + saleId, e);
        }
    }
}
