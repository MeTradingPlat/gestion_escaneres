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
import com.metradingplat.gestion_escaneres.domain.strategies.ValidadorParametroFiltro;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class EstrategiaFiltroPercentageChange implements IEstrategiaFiltro {
    private final EnumFiltro enumFiltro = EnumFiltro.PERCENTAGE_CHANGE;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO;

    @Autowired
    private ValidadorParametroFiltro objValidador;

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
        parametros.add(this.crearParametroTimeframe((ValorString)valoresSeleccionados.get(EnumParametro.TIMEFRAME_PERCENTAGE_CHANGE_PERCENT)));

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
        EnumCondicional enumCondicional = valorUsuario != null ? valorUsuario.getEnumCondicional() : EnumCondicional.MAYOR_QUE;
        ValorCondicional valor = new ValorCondicional(
            enumCondicional.getEtiqueta(),
            enumTipoValor,
            enumCondicional,
            valorUsuario != null ? valorUsuario.getValor1() : -30.0F,
            valorUsuario != null ? valorUsuario.getValor2() : 100.0F
        );
        return new Parametro(EnumParametro.CONDICION,EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroTimeframe(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumTimeframe.values(), enumTipoValor);
        EnumTimeframe enumValor = valorUsuario != null ? EnumTimeframe.valueOf(valorUsuario.getValor()) : EnumTimeframe._1D;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.TIMEFRAME_PERCENTAGE_CHANGE_PERCENT, EnumParametro.TIMEFRAME_PERCENTAGE_CHANGE_PERCENT.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarCondicional(EnumParametro.CONDICION, valoresSeleccionados.get(EnumParametro.CONDICION), -95.0F, 2_000.0F)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.TIMEFRAME_PERCENTAGE_CHANGE_PERCENT,
                valoresSeleccionados.get(EnumParametro.TIMEFRAME_PERCENTAGE_CHANGE_PERCENT), EnumTimeframe.class)
                .ifPresent(errores::add);
        
        return errores;
    }
}