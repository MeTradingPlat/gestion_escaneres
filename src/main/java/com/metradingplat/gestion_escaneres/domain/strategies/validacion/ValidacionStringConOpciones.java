package com.metradingplat.gestion_escaneres.domain.strategies.validacion;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorString;

import java.util.List;
import java.util.Optional;

public class ValidacionStringConOpciones<E extends Enum<E> & IEnumValores> implements IValidacionFiltro {

    private final List<E> opcionesPermitidas;

    public ValidacionStringConOpciones(List<E> opcionesPermitidas) {
        this.opcionesPermitidas = opcionesPermitidas;
    }

    @Override
    public Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor) {
        if (valor == null) {
            return resultado("validation.parameter.value.required", enumParametro);
        }

        if (!(valor instanceof ValorString valorStr)) {
            return resultado("validation.parameter.type.invalid", enumParametro);
        }

        String valorTexto = valorStr.getValor();

        if (valorTexto.trim().isEmpty()) {
            return resultado("validation.parameter.value.required", enumParametro);
        }

        boolean esValido = opcionesPermitidas.stream()
                .anyMatch(opcion -> opcion.name().equalsIgnoreCase(valorTexto));

        if (!esValido) {
            return resultado("validation.enum.invalid", enumParametro);
        }

        return Optional.empty();
    }

    private Optional<ResultadoValidacion> resultado(String mensaje, EnumParametro parametro, Object... args) {
        return Optional.of(new ResultadoValidacion(parametro, mensaje, args));
    }
}