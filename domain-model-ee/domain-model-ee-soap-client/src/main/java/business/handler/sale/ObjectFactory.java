
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

    private final static QName _CloseSaleResponse_QNAME = new QName("http://sale.handler.business/", "closeSaleResponse");
    private final static QName _ApplicationException_QNAME = new QName("http://sale.handler.business/", "ApplicationException");
    private final static QName _CloseSale_QNAME = new QName("http://sale.handler.business/", "closeSale");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business.handler.sale
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CloseSale }
     * 
     */
    public CloseSale createCloseSale() {
        return new CloseSale();
    }

    /**
     * Create an instance of {@link ApplicationException }
     * 
     */
    public ApplicationException createApplicationException() {
        return new ApplicationException();
    }

    /**
     * Create an instance of {@link CloseSaleResponse }
     * 
     */
    public CloseSaleResponse createCloseSaleResponse() {
        return new CloseSaleResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "closeSaleResponse")
    public JAXBElement<CloseSaleResponse> createCloseSaleResponse(CloseSaleResponse value) {
        return new JAXBElement<CloseSaleResponse>(_CloseSaleResponse_QNAME, CloseSaleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "ApplicationException")
    public JAXBElement<ApplicationException> createApplicationException(ApplicationException value) {
        return new JAXBElement<ApplicationException>(_ApplicationException_QNAME, ApplicationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "closeSale")
    public JAXBElement<CloseSale> createCloseSale(CloseSale value) {
        return new JAXBElement<CloseSale>(_CloseSale_QNAME, CloseSale.class, null, value);
    }

}
