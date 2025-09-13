package com.metradingplat.gestion_escaneres.domain.strategies.validacion;

import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;

public interface IValidacionFiltro {
    Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor);
}