package com.teaShop.bean;

import com.teaShop.utils.Pagination;

public class ColdStorageTenant extends Pagination{
    private String renterId;

    private String renterName;

    private String idPhoto;

    private String name;

    private String phone;

    private String email;

    private String businessRange;

    private String id1;

    private String id1Pic;

    private String id2;

    private String id2Pic;

    private String id3;

    private String id3Pic;

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId == null ? null : renterId.trim();
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName == null ? null : renterName.trim();
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto == null ? null : idPhoto.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBusinessRange() {
        return businessRange;
    }

    public void setBusinessRange(String businessRange) {
        this.businessRange = businessRange == null ? null : businessRange.trim();
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1 == null ? null : id1.trim();
    }

    public String getId1Pic() {
        return id1Pic;
    }

    public void setId1Pic(String id1Pic) {
        this.id1Pic = id1Pic == null ? null : id1Pic.trim();
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2 == null ? null : id2.trim();
    }

    public String getId2Pic() {
        return id2Pic;
    }

    public void setId2Pic(String id2Pic) {
        this.id2Pic = id2Pic == null ? null : id2Pic.trim();
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3 == null ? null : id3.trim();
    }

    public String getId3Pic() {
        return id3Pic;
    }

    public void setId3Pic(String id3Pic) {
        this.id3Pic = id3Pic == null ? null : id3Pic.trim();
    }

	@Override
	public String toString() {
		return "ColdStorageTenant [renterId=" + renterId + ", renterName="
				+ renterName + ", idPhoto=" + idPhoto + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", businessRange="
				+ businessRange + ", id1=" + id1 + ", id1Pic=" + id1Pic
				+ ", id2=" + id2 + ", id2Pic=" + id2Pic + ", id3=" + id3
				+ ", id3Pic=" + id3Pic + "]";
	}
    
    
}