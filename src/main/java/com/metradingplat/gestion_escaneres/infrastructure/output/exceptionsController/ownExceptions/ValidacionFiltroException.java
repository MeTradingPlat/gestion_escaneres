package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions;

import java.util.List;

import lombok.Getter;
import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;

@Getter
public class ValidacionFiltroException extends BaseException {
    private final List<ResultadoValidacion> erroresValidacion;

    public ValidacionFiltroException(List<ResultadoValidacion> erroresValidacion) {
        super(CodigoError.VIOLACION_REGLA_DE_NEGOCIO, "");
        this.erroresValidacion = erroresValidacion;
    }
}