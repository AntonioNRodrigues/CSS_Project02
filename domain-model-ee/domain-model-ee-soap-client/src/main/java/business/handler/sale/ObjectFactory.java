
package business.handler.sale;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the business.handler.sale package. 
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

    private final static QName _GetSale_QNAME = new QName("http://sale.handler.business/", "getSale");
    private final static QName _GetSaleResponse_QNAME = new QName("http://sale.handler.business/", "getSaleResponse");
    private final static QName _ApplicationException_QNAME = new QName("http://sale.handler.business/", "ApplicationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business.handler.sale
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
     * Create an instance of {@link GetSale }
     * 
     */
    public GetSale createGetSale() {
        return new GetSale();
    }

    /**
     * Create an instance of {@link GetSaleResponse }
     * 
     */
    public GetSaleResponse createGetSaleResponse() {
        return new GetSaleResponse();
    }

    /**
     * Create an instance of {@link Sale }
     * 
     */
    public Sale createSale() {
        return new Sale();
    }

    /**
     * Create an instance of {@link Transaction }
     * 
     */
    public Transaction createTransaction() {
        return new Transaction();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "getSale")
    public JAXBElement<GetSale> createGetSale(GetSale value) {
        return new JAXBElement<GetSale>(_GetSale_QNAME, GetSale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "getSaleResponse")
    public JAXBElement<GetSaleResponse> createGetSaleResponse(GetSaleResponse value) {
        return new JAXBElement<GetSaleResponse>(_GetSaleResponse_QNAME, GetSaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "ApplicationException")
    public JAXBElement<ApplicationException> createApplicationException(ApplicationException value) {
        return new JAXBElement<ApplicationException>(_ApplicationException_QNAME, ApplicationException.class, null, value);
    }

}
