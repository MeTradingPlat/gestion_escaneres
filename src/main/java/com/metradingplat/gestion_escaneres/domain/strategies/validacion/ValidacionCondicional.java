package com.metradingplat.gestion_escaneres.domain.strategies.validacion;

import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorCondicional;

public class ValidacionCondicional implements IValidacionFiltro {

    private float min;
    private float max;

    public ValidacionCondicional(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (!(valor instanceof ValorCondicional vc)) {
            return resultado("validation.parameter.type.invalid", enumParametro);
        }

        Float v1 = toFloat(vc.getValor1());
        EnumCondicional cond = vc.getEnumCondicional();

        if (cond == EnumCondicional.ENTRE || cond == EnumCondicional.FUERA) {
            if (vc.getValor2() == null) {
                return resultado("validation.parameter.secondValue.required", enumParametro);
            }

            Float v2 = toFloat(vc.getValor2());

            if (cond == EnumCondicional.ENTRE && v1 >= v2){
                return resultado("validation.parameter.values.orderInvalid.between", enumParametro, v1, v2);
            }
            if (cond == EnumCondicional.FUERA && v1 <= v2) {
                return resultado("validation.parameter.values.orderInvalid.outside", enumParametro, v1, v2);
            }

            if (v1 < min || v2 > max) {
                return resultado("validation.parameter.values.outOfRange", enumParametro, min, max);
            }

        } else {
            if (v1 < min || v1 > max) {
                return resultado("validation.parameter.values.outOfRange", enumParametro, min, max);
            }
        }

        return Optional.empty();
    }

    private static float toFloat(Number n) {
        return switch (n) {
            case Integer i -> i.floatValue();
            case Float f   -> f;
            default       -> n.floatValue();
        };
    }

    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro, Object... args) {
        return Optional.of(new ResultadoValidacion(parametro, mensaje, args));
    }
}