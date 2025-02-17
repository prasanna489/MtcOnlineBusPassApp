package com.mtc.online.buspass.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MtcAdmin {
	@Id
	private Integer depotnumber;
	
	private String depotname;
	private String adminpassword;
	
	public Integer getDepotnumber() {
		return depotnumber;
	}
	public void setDepotnumber(Integer depotnumber) {
		this.depotnumber = depotnumber;
	}
	public String getDepotname() {
		return depotname;
	}
	public void setDepotname(String depotname) {
		this.depotname = depotname;
	}
	public String getAdminpassword() {
		return adminpassword;
	}
	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}
	

}
