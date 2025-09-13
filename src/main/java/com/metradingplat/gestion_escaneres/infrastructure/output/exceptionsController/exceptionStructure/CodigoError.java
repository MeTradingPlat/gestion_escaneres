package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodigoError {
    ERROR_GENERICO("GC-0001", "error.generico"),
    ENTIDAD_YA_EXISTE("GC-0002", "error.entidadYaExiste"),
    ENTIDAD_NO_ENCONTRADA("GC-0003", "error.entidadNoEncontrada"),
    CAMBIO_DE_ESTADO_NO_PERMITIDO("GC-0004", "error.cambioDeEstado.noPermitido"),
    VIOLACION_REGLA_DE_NEGOCIO("GC-0005", "error.violacionReglaDeNegocio"),
    ERROR_VALIDACION("GC-0009", "error.validacion"),
    CREDENCIALES_INVALIDAS("GC-0006", "error.credencialesInvalidas"),
    USUARIO_DESHABILITADO("GC-0007", "error.usuarioDeshabilitado"),
    TOKEN_INVALIDO("GC-0008", "error.tokenInvalido"),
    TIPO_DE_ARGUMENTO_INVALIDO("GC-0009", "error.tipo.invalido");

    private final String codigo;
    private final String llaveMensaje;
}