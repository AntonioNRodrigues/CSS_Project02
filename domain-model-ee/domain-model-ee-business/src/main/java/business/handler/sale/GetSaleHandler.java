package business.handler.sale;

import business.catalog.SaleCatalog;
import business.persistence.entities.Sale;
import business.persistence.entities.Transaction;
import facade.exceptions.ApplicationException;
import facade.handlers.IGetSaleHandlerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

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
