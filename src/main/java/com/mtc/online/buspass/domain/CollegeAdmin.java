package com.mtc.online.buspass.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class CollegeAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer collegeid;
	
	private String collegename;
	private String collegeaffiliation;
	private String collegeaddress;
	private BigInteger collegetelephone;
	private BigInteger inchargenumber;
	private String collegeemail;
	private String stop1;
	private String stop2;
	private String requestformname;
	private String requestformtype;
	private String collegedepot;
	private String applieddate;
	private boolean approved;
	private Date approvedate;
	private String link;
	private boolean linkenabled;
	private String password;
	private boolean submit;
	private boolean verified;
	
	@Lob
	private byte docdata[];
	
	
	public Integer getCollegeid() {
		return collegeid;
	}

	public String getCollegename() {
		return collegename;
	}

	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}

	public String getCollegeaffiliation() {
		return collegeaffiliation;
	}

	public void setCollegeaffiliation(String collegeaffiliation) {
		this.collegeaffiliation = collegeaffiliation;
	}

	public String getCollegeaddress() {
		return collegeaddress;
	}

	public void setCollegeaddress(String collegeaddress) {
		this.collegeaddress = collegeaddress;
	}

	public BigInteger getCollegetelephone() {
		return collegetelephone;
	}

	public void setCollegetelephone(BigInteger collegetelephone) {
		this.collegetelephone = collegetelephone;
	}

	public BigInteger getInchargenumber() {
		return inchargenumber;
	}

	public void setInchargenumber(BigInteger inchargenumber) {
		this.inchargenumber = inchargenumber;
	}

	public String getCollegeemail() {
		return collegeemail;
	}

	public void setCollegeemail(String collegeemail) {
		this.collegeemail = collegeemail;
	}

	public String getStop1() {
		return stop1;
	}

	public void setStop1(String stop1) {
		this.stop1 = stop1;
	}

	public String getStop2() {
		return stop2;
	}

	public void setStop2(String stop2) {
		this.stop2 = stop2;
	}

	public String getRequestformname() {
		return requestformname;
	}

	public void setRequestformname(String requestformname) {
		this.requestformname = requestformname;
	}

	public String getRequestformtype() {
		return requestformtype;
	}

	public void setRequestformtype(String requestformtype) {
		this.requestformtype = requestformtype;
	}

	public String getCollegedepot() {
		return collegedepot;
	}

	public void setCollegedepot(String collegedepot) {
		this.collegedepot = collegedepot;
	}
	
	public String getApplieddate() {
		return applieddate;
	}

	public void setApplieddate(String string) {
		this.applieddate = string;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public byte[] getDocdata() {
		return docdata;
	}

	public void setDocdata(byte[] docdata) {
		this.docdata = docdata;
	}
	
	
	public Date getApprovedate() {
		return approvedate;
	}
	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isLinkenabled() {
		return linkenabled;
	}

	public void setLinkenabled(boolean linkenabled) {
		this.linkenabled = linkenabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public boolean isSubmit() {
		return submit;
	}

	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	
	

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	//constructor lines
	public CollegeAdmin() {
		super();
	}

	public CollegeAdmin(String requestformname, String requestformtype, byte[] docdata) {
		super();
		this.requestformname = requestformname;
		this.requestformtype = requestformtype;
		this.docdata = docdata;
	}
	
	

}
