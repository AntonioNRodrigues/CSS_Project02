package presentation.web.model;

import java.util.List;

import business.Customer;

public class ViewCustomersModel extends Model{

	private List<Customer> customers;
	
	public ViewCustomersModel(List<Customer> customers){
		this.customers = customers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	
	
}
