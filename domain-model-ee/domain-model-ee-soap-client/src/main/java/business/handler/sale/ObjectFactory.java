
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

    private final static QName _PaySale_QNAME = new QName("http://sale.handler.business/", "paySale");
    private final static QName _PaySaleResponse_QNAME = new QName("http://sale.handler.business/", "paySaleResponse");
    private final static QName _ApplicationException_QNAME = new QName("http://sale.handler.business/", "ApplicationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business.handler.sale
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaySaleResponse }
     * 
     */
    public PaySaleResponse createPaySaleResponse() {
        return new PaySaleResponse();
    }

    /**
     * Create an instance of {@link ApplicationException }
     * 
     */
    public ApplicationException createApplicationException() {
        return new ApplicationException();
    }

    /**
     * Create an instance of {@link PaySale }
     * 
     */
    public PaySale createPaySale() {
        return new PaySale();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaySale }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "paySale")
    public JAXBElement<PaySale> createPaySale(PaySale value) {
        return new JAXBElement<PaySale>(_PaySale_QNAME, PaySale.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaySaleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sale.handler.business/", name = "paySaleResponse")
    public JAXBElement<PaySaleResponse> createPaySaleResponse(PaySaleResponse value) {
        return new JAXBElement<PaySaleResponse>(_PaySaleResponse_QNAME, PaySaleResponse.class, null, value);
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
