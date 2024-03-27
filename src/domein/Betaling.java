package domein;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries ({
	@NamedQuery(name="Betaling.vindOnverwerkteBetalingen",
				query="SELECT b FROM Betaling b WHERE b.isAfgehandeld = 0")
})
public class Betaling implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;
	
	private boolean isAfgehandeld;
	
	public Betaling() {}
	
	public Betaling(int orderId, boolean isAfgehandeld) {
		setOrderId(orderId);
		setIsAfgehandeld(isAfgehandeld);
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public void setIsAfgehandeld(boolean isAfgehandeld) {
		this.isAfgehandeld = isAfgehandeld;
	}
	
	public int getOrderId() {
		return this.orderId;
	}
}
