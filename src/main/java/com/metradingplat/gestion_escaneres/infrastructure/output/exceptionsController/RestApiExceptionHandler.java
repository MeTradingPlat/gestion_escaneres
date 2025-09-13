package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.Error;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.helper.ErrorBuilderHelper;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final ErrorBuilderHelper errorHelper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(HttpServletRequest req,
                                                        Exception ex,
                                                        Locale locale) {
        return errorHelper.buildResponse(req, locale,
                CodigoError.ERROR_GENERICO,
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                "");
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleEntidadNoExisteException(EntidadNoExisteException ex,
                                                                HttpServletRequest req,
                                                                Locale locale) {
        return errorHelper.buildFromException(ex, req, locale, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntidadYaExisteException.class)
    public ResponseEntity<Error> handleEntidadYaExisteException(EntidadYaExisteException ex,
                                                                HttpServletRequest req,
                                                                Locale locale) {
        return errorHelper.buildFromException(ex, req, locale, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EstadoDenegadoException.class)
    public ResponseEntity<Error> handleEstadoDenegadoException(EstadoDenegadoException ex,
                                                               HttpServletRequest req,
                                                               Locale locale) {
        return errorHelper.buildFromException(ex, req, locale, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Error> handleReglaNegocioException(ReglaNegocioException ex,
                                                             HttpServletRequest req,
                                                             Locale locale) {
        return errorHelper.buildFromException(ex, req, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidacionFiltroException.class)
    public ResponseEntity<List<Error>> handleValidacionMultipleException(ValidacionFiltroException ex,
                                                                         HttpServletRequest req,
                                                                         Locale locale) {
        List<Error> errores = ex.getErroresValidacion().stream()
                .map(rv -> errorHelper.buildError(req,
                        locale,
                        ex.getCodigoError(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        null,
                        rv.enumParametro().name()))
                .toList();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                HttpServletRequest req,
                                                                Locale locale) {
        List<Error> errores = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String campo = ((FieldError) error).getField();
                    String mensajeKey = error.getDefaultMessage(); // debe ser llave i18n
                    return errorHelper.buildSimpleValidationError(req, locale, mensajeKey, campo, HttpStatus.BAD_REQUEST);
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Error>> handleConstraintViolationException(ConstraintViolationException ex,
                                                                        HttpServletRequest req,
                                                                        Locale locale) {
        List<Error> errores = ex.getConstraintViolations().stream()
                .map(cv -> {
                    String campo = cv.getPropertyPath().toString();
                    String mensajeKey = cv.getMessage(); // debe ser llave i18n
                    return errorHelper.buildSimpleValidationError(req, locale, mensajeKey, campo, HttpStatus.BAD_REQUEST);
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                    HttpServletRequest req,
                                                    Locale locale) {
        String campo = ex.getName();
        return errorHelper.buildResponse(
                req,
                locale,
                CodigoError.TIPO_DE_ARGUMENTO_INVALIDO,
                HttpStatus.BAD_REQUEST,
                campo,
                String.valueOf(ex.getValue())
        );
    }
}
