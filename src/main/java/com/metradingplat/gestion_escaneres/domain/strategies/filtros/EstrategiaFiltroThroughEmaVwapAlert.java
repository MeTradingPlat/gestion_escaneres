package com.metradingplat.gestion_escaneres.domain.strategies.filtros;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumDireccionRompimiento;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumLineaCruce;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;
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

public class EstrategiaFiltroThroughEmaVwapAlert implements IEstrategiaFiltro {
    private final EnumFiltro enumFiltro = EnumFiltro.THROUGH_EMA_VWAP_ALERT;
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
        parametros.add(this.crearParametroLineaCruce((ValorString)valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE)));
        parametros.add(this.crearParametroPeriodoEma((ValorInteger)valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA)));
        parametros.add(this.crearParametroDireccionRompimiento((ValorString)valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO)));

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

    private Parametro crearParametroLineaCruce(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumLineaCruce.values(),enumTipoValor);
        ValorString valor = new ValorString(
            EnumLineaCruce.VWAP.getEtiqueta(),
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : EnumLineaCruce.VWAP.name()
        );
        return new Parametro(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE, EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroPeriodoEma(ValorInteger valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0], enumTipoValor);
        ValorInteger valor = new ValorInteger(
            "etiqueta.vacia",
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : 0
        );
        return new Parametro(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA, EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroDireccionRompimiento(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumDireccionRompimiento.values(),enumTipoValor);
        ValorString valor = new ValorString(
            EnumDireccionRompimiento.ABOVE.getEtiqueta(),
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : EnumDireccionRompimiento.ABOVE.name()
        );
        return new Parametro(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO, EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        Valor lineaCruceValor = valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE);
        this.objValidador.validarString(EnumParametro.THROUGH_EMA_VWAP_LINEA_CRUCE, lineaCruceValor,
                EnumLineaCruce.class)
                .ifPresent(errores::add);
        
        // Validacion condicional para PERIODO_EMA_THROUGH_EMA_VWAP
        if (lineaCruceValor instanceof ValorString && ((ValorString) lineaCruceValor).getValor().equalsIgnoreCase(EnumLineaCruce.EMA.name())) {
            this.objValidador.validarInteger(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA,
                    valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_PERIODO_EMA), 2, 100)
                    .ifPresent(errores::add);
        }

        this.objValidador.validarString(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO,
                valoresSeleccionados.get(EnumParametro.THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO),
                EnumDireccionRompimiento.class)
                .ifPresent(errores::add);

        return errores;
    }
}