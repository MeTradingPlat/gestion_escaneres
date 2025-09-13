package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorsResponse {
    private String codigoError;
    private String mensaje;
    private Integer codigoHttp;
    private String url;
    private String metodo;
    private List<ValidationError> erroresValidacion;
}