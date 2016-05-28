package business;

import business.catalog.SaleCatalog;
import business.persistence.entities.Sale;
import facade.handlers.IGetSaleHandlerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService
public class getSaleHandler implements IGetSaleHandlerRemote {

    /**
     * The customer's catalog
     */
    @EJB
    private SaleCatalog saleCatalog;

    public Sale getSale() {

//        try {
//            saleCatalog.getSale(vat, denomination, phoneNumber, discount);
//        } catch (Exception e) {
//            throw new ApplicationException("Error adding customer with VAT " + vat, e);
//        }
        return null;
    }
}
