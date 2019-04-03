package coupon.beans;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	private long id;
	private String custName;
	private String password;
	private Set <Coupon> coupons = new LinkedHashSet<>();
	public Customer() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password ;
	}
	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	
}
