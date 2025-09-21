package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.Error;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.ErrorUtils;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final FuenteMensajesIntPort objFuenteMensajes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(HttpServletRequest req, Exception ex) {
        return createErrorResponse(
            CodigoError.ERROR_GENERICO,
            HttpStatus.INTERNAL_SERVER_ERROR,
            req,
            CodigoError.ERROR_GENERICO.getLlaveMensaje()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        return createErrorResponse(
            CodigoError.TIPO_DE_ARGUMENTO_INVALIDO,
            HttpStatus.BAD_REQUEST,
            req,
            CodigoError.TIPO_DE_ARGUMENTO_INVALIDO.getLlaveMensaje()
        );
    }

    @ExceptionHandler(EntidadYaExisteException.class)
    public ResponseEntity<Error> handleEntidadYaExisteException(final HttpServletRequest req, final EntidadYaExisteException ex) {
        return createErrorResponse(
            CodigoError.ENTIDAD_YA_EXISTE,
            HttpStatus.NOT_ACCEPTABLE,
            req,
            ex.getMessage(),
            ex.getArgs()
        );
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleEntidadNoExisteException(EntidadNoExisteException ex, HttpServletRequest req) {
        return createErrorResponse(
            CodigoError.ENTIDAD_NO_ENCONTRADA,
            HttpStatus.NOT_ACCEPTABLE,
            req,
            ex.getMessage(),
            ex.getArgs()
        );
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Error> handleReglaNegocioExcepcion(ReglaNegocioException ex, HttpServletRequest req) {
        return createErrorResponse(
            CodigoError.VIOLACION_REGLA_DE_NEGOCIO,
            HttpStatus.BAD_REQUEST,
            req,
            ex.getMessage(),
            ex.getArgs()
        );
    }

    @ExceptionHandler(EstadoDenegadoException.class)
    public ResponseEntity<Error> handleEstadoDenegadoException(EstadoDenegadoException ex, HttpServletRequest req) {
        return createErrorResponse(
            CodigoError.VIOLACION_REGLA_DE_NEGOCIO,
            HttpStatus.BAD_REQUEST,
            req,
            ex.getMessage(),
            ex.getArgs()
        );
    }

    @ExceptionHandler(ValidacionFiltroException.class)
    public ResponseEntity<Map<EnumParametro, String>> handleValidationFiltroExceptions(ValidacionFiltroException ex, HttpServletRequest req) {
        Map<EnumParametro, String> errores = new HashMap<>();

        ex.getErroresValidacion().forEach(errorValidacion -> {
            String mensaje = internacionalizarMensaje(errorValidacion.mensaje(), errorValidacion.args());
            errores.put(errorValidacion.enumParametro(), mensaje);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensajeDeError = internacionalizarMensaje(error.getDefaultMessage(), error.getArguments());
            errores.put(campo, mensajeDeError);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    
    private ResponseEntity<Error> createErrorResponse(CodigoError codigoError, HttpStatus httpStatus, 
                                                     HttpServletRequest req, String mensajeClave, Object... args) {
        String mensaje = internacionalizarMensaje(mensajeClave, args);
        
        Error error = ErrorUtils.crearError(
            codigoError.getCodigo(),
            mensaje,
            httpStatus.value(),
            req.getRequestURL().toString(),
            req.getMethod()
        );
        
        return ResponseEntity.status(httpStatus).body(error);
    }

    private String internacionalizarMensaje(String claveMensaje, Object... args) {
        Object[] argumentos = (args == null || args.length == 0) ? new Object[]{} : args;
        return this.objFuenteMensajes.internacionalizarMensaje(claveMensaje, argumentos);
    }
}