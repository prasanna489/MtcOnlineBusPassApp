package com.mtc.online.buspass.controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Lob;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.domain.MtcAdmin;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.repository.CollegeAdminrepo;
import com.mtc.online.buspass.repository.Studentsrepo;
import com.mtc.online.buspass.service.MtcAdminService;

@Controller
public class MtcAdminController {
	@Autowired
	MtcAdminService mtcadminservice;
	
	@Autowired
	Studentsrepo studentsrepo;
	
	@Autowired
	CollegeAdminrepo collegeadminrepo;
	
	@Lob
	byte[] byteString;

	
	BCryptPasswordEncoder bcryptpassword=new BCryptPasswordEncoder();
	
	@GetMapping("/todepot/admin/login-page")
	public String depotadminloginpage(Model model,HttpServletRequest req) {
		HttpSession session=req.getSession();
		if(session.getAttribute("name")!=null) {
		String depotname=(String) session.getAttribute("name");
		MtcAdmin mtcadmin=	mtcadminservice.getmtcAdmin(depotname);
			model.addAttribute("admin", mtcadmin);
			List<CollegeAdmin> collegelist=	mtcadminservice.getCollegeswithDepot(mtcadmin.getDepotname());
			model.addAttribute("collegeadmin",collegelist);
			model.addAttribute("collegestoverify",mtcadminservice.getCollegeSubmitted(mtcadmin.getDepotname()));
			model.addAttribute("collegestoapprove",mtcadminservice.getCollegeToApprove(mtcadmin.getDepotname()));	
			model.addAttribute("collegestobedisabled",mtcadminservice.findcollegetoDisable(mtcadmin.getDepotname()));
			model.addAttribute("collegesverified", mtcadminservice.findByVerified(mtcadmin.getDepotname()));
			return "AdminWindow";
		
		}else {
		model.addAttribute("notfound",false);
		return "Loginpage";
		}
	}
	
	@PostMapping("/depot/admin/login/verify")
	public String verifyadmin(Model model,@ModelAttribute(name="MtcAdmin")MtcAdmin admin,HttpServletRequest req) {
		HttpSession httpsession=req.getSession();
		MtcAdmin verifiedAdmin=mtcadminservice.verifyAdmin(admin);
		if(verifiedAdmin!=null) {
			model.addAttribute("admin", verifiedAdmin);
			List<CollegeAdmin> collegelist=	mtcadminservice.getCollegeswithDepot(verifiedAdmin.getDepotname());
			model.addAttribute("collegeadmin",collegelist);
			model.addAttribute("collegestoverify",mtcadminservice.getCollegeSubmitted(verifiedAdmin.getDepotname()));
			model.addAttribute("collegestoapprove",mtcadminservice.getCollegeToApprove(verifiedAdmin.getDepotname()));
			
			httpsession.setAttribute("name", verifiedAdmin.getDepotname());
			model.addAttribute("collegestobedisabled",mtcadminservice.findcollegetoDisable(verifiedAdmin.getDepotname()));
			model.addAttribute("collegesverified", mtcadminservice.findByVerified(verifiedAdmin.getDepotname()));
			return "AdminWindow";
		}
		else {
			model.addAttribute("notfound",true);
			return "Loginpage";
		}
	}
	
	@RequestMapping("/admin/forgot/password/")
	public String forgotPass() {
		return "forgotadmin";
	}
	@RequestMapping("/depot/admin/change-passwd")
	public String ChangePass(Model model,@RequestParam("depotnumber")String depotno,@RequestParam("depotname")String depotname,@RequestParam("newpassword")String password) {
		
	boolean changed=mtcadminservice.changepass(depotname, password);
		if(changed) {
			return "Loginpage";
		}
		else {
		//	model.addAttribute("cantchange", true);
		return "Loginpage";
		}
	}
	
