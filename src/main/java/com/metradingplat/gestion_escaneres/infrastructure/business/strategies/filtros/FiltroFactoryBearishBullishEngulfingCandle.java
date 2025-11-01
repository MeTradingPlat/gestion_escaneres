package com.metradingplat.gestion_escaneres.infrastructure.business.strategies.filtros;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTimeframe;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTipoPatron;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorString;

import com.metradingplat.gestion_escaneres.infrastructure.business.strategies.IFiltroFactory;
import com.metradingplat.gestion_escaneres.infrastructure.business.validation.ValidadorParametroFiltro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FiltroFactoryBearishBullishEngulfingCandle implements IFiltroFactory {
    private final EnumFiltro enumFiltro = EnumFiltro.BEARISH_BULLISH_ENGULFING;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.TIEMPO_Y_PATRONES_DE_PRECIO;
    private final ValidadorParametroFiltro objValidador;

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
    public Filtro obtenerInformacionFiltro(){
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
        parametros.add(this.crearParametroTimeframe((ValorString)valoresSeleccionados.get(EnumParametro.TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE)));
        parametros.add(this.crearParametroTipoPatron((ValorString)valoresSeleccionados.get(EnumParametro.TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
        return Arrays.stream(enumValores)
            .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
            .collect(Collectors.toList());
    }

    private Parametro crearParametroTimeframe(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumTimeframe.values());
        EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor()) : EnumTimeframe._1D;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE, EnumParametro.TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroTipoPatron(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumTipoPatron.values());
        EnumTipoPatron enumValor = valorUsuario != null ? EnumTipoPatron.valueOf(valorUsuario.getValor()) : EnumTipoPatron.BULLISH;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE, EnumParametro.TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarString(this.enumFiltro, EnumParametro.TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE,
                valoresSeleccionados.get(EnumParametro.TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE), EnumTimeframe.class)
                .ifPresent(errores::add);

        this.objValidador.validarString(this.enumFiltro, EnumParametro.TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE,
                valoresSeleccionados.get(EnumParametro.TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE),
                EnumTipoPatron.class)
                .ifPresent(errores::add);

        return errores;
    }
}