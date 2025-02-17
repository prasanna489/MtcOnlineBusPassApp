package com.mtc.online.buspass.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.repository.Studentsrepo;

@Service
public class StudentsService {
	
		@Autowired
		Studentsrepo studentsrepo;
	
		@Autowired
		MtcAdminService mtcadmin;
		
		@Autowired
		CollegeAdminService collegeadmin;
		public boolean isalreadyreg(Students student) {
		Students std=studentsrepo.studentExists(student.getFirstname(), student.getCollege(), student.getRegisternumber(),student.getPhonenumber());
			if(std==null) {
				return false;
			}
			else {
				return true;
			}
		}
	
		public Students saveFileaddressproof(MultipartFile file, Students stud) {
			
			String docname=file.getOriginalFilename();
			
			stud.setAddressproofname(docname);
			stud.setAddressprooftype(file.getContentType());
			try {
				stud.setAddressproofdata(file.getBytes());
				//return studentsrepo.save(stud);
			} catch (IOException e) {
				System.out.println("New Error");
			}
			catch(JpaSystemException e) {
				return null;
			}

			return stud;
		}
		public Students saveFileaddressproofother(MultipartFile file, Students stud) {
			String docname=file.getOriginalFilename();
			
			stud.setAddressproofothername(docname);
			stud.setAsddressproofothertype(file.getContentType());
			try {
				stud.setAddressproofotherdata(file.getBytes());
				//return studentsrepo.save(stud);
			} catch (IOException e) {
				System.out.println("New Error");
			}
			catch(JpaSystemException e) {
				return null;
			}

			return stud;
		}
		
		public Students saveFileIDCollege(MultipartFile file, Students stud) {
			String docname=file.getOriginalFilename();
			stud.setClgidfilename(docname);
			stud.setClgidfiletype(file.getContentType());
			CollegeAdmin collegeadmindetail= collegeadmin.collegeadminrepo.findByCollegeName(stud.getCollege());
			BigInteger phno=stud.getPhonenumber();
			BigInteger applicationno=getappliactionno(stud.getRegisternumber(),collegeadmindetail.getCollegeid(),phno);
			System.out.println("application no "+applicationno);
			stud.setApplicationno(applicationno);
			try {
				stud.setClgidfiledata(file.getBytes());
				return studentsrepo.save(stud);
			} catch (IOException e) {
				System.out.println("New Error");
			}
			catch(JpaSystemException e) {
				return null;
			}

			return stud;
		}
		
		public Students saveImage(MultipartFile multipartfile,Students stud,Model model) throws IOException {
			String filename=StringUtils.cleanPath(multipartfile.getOriginalFilename());
			stud.setImagepath(filename);
			
			String uploadDir="./stud-logo/"+stud.getRegisternumber();
			
			Path uploadPath=Paths.get(uploadDir);
			//System.out.println(uploadPath);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inputstream=multipartfile.getInputStream()) {
			  Path filepath=uploadPath.resolve(filename);
			  Files.copy(inputstream, filepath, StandardCopyOption.REPLACE_EXISTING);
			} catch(IOException e) {
				System.out.println("Her that erroe");
			}
			//ra.addFlashAttribute("message","brand has been successfully uploaded");
			model.addAttribute("stud",stud);
			return stud;

		}
		
		public Students saveStudent(Students stud) {
			Students savedstud=studentsrepo.save(stud);
			return savedstud;
		}
		
		
		public BigInteger getappliactionno(BigInteger number,Integer collegeid,BigInteger phno) {
			String s1 = number.toString(); 
			String s2=collegeid.toString();
			String phone=phno.toString();
			System.out.println("college id "+s2);
			char a,b,c,d,e,f;
			
		
			d=phone.charAt(phone.length()-3);
			e=phone.charAt(phone.length()-2);
			f=phone.charAt(phone.length()-1);
			Date date=new Date();
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month =String.valueOf(cal.get(Calendar.MONTH)+1) ;
			System.out.println("curent date "+year+""+month);
			a=s1.charAt(s1.length()-3);
			System.out.println("1st "+a);
			b=s1.charAt(s1.length()-2);
			System.out.println("2nd "+b);
			c=s1.charAt(s1.length()-1);
			System.out.println("3rd "+c);
	        String s = year+month+s2+d+e+f+a+b+c;
	        System.out.println(s);
	        BigInteger appno=new BigInteger(s);
	        
			return appno;
		}
		
		public Students getsavedStudent(String name,String collegename,BigInteger registerno) {
			return studentsrepo.findSavedStudentDetails(name,collegename,registerno);
		}
		public Students getstudentwithAppnoAndRegno(BigInteger appno,BigInteger regno,BigInteger phno) {
			return studentsrepo.findByApplicationNoandPhonenumber(appno, regno, phno);
		}
		public List<Students> getStduentsWithCollege(String college) {
			return studentsrepo.findStduentsWithCollege(college);
		}
		public Students deleteStudent(BigInteger appno) {
			Students stud=studentsrepo.findByApplicationNo(appno);
			studentsrepo.delete(stud);
			return null;
		}
		public Students getStudent(BigInteger appno) {
			return studentsrepo.findByApplicationNo(appno);
			
		}
}
