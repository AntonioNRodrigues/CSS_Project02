package facade.handlers;

import business.Product;
import business.Sale;

import javax.ejb.Remote;

@Remote
public interface IAddProductToSaleHandlerRemote {

    void addProductToSale(Sale sale, Product product, int qty);

}
