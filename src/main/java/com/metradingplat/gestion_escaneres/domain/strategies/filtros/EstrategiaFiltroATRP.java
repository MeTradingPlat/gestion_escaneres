package com.metradingplat.gestion_escaneres.domain.strategies.filtros;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTimeframe;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumModoPromedioMovil;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorFloat;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;
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

public class EstrategiaFiltroATRP implements IEstrategiaFiltro {
    private final EnumFiltro enumFiltro = EnumFiltro.ATRP;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.VOLATILIDAD;

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

        filtro.setObjCategoria(objCategoriaFiltro);

        return filtro;
    }

    @Override
    public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados) {
        Filtro filtro = this.obtenerInformacionFiltro();
        
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(this.crearParametroCondicion((ValorCondicional)valoresSeleccionados.get(EnumParametro.CONDICION)));
        parametros.add(this.crearParametroTimeframe((ValorString)valoresSeleccionados.get(EnumParametro.TIMEFRAME_ATRP)));
        parametros.add(this.crearParametroPeriodoAtr((ValorInteger)valoresSeleccionados.get(EnumParametro.PERIODO_ATR_ATRP)));
        parametros.add(this.crearParametroTipoPromedioMovil((ValorString)valoresSeleccionados.get(EnumParametro.TIPO_PROMEDIO_MOVIL_ATRP)));
        parametros.add(this.crearParametroValorPromedioMovil((ValorFloat)valoresSeleccionados.get(EnumParametro.VALOR_PROMEDIO_MOVIL_ATRP)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
        return Arrays.stream(enumValores)
            .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
            .collect(Collectors.toList());
    }

    private Parametro crearParametroCondicion(ValorCondicional valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.CONDICIONAL;
        List<Valor> opciones = this.obtenerOpciones(EnumCondicional.values());
        EnumCondicional enumCondicional = valorUsuario != null ? valorUsuario.getEnumCondicional() : EnumCondicional.MAYOR_QUE;
        ValorCondicional valor = new ValorCondicional(
            enumCondicional.getEtiqueta(),
            enumTipoValor,
            enumCondicional,
            valorUsuario != null ? valorUsuario.getValor1() : 0.1F,
            valorUsuario != null ? valorUsuario.getValor2() : 50.0F
        );
        return new Parametro(EnumParametro.CONDICION, EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
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
        return new Parametro(EnumParametro.TIMEFRAME_ATRP, EnumParametro.TIMEFRAME_ATRP.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroPeriodoAtr(ValorInteger valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]); // No hay opciones de enum para periodo
        ValorInteger valor = new ValorInteger(
            "etiqueta.vacia",
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : 14
        );
        return new Parametro(EnumParametro.PERIODO_ATR_ATRP, EnumParametro.PERIODO_ATR_ATRP.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroTipoPromedioMovil(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumModoPromedioMovil.values());
        EnumModoPromedioMovil enumValor = valorUsuario != null ? EnumModoPromedioMovil.valueOf(valorUsuario.getValor()) : EnumModoPromedioMovil.EMA;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.TIPO_PROMEDIO_MOVIL_ATRP, EnumParametro.TIPO_PROMEDIO_MOVIL_ATRP.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroValorPromedioMovil(ValorFloat valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.FLOAT;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]); 
        ValorFloat valor = new ValorFloat(
            "etiqueta.vacia",
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : 0F
        );
        return new Parametro(EnumParametro.VALOR_PROMEDIO_MOVIL_ATRP, EnumParametro.VALOR_PROMEDIO_MOVIL_ATRP.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarCondicional(EnumParametro.CONDICION, valoresSeleccionados.get(EnumParametro.CONDICION), 0.1f, 50.0f)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.TIMEFRAME_ATRP, valoresSeleccionados.get(EnumParametro.TIMEFRAME_ATRP),
                EnumTimeframe.class)
                .ifPresent(errores::add);

        this.objValidador.validarInteger(EnumParametro.PERIODO_ATR_ATRP, valoresSeleccionados.get(EnumParametro.PERIODO_ATR_ATRP),
                5, 100)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.TIPO_PROMEDIO_MOVIL_ATRP,
                valoresSeleccionados.get(EnumParametro.TIPO_PROMEDIO_MOVIL_ATRP), EnumModoPromedioMovil.class)
                .ifPresent(errores::add);

        this.objValidador.valdiarFloat(EnumParametro.VALOR_PROMEDIO_MOVIL_ATRP,
                valoresSeleccionados.get(EnumParametro.VALOR_PROMEDIO_MOVIL_ATRP), 0.0f, Float.MAX_VALUE)
                .ifPresent(errores::add);

        return errores;
    }
}