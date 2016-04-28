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
	 * @param d
	 * @param idSale
	 */
	public Debit(double value, Date d, int idSale) {
		super(value, d, idSale);
	}

	public List<Product> getAllProdutsByID() {
		return new ArrayList<>();
	}

}
