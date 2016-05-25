package presentation.web.model;

import java.util.List;

import business.Customer;

public class NewSaleModel extends Model{

	private List<Customer> customers;
	
	public NewSaleModel(){}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
	
}
