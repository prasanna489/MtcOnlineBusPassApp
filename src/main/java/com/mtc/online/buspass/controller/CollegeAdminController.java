package com.mtc.online.buspass.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.repository.CollegeAdminrepo;
import com.mtc.online.buspass.service.CollegeAdminService;
import com.mtc.online.buspass.service.StudentsService;

@Controller()
public class CollegeAdminController {
	
	@Autowired
	CollegeAdminService collegeadminservice;
	@Autowired
	CollegeAdminrepo clgrepo;
	@Autowired
	StudentsService studentservice;
	
	@RequestMapping("/register/college/year2021")
	public String registerCollegePage(Model model) {
		return "College_Registration_Form";
	}
	
	@PostMapping("/success/college/register")
	public String registerCollege(Model model,@ModelAttribute(name="CollegeAdmin") CollegeAdmin collegeadmin,RedirectAttributes ra,@RequestParam("requestform") MultipartFile multipartfilepdf) throws java.io.IOException {
		String college=collegeadmin.getCollegename();
		if(collegeadminservice.alreadyExists(college)) {
			model.addAttribute("success",false);
			model.addAttribute("already", true);
			model.addAttribute("filelengthlong",false);
			model.addAttribute("collegeadmin", collegeadmin);
			return "Success_College_Register";
		}
		else {
		CollegeAdmin clgadmin=collegeadminservice.saveFileCollegeAdmin(multipartfilepdf, collegeadmin);
			if(clgadmin==null) {
				model.addAttribute("filelengthlong", true);
				model.addAttribute("success",false);
				model.addAttribute("already", false);
				return "Success_College_Register";
			}
			else {
			model.addAttribute("collegeadmin", clgadmin);
			model.addAttribute("already", false);
			model.addAttribute("success",true);
			model.addAttribute("filelengthlong",false);
			return "Success_College_Register";
			}
	}
}
	
	@GetMapping("/college/login/login-as/college")
	public String LoginCollegePage(Model model,HttpServletRequest req) {
		HttpSession session=req.getSession();
		if(session.getAttribute("collegename")!=null) {
			CollegeAdmin collegeadmin=collegeadminservice.getCollegeAdmin((String)session.getAttribute("collegename"));
			model.addAttribute("college", collegeadmin);
			List<Students> std= studentservice.getStduentsWithCollege(collegeadmin.getCollegename());
			model.addAttribute("studtotal", std.size());
			model.addAttribute("success", true);
			return "College_Window";
		}
		else {
			System.out.println("Here");
		return "CollegeLogin";
		}
	}
	
	@PostMapping("/college/login/login-as/college/verify")
	public String LoginVerifyCollegePage(Model model,HttpServletRequest req,@RequestParam("collegename")String collegeemail,@RequestParam("password")String enteredpassword) {
		HttpSession session=req.getSession();
		
		CollegeAdmin verifiedcollegeadmin=collegeadminservice.verifyCollege(collegeemail, enteredpassword);
		if(verifiedcollegeadmin!=null) {
			System.out.print("here2");
			model.addAttribute("success",true);
			session.setAttribute("collegename", verifiedcollegeadmin.getCollegename());
			model.addAttribute("college", verifiedcollegeadmin);
			model.addAttribute("fail",false);
			model.addAttribute("studtotal",studentservice.getStduentsWithCollege(verifiedcollegeadmin.getCollegename()).size());
			return "College_Window";
		}
		else {
			System.out.println("here fail");
			model.addAttribute("faillogin", true);
		return "CollegeLogin";
		}
	}

		@PostMapping("/college/admin/action-logout")
		public String logoutCollgeAdmin(HttpServletRequest req) {
			HttpSession session=req.getSession();
			String sessionvar=(String) session.getAttribute("name");
			System.out.println("session mtc "+sessionvar);
			session.invalidate();
			return "CollegeLogin";
		}
		@PostMapping("/college/admin/submit/forms/{college}")
		public String submitForm(Model model,@PathVariable("college")String college) {
			CollegeAdmin clgadmin=collegeadminservice.getCollegeAdmin(college);
			clgadmin.setSubmit(true);
			clgrepo.save(clgadmin);
			model.addAttribute("fail", true);
			model.addAttribute("college", clgadmin);
			return "College_Window";
		}
		
		@PostMapping("/college/admin/search/department/{college}")
		public String searchByDepartment(Model model,@PathVariable("college")String college) { 
		
			return "";
		}
		
}
