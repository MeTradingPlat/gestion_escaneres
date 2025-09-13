package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions;

import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;

public class EntidadNoExisteException extends BaseException {
    public EntidadNoExisteException(String llaveMensaje, Object... args) {
        super(CodigoError.ENTIDAD_NO_ENCONTRADA, llaveMensaje, args);
    }
    
}
