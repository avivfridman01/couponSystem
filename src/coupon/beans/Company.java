package coupon.beans;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Company implements Serializable{
	private static final long serialVersionUID = 1L;
private long id;
private String compName;
private String password;
private String email;
private Set <Coupon> coupons = new LinkedHashSet<>();
public Company() {
	super();
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCompName() {
	return compName;
}
public void setCompName(String compName) {
	this.compName = compName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Set<Coupon> getCoupons() {
	return coupons;
}
public void setCoupons(Set<Coupon> coupons) {
	this.coupons = coupons;
}
@Override
public String toString() {
	return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email ;
}
public Company(long id, String compName, String password, String email) {
	super();
	this.id = id;
	this.compName = compName;
	this.password = password;
	this.email = email;
}

}
