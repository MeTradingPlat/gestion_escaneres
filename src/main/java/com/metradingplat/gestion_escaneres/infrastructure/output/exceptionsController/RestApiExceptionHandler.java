package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.Error;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.ErrorUtils;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final FuenteMensajesIntPort objFuenteMensajes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(HttpServletRequest req,
                                                        Exception ex,
                                                        Locale locale) {
        String llaveMensaje = CodigoError.ERROR_GENERICO.getLlaveMensaje();
        String mensajeFinal = this.objFuenteMensajes.obtenerMensaje(llaveMensaje,locale);

        Error error = ErrorUtils.crearError(
                CodigoError.ERROR_GENERICO.getCodigo(),
                mensajeFinal,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ResponseEntity<Error> handleBaseException(BaseException ex,
                                                     HttpServletRequest req,
                                                     Locale locale,
                                                     HttpStatus status) {

        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();
        String pattern = this.objFuenteMensajes.obtenerMensaje(ex.getMessage(), locale);

        String mensajeException = (args.length > 0 && containsBraces(pattern))
                ? MessageFormat.format(pattern, args)
                : pattern;

        String mensajeCodigo = this.objFuenteMensajes.obtenerMensaje(ex.getCodigoError().getLlaveMensaje(), locale, new Object[]{});

        String mensajeFinal = (mensajeException != null && !mensajeException.isEmpty())
                ? mensajeCodigo + ", " + mensajeException
                : mensajeCodigo;

        Error error = ErrorUtils.crearError(
                ex.getCodigoError().getCodigo(),
                mensajeFinal,
                status.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Error> handleEntidadNoExisteException(EntidadNoExisteException ex,
                                                                HttpServletRequest req,
                                                                Locale locale) {
                                                                    
        return handleBaseException(ex, req, locale, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntidadYaExisteException.class)
    public ResponseEntity<Error> handleEntidadYaExisteException(EntidadYaExisteException ex,
                                                                HttpServletRequest req,
                                                                Locale locale) {
        return handleBaseException(ex, req, locale, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EstadoDenegadoException.class)
    public ResponseEntity<Error> handleEstadoDenegadoException(EstadoDenegadoException ex,
                                                               Locale locale) {
        return handleBaseException(ex, null, locale, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Error> handleReglaNegocioException(ReglaNegocioException ex,
                                                             HttpServletRequest req,
                                                             Locale locale) {
        return handleBaseException(ex, req, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidacionFiltroException.class)
    public ResponseEntity<List<Error>> handleValidacionMultipleException(ValidacionFiltroException ex,
                                                                         HttpServletRequest req,
                                                                         Locale locale) {
        List<Error> errores = ex.getErroresValidacion().stream()
                .map(rv -> {
                    String mensajeCodigo = this.objFuenteMensajes.obtenerMensaje(ex.getCodigoError().getLlaveMensaje(), locale, new Object[]{});
                    return ErrorUtils.crearError(
                            ex.getCodigoError().getCodigo(),
                            mensajeCodigo,
                            HttpStatus.UNPROCESSABLE_ENTITY.value(),
                            req.getRequestURL().toString(),
                            req.getMethod()
                    );
                })
                .toList();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errores);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                  HttpServletRequest req,
                                                                  Locale locale) {
        List<Error> errores = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String mensajeKey = error.getDefaultMessage(); // debe ser llave i18n
                    String mensajeTraducido = this.objFuenteMensajes.obtenerMensaje(mensajeKey, locale, new Object[]{});
                    return ErrorUtils.crearError(
                            CodigoError.ERROR_VALIDACION.getCodigo(),
                            mensajeTraducido,
                            HttpStatus.BAD_REQUEST.value(),
                            req.getRequestURL().toString(),
                            req.getMethod()
                    );
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
                    String mensajeKey = cv.getMessage(); // debe ser llave i18n
                    String mensajeTraducido = this.objFuenteMensajes.obtenerMensaje(mensajeKey, locale, new Object[]{});
                    return ErrorUtils.crearError(
                            CodigoError.ERROR_VALIDACION.getCodigo(),
                            mensajeTraducido,
                            HttpStatus.BAD_REQUEST.value(),
                            req.getRequestURL().toString(),
                            req.getMethod()
                    );
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                    HttpServletRequest req,
                                                    Locale locale) {
        String mensajeFinal = ex.getName() + ": " + ex.getValue();
        Error error = ErrorUtils.crearError(
                CodigoError.TIPO_DE_ARGUMENTO_INVALIDO.getCodigo(),
                mensajeFinal,
                HttpStatus.BAD_REQUEST.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private boolean containsBraces(String s) {
        return s != null && s.indexOf('{') >= 0 && s.indexOf('}') >= 0;
    }
}
