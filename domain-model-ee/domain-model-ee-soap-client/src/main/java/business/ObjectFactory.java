
package business;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the business package. 
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

    private final static QName _AddCustomerResponse_QNAME = new QName("http://business/", "addCustomerResponse");
    private final static QName _ApplicationException_QNAME = new QName("http://business/", "ApplicationException");
    private final static QName _AddCustomer_QNAME = new QName("http://business/", "addCustomer");
    private final static QName _GetDiscounts_QNAME = new QName("http://business/", "getDiscounts");
    private final static QName _GetDiscountsResponse_QNAME = new QName("http://business/", "getDiscountsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: business
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddCustomer }
     * 
     */
    public AddCustomer createAddCustomer() {
        return new AddCustomer();
    }

    /**
     * Create an instance of {@link ApplicationException }
     * 
     */
    public ApplicationException createApplicationException() {
        return new ApplicationException();
    }

    /**
     * Create an instance of {@link AddCustomerResponse }
     * 
     */
    public AddCustomerResponse createAddCustomerResponse() {
        return new AddCustomerResponse();
    }

    /**
     * Create an instance of {@link GetDiscountsResponse }
     * 
     */
    public GetDiscountsResponse createGetDiscountsResponse() {
        return new GetDiscountsResponse();
    }

    /**
     * Create an instance of {@link GetDiscounts }
     * 
     */
    public GetDiscounts createGetDiscounts() {
        return new GetDiscounts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://business/", name = "addCustomerResponse")
    public JAXBElement<AddCustomerResponse> createAddCustomerResponse(AddCustomerResponse value) {
        return new JAXBElement<AddCustomerResponse>(_AddCustomerResponse_QNAME, AddCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://business/", name = "ApplicationException")
    public JAXBElement<ApplicationException> createApplicationException(ApplicationException value) {
        return new JAXBElement<ApplicationException>(_ApplicationException_QNAME, ApplicationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://business/", name = "addCustomer")
    public JAXBElement<AddCustomer> createAddCustomer(AddCustomer value) {
        return new JAXBElement<AddCustomer>(_AddCustomer_QNAME, AddCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDiscounts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://business/", name = "getDiscounts")
    public JAXBElement<GetDiscounts> createGetDiscounts(GetDiscounts value) {
        return new JAXBElement<GetDiscounts>(_GetDiscounts_QNAME, GetDiscounts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDiscountsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://business/", name = "getDiscountsResponse")
    public JAXBElement<GetDiscountsResponse> createGetDiscountsResponse(GetDiscountsResponse value) {
        return new JAXBElement<GetDiscountsResponse>(_GetDiscountsResponse_QNAME, GetDiscountsResponse.class, null, value);
    }

}
