package com.metradingplat.gestion_escaneres.infrastructure.business.strategies.filtros;

import com.metradingplat.gestion_escaneres.application.dto.ResultadoValidacion;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumLineaReferencia;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumTipoRol;
import com.metradingplat.gestion_escaneres.domain.enums.valores.IEnumValores;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.models.ValorInteger;
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
public class FiltroFactoryEmaVwapSupportResistance implements IFiltroFactory {
    private final EnumFiltro enumFiltro = EnumFiltro.EMA_VWAP_SUPPORT_RESISTANCE;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.PRECIO_Y_MOVIMIENTO;
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
        parametros.add(this.crearParametroLineaReferencia((ValorString)valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT)));
        parametros.add(this.crearParametroPeriodoEma((ValorInteger)valoresSeleccionados.get(EnumParametro.PERIODO_EMA_EMA_VWAP_SUPPORT)));
        parametros.add(this.crearParametroTipoRol((ValorString)valoresSeleccionados.get(EnumParametro.TIPO_ROL_EMA_VWAP_SUPPORT)));

        filtro.setParametros(parametros);
        return filtro;
    }

    private List<Valor> obtenerOpciones(IEnumValores[] enumValores) {
        return Arrays.stream(enumValores)
            .map(e -> new ValorString(e.getEtiqueta(), EnumTipoValor.STRING, e.getName()))
            .collect(Collectors.toList());
    }

    private Parametro crearParametroLineaReferencia(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumLineaReferencia.values());
        EnumLineaReferencia enumValor = valorUsuario != null ? EnumLineaReferencia.valueOf(valorUsuario.getValor()) : EnumLineaReferencia.VWAP;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT, EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroPeriodoEma(ValorInteger valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.INTEGER;
        List<Valor> opciones = this.obtenerOpciones(new IEnumValores[0]);
        ValorInteger valor = new ValorInteger(
            "etiqueta.vacia",
            enumTipoValor,
            valorUsuario != null ? valorUsuario.getValor() : 0
        );
        return new Parametro(EnumParametro.PERIODO_EMA_EMA_VWAP_SUPPORT, EnumParametro.PERIODO_EMA_EMA_VWAP_SUPPORT.getEtiqueta(), valor, opciones);
    }

    private Parametro crearParametroTipoRol(ValorString valorUsuario) {
        EnumTipoValor enumTipoValor = EnumTipoValor.STRING;
        List<Valor> opciones = this.obtenerOpciones(EnumTipoRol.values());
        EnumTipoRol enumValor = valorUsuario != null ? EnumTipoRol.valueOf(valorUsuario.getValor()) : EnumTipoRol.RESISTENCIA;
        ValorString valor = new ValorString(
            enumValor.getEtiqueta(),
            enumTipoValor,
            enumValor.name()
        );
        return new Parametro(EnumParametro.TIPO_ROL_EMA_VWAP_SUPPORT, EnumParametro.TIPO_ROL_EMA_VWAP_SUPPORT.getEtiqueta(), valor, opciones);
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados) {
        List<ResultadoValidacion> errores = new ArrayList<>();

        this.objValidador.validarString(EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT,
                valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT),
                EnumLineaReferencia.class)
                .ifPresent(errores::add);

        this.objValidador.validarString(EnumParametro.TIPO_ROL_EMA_VWAP_SUPPORT,
                valoresSeleccionados.get(EnumParametro.TIPO_ROL_EMA_VWAP_SUPPORT), EnumTipoRol.class)
                .ifPresent(errores::add);

        Valor lineaReferenciaValor = valoresSeleccionados.get(EnumParametro.LINEA_REFERENCIA_EMA_VWAP_SUPPORT);
        if (lineaReferenciaValor instanceof ValorString && ((ValorString) lineaReferenciaValor).getValor().equalsIgnoreCase(EnumLineaReferencia.EMA.name())) {
            this.objValidador.validarInteger(EnumParametro.PERIODO_EMA_EMA_VWAP_SUPPORT,
                    valoresSeleccionados.get(EnumParametro.PERIODO_EMA_EMA_VWAP_SUPPORT), 2, 100)
                    .ifPresent(errores::add);
        }

        return errores;
    }
}