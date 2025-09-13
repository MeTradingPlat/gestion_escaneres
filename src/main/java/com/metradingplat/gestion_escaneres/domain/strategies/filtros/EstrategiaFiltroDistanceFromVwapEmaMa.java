package com.metradingplat.gestion_escaneres.domain.strategies.filtros;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumLineaReferencia;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTipoValorMedida;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;
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

public class EstrategiaFiltroDistanceFromVwapEmaMa implements IEstrategiaFiltro {
    private final EnumFiltro enumFiltro = EnumFiltro.DISTANCE_FROM_VWAP;
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
        parametros.add(this.crearParametroLineaReferencia((ValorString)valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA)));
        parametros.add(this.crearParametroModoDistancia((ValorString)valoresSeleccionados.get(EnumParametro.MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA)));
        parametros.add(this.crearParametroPeriodoLinea((ValorInteger)valoresSeleccionados.get(EnumParametro.PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA)));

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
        List<Valor> opciones = this.obtenerOpciones(EnumCondicional.values(), enumTipoValor);
        ValorCondicional valor = new ValorCondicional(
            EnumCondicional.MAYOR_QUE.getEtiqueta(),
            enumTipoValor,
            EnumCondicional.MAYOR_QUE,
            valorUsuario != null ? valorUsuario.getValor1() : -10.0F,
            valorUsuario != null ? valorUsuario.getValor2() : 10.0F
        );
        return new Parametro(EnumParametro.CONDICION,EnumParametro.CONDICION.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroLineaReferencia(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumLineaReferencia.values(), enumTipoValor);
        ValorString valor = new ValorString(
            EnumLineaReferencia.VWAP.getEtiqueta(),
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : EnumLineaReferencia.VWAP.name()
        );
        return new Parametro(EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA, EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroModoDistancia(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumTipoValorMedida.values(), enumTipoValor);
        ValorString valor = new ValorString(
            EnumTipoValorMedida.PRECIO.getEtiqueta(),
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : EnumTipoValorMedida.PRECIO.name()
        );
        return new Parametro(EnumParametro.MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA, EnumParametro.MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroPeriodoLinea(ValorInteger valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0], enumTipoValor); 
        ValorInteger valor = new ValorInteger(
            "etiqueta.vacia",
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : 0
        );
        return new Parametro(EnumParametro.PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA, EnumParametro.PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarCondicional(EnumParametro.CONDICION, valoresSeleccionados.get(EnumParametro.CONDICION), -100.0F, 100.0F)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA,
                valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA),
                EnumLineaReferencia.class)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA,
                valoresSeleccionados.get(EnumParametro.MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA),
                EnumTipoValorMedida.class)
                .ifPresent(errores::add);
        
        Valor lineaReferenciaValor = valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA);
        if (lineaReferenciaValor instanceof ValorString &&
            (((ValorString) lineaReferenciaValor).getValor().equalsIgnoreCase(EnumLineaReferencia.EMA.name()) ||
             ((ValorString) lineaReferenciaValor).getValor().equalsIgnoreCase(EnumLineaReferencia.MA.name()))) {
            this.objValidador.validarInteger(EnumParametro.PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA,
                    valoresSeleccionados.get(EnumParametro.PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA), 2, 200)
                    .ifPresent(errores::add);
        }

        return errores;
    }
}