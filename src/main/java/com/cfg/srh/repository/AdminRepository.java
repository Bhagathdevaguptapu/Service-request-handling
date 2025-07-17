package com.cfg.srh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cfg.srh.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	

}
