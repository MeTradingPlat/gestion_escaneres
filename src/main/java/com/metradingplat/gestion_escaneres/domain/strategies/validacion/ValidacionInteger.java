package com.metradingplat.gestion_escaneres.domain.strategies.validacion;

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
            return resultado("filtro.parametro.requerido", enumParametro);
        }

        if (!(valor instanceof ValorInteger valorInteger)) {
            return resultado("filtro.parametro.tipoInvalido", enumParametro);
        }

        Integer contenido = valorInteger.getValor();

        if (contenido == null) {
            return resultado("filtro.parametro.valorInvalido", enumParametro);
        }

        if (contenido < min || contenido > max) {
            return resultado("filtro.parametro.rangoInvalido", enumParametro);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro) {
        return Optional.of(new ResultadoValidacion(mensaje, parametro));
    }
}
