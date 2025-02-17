package com.mtc.online.buspass.interfaces;

import org.springframework.data.domain.Page;

import com.mtc.online.buspass.domain.Students;

public interface CollegeAdminInterface {

	Page<Students> findByCollgePaginated(int pageNo,int pageSize,String college);
	
	
}
