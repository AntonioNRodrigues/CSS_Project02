package presentation.web.model;

import java.util.LinkedList;

import facade.exceptions.ApplicationException;
import facade.handlers.IAddCustomerHandlerRemote;
import facade.interfaces.IDiscount;

/**
 * Helper class to assist in the response of novo cliente.
 * This class is the response information expert.
 * 
 * @author fmartins
 *
 */
public class NewCustomerModel extends Model {

	private String designation;
	private String vatNumber;
	private String phoneNumber;
	private String discountType;
	private IAddCustomerHandlerRemote addCustomerHandler;
		
	public void setAddCustomerHandler(IAddCustomerHandlerRemote addCustomerHandler) {
		this.addCustomerHandler = addCustomerHandler;
	}
	
	public void setDesignation(String designation) {
		this.designation = designation;	
	}
	
	public String getDesignation() {
		return designation;
	}
	
	public String getVATNumber() {
		return vatNumber;
	}
	
	public void setVATNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountType() {
		return discountType;
	}
	
	public void clearFields() {
		designation = vatNumber = phoneNumber = "";
		discountType = "1";
	}
	
	public Iterable<? extends IDiscount> getDiscounts () {
		try {
			return addCustomerHandler.getDiscounts();
		} catch (ApplicationException e) {
			return new LinkedList<IDiscount> ();		
		}
	}	
}
