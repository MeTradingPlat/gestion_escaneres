package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions;

import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;

public class EstadoDenegadoException extends BaseException {
    public EstadoDenegadoException(String llaveMensaje, Object... args) {
        super(CodigoError.CAMBIO_DE_ESTADO_NO_PERMITIDO, llaveMensaje, args);
    }
}