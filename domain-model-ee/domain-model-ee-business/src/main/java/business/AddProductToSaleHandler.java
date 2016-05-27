package business;

import facade.exceptions.ApplicationException;
import facade.handlers.IAddProductToSaleHandlerRemote;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService
public class AddProductToSaleHandler implements IAddProductToSaleHandlerRemote {

    public void addProductToSale(Sale sale, Product product, int qty) {

        try {

        } catch (Exception e) {
//            throw new ApplicationException("Error adding customer with VAT ", e);
        }
    }
}
