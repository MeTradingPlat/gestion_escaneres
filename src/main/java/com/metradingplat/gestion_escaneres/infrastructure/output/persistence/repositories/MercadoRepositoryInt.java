package com.metradingplat.gestion_escaneres.infrastructure.output.persistence.repositories;

import com.metradingplat.gestion_escaneres.domain.enums.EnumMercado;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.entitys.MercadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MercadoRepositoryInt extends JpaRepository<MercadoEntity, Long> {

    Optional<MercadoEntity> findByEnumMercado(EnumMercado enumMercado);

    List<MercadoEntity> findByEnumMercadoIn(List<EnumMercado> enumMercados);
}