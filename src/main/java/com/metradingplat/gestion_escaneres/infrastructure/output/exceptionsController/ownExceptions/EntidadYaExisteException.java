package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions;

import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;

public class EntidadYaExisteException extends BaseException {
    public EntidadYaExisteException(String llaveMensaje, Object... args) {
        super(CodigoError.ENTIDAD_YA_EXISTE, llaveMensaje, args);
    }
}
