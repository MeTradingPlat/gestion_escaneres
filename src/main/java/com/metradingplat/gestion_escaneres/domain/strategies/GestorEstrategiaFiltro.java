package com.metradingplat.gestion_escaneres.domain.strategies;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import lombok.Getter;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.strategies.filtros.IEstrategiaFiltro;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;

@Getter
@Setter
public class GestorEstrategiaFiltro {
    private Map<EnumFiltro, IEstrategiaFiltro> mapEnumFiltroIEstrategiaFiltro;
    private Map<EnumCategoriaFiltro, List<EnumFiltro>> mapCategoriaFiltros;

    public GestorEstrategiaFiltro(Set<IEstrategiaFiltro> filtros) {
        this.mapEnumFiltroIEstrategiaFiltro = new HashMap<>();
        this.mapCategoriaFiltros = new EnumMap<>(EnumCategoriaFiltro.class);

        for (IEstrategiaFiltro filtro : filtros) {
            EnumFiltro enumFiltro = filtro.obtenerEnumFiltro();
            EnumCategoriaFiltro categoria = filtro.obtenerEnumCategoria();

            this.mapEnumFiltroIEstrategiaFiltro.put(enumFiltro, filtro);

            this.mapCategoriaFiltros
                .computeIfAbsent(categoria, k -> new ArrayList<>())
                .add(enumFiltro);

            this.mapCategoriaFiltros
                .computeIfAbsent(EnumCategoriaFiltro.TODOS, k -> new ArrayList<>())
                .add(enumFiltro);
        }
    }

    public List<EnumFiltro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro categoria) {
        return this.mapCategoriaFiltros.getOrDefault(categoria, List.of(EnumFiltro.UNKNOWN));
    }

    public Filtro obtenerInfomracionFiltro(EnumFiltro tipoFiltro){
        return this.mapEnumFiltroIEstrategiaFiltro.get(tipoFiltro).obtenerInformacionFiltro();
    }

    public Filtro obtenerFiltroConValoresPorDefecto(EnumFiltro tipoFiltro){      
        return this.mapEnumFiltroIEstrategiaFiltro.get(tipoFiltro).obtenerFiltro();
    }

    public Filtro crearFiltroConValoresSeleccionados(EnumFiltro tipoFiltro, Map<EnumParametro,Valor> valores){
        return this.mapEnumFiltroIEstrategiaFiltro.get(tipoFiltro).obtenerFiltro(valores);

    }

    public Boolean validarEnumFiltro(EnumFiltro tipoFiltro){
        return this.mapEnumFiltroIEstrategiaFiltro.containsKey(tipoFiltro);
    }

    public List<ResultadoValidacion> validarValoresSeleccionados(EnumFiltro tipoFiltro, Map<EnumParametro,Valor> valores){
        return this.mapEnumFiltroIEstrategiaFiltro.get(tipoFiltro).validarValoresSeleccionados(valores);
    }
}
