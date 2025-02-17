package com.mtc.online.buspass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mtc.online.buspass.domain.MtcAdmin;

@Repository
public interface MtcAdminrepo extends JpaRepository<MtcAdmin, Integer> {

	@Query("FROM MtcAdmin where  depotname = ?1 AND depotnumber = ?2")
	MtcAdmin getAdmin(String depotname,Integer depotnumber); 
	
	@Query("FROM MtcAdmin where  depotname = ?1 ")
	MtcAdmin getAdmin(String depotname); 
}
