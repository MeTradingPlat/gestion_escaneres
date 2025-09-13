package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Estructura estándar para representar errores en las respuestas del backend.
 * Diseñada para ser internacionalizable, trazable y útil en interfaces frontend.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    /**
     * Código único que identifica el tipo de error dentro de la lógica de negocio.
     * Ejemplo: "ERR_USUARIO_NO_ENCONTRADO"
     */
    private String codigoError;

    /**
     * Mensaje descriptivo del error, pensado para ser internacionalizado.
     * Puede ser una llave de mensaje o un texto directo.
     */
    private String mensaje;

    /**
     * Código de estado HTTP asociado al error.
     * Ejemplo: 404 para "No encontrado", 500 para "Error interno".
     */
    private Integer codigoHttp;

    /**
     * URL de la petición que generó el error.
     * Útil para trazabilidad y auditoría.
     */
    @Accessors(chain = true)
    private String url;

    /**
     * Método HTTP de la petición que generó el error.
     * Ejemplo: GET, POST, PUT, DELETE.
     */
    @Accessors(chain = true)
    private String metodo;

    /**
     * Nombre del campo o parámetro que causó el error, si aplica.
     * Útil para mostrar el error en el lugar correcto del formulario.
     * Ejemplo: "email", "password", "fechaNacimiento"
     */
    private String campo;

}