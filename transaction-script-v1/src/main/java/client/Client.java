package client;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import SaleSys.SaleSys;
import business.ApplicationException;
import business.DiscountType;
import presentation.CustomerService;
import presentation.ProductService;
import presentation.SaleService;

public class Client {

	// menu options
	private static final int ADD_CUSTOMER = 1;
	private static final int OPEN_SALE = 2;
	private static final int CLOSE_SALE = 3;
	private static final int CHECK_CUSTOMERS = 4;
	private static final int CHECK_ACCOUNT = 5;
	private static final int GO_HOME = 6;
	private static final int QUIT = -1;
	
	// disount type convertion map
	private static final int NO_DISCOUNT_TYPE = 1;
	private static final int ELEGIBLE_DISCOUNT_TYPE = 2;
	private static final int SALE_DISCOUNT_TYPE = 3;
	
	// utils
	private final static String LINE_SEPARATOR = "=============================";
	private final static String SMOOTH_LINE_SEPARATOR = ".............................";
	
	public static void main(String[] args){
		
		SaleSys app = new SaleSys();
		try {
			app.run();
		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
			System.out.println("Application Message: " + e.getMessage());
			SQLException e1 = (SQLException) e.getCause().getCause();
			System.out.println("SQLException: " + e1.getMessage());
			System.out.println("SQLState: " + e1.getSQLState());
			System.out.println("VendorError: " + e1.getErrorCode());
			return;
		}
		
		// initialize available services
		CustomerService cs = app.getCustomerService();
		SaleService ss = app.getSaleService();
		ProductService ps = app.getProductService();
		
		Scanner sc = new Scanner(System.in);
		int option;
		do{
			printMainMenu();
			option = sc.nextInt();
			
			// parse option
			switch(option){
			case ADD_CUSTOMER:
				addCustomer(cs);
				break;
			case OPEN_SALE:
				addSale(ss, ps, cs);
				break;
			case CHECK_CUSTOMERS:
				checkCustomers(cs);
				break;
			}
			
		}while(option != QUIT);
		
		// shutdown system
		app.stopRun();
		System.out.println("Application closed");
		
	}
	
	private static void printMainMenu(){
		System.out.println("=================================");
		System.out.println("MENU");
		System.out.println("Press the corresponding number");
		System.out.println(ADD_CUSTOMER+" - Add new customer");
		System.out.println(OPEN_SALE+" - Open a new sale");
		System.out.println(CLOSE_SALE+" - Close an existing sale");
		System.out.println(CHECK_CUSTOMERS+" - Check existing customers");
		System.out.println(CHECK_ACCOUNT+" - Check user account");
		System.out.println(QUIT+" - Quit");
		System.out.println("=================================");
	}
	
	
	
	// functionalities
	private static void addCustomer(CustomerService cs){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Inseting a new customer");
		
		System.out.print("Insert customer VAT: ");
		int vat = sc.nextInt();
		
		System.out.print("Insert customer denomination: ");
		String denomination = sc.next();
		
		System.out.print("Insert customer phone number: ");
		int phoneNumber = sc.nextInt();
		
		System.out.print("Insert customer discount type: ");
		DiscountType discountType = null;
		while(discountType == null)
		{
			System.out.println("Discount type");
			System.out.println(NO_DISCOUNT_TYPE+" - No discount");
			System.out.println(ELEGIBLE_DISCOUNT_TYPE+" - Elegible discount type");
			System.out.println(SALE_DISCOUNT_TYPE+" - Sale discount type");
			discountType = convertToDiscountType(sc.nextInt());
		}
		
		// persist information
		boolean added = true;
		try {
			cs.addCustomer(vat, denomination, phoneNumber, discountType);
		} catch (ApplicationException e) {
			added = false;
		}finally{
			if(added)
				System.out.println("Customer added!");
			else
				System.out.println("Something went wrong.");
		}
		
	}
	
	private static void checkCustomers(CustomerService cs){
		try{
			// obtain all customers
			List<String> customers = cs.getAllCustomers();
			
			// print them all
			System.out.println("Customers list");
			for(String cur : customers)
				System.out.println(cur);
			
		}catch(ApplicationException e){
			System.out.println("Error when getting all customers.");
		}
			
	}
	
	private static void addSale(SaleService ss, ProductService ps, CustomerService cs){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println(LINE_SEPARATOR);
		checkCustomers(cs);
		
		System.out.print("Insert user VAT: ");
		int vat = sc.nextInt();
		
		try{
			int saleId = ss.newSale(vat);
			
			// ask for products
			boolean done = false;
			do{
				// ask for more products
				System.out.println(LINE_SEPARATOR);
				System.out.println("Add more products or enter "+QUIT+" to finish");
				// print all available products
				printAll(ps.getAvailableProducts());
				System.out.println(LINE_SEPARATOR);
				
				int prodCode = sc.nextInt();  
				if(prodCode == QUIT)
					break;
				
				// ask for quantity
				System.out.print("Quantity: ");
				int qty = sc.nextInt();
				
				// add product to sale
				try{
					ss.addProductToSale(saleId, prodCode, qty);					
				} catch (ApplicationException e){
					
					
					
					System.out.println("Please, do not try to cheat me again :P");
				}
				
			}while(!done);
			
			System.out.println("Would you like to close this sale? Y/n");
			if(sc.next().equals("Y"))
				System.out.println("CLOSED");
			else
				System.out.println("REMAIN OPEN");
			
			// print all
			System.out.println(LINE_SEPARATOR);
			System.out.println("YOUR BASKET HAS");
			System.out.println(SMOOTH_LINE_SEPARATOR);
			List<String> saleProducts = ss.getSaleProducts(saleId);
			for(String prod : saleProducts)
				System.out.println(prod);
			
		}catch(ApplicationException e){
			System.out.print("Error when creating sale");
		}
		
		
	}
	
	private static void printAll(List<String> list){
		for(String cur : list)
			System.out.println(cur);
	}
	
	// verifiers
	private static DiscountType convertToDiscountType(int discount){
		if(discount == ELEGIBLE_DISCOUNT_TYPE)
			return DiscountType.ELIGIBLE_PRODUCTS;
		else if(discount == SALE_DISCOUNT_TYPE)
			return DiscountType.SALE_AMOUNT;
		else if(discount == NO_DISCOUNT_TYPE)
			return DiscountType.NO_DISCOUNT;
		else
			return null;
	}
}
