package com.metradingplat.gestion_escaneres.domain.strategies.filtros;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTimeframe;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorCondicional;
import com.metradingplat.gestion_escaneres.domain.models.ValorString;
import com.metradingplat.gestion_escaneres.domain.strategies.ServicioValidacionFiltro;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class EstrategiaFiltroPivots implements IEstrategiaFiltro {
    private final EnumFiltro enumFiltro = EnumFiltro.PIVOTS;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO;

    @Autowired
    private ServicioValidacionFiltro objValidador;

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

        filtro.setObjCategoriaFiltro(objCategoriaFiltro);

        return filtro;
    }

    @Override
    public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados) {
        Filtro filtro = this.obtenerInformacionFiltro();
        
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(this.crearParametroCondicion((ValorCondicional)valoresSeleccionados.get(EnumParametro.CONDICION)));
        parametros.add(this.crearParametroTimeframe((ValorString)valoresSeleccionados.get(EnumParametro.TIMEFRAME_PIVOTS)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private List<Valor> obtenerOpciones(IEnumValores[] enumValores, EnumTipoValor tipoValor) {
        List<Valor> opciones = Arrays.stream(enumValores)
            .map(e -> new Valor(e.getEtiqueta(), tipoValor))
            .collect(Collectors.toList());

        if (opciones.isEmpty()) {
            opciones.add(new Valor("etiqueta.vacia", tipoValor));
        }

        return opciones;
    }
    
    private Parametro crearParametroCondicion(ValorCondicional valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
        List<Valor> opciones = this.obtenerOpciones(EnumCondicional.values(),enumTipoValor);
        ValorCondicional valor = new ValorCondicional(
            EnumCondicional.MAYOR_QUE.getEtiqueta(),
            enumTipoValor,
            EnumCondicional.MAYOR_QUE,
            valorUsuario != null ? valorUsuario.getValor1() : 0.0F,
            valorUsuario != null ? valorUsuario.getValor2() : 0.0F
        );
        return new Parametro(EnumParametro.CONDICION,EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroTimeframe(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(new EnumTimeframe[]{EnumTimeframe._1D, EnumTimeframe._1W, EnumTimeframe._1MO}, enumTipoValor);
        ValorString valor = new ValorString(
            EnumTimeframe._1D.getEtiqueta(),
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : EnumTimeframe._1D.name()
        );
        return new Parametro(EnumParametro.TIMEFRAME_PIVOTS, EnumParametro.TIMEFRAME_PIVOTS.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarCondicional(EnumParametro.CONDICION, valoresSeleccionados.get(EnumParametro.CONDICION), 0.0F,
                50_000_000_000.0F)
                .ifPresent(errores::add);

        List<EnumTimeframe> allowedTimeframesPivots = Arrays.asList(
            EnumTimeframe._1D, EnumTimeframe._1W, EnumTimeframe._1MO
        );
        this.objValidador.validarStringConOpciones(EnumParametro.TIMEFRAME_PIVOTS,
                valoresSeleccionados.get(EnumParametro.TIMEFRAME_PIVOTS), allowedTimeframesPivots)
                .ifPresent(errores::add);

        return errores;
    }
}