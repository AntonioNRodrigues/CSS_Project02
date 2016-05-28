
package business.handler.sale.product;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the business.handler.sale.product package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InsertSaleProductResponse_QNAME = new QName("http://product.sale.handler.business/", "insertSaleProductResponse");
    private final static QName _ApplicationException_QNAME = new QName("http://product.sale.handler.business/", "ApplicationException");
    private final static QName _InsertSaleProduct_QNAME = new QName("http://product.sale.handler.business/", "insertSaleProduct");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business.handler.sale.product
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ApplicationException }
     * 
     */
    public ApplicationException createApplicationException() {
        return new ApplicationException();
    }

    /**
     * Create an instance of {@link InsertSaleProduct }
     * 
     */
    public InsertSaleProduct createInsertSaleProduct() {
        return new InsertSaleProduct();
    }

    /**
     * Create an instance of {@link InsertSaleProductResponse }
     * 
     */
    public InsertSaleProductResponse createInsertSaleProductResponse() {
        return new InsertSaleProductResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertSaleProductResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://product.sale.handler.business/", name = "insertSaleProductResponse")
    public JAXBElement<InsertSaleProductResponse> createInsertSaleProductResponse(InsertSaleProductResponse value) {
        return new JAXBElement<InsertSaleProductResponse>(_InsertSaleProductResponse_QNAME, InsertSaleProductResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://product.sale.handler.business/", name = "ApplicationException")
    public JAXBElement<ApplicationException> createApplicationException(ApplicationException value) {
        return new JAXBElement<ApplicationException>(_ApplicationException_QNAME, ApplicationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertSaleProduct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://product.sale.handler.business/", name = "insertSaleProduct")
    public JAXBElement<InsertSaleProduct> createInsertSaleProduct(InsertSaleProduct value) {
        return new JAXBElement<InsertSaleProduct>(_InsertSaleProduct_QNAME, InsertSaleProduct.class, null, value);
    }

}
