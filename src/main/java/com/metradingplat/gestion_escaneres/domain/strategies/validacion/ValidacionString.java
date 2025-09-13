package com.metradingplat.gestion_escaneres.domain.strategies.validacion;

import java.util.Optional;
import java.util.stream.Stream;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorString;

public class ValidacionString<E extends Enum<E>> implements IValidacionFiltro {
    private final Class<E> enumOpcionesClass;

    public ValidacionString(Class<E> enumOpcionesClass) {
        this.enumOpcionesClass = enumOpcionesClass;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado("filtro.parametro.requerido", enumParametro);
        }

        if (!(valor instanceof ValorString valorString)) {
            return resultado("filtro.parametro.tipoInvalido", enumParametro);
        }

        String contenido = valorString.getValor();

        if (contenido == null || !esValorEnumValido(contenido)) {
            return resultado("filtro.parametro.valorInvalido", enumParametro);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro) {
        return Optional.of(new ResultadoValidacion(mensaje, parametro));
    }

    private boolean esValorEnumValido(String valor) {
        return Stream.of(enumOpcionesClass.getEnumConstants())
                     .anyMatch(e -> e.name().equalsIgnoreCase(valor));
    }
}