package com.metradingplat.gestion_escaneres.infrastructure.business.validation;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;

public class ValidacionInteger implements IValidacionFiltro{

    private final Integer min;
    private final Integer max;

    public ValidacionInteger (Integer min, Integer max){
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumFiltro enumFiltro, EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (!(valor instanceof ValorInteger valorInteger)) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.type.invalid");
        }

        Integer contenido = valorInteger.getValor();

        if (contenido == null) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.value.required");
        }

        if (contenido < min || contenido > max) {
            return resultado(enumFiltro, enumParametro, "validation.parameter.values.outOfRange", min, max);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(EnumFiltro filtro, EnumParametro parametro, String mensaje, Object... args) {
        return Optional.of(new ResultadoValidacion(filtro, parametro, mensaje, args));
    }
}
