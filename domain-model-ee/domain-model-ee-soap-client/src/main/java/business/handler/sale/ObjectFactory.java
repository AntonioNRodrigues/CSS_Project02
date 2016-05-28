
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

    private final static QName _AddSaleResponse_QNAME = new QName("http://sale.handler.business/", "addSaleResponse");
    private final static QName _AddSale_QNAME = new QName("http://sale.handler.business/", "addSale");
    private final static QName _ApplicationException_QNAME = new QName("http://sale.handler.business/", "ApplicationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business.handler.sale
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddSale }
     * 
     */
    public AddSale createAddSale() {
        return new AddSale();
    }

    /**
     * Create an instance of {@link ApplicationException }
     * 
     */
    public ApplicationException createApplicationException() {
        return new ApplicationException();
    }

    /**
     * Create an instance of {@link AddSaleResponse }
     * 
     */
    public AddSaleResponse createAddSaleResponse() {
        return new AddSaleResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "addSaleResponse")
    public JAXBElement<AddSaleResponse> createAddSaleResponse(AddSaleResponse value) {
        return new JAXBElement<AddSaleResponse>(_AddSaleResponse_QNAME, AddSaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "addSale")
    public JAXBElement<AddSale> createAddSale(AddSale value) {
        return new JAXBElement<AddSale>(_AddSale_QNAME, AddSale.class, null, value);
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
