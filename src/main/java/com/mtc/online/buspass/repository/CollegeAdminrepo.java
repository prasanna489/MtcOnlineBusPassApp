package com.mtc.online.buspass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mtc.online.buspass.domain.CollegeAdmin;

@Repository
public interface CollegeAdminrepo extends JpaRepository<CollegeAdmin, Integer>{
	
	@Query("FROM CollegeAdmin where collegeid = ?1 AND collegename = ?2")
	public CollegeAdmin getCollegeAdmin(Integer clgid,String college);
	
	@Query("FROM CollegeAdmin where collegename = ?1")
	public CollegeAdmin getCollegeExists(String college);
	
	@Query("FROM CollegeAdmin where approved = ?1 AND collegedepot = ?2")
	public List<CollegeAdmin> findByIsapproved(boolean state,String depot);
	
	@Query("FROM CollegeAdmin where collegedepot = ?1")
	public List<CollegeAdmin> findBydepotname(String depotname);

	@Query("FROM CollegeAdmin where collegename = ?1")
	public CollegeAdmin findByCollegeName(String collgeename);
	
	@Query("FROM CollegeAdmin where submit = ?1 AND collegedepot = ?2")
	public List<CollegeAdmin> findBySubmitted(boolean state,String depot);

	@Query("FROM CollegeAdmin where approved = ?1 AND collegedepot = ?2")
	public List<CollegeAdmin> findByCollegeNotAppoved(boolean state, String depotname);
	
	@Query("FROM CollegeAdmin where approved = ?1 AND collegedepot = ?2")
	public List<CollegeAdmin> findByCollegetodisable(boolean state, String depotname);

	@Query("FROM CollegeAdmin where collegedepot = ?1 AND approved = ?2")
	public List<CollegeAdmin> getCollegesApproved(String depotname,boolean b);
	
	@Query("FROM CollegeAdmin where collegedepot = ?1 AND verified = ?2")
	public List<CollegeAdmin> getCollegesVerified(String depotname,boolean b);
	
	@Query("FROM CollegeAdmin where collegeemail = ?1 ")
	public CollegeAdmin getCollegeBYEmail(String collegeemail);
	
	
}
