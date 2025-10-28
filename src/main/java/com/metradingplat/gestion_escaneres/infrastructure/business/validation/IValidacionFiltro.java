package com.metradingplat.gestion_escaneres.infrastructure.business.validation;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import java.util.Optional;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;

/**
 * Interfaz que define el contrato para estrategias de validación de parámetros de filtros.
 *
 * <p><b>Patrón de Diseño:</b> Strategy Pattern
 *
 * <p>Esta interfaz permite implementar diferentes algoritmos de validación que pueden
 * ser intercambiados en tiempo de ejecución. Cada implementación concreta encapsula
 * un algoritmo específico de validación (Float, Integer, String, etc.).
 *
 * <p><b>Implementaciones concretas:</b>
 * <ul>
 *   <li>{@link ValidacionFloat} - Valida valores Float dentro de un rango</li>
 *   <li>{@link ValidacionInteger} - Valida valores Integer dentro de un rango</li>
 *   <li>{@link ValidacionString} - Valida valores String contra una enumeración</li>
 *   <li>{@link ValidacionStringConOpciones} - Valida valores String contra una lista de opciones</li>
 *   <li>{@link ValidacionCondicional} - Valida valores condicionales (rangos con operadores)</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b>
 * <pre>{@code
 * IValidacionFiltro estrategia = new ValidacionFloat(0f, 100f);
 * Optional<ResultadoValidacion> resultado = estrategia.validar(parametro, valor);
 *
 * if (resultado.isPresent()) {
 *     // La validación falló
 *     ResultadoValidacion error = resultado.get();
 *     System.out.println("Error: " + error.mensaje());
 * } else {
 *     // La validación fue exitosa
 * }
 * }</pre>
 *
 * @see ValidadorParametroFiltro
 * @see ResultadoValidacion
 */
public interface IValidacionFiltro {

    /**
     * Valida un valor según las reglas específicas de la estrategia implementada.
     *
     * @param enumParametro El parámetro que se está validando (usado para mensajes de error)
     * @param valor El valor a validar
     * @return Optional.empty() si la validación es exitosa,
     *         Optional con ResultadoValidacion si hay errores de validación
     */
    Optional<ResultadoValidacion> validar(EnumParametro enumParametro, Valor valor);
}
