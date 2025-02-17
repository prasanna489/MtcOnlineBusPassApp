package com.mtc.online.buspass.domain;

import java.beans.Transient;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class Students {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;
	
	private BigInteger applicationno;
	private String college;
	private String applydate;
	private String firstname;
	private String lastname;
	private String email;
	private String gender;
	private BigInteger registernumber;
	private String department;
	private String gradutiontype;
	private String yearofstudy;
	private String dob;
	private int age;
	private String address;
	private Integer pincode;
	private BigInteger phonenumber;
	private String imagepath;
	
	private String clgidfilename;
	private String clgidfiletype;
	@Lob
	private byte clgidfiledata[];
	
	private String addressproofname;
	private String addressprooftype;
	@Lob
	private byte addressproofdata[];
	
	private String addressproofothername;
	private String asddressproofothertype;
	@Lob
	private byte addressproofotherdata[];
	
	private int monthlypayment;
	private boolean addressproofother;
	private String typeofroute;
	private String fromcollege;
	private String tomidstop;
	private String tohome;
	private boolean approved;
	private boolean feepay;
	private boolean monthpay;
	
	
	public Integer getStudentId() {
		return studentId;
	}
	
	public BigInteger getApplicationno() {
		return applicationno;
	}

	public void setApplicationno(BigInteger applicationno) {
		this.applicationno = applicationno;
	}

	public String getCollege() {
		return college;
	}


	public void setCollege(String college) {
		this.college = college;
	}


	public String getApplydate() {
		return applydate;
	}
	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public BigInteger getRegisternumber() {
		return registernumber;
	}
	public void setRegisternumber(BigInteger registernumber) {
		this.registernumber = registernumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getGradutiontype() {
		return gradutiontype;
	}
	public void setGradutiontype(String gradutiontype) {
		this.gradutiontype = gradutiontype;
	}
	public String getYearofstudy() {
		return yearofstudy;
	}
	public void setYearofstudy(String yearofstudy) {
		this.yearofstudy = yearofstudy;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public BigInteger getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(BigInteger phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public String getClgidfilename() {
		return clgidfilename;
	}
	public void setClgidfilename(String clgidfilename) {
		this.clgidfilename = clgidfilename;
	}
	public String getClgidfiletype() {
		return clgidfiletype;
	}
	public void setClgidfiletype(String clgidfiletype) {
		this.clgidfiletype = clgidfiletype;
	}
	public byte[] getClgidfiledata() {
		return clgidfiledata;
	}
	public void setClgidfiledata(byte[] clgidfiledata) {
		this.clgidfiledata = clgidfiledata;
	}
	public String getAddressproofname() {
		return addressproofname;
	}
	public void setAddressproofname(String addressproofname) {
		this.addressproofname = addressproofname;
	}
	public String getAddressprooftype() {
		return addressprooftype;
	}
	public void setAddressprooftype(String addressprooftype) {
		this.addressprooftype = addressprooftype;
	}
	public byte[] getAddressproofdata() {
		return addressproofdata;
	}
	public void setAddressproofdata(byte[] addressproofdata) {
		this.addressproofdata = addressproofdata;
	}
	public String getAddressproofothername() {
		return addressproofothername;
	}
	public void setAddressproofothername(String addressproofothername) {
		this.addressproofothername = addressproofothername;
	}
	public String getAsddressproofothertype() {
		return asddressproofothertype;
	}
	public void setAsddressproofothertype(String asddressproofothertype) {
		this.asddressproofothertype = asddressproofothertype;
	}
	public byte[] getAddressproofotherdata() {
		return addressproofotherdata;
	}
	public void setAddressproofotherdata(byte[] addressproofotherdata) {
		this.addressproofotherdata = addressproofotherdata;
	}
	
	public int getMonthlypayment() {
		return monthlypayment;
	}

	public void setMonthlypayment(int monthlypayment) {
		this.monthlypayment = monthlypayment;
	}

	public boolean isAddressproofother() {
		return addressproofother;
	}

	public void setAddressproofother(boolean addressproofother) {
		this.addressproofother = addressproofother;
	}

	public String getTypeofroute() {
		return typeofroute;
	}
	public void setTypeofroute(String typeofroute) {
		this.typeofroute = typeofroute;
	}
	public String getFromcollege() {
		return fromcollege;
	}
	public void setFromcollege(String fromcollege) {
		this.fromcollege = fromcollege;
	}
	public String getTomidstop() {
		return tomidstop;
	}
	public void setTomidstop(String tomidstop) {
		this.tomidstop = tomidstop;
	}
	public String getTohome() {
		return tohome;
	}
	public void setTohome(String tohome) {
		this.tohome = tohome;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	public boolean isFeepay() {
		return feepay;
	}

	public void setFeepay(boolean feepay) {
		this.feepay = feepay;
	}

	public boolean isMonthpay() {
		return monthpay;
	}

	public void setMonthpay(boolean monthpay) {
		this.monthpay = monthpay;
	}

	//constructor lines
	public Students() {
		super();
	}
	public Students(String addressproofothername, String asddressproofothertype, byte[] addressproofotherdata,int a) {
		super();
		this.addressproofothername = addressproofothername;
		this.asddressproofothertype = asddressproofothertype;
		this.addressproofotherdata = addressproofotherdata;
	}
	public Students(String clgidfilename, String clgidfiletype, byte[] clgidfiledata,String b) {
		super();
		this.clgidfilename = clgidfilename;
		this.clgidfiletype = clgidfiletype;
		this.clgidfiledata = clgidfiledata;
	}
	public Students(String addressproofname, String addressprooftype, byte[] addressproofdata) {
		super();
		this.addressproofname = addressproofname;
		this.addressprooftype = addressprooftype;
		this.addressproofdata = addressproofdata;
	}
	
	@Transient
	public String getStdImg() {
		if(imagepath == null || registernumber == null ) return null;	
		   System.out.println(imagepath+""+registernumber);
		return "/stud-logo/"+registernumber+"/"+imagepath;
	}
	@Transient
	public String getStudentImg() {
		if(imagepath == null || registernumber == null ) return null;	
		   System.out.println(imagepath+""+registernumber);
		return "./stud-logo/"+registernumber+"/"+imagepath;
	}
	
}
