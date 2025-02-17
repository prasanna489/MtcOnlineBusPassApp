package com.mtc.online.buspass.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mtc.online.buspass.domain.Students;

@Repository
public interface Studentsrepo extends JpaRepository<Students, Integer> {

		@Query("FROM Students where  firstname = ?1 AND college = ?2 AND registernumber = ?3 AND phonenumber = ?4")
		public Students studentExists(String name,String college,BigInteger regno,BigInteger phone); 
		
		
		Page<Students> findByCollege(String college,org.springframework.data.domain.Pageable pageable);
		
		Students findByRegisternumber(BigInteger regno);

		@Query("FROM Students where  firstname = ?1 AND college = ?2 AND registernumber = ?3")
		public Students findSavedStudentDetails(String name, String collegename, BigInteger registerno);

		@Query("FROM Students where  applicationno = ?1 ")
		public Students findByApplicationNo(BigInteger applicationno);
		
		@Query("FROM Students where  applicationno = ?1 AND college = ?2")
		public Students findByApplicationNoandCollege(BigInteger applicationno,String college);
		
		@Query("FROM Students where  college = ?1 ")
		public List<Students> findStduentsWithCollege(String college);
		
		@Query("FROM Students where  applicationno = ?1 AND registernumber = ?2 AND phonenumber = ?3")
		public Students findByApplicationNoandPhonenumber(BigInteger applicationno,BigInteger registerno,BigInteger phno);

		@Query("FROM Students group by department HAVING college='St Michaels college of arts & science' AND department='Bsc'")
		public List<Students> countByDepartment();
		
		
}
