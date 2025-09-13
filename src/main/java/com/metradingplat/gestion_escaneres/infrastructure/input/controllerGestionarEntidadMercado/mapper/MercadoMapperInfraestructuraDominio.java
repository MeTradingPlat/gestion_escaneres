package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEntidadMercado.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.metradingplat.gestion_escaneres.domain.models.Mercado;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEntidadMercado.DTOAnswer.MercadoDTORespuesta;

import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition.MercadoDTOPeticion;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MercadoMapperInfraestructuraDominio {

    // --- Petición -> Dominio ---
    Mercado mappearDePeticionAMercado(MercadoDTOPeticion peticion);
    List<Mercado> mappearListaDePeticionAMercado(List<MercadoDTOPeticion> peticiones);

    // --- Dominio -> Respuesta ---
    MercadoDTORespuesta mappearDeMercadoARespuesta(Mercado mercado);
    List<MercadoDTORespuesta> mappearListaDeMercadoARespuesta(List<Mercado> mercados);
}