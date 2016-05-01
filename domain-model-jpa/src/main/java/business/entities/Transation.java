package business.entities;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.ApplicationException;
import business.Sale;

/**
 * Entity implementation class for Entity: Transation
 * 
 * @author Antonio Rodrigues
 * @author Simão Neves
 * @author Joao Rodrigues
 * @Group:: css018
 * @Date 2016/04/28
 * 
 */
@Entity
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Transation {

	private static final String DEBIT = "debit";
	private static final String CREDIT = "credit";
	@Id
	@GeneratedValue
	private int id;

	@Column
	private double value;

	@Temporal(TemporalType.DATE)
	private Date date;

	@OneToOne(mappedBy = "trans")
	private Sale sale;

	public Transation() {
	}

	Transation(double value, Date d) {
		this.date = d;
		this.value = value;
	}

	public Transation(double value, Date d, String control) {
		factory(control, value, d);
	}

	/**
	 * Method to build Transations of different types
	 * 
	 * @param control
	 * @param value
	 * @param date
	 * @return
	 * @required String control as
	 */
	public static Transation factory(String control, double value, Date date) {
		return (control.equalsIgnoreCase(DEBIT)) ? new Debit(value, date)
				: (control.equalsIgnoreCase(CREDIT)) ? new Credit(value, date) : null;
	}

	public double getValor() {
		return value;
	}

	public void setValor(double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
