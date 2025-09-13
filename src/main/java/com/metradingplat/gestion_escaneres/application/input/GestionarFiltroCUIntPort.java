package com.metradingplat.gestion_escaneres.application.input;

import java.util.List;

import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;

public interface GestionarFiltroCUIntPort {
    public List<CategoriaFiltro> obtenerCategorias();
    public List<Filtro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro enumCategoria);
    public Filtro obtenerFiltroPorDefecto(EnumFiltro enumFiltro);
    public List<Filtro> obtenerFiltros(Long idEscaner);
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtrosEscaner);
    public Boolean eliminarFitroGuardado(Long idFiltro);
}