package com.mtc.online.buspass.controller;

import java.math.BigInteger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.mtc.online.buspass.domain.CollegeAdmin;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.repository.Studentsrepo;
import com.mtc.online.buspass.service.CollegeAdminService;
import com.mtc.online.buspass.service.StudentsService;

@Controller
public class StudentsController {

	@Autowired
	CollegeAdminService collegeadminservice;
	
	@Autowired
	StudentsService studentsservice;
	@Autowired
	Studentsrepo studentsrepo;
	
	@Autowired
	ServletContext servletContext1;
	
	private final TemplateEngine templateEngine;

    public StudentsController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
	
	@RequestMapping("/buspass/students/2021/{clgid}/{college}/form-submission/link")
	public String uploadStudentDetails(Model model,@PathVariable("college") String college,@PathVariable("clgid")Integer clgid) {
		
		CollegeAdmin collegeadmin=collegeadminservice.getCollegeAdminservice(clgid, college);
		if(collegeadmin==null) {
			model.addAttribute("notFound",true);
			model.addAttribute("notEnabled",false);
			model.addAttribute("alreadyregstud",false);
			return "Feedback_on_stud_reg";
		}
		if(collegeadmin.isLinkenabled()) {
			System.out.println("here");
			model.addAttribute("clgadmin", collegeadmin);
			return "choosing_submission_type";
		}
		else {
			System.out.println("here1");
			model.addAttribute("notFound",false);
			model.addAttribute("notEnabled",true);
			model.addAttribute("alreadyregstud",false);
			return "Feedback_on_stud_reg";
		}
		
		
	}
	
	@GetMapping("/success/student/ownProof/form/{collegename}/{collegeid}")
	public String selfAddressPage(Model model,@PathVariable("collegename")String college,@PathVariable("collegeid")Integer clgid) {
		CollegeAdmin collegeadmin=collegeadminservice.getCollegeAdminservice(clgid, college);
		model.addAttribute("clgadmin", collegeadmin);
		return "Students_Appliaction_Form_self_address";
	}
	
	@GetMapping("/success/student/othersProof/form/{collegename}/{collegeid}")
	public String othersAddressPage(Model model,@PathVariable("collegename")String college,@PathVariable("collegeid")Integer clgid) {
		CollegeAdmin collegeadmin=collegeadminservice.getCollegeAdminservice(clgid, college);
		model.addAttribute("clgadmin", collegeadmin);
		return "Students_Appliaction_Form_others_address";
	}

	
	@PostMapping("/success/student/ownProof/form-submission")
	public String saveStudentsdetailswithOwnProof(@ModelAttribute(name="Students") Students student,RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartfile ,Model model ,@RequestParam("clgidfile") MultipartFile multipartfilepdfidcard,@RequestParam("addressproof1") MultipartFile multipartfilepdfaddressproof) throws java.io.IOException {
		
		if(studentsservice.isalreadyreg(student)) {	
			model.addAttribute("notFound",false);
			model.addAttribute("notEnabled",false);
			model.addAttribute("alreadyregstud",true);
			return "Feedback_on_stud_reg";
		}
		else {
			CollegeAdmin clgid=collegeadminservice.getCollegeAdmin(student.getCollege());
			if(studentsservice.saveImage(multipartfile, student, model)==null) {
				model.addAttribute("success", false);
				model.addAttribute("fail", true);
				model.addAttribute("clgadmin", clgid);
				return "Students_Appliaction_Form_self_address";
			}
			if(studentsservice.saveFileaddressproof(multipartfilepdfaddressproof, student)==null) {
				
			model.addAttribute("success", false);
			model.addAttribute("fail", true);
			model.addAttribute("clgadmin", clgid);
			return "Students_Appliaction_Form_self_address";
			}
			if(studentsservice.saveFileIDCollege(multipartfilepdfidcard, student)==null) {
				model.addAttribute("success", false);
				model.addAttribute("fail", true);
				model.addAttribute("clgadmin", clgid);
				return "Students_Appliaction_Form_self_address";
			}
			Students studentsavedetail=studentsservice.getsavedStudent(student.getFirstname(), student.getCollege(), student.getRegisternumber());
			System.out.println(" application saved "+studentsavedetail.getApplicationno());
			model.addAttribute("savedstudent", studentsavedetail);
			model.addAttribute("success", true);
		return "Payment_Window";
		}
	}
	
	
	@PostMapping("/success/student/othersProof/form-submission")
	public String saveStudentsdetailswithOthersProof(@ModelAttribute(name="Students") Students student,RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartfile ,Model model ,@RequestParam("clgidfile") MultipartFile multipartfilepdfidcard,@RequestParam("addressproof1") MultipartFile multipartfilepdfaddressproof,@RequestParam("addressproof1") MultipartFile multipartfilepdfaddressproofgaurd) throws java.io.IOException {
		
		if(studentsservice.isalreadyreg(student)) {	
			model.addAttribute("notFound",false);
			model.addAttribute("notEnabled",false);
			model.addAttribute("alreadyregstud",true);
			return "Feedback_on_stud_reg";
		}
		else {
			CollegeAdmin clgid=collegeadminservice.getCollegeAdmin(student.getCollege());
			studentsservice.saveImage(multipartfile, student, model);
			if(studentsservice.saveFileaddressproof(multipartfilepdfaddressproof, student)==null) {
				model.addAttribute("success", false);
			model.addAttribute("fail", true);
			model.addAttribute("clgadmin", clgid);
			return "Students_Appliaction_Form_self_address";
			}
			if(studentsservice.saveFileaddressproofother(multipartfilepdfaddressproofgaurd, student)==null) {
				model.addAttribute("success", false);
				model.addAttribute("fail", true);
				model.addAttribute("clgadmin", clgid);
				return "Students_Appliaction_Form_self_address";
			}
			if(studentsservice.saveFileIDCollege(multipartfilepdfidcard, student)==null) {
				model.addAttribute("success", false);
				model.addAttribute("fail", true);
				model.addAttribute("clgadmin", clgid);
				return "Students_Appliaction_Form_self_address";
				}
			Students studentsavedetail=studentsservice.getsavedStudent(student.getFirstname(), student.getCollege(), student.getRegisternumber());
			System.out.println(" application saved "+studentsavedetail.getApplicationno());
			studentsavedetail.setAddressproofother(true);
			studentsrepo.save(studentsavedetail);
			model.addAttribute("savedstudent", studentsavedetail);
			model.addAttribute("success", true);
		return "reg_success";
		}
	}
	
