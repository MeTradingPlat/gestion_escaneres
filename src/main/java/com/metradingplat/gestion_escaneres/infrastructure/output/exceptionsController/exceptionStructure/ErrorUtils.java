package com.metradingplat.gestion_escaneres.infrastructure.output.exceptionsController.exceptionStructure;

/**
 * Utilitario para la creación estructurada de objetos <code>Error</code>.
 * Centraliza la lógica de construcción para mantener consistencia en el manejo de errores.
 */
public final class ErrorUtils {

    /**
     * Constructor privado para evitar instanciación.
     */
    private ErrorUtils() {
        // No instanciable
    }

    /**
     * Crea un nuevo objeto de <code>Error</code> con todos los campos disponibles.
     *
     * @param codigoError  Código único del error dentro de la lógica de negocio
     * @param mensaje      Mensaje descriptivo o llave de internacionalización
     * @param codigoHttp   Código de estado HTTP asociado al error
     * @param url          URL de la petición que generó el error
     * @param metodo       Método HTTP de la petición (GET, POST, etc.)
     * @return Objeto <code>Error</code> completamente construido
     */
    public static Error crearError(final String codigoError,
                                   final String mensaje,
                                   final Integer codigoHttp,
                                   final String url,
                                   final String metodo) {
        return Error.builder()
            .codigoError(codigoError)
            .mensaje(mensaje)
            .codigoHttp(codigoHttp)
            .url(url)
            .metodo(metodo)
            .build();
    }

    /**
     * Crea un nuevo objeto de <code>Error</code> con todos los campos disponibles.
     *
     * @param codigoError  Código único del error dentro de la lógica de negocio
     * @param mensaje      Mensaje descriptivo o llave de internacionalización
     * @param campo        Campo donde se origno el error
     * @param codigoHttp   Código de estado HTTP asociado al error
     * @param url          URL de la petición que generó el error
     * @param metodo       Método HTTP de la petición (GET, POST, etc.)
     * @return Objeto <code>Error</code> completamente construido
     */
    public static Error crearError(final String codigoError,
                                   final String mensaje,
                                   final String campo,
                                   final Integer codigoHttp,
                                   final String url,
                                   final String metodo) {
        return Error.builder()
            .codigoError(codigoError)
            .mensaje(mensaje)
            .campo(campo)
            .codigoHttp(codigoHttp)
            .url(url)
            .metodo(metodo)
            .build();
    }
}
