package coupon.beans;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Coupon implements Serializable{
	private static final long serialVersionUID = 1L;
private long id;
private String title;
private Date startDate; 
private Date endDate; 
private int amount;
private CouponType couponType;
private String message;
private double price;
private String image;
public Coupon() {
	super();
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Date getStartDate() {
	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public CouponType getCouponType() {
	return couponType;
}
public void setCouponType(CouponType couponType) {
	this.couponType = couponType;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
@Override
public String toString() {
	return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
			+ amount + ", couponType=" + couponType + ", message=" + message + ", price=" + price + ", image=" + image
			+ "]";
}
public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType couponType, String message,
		double price, String image) {
	super();
	this.id = id;
	this.title = title;
	this.startDate = startDate;
	this.endDate = endDate;
	this.amount = amount;
	this.couponType = couponType;
	this.message = message;
	this.price = price;
	this.image = image;
}

}
