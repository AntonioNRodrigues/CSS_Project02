package facade.handlers;


import business.persistence.entities.Product;
import business.persistence.entities.Sale;

import javax.ejb.Remote;

@Remote
public interface IAddProductToSaleHandlerRemote {

    void addProductToSale(Sale sale, Product product, int qty);

}
