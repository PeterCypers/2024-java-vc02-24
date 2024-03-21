package domein;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
//@NamedQueries ({
//	@NamedQuery(name="Betaling.vindOpId",
//				query="SELECT b FROM Betaling b"
//					+ "WHERE b.orderId = :orderId")
//})
public class Betaling implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;
	
	/*@OneToOne(cascade=CascadeType.PERSIST)
	Bestelling bestelling;*/
	
	private boolean isAfgehandeld;
	
	public Betaling() {}
	
	public Betaling(/*Bestelling bestelling*/ int orderId, boolean isAfgehandeld) {
		setOrderId(orderId);
		setIsAfgehandeld(isAfgehandeld);
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public void setIsAfgehandeld(boolean isAfgehandeld) {
		this.isAfgehandeld = isAfgehandeld;
	}
}
