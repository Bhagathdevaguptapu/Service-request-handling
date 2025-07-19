package com.cfg.srh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cfg.srh.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}