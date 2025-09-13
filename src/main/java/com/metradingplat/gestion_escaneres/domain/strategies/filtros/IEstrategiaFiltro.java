package com.metradingplat.gestion_escaneres.domain.strategies.filtros;

import java.util.List;
import java.util.Map;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;

public interface IEstrategiaFiltro {
    public EnumFiltro obtenerEnumFiltro();
    public EnumCategoriaFiltro obtenerEnumCategoria();
    public Filtro obtenerFiltro();
    public Filtro obtenerInformacionFiltro();
    public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados);
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valoresSeleccionados);
}