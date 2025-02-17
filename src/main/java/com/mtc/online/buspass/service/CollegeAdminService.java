package com.mtc.online.buspass.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.repository.CollegeAdminrepo;
import com.mtc.online.buspass.repository.Studentsrepo;
import com.mtc.online.buspass.useful.functions.DateFunctions;

import javassist.bytecode.annotation.NoSuchClassError;

@Service
public class CollegeAdminService  {

	@Autowired
	CollegeAdminrepo collegeadminrepo;
	
	@Autowired
	DateFunctions datefunction;
	@Autowired
	Studentsrepo studentrepo;
	
	BCryptPasswordEncoder bcryptpassword=new BCryptPasswordEncoder();
	
	
	public CollegeAdmin getCollegeAdminservice(Integer clgid ,String college) {
		try {
		CollegeAdmin collegeadmin= collegeadminrepo.getCollegeAdmin(clgid, college);
			return collegeadmin;
		}
		catch(NoSuchClassError e) {
			return null;
		}
	}
	
	public boolean alreadyExists(String college){
		
		CollegeAdmin clgadmin= collegeadminrepo.getCollegeExists(college);
		if(clgadmin==null) {
			return false;
		}
		else {
		return true;
		}
	}

	public CollegeAdmin saveFileCollegeAdmin(MultipartFile file, CollegeAdmin collegeadmin) {
		
		BCryptPasswordEncoder bcryptpassword=new BCryptPasswordEncoder();
        String docname=file.getOriginalFilename();
		String password="2021"+collegeadmin.getCollegename()+"@"+collegeadmin.getCollegeid();
		String encpassword=bcryptpassword.encode(password);
		collegeadmin.setPassword(encpassword);
		collegeadmin.setSubmit(false);
		collegeadmin.setRequestformname(docname);
		collegeadmin.setRequestformtype(file.getContentType());
		try {
			collegeadmin.setDocdata(file.getBytes());
			collegeadminrepo.save(collegeadmin);
			collegeadmin.setLink("/buspass/students/2021/"+collegeadmin.getCollegeid()+"/"+collegeadmin.getCollegename()+"/form-submission/link");
			collegeadmin.setLinkenabled(false);
			collegeadmin.setApplieddate(datefunction.getToday());
			return collegeadminrepo.save(collegeadmin);
		} catch (IOException e) {
		      
		}
		 catch (JpaSystemException e) {
				return null;
			}

		return null;
		
	}
	public CollegeAdmin getCollegeAdmin(String collegename) {
		return collegeadminrepo.findByCollegeName(collegename);
	}

	
	public CollegeAdmin verifyCollege(String email,String enteredpassword) {
		CollegeAdmin collegeadminInDb=collegeadminrepo.getCollegeBYEmail(email);
		if(collegeadminInDb!=null) {
		String inDbPassword=collegeadminInDb.getPassword();
		boolean  verified=bcryptpassword.matches(enteredpassword,inDbPassword);
			if(verified) {
				return collegeadminInDb;
			}
			else {
				return null;
			}
		}
		else {
		return null;
		}
	}
	
//	public String[] serchbydepartment() {
//	
//	}
	
}
