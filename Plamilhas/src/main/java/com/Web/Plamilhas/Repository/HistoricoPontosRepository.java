package com.Web.Plamilhas.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Web.Plamilhas.Entity.HistoricoPontosEntity;

public interface HistoricoPontosRepository extends JpaRepository <HistoricoPontosEntity,UUID> {
    
}
