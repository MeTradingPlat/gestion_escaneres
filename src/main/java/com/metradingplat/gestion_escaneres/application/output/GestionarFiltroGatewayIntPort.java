package com.metradingplat.gestion_escaneres.application.output;

import java.util.List;

import com.metradingplat.gestion_escaneres.domain.models.Filtro;

public interface GestionarFiltroGatewayIntPort {
    public List<Filtro> obtenerFiltrosGuardados(Long idEscaner);
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros);
}
