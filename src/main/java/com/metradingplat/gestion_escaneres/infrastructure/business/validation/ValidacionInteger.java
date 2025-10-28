package com.metradingplat.gestion_escaneres.infrastructure.business.validation;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import java.util.Optional;

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
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado("validation.parameter.value.required", enumParametro);
        }

        if (!(valor instanceof ValorInteger valorInteger)) {
            return resultado("validation.parameter.type.invalid", enumParametro);
        }

        Integer contenido = valorInteger.getValor();

        if (contenido == null) {
            return resultado("validation.parameter.value.required", enumParametro);
        }

        if (contenido < min || contenido > max) {
            return resultado("validation.parameter.values.outOfRange", enumParametro, min, max);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro, Object... args) {
        return Optional.of(new ResultadoValidacion(parametro, mensaje, args));
    }
}
