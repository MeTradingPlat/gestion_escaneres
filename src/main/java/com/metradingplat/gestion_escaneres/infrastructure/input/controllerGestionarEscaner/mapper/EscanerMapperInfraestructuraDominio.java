package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.metradingplat.gestion_escaneres.domain.models.Escaner;
import com.metradingplat.gestion_escaneres.domain.models.EstadoEscaner;
import com.metradingplat.gestion_escaneres.domain.models.Mercado;
import com.metradingplat.gestion_escaneres.domain.models.TipoEjecucion;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEntidadMercado.DTOAnswer.MercadoDTORespuesta;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EscanerDTORespuesta;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOAnswer.EstadoEscanerDTORespuesta;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOAnswer.TipoEjecucionDTORespuesta;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition.EscanerDTOPeticion;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition.MercadoDTOPeticion;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition.TipoEjecucionDTOPeticion;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EscanerMapperInfraestructuraDominio {

    // --- Escaner ---
    Escaner mappearDePeticionAEscaner(EscanerDTOPeticion peticion);
    EscanerDTORespuesta mappearDeEscanerARespuesta(Escaner escaner);
    List<EscanerDTORespuesta> mappearListaDeEscanerARespuesta(List<Escaner> escaneres);

    // --- EstadoEscaner ---
    EstadoEscanerDTORespuesta mappearDeEstadoEscanerARespuesta(EstadoEscaner estado);

    // --- TipoEjecucion ---
    TipoEjecucion mappearDePeticionATipoEjecucion(TipoEjecucionDTOPeticion peticion);
    TipoEjecucionDTORespuesta mappearDeTipoEjecucionARespuesta(TipoEjecucion tipo);
    List<TipoEjecucionDTORespuesta> mappearListaDeTipoEjecucionARespuesta(List<TipoEjecucion> tipos);

    // --- Mercado ---
    Mercado mappearDePeticionAMercado(MercadoDTOPeticion peticion);
    MercadoDTORespuesta mappearDeMercadoARespuesta(Mercado mercado);
    List<MercadoDTORespuesta> mappearListaDeMercadoARespuesta(List<Mercado> mercados);
}
