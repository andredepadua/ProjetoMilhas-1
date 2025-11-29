package com.Web.Plamilhas.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Web.Plamilhas.Entity.HistoricoPontosEntity;

@Repository
public interface HistoricoPontosRepository extends JpaRepository <HistoricoPontosEntity,UUID> {
    
}