	@RequestMapping("/admin/get-details/college")
	public String getCollegeDetails(@RequestParam("collegename")String collegename,Model model,HttpServletRequest req) {
		HttpSession httpsession=req.getSession();
		String depotlog=(String) httpsession.getAttribute("name");
		System.out.println("Session name"+depotlog);
		CollegeAdmin collegelist=mtcadminservice.getCollegeDetails(collegename);
		if(collegelist==null) {
			model.addAttribute("notfound", true);
			model.addAttribute("approved",false);
			model.addAttribute("found", false);
			return "College_Details";
		}
		else {
			model.addAttribute("notfound", false);
			model.addAttribute("approved",collegelist.isApproved());
			model.addAttribute("found", true);
			model.addAttribute("collegedetails", collegelist);
			return "College_Details";
		}
	}
	
	@PostMapping("/admin/getStudent/By-college-name")
	   public String getStudentBycollege(Model model,@RequestParam("college_students") String college) {
		System.out.println("college name "+college);
	   	return findstudbycollege(1,model,college);
	   
	   }
	
	  @GetMapping("/page/{college}/{pageno}")
	  public String findstudbycollege(@PathVariable(value="pageno") int pageNo,Model model,@PathVariable(value="college") String college) {
		   int pageSize=4;
		  String collegefind=college; 
	     Page<Students> page=mtcadminservice.findByCollgePaginated(pageNo, pageSize, collegefind);
		   List<Students> listStudents=page.getContent();
		   System.out.println(listStudents.size());
		   System.out.println(listStudents.get(0).getCollege());
		   model.addAttribute("collegename", college);
		   model.addAttribute("total_students", listStudents.size());
		   model.addAttribute("students", listStudents);
		   model.addAttribute("currentPage", pageNo);
		   model.addAttribute("totalPages", page.getTotalPages());
	      model.addAttribute("totalItems",page.getTotalElements());
	      model.addAttribute("listStudents", listStudents);
	      model.addAttribute("college", listStudents.get(0).getCollege());
	      return "Students_List_page";
	  }
	  
	  @PostMapping("/admin/approve/college/do-action/{collegename}/{collegedepot}")
	  public String approvedisapproveCollege(Model model,@PathVariable("collegename")String collegename,@PathVariable("collegedepot")String collegedepot,@RequestParam("approve")boolean state) {
		CollegeAdmin collegedetail= mtcadminservice.findcollegetoApprove(collegename, state);
		  MtcAdmin mtcadmin=mtcadminservice.getmtcAdmin(collegedepot);
		  if(state) {
			  Date date=new Date();
			  collegedetail.setApprovedate(date);
			  collegeadminrepo.save(collegedetail);
		  model.addAttribute("approved",true);
		  model.addAttribute("notfound", false);
			model.addAttribute("found", true);
			model.addAttribute("depotname", mtcadmin.getDepotname());
			model.addAttribute("collegedetails", collegedetail);
		  return "College_Details";
		  }
		  else {
			  model.addAttribute("approved",false);
			  model.addAttribute("notfound", false);
				model.addAttribute("found", true);
				model.addAttribute("collegedetails", collegedetail);
				 return "College_Details";
		  }
	  }
	  
	  @PostMapping("/admin/approve/college/do-action-approve/next-step/{collegename}/{collegedepot}")
	  public String saveapprovedCollege(Model model,@PathVariable("collegename")String collegename,@PathVariable("collegedepot")String collegedepot,@RequestParam("approved")boolean state) {
		CollegeAdmin collegedetail= mtcadminservice.findcollegetoApprove(collegename, state);
		//  MtcAdmin mtcadmin=mtcadminservice.getmtcAdmin(collegedepot);
		  if(state) {
			  collegedetail.setApproved(true);
			  collegedetail.setLinkenabled(true);
			  collegeadminrepo.save(collegedetail);
		  model.addAttribute("approved_section", true);
		  model.addAttribute("collegedetails", collegedetail);
		  model.addAttribute("collegepassword", collegedetail.getCollegetelephone());
		  return "Save_College";
		  }
		  else {
			  model.addAttribute("approved_section", false);
			  model.addAttribute("collegedetails", collegedetail);
			  return "Save_College";
		  }
	  }
	  
