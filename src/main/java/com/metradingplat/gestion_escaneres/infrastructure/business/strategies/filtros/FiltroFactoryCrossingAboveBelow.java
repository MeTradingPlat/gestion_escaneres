package com.metradingplat.gestion_escaneres.infrastructure.business.strategies.filtros;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;
import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumNivelCruce;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;
import com.metradingplat.gestion_escaneres.domain.models.ValorString;
import com.metradingplat.gestion_escaneres.infrastructure.business.strategies.IFiltroFactory;
import com.metradingplat.gestion_escaneres.infrastructure.business.validation.ValidadorParametroFiltro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Factory para crear filtros de tipo Crossing Above/Below.
 *
 * <p>Permite detectar cruces de precio por encima o por debajo de niveles de referencia
 * como Open, VWAP, o EMAs específicas.
 */
@Component
@RequiredArgsConstructor
public class FiltroFactoryCrossingAboveBelow implements IFiltroFactory {

    private final EnumFiltro enumFiltro = EnumFiltro.CROSSING_ABOVE_BELOW;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO;    private final ValidadorParametroFiltro objValidador;


    @Override
    public EnumFiltro obtenerEnumFiltro() {
        return this.enumFiltro;
    }

    @Override
    public EnumCategoriaFiltro obtenerEnumCategoria() {
        return this.enumCategoria;
    }

    @Override
    public Filtro obtenerFiltro() {
        return this.obtenerFiltro(new HashMap<>());
    }

    @Override
    public Filtro obtenerInformacionFiltro() {
        Filtro filtro = new Filtro();
        filtro.setEnumFiltro(this.enumFiltro);
        filtro.setEtiquetaNombre(this.enumFiltro.getEtiquetaNombre());
        filtro.setEtiquetaDescripcion(this.enumFiltro.getEtiquetaDescripcion());

        CategoriaFiltro objCategoriaFiltro = new CategoriaFiltro();
        objCategoriaFiltro.setEnumCategoriaFiltro(this.enumCategoria);
        objCategoriaFiltro.setEtiqueta(this.enumCategoria.getEtiqueta());

        filtro.setObjCategoria(objCategoriaFiltro);

        return filtro;
    }

    @Override
    public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados) {
        Filtro filtro = this.obtenerInformacionFiltro();

        List<Parametro> parametros = new ArrayList<>();
        parametros.add(this.crearParametroNivelCruce((ValorString) valoresSeleccionados.get(EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW)));
        parametros.add(this.crearParametroPeriodoEma((ValorInteger) valoresSeleccionados.get(EnumParametro.PERIODO_EMA_CROSSING_ABOVE_BELOW)));

        filtro.setParametros(parametros);
        return filtro;
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarString(
                        this.enumFiltro,
                        EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW,
                        valoresSeleccionados.get(EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW),
                        EnumNivelCruce.class)
                .ifPresent(errores::add);

        Valor nivelCruceValor = valoresSeleccionados.get(EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW);
        if (nivelCruceValor instanceof ValorString && ((ValorString) nivelCruceValor).getValor().equalsIgnoreCase(EnumNivelCruce.EMA.name())) {
            this.objValidador.validarInteger(
                            this.enumFiltro,
                            EnumParametro.PERIODO_EMA_CROSSING_ABOVE_BELOW,
                            valoresSeleccionados.get(EnumParametro.PERIODO_EMA_CROSSING_ABOVE_BELOW),
                            2, 100)
                    .ifPresent(errores::add);
        }

        return errores;
    }

    // ==================== Private Helper Methods ====================

    private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
        return Arrays.stream(enumValores)
                .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
                .collect(Collectors.toList());
    }

    private Parametro crearParametroNivelCruce(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumNivelCruce.values());
        EnumNivelCruce enumValor = valorUsuario != null
                ? EnumNivelCruce.valueOf(valorUsuario.getValor())
                : EnumNivelCruce.OPEN;
        ValorString valor = new ValorString(
                enumValor.getEtiqueta(),
                enumTipoValor,
                enumValor.name()
        );
        return new Parametro(
                EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW,
                EnumParametro.NIVEL_CRUCE_CROSSING_ABOVE_BELOW.getEtiqueta(),
                valor,
                opciones
        );
    }

    private Parametro crearParametroPeriodoEma(ValorInteger valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
        ValorInteger valor = new ValorInteger(
                "etiqueta.vacia",
                enumTipoValor,
                valorUsuario != null ? valorUsuario.getValor() : 2
        );
        return new Parametro(
                EnumParametro.PERIODO_EMA_CROSSING_ABOVE_BELOW,
                EnumParametro.PERIODO_EMA_CROSSING_ABOVE_BELOW.getEtiqueta(),
                valor,
                opciones
        );
    }
}