	@PostMapping("/student/reg-status/pay-cancel/{applicationno}")
	public String studPayCancel(Model model,@PathVariable("applicationno")BigInteger appno) {
		studentsservice.deleteStudent(appno);
		model.addAttribute("success", false);
		model.addAttribute("fail", true);
		return "reg_success";
	}
	@PostMapping("/student/reg-status/pay-success/{applicationno}")
	public String studPaySuccess(Model model,@PathVariable("applicationno")BigInteger appno) {
		model.addAttribute("savedstudent",studentsservice.getStudent(appno));
		model.addAttribute("success", true);
		model.addAttribute("fail", false);
		return "reg_success";
	}
	
	
	@GetMapping("/student/login/download/Id-Card")
	public String studLoginPage() {
		return "studentslogin";
	}
		
	
	
	@PostMapping(path = "/student/download/Id-Card")
    public ResponseEntity<?> getStudentIdCardDownload(Model model,HttpServletRequest request, HttpServletResponse response,@RequestParam("applicationo") BigInteger appno,@RequestParam("registerno") BigInteger regno,@RequestParam("phno") BigInteger phno) throws IOException {
		
	  Students student=studentsservice.getstudentwithAppnoAndRegno(appno, regno, phno);
	  if(!student.isApproved()) {
		  
		  return ResponseEntity.ok()
	                .contentType(MediaType.TEXT_PLAIN)
	                .body("You are not Approved");
	  }
	  else {
	  CollegeAdmin college=collegeadminservice.getCollegeAdmin(student.getCollege());
        WebContext context = new WebContext(request, response, servletContext1);
        context.setVariable("student", student);
        context.setVariable("college", college);
       
        String studentHtml = templateEngine.process("IdentityCard_Template", context);
        ByteArrayOutputStream target = new ByteArrayOutputStream();

       HtmlConverter.convertToPdf(studentHtml, target);  
        byte[] bytes = target.toByteArray();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
        		
        		
	  }
    }
	
	@GetMapping("/student/do-action/renew/verify")
	public String studRenewPage() {
		return "Renew_Window";
	}
		
	
}
