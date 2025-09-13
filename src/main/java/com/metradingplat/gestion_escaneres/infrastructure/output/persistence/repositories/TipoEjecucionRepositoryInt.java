package com.metradingplat.gestion_escaneres.infrastructure.output.persistence.repositories;

import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoEjecucion;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.entitys.TipoEjecucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEjecucionRepositoryInt extends JpaRepository<TipoEjecucionEntity, Long> {
    Optional<TipoEjecucionEntity> findByEnumTipoEjecucion(EnumTipoEjecucion enumTipoEjecucion);
}