package com.Web.Plamilhas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Web.Plamilhas.Entity.ProgramaEntity;
@Repository
public interface ProgramaRepository extends JpaRepository<ProgramaEntity,Integer>{

}