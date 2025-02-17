package com.mtc.online.buspass.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.domain.MtcAdmin;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.interfaces.StudentsInterface;
import com.mtc.online.buspass.repository.CollegeAdminrepo;
import com.mtc.online.buspass.repository.MtcAdminrepo;
import com.mtc.online.buspass.repository.Studentsrepo;
import com.mtc.online.buspass.useful.functions.DateFunctions;

@Service
public class MtcAdminService implements StudentsInterface {
	@Autowired
	MtcAdminrepo mtcadminrepo;
	@Autowired
	Studentsrepo studentrepo;
	@Autowired
	CollegeAdminrepo collegeadminrepo;
	
	@Autowired
	DateFunctions datefunction;
	
	public MtcAdmin verifyAdmin(MtcAdmin enteredadmin) {
		BCryptPasswordEncoder bcryptpassword =new BCryptPasswordEncoder();
		MtcAdmin inDbadmin=mtcadminrepo.getAdmin(enteredadmin.getDepotname(),enteredadmin.getDepotnumber());
		if(inDbadmin==null) {
			
			return null;
		}
		
		String indbPassword=inDbadmin.getAdminpassword();
		String enteredPassword=enteredadmin.getAdminpassword();
		boolean verified= bcryptpassword.matches(enteredPassword,indbPassword);

		if(verified) {
			System.out.println("Verified");
			return inDbadmin;
		}
		else {
			System.out.println("Not-Verified");
			return null;
		}
		
	}
	public boolean changepass(String name,String password) {
		MtcAdmin changedadmin=mtcadminrepo.getAdmin(name);
		if(changedadmin!=null) {
		BCryptPasswordEncoder bcryptpassword =new BCryptPasswordEncoder();
		String hashedPassword=bcryptpassword.encode(password);
		changedadmin.setAdminpassword(hashedPassword);
		mtcadminrepo.save(changedadmin);
		return true;
		}
		else {
			return false;
		}
	}
	public List<CollegeAdmin> getCollegeswithDepot(String depotname){
		return collegeadminrepo.findBydepotname(depotname);
	}
	
	public List<CollegeAdmin> getCollegeSubmitted(String depotname){
		return collegeadminrepo.findBySubmitted(true,depotname);
	}
	public List<CollegeAdmin> getCollegeToApprove(String depotname){
		return collegeadminrepo.findByCollegeNotAppoved(false,depotname);
	}
	
	public CollegeAdmin getCollegeDetails(String collegename){
		return collegeadminrepo.findByCollegeName(collegename);
	}
	
	
	public Page<Students> findByCollgePaginated(int pageNo, int pageSize, String college) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return this.studentrepo.findByCollege(college,pageable);
	}
	
	public List<CollegeAdmin> getApprovedCollege(String depotname) {
		return collegeadminrepo.findByIsapproved(true,depotname);
	}
	
	public boolean tobeDisabled(CollegeAdmin collegdetail) {
		Date approoveddate=collegdetail.getApprovedate();
	    Long noofdays=datefunction.diffInday(approoveddate);
	   // System.out.println("noof days"+noofdays);
	    if(noofdays>5) {
	    	return true;
	    }
	    else {
	    	return false;	
	    }
	}
	
	public List<CollegeAdmin> findcollegetoDisable(String depotname){
			List<CollegeAdmin> collegesapproved=collegeadminrepo.getCollegesApproved(depotname,true);
			List<CollegeAdmin> todisableList=new LinkedList<CollegeAdmin>();
			for(int i=0;i<collegesapproved.size();i++) {
				boolean istobedisabled=tobeDisabled(collegesapproved.get(i));
				//System.out.println(collegesapproved.get(i));
				if(istobedisabled==true) {
					todisableList.add(collegesapproved.get(i));
					//System.out.println(collegesapproved.get(i).getCollegename());
				}
			}
		return todisableList;
	}
	
	public CollegeAdmin findcollegetoApprove(String college,boolean state) {
		CollegeAdmin clgadmin=collegeadminrepo.findByCollegeName(college);
		clgadmin.setApproved(state);
		collegeadminrepo.save(clgadmin);
		return clgadmin;
	}
	public MtcAdmin getmtcAdmin(String depotname) {
		MtcAdmin mtcadmin=mtcadminrepo.getAdmin(depotname);
		return mtcadmin;
	}
	
	public List<CollegeAdmin> findByVerified(String depotame){
		return collegeadminrepo.getCollegesVerified(depotame, true);
	}
	
	public CollegeAdmin findcollegeByEmail(String collegeemail){
		return collegeadminrepo.getCollegeBYEmail(collegeemail);
	}
	public Students getStudentByAppNo(BigInteger applicationno) {
	
		return studentrepo.findByApplicationNo(applicationno);
	}
	
	public Students setAmountFoStudent(BigInteger appno,String college,int amount) {
		Students stud=studentrepo.findByApplicationNoandCollege(appno, college);
		stud.setMonthlypayment(amount);
		studentrepo.save(stud);
		return stud;
	}
}
