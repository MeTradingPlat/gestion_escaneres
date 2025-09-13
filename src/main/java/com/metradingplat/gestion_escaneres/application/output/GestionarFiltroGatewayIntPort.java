package com.metradingplat.gestion_escaneres.application.output;

import java.util.List;

import com.metradingplat.gestion_escaneres.domain.models.Filtro;

public interface GestionarFiltroGatewayIntPort {
    public Boolean existeFiltroPorId(Long idFiltro);
    public Filtro obtenerFiltroGuardado(Long idFiltro);
    public List<Filtro> obtenerFiltrosGuardados(Long idEscaner);
    public Filtro guardarFiltro(Long idEscaner, Filtro objFiltro);
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros);
    public Boolean eliminarFiltro(Long idFiltro);
}
