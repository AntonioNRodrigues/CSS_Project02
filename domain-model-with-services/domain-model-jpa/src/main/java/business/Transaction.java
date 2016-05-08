package business;

import static javax.persistence.EnumType.STRING;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTION")
@NamedQueries(
		{
			@NamedQuery(name=Transaction.GET_BY_ID, query="select t from Transaction t where t.id =:"+Transaction.ID)
		}
)
public class Transaction {

	public static final String GET_BY_ID = "TransactionGetById";
	
	public static final String ID = "TransactionID";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name="TYPE")
	@Enumerated(STRING) private TransactionType type; 

	@Column(name="VALUE")
	private double value;
	
	@ManyToOne private Sale sale;
	
	@Column(name="CREATED_AT") private Date date;
	
	public Transaction(){}

	public Transaction(int id, TransactionType type, Sale sale, Date date, double value) {
		super();
		this.id = id;
		this.type = type;
		this.sale = sale;
		this.date = date;
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + this.id + " | ");
		if(this.type == TransactionType.CREDIT)
			sb.append("TYPE: CREDIT | ");
		else
			sb.append("TYPE: DEBIT | ");
		sb.append("AMOUNT: " +this.value+ " | ");
		sb.append("DATE: " + this.date);
		return sb.toString();
	}
	
	
	
}
