package com.metradingplat.gestion_escaneres.infrastructure.business.validation;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorCondicional;

import java.util.Optional;

/**
 * Estrategia de validación para valores condicionales.
 *
 * <p>Valida que los valores numéricos cumplan con:
 * <ul>
 *   <li>Rangos mínimos y máximos permitidos</li>
 *   <li>Orden correcto para operadores ENTRE/FUERA</li>
 *   <li>Tipo de dato correcto (Integer vs Float)</li>
 * </ul>
 *
 * @see IValidacionFiltro
 */
public class ValidacionCondicional implements IValidacionFiltro {

    private final float min;
    private final float max;

    public ValidacionCondicional(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (!(valor instanceof ValorCondicional vc)) {
            return resultado("validation.parameter.type.invalid", enumParametro);
        }

        // Validar que isInteger esté configurado
        if (vc.getIsInteger() == null) {
            return resultado("validation.parameter.isInteger.notSet", enumParametro);
        }

        // Validar tipo de dato según isInteger
        Optional<ResultadoValidacion> errorTipo = validarTipoDato(vc, enumParametro);
        if (errorTipo.isPresent()) {
            return errorTipo;
        }

        Float v1 = toFloat(vc.getValor1());
        EnumCondicional cond = vc.getEnumCondicional();

        // Validar operadores que requieren dos valores
        if (cond == EnumCondicional.ENTRE || cond == EnumCondicional.FUERA) {
            if (vc.getValor2() == null) {
                return resultado("validation.parameter.secondValue.required", enumParametro);
            }

            Float v2 = toFloat(vc.getValor2());

            // Validar orden de valores
            if (cond == EnumCondicional.ENTRE && v1 >= v2) {
                return resultado("validation.parameter.values.orderInvalid.between", enumParametro, v1, v2);
            }
            if (cond == EnumCondicional.FUERA && v1 <= v2) {
                return resultado("validation.parameter.values.orderInvalid.outside", enumParametro, v1, v2);
            }

            // Validar rango
            if (v1 < min || v2 > max) {
                return resultado("validation.parameter.values.outOfRange", enumParametro, min, max);
            }

        } else {
            // Validar rango para operadores de un solo valor
            if (v1 < min || v1 > max) {
                return resultado("validation.parameter.values.outOfRange", enumParametro, min, max);
            }
        }

        return Optional.empty();
    }

    /**
     * Valida que el tipo de dato del valor coincida con el flag isInteger.
     *
     * @param vc ValorCondicional a validar
     * @param enumParametro Parámetro para el mensaje de error
     * @return Optional con error si no coincide, empty si es válido
     */
    private Optional<ResultadoValidacion> validarTipoDato(ValorCondicional vc, EnumParametro enumParametro) {
        Number v1 = vc.getValor1();
        Number v2 = vc.getValor2();
        Boolean isInteger = vc.getIsInteger();

        if (isInteger) {
            // Si isInteger=true, debe ser Integer o Long
            if (v1 != null && !(v1 instanceof Integer) && !(v1 instanceof Long)) {
                return resultado("validation.parameter.type.mustBeInteger", enumParametro);
            }
            if (v2 != null && !(v2 instanceof Integer) && !(v2 instanceof Long)) {
                return resultado("validation.parameter.type.mustBeInteger", enumParametro);
            }
        } else {
            // Si isInteger=false, debe ser Float o Double
            if (v1 != null && !(v1 instanceof Float) && !(v1 instanceof Double)) {
                return resultado("validation.parameter.type.mustBeFloat", enumParametro);
            }
            if (v2 != null && !(v2 instanceof Float) && !(v2 instanceof Double)) {
                return resultado("validation.parameter.type.mustBeFloat", enumParametro);
            }
        }

        return Optional.empty();
    }

    /**
     * Convierte un Number a Float para comparaciones.
     *
     * @param n Número a convertir
     * @return Valor como Float
     */
    private static float toFloat(Number n) {
        return switch (n) {
            case Integer i -> i.floatValue();
            case Long l -> l.floatValue();
            case Float f -> f;
            case Double d -> d.floatValue();
            default -> n.floatValue();
        };
    }

    /**
     * Crea un ResultadoValidacion con el mensaje y parámetros dados.
     *
     * @param mensaje Clave del mensaje de error
     * @param parametro Parámetro que falló la validación
     * @param args Argumentos adicionales para el mensaje
     * @return Optional con el resultado de validación
     */
    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro, Object... args) {
        return Optional.of(new ResultadoValidacion(parametro, mensaje, args));
    }
}
