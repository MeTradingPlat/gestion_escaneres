package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.helper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.CodigoError;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.Error;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure.ErrorUtils;
import com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.ownExceptions.BaseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ErrorBuilderHelper {

    private final FuenteMensajesIntPort fuenteMensajes;

    public ResponseEntity<Error> buildFromException(BaseException ex,
                                                    HttpServletRequest req,
                                                    Locale locale,
                                                    HttpStatus status) {
        Object[] args = (ex.getArgs() == null) ? new Object[]{} : ex.getArgs();

        // 1) Obtén el patrón desde el message source
        String pattern = this.fuenteMensajes.obtenerMensaje(ex.getMessage(), locale, new Object[]{});

        // 2) Aplica formateo manual si es necesario
        String mensajeException = (args.length > 0 && containsBraces(pattern))
                ? MessageFormat.format(pattern, args)
                : pattern;

        Error error = buildError(req, locale, ex.getCodigoError(), status, mensajeException, "");
        return ResponseEntity.status(status).body(error);
    }

    public ResponseEntity<Error> buildResponse(HttpServletRequest req,
                                               Locale locale,
                                               CodigoError codigoError,
                                               HttpStatus status,
                                               String mensajeException,
                                               String campo) {
        Error error = buildError(req, locale, codigoError, status, mensajeException, campo);
        return ResponseEntity.status(status).body(error);
    }

    public Error buildError(HttpServletRequest req,
                            Locale locale,
                            CodigoError codigoError,
                            HttpStatus status,
                            String mensajeException,
                            String campo) {

        String mensajeCodigo = this.fuenteMensajes.obtenerMensaje(codigoError.getLlaveMensaje(), locale, new Object[]{});

        String mensajeFinal = (mensajeException != null && !mensajeException.isEmpty())
                ? mensajeCodigo + ", " + mensajeException
                : mensajeCodigo;

        return ErrorUtils.crearError(
                codigoError.getCodigo(),
                mensajeFinal,
                campo,
                status.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
    }

    public Error buildSimpleValidationError(HttpServletRequest req,
                                        Locale locale,
                                        String mensajeKey,
                                        String campo,
                                        HttpStatus status) {

        // 1) Traducción del mensaje (se asume que mensajeKey ya es llave de i18n)
        String mensajeTraducido = this.fuenteMensajes.obtenerMensaje(mensajeKey, locale, new Object[]{});

        // 2) Crear Error estándar
        return ErrorUtils.crearError(
                CodigoError.ERROR_VALIDACION.getCodigo(),
                mensajeTraducido,
                campo,
                status.value(),
                req.getRequestURL().toString(),
                req.getMethod()
        );
    }


    private boolean containsBraces(String s) {
        return s != null && s.indexOf('{') >= 0 && s.indexOf('}') >= 0;
    }
}
