package com.metradingplat.gestion_escaneres.infrastructure.output.persistence.repositories;

import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.entitys.EstadoEscanerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoEscanerRepositoryInt extends JpaRepository<EstadoEscanerEntity, Long> {

}