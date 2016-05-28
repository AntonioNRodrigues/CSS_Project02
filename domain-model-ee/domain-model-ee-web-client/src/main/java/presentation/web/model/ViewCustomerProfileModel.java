package presentation.web.model;

import business.persistence.entities.Customer;

public class ViewCustomerProfileModel extends Model{

	private Customer customer;

	public ViewCustomerProfileModel(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
