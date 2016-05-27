package presentation.web.model;

import java.util.ArrayList;
import java.util.List;

import business.Sale;
import facade.exceptions.ApplicationException;
import facade.handlers.IGetSalesHandlerRemote;

public class ViewCustomerSalesModel extends Model{
	
	/**
	 * Customer VAT
	 */
	private int customerVAT;
	
	/**
	 * Customer id
	 */
	private int customerID;
	
	/**
	 * Customer Designation
	 */
	private String customerDesignation;

	
	/**
	 * Customer Sales list
	 */
	private IGetSalesHandlerRemote getSalesHandler;

	public ViewCustomerSalesModel(){}
	public ViewCustomerSalesModel(int customerVAT, int customerID, String customerDesignation, IGetSalesHandlerRemote getSalesHandler){
		this.customerDesignation = customerDesignation;
		this.customerVAT = customerVAT;
		this.customerID = customerID;
		this.getSalesHandler = getSalesHandler;
	}
	
	
	
	
	public int getCustomerVAT() {
		return customerVAT;
	}
	public void setCustomerVAT(int customerVAT) {
		this.customerVAT = customerVAT;
	}
	public String getCustomerDesignation() {
		return customerDesignation;
	}
	public void setCustomerDesignation(String customerDesignation) {
		this.customerDesignation = customerDesignation;
	}
	
	public List<Sale> getSales(){
		try {			
			List<Sale> sales = this.getSalesHandler.getCustomerSales(customerID);
			System.out.println("SALES NUM: " + sales.size());
			return sales;
		} catch (ApplicationException e) {
			return new ArrayList<Sale>();
		} 
	}
	
}
