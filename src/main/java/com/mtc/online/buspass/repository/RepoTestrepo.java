package com.mtc.online.buspass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mtc.online.buspass.domain.RepoTest;

@Repository
public interface RepoTestrepo extends JpaRepository<RepoTest, Integer> {

}
