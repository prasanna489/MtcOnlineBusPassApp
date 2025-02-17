package com.mtc.online.buspass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mtc.online.buspass.domain.RepoTest;
import com.mtc.online.buspass.domain.Students;
import com.mtc.online.buspass.repository.RepoTestrepo;
import com.mtc.online.buspass.repository.Studentsrepo;
/*
* Developed By PRASANNAVENKATESH V
* February 2021
*/
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
	@Autowired
	Studentsrepo stdrepo;

	@Autowired
	RepoTestrepo repotest;
	
	@GetMapping("/home")
	public String homePage()
	{
	
		List<Students> count=stdrepo.countByDepartment();
		System.out.println("Count = "+count.get(0).getLastname());
		//System.out.println("Count = "+count.get(1).getLastname());
		System.out.println("Count = "+count.size());
		return "HomePage";
	}
	
	@PostMapping("/getValue")
	public String getValue(@RequestBody RepoTest rpo){
		System.out.println("been here");
		repotest.save(rpo);
		List<RepoTest> rpotest=repotest.findAll();
		System.out.println("Class "+rpo);
		return "HomePage";
	}
	
}
