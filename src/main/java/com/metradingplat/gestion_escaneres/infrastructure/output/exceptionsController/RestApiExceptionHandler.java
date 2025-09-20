package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;
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
    public ResponseEntity<Error> handleGenericException(HttpServletRequest req,
                                                        Exception ex) {

        String mensaje = this.objFuenteMensajes.internacionalizarMensaje(CodigoError.ERROR_GENERICO.getLlaveMensaje());

        final Error error = ErrorUtils.crearError(
                CodigoError.ERROR_GENERICO.getCodigo(),
                mensaje,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(EntidadYaExisteException.class)
    public ResponseEntity<Error> handleEntidadYaExisteException(final HttpServletRequest req,
                                                                final EntidadYaExisteException ex) {
        
        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();
        String mensaje = String.format(
                "%s, %s",
                this.objFuenteMensajes.internacionalizarMensaje(CodigoError.ENTIDAD_YA_EXISTE.getLlaveMensaje()),
                this.objFuenteMensajes.internacionalizarMensaje(ex.getMessage(),args)
        );

        final Error error = ErrorUtils.crearError(
                CodigoError.ENTIDAD_YA_EXISTE.getCodigo(),
                mensaje,
                HttpStatus.NOT_ACCEPTABLE.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleEntidadNoExisteException(EntidadNoExisteException ex,
                                                                HttpServletRequest req) {
  
        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();
        String mensaje = String.format(
                "%s, %s",
                this.objFuenteMensajes.internacionalizarMensaje(CodigoError.ENTIDAD_NO_ENCONTRADA.getLlaveMensaje()),
                this.objFuenteMensajes.internacionalizarMensaje(ex.getMessage(),args)
        );

        final Error error = ErrorUtils.crearError(
                CodigoError.ENTIDAD_NO_ENCONTRADA.getCodigo(),
                mensaje,
                HttpStatus.NOT_ACCEPTABLE.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Error> handleReglaNegocioExcepcion(ReglaNegocioException ex,
                                                                HttpServletRequest req) {
  
        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();
        String mensaje = String.format(
                "%s, %s",
                this.objFuenteMensajes.internacionalizarMensaje(CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getLlaveMensaje()),
                this.objFuenteMensajes.internacionalizarMensaje(ex.getMessage(),args)
        );

        final Error error = ErrorUtils.crearError(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(),
                mensaje,
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EstadoDenegadoException.class)
    public ResponseEntity<Error> handleEstadoDenegadoException(EstadoDenegadoException ex,
                                                                HttpServletRequest req) {
  
        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();
        String mensaje = String.format(
                "%s, %s",
                this.objFuenteMensajes.internacionalizarMensaje(CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getLlaveMensaje()),
                this.objFuenteMensajes.internacionalizarMensaje(ex.getMessage(),args)
        );
        final Error error = ErrorUtils.crearError(
                CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(),
                mensaje,
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            Object[] args = error.getArguments();
            String llaveMensaje = error.getDefaultMessage();
            String mensajeDeError = this.objFuenteMensajes.internacionalizarMensaje(llaveMensaje,args);
            errores.put(campo, mensajeDeError);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                  HttpServletRequest req) {
        
        Map<String, String> error = new HashMap<>();
        String campo = ex.getName();
        String mensaje = this.objFuenteMensajes.internacionalizarMensaje("validacion.tipo.invalida");
        error.put(campo, mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
