package com.metradingplat.gestion_escaneres.infrastructure.business.validation;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorFloat;

public class ValidacionFloat implements IValidacionFiltro{
    private final Float min;
    private final Float max;

    public ValidacionFloat (Float min, Float max){
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado("validation.parameter.value.required", enumParametro);
        }

        if (!(valor instanceof ValorFloat valorFloat)) {
            return resultado("validation.format.invalid", enumParametro);
        }

        Float contenido = valorFloat.getValor();

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