	  @PostMapping("/admin/tries/to-logout")
	  public String logout(HttpServletRequest req) {
		  HttpSession httpsession=req.getSession();
		  httpsession.invalidate();
		 // System.out.println(httpsession.getAttribute("name"));
		  return "redirect:/todepot/admin/login-page";
	  }
	  
//	  @PostMapping("/admin/approve/college/do-action-approve/next-step/{collegename}/{collegedepot}")
//	  public String saveDisapprovedCollege(Model model,@PathVariable("collegename")String collegename,@PathVariable("collegedepot")String collegedepot,@RequestParam("approved")boolean state) {
//		CollegeAdmin collegedetail= mtcadminservice.findcollegetoApprove(collegename, state);
//		  MtcAdmin mtcadmin=mtcadminservice.getmtcAdmin(collegedepot);
//		  model.addAttribute("approved_section", false);
//		  model.addAttribute("collegedetail", collegedetail);
//		  return "Save_College";
//	  }
	  
	  @RequestMapping("/setapprove/{page}/{no}/{app}")
	  public String setApproval(Model model,@PathVariable("app")boolean approve,@PathVariable("no")BigInteger regno,@PathVariable("page")int page) {
		  Students std=studentsrepo.findByRegisternumber(regno);
		  std.setApproved(approve);
		  studentsrepo.save(std);
		  System.out.println(std.isApproved());
		  return findstudbycollege(page,model,std.getCollege());
	  }
	  
	  @GetMapping("/admin/save-college/login/login-details")
	  public String saveCollegeloginDetails(Model model,@RequestParam("usernameclg")String collegeusername,@RequestParam("passwordclg")String password) {
		  CollegeAdmin collegeadmin=mtcadminservice.findcollegeByEmail(collegeusername);
		  if(collegeadmin!=null) {
			  String bcryptedpassword=bcryptpassword.encode(password);
			  collegeadmin.setPassword(bcryptedpassword);
			  collegeadminrepo.save(collegeadmin);
			  model.addAttribute("success", true);
			  model.addAttribute("college", collegeadmin);
			  return "College_Saved";
		  }
		  else {
			  model.addAttribute("fail", true);
			  return "College_Saved";
		  }
		 
	  }
	  
	  @GetMapping("/admin/show/proof/student-Id/{studentapplicationno}")
		public ResponseEntity<byte[]> showCollegeIdFile(@PathVariable("studentapplicationno") BigInteger applicationno){
			Students students=mtcadminservice.getStudentByAppNo(applicationno);
			return ResponseEntity.ok()
					 .contentType(MediaType.parseMediaType(students.getClgidfiletype()))
		             .body(students.getClgidfiledata());
		}	
		
		 @GetMapping("/admin/show/proof/student-address/{studentapplicationno}")
			public ResponseEntity<byte[]> showStudentAddressFile(@PathVariable("studentapplicationno") BigInteger applicationno){
				Students students=mtcadminservice.getStudentByAppNo(applicationno);
				return ResponseEntity.ok()
						 .contentType(MediaType.parseMediaType(students.getAddressprooftype()))
			             .body(students.getAddressproofdata());
			}	
			 @GetMapping("/admin/show/proof/student-Otheraddress/{studentapplicationno}")
				public ResponseEntity<byte[]> showStudentAddressotherFile(@PathVariable("studentapplicationno") BigInteger applicationno){
					Students students=mtcadminservice.getStudentByAppNo(applicationno);
					if(students.getAddressproofothername()==null) {
						String message="Document Not needed";
						byteString=message.getBytes();
						return ResponseEntity.ok()
								 .contentType(MediaType.TEXT_PLAIN)
								 .body(byteString);
					             
					}
					else {
						return ResponseEntity.ok()
								 .contentType(MediaType.parseMediaType(students.getAddressprooftype()))
					             .body(students.getAddressproofotherdata());	
					}
					
				}	
			
		@GetMapping("/admin/action/student/set-payment/{appno}/{college}/{page}")		
		public String setAmountStudent(Model model, @PathVariable("appno")BigInteger appno,@PathVariable("college")String college,@PathVariable("page")int page,@RequestParam("mothlypay")int amount) {
			mtcadminservice.setAmountFoStudent(appno, college, amount);
			return findstudbycollege(page,model,college);
		}
	  
	  

}
