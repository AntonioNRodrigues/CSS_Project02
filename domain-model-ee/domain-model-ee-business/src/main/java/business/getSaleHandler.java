package business;

import facade.exceptions.ApplicationException;
import facade.interfaces.IGetSaleHandler;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService
public class getSaleHandler implements IGetSaleHandler {

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
