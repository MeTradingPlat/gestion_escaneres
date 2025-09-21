package com.metradingplat.gestion_escaneres.domain.strategies;

import java.util.List;
import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.IValidacionFiltro;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.*;

public class ValidadorParametroFiltro {

    public Optional<ResultadoValidacion> validarCondicional(EnumParametro enumParametro, Valor valor, Float min, Float max){
        IValidacionFiltro objValidacion = new ValidacionCondicional(min, max);
        return objValidacion.validar(enumParametro, valor);
    }

    public Optional<ResultadoValidacion> valdiarFloat(EnumParametro enumParametro, Valor valor, Float min, Float max){
        IValidacionFiltro objValidador = new ValidacionFloat(min, max);
        return objValidador.validar(enumParametro, valor);
    }

    public Optional<ResultadoValidacion> validarInteger(EnumParametro enumParametro, Valor valor, float min, float max) {
        IValidacionFiltro validador = new ValidacionInteger((int) min, (int) max);
        return validador.validar(enumParametro, valor);
    }

    public <E extends Enum<E>> Optional<ResultadoValidacion> validarString(EnumParametro enumParametro, Valor valor, Class<E> enumOpciones) {
        IValidacionFiltro validador = new ValidacionString<>(enumOpciones);
        return validador.validar(enumParametro, valor);
    }

    public <E extends Enum<E> & IEnumValores> Optional<ResultadoValidacion> validarStringConOpciones(EnumParametro enumParametro, Valor valor, List<E> opcionesPermitidas) {
        IValidacionFiltro validador = new ValidacionStringConOpciones<>(opcionesPermitidas);
        return validador.validar(enumParametro, valor);
    }

}

