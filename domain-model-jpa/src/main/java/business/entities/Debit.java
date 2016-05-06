/**
 * 
 */
package business.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import business.Product;

/**
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 *
 */
@Entity
public class Debit extends Transation {

	public Debit() {
		super();
	}

	/**
	 * @param value
	 * @param date
	 * @param idSale
	 */
	public Debit(double value, Date date) {
		super(value, date);
	}

	public List<Product> getAllProdutsByID() {
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return "DEBIT:: "+ super.toString();
	}

}
