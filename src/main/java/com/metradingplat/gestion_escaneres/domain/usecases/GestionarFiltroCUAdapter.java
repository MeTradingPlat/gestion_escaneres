package com.metradingplat.gestion_escaneres.domain.usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.input.GestionarFiltroCUIntPort;
import com.metradingplat.gestion_escaneres.application.output.FormateadorResultadosIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;
import com.metradingplat.gestion_escaneres.domain.models.CategoriaFiltro;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.domain.models.Parametro;
import com.metradingplat.gestion_escaneres.domain.models.Valor;
import com.metradingplat.gestion_escaneres.domain.strategies.GestorEstrategiaFiltro;
import com.metradingplat.gestion_escaneres.domain.strategies.validacion.ResultadoValidacion;

@RequiredArgsConstructor
public class GestionarFiltroCUAdapter implements GestionarFiltroCUIntPort { 

    private final GestionarFiltroGatewayIntPort objGestionarFiltroGatewayIntPort;
    private final GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort;
    private final GestorEstrategiaFiltro objGestorFactoryFiltro;
    private final FormateadorResultadosIntPort objFormateadorResultadosIntPort;

    @Override
    public List<CategoriaFiltro> obtenerCategorias(){
        return Arrays.stream(EnumCategoriaFiltro.values())
                     .map(enumCat -> new CategoriaFiltro(
                        enumCat.getEtiqueta(),
                        enumCat
                        )).collect(Collectors.toList());
    }

    @Override
    public List<Filtro> obtenerFiltrosPorCategoria(EnumCategoriaFiltro enumCategoria){
        List<EnumFiltro> enumsFiltro = this.objGestorFactoryFiltro.obtenerFiltrosPorCategoria(enumCategoria);
        if (enumsFiltro.size() == 1 && enumsFiltro.get(0) == EnumFiltro.UNKNOWN) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("filtros.enumCategoria.noEncontrado", enumCategoria);
        }
        return enumsFiltro.stream()
            .map(this.objGestorFactoryFiltro::obtenerInfomracionFiltro)
            .collect(Collectors.toList());
    }

    @Override
    public Filtro obtenerFiltroPorDefecto(EnumFiltro enumFiltro){
        if (!this.objGestorFactoryFiltro.validarEnumFiltro(enumFiltro)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("filtros.enumFiltro.noEncontrado", enumFiltro);
        }
        return this.objGestorFactoryFiltro.obtenerFiltroConValoresPorDefecto(enumFiltro);
    }

    @Override
    public List<Filtro> obtenerFiltros(Long idEscaner){
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)){
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("filtros.escaner.noEncontrado", idEscaner);
        }
        return this.objGestionarFiltroGatewayIntPort.obtenerFiltrosGuardados(idEscaner);
    }

    @Override
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros) {
        List<ResultadoValidacion> errores = new ArrayList<ResultadoValidacion>();
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("filtros.escaner.noEncontrado", idEscaner);
        }
        for (Filtro filtro : filtros) {
            System.out.println("Filtro: " + filtro.getEnumFiltro() 
            + " tiene " + (filtro.getParametros() == null ? "null" : filtro.getParametros().size()) + " parámetros");

            if (filtro.getParametros() != null) {
                for (Parametro p : filtro.getParametros()) {
                    System.out.println("Parametro=" + p.getEnumParametro() 
                        + ", valor=" + (p.getObjValorSeleccionado() == null ? "null" : p.getObjValorSeleccionado().getClass().getName()));
                }
            }


            Map<EnumParametro, Valor> valoresSeleccionados = filtro.getParametros().stream()
                    .collect(Collectors.toMap(
                            Parametro::getEnumParametro,
                            Parametro::getObjValorSeleccionado
                    ));
            valoresSeleccionados.forEach((k, v) -> {
                System.out.println("Parametro=" + k + ", clase=" + (v == null ? "null" : v.getClass().getName()));
            });

            errores.addAll(this.objGestorFactoryFiltro.validarValoresSeleccionados(
                    filtro.getEnumFiltro(),
                    valoresSeleccionados
            ));
        }
        if (!errores.isEmpty()) {
                this.objFormateadorResultadosIntPort.errorValidacionFiltro(errores);
            }

        return this.objGestionarFiltroGatewayIntPort.guardarFiltros(idEscaner, filtros);
    }

    public Boolean eliminarFitroGuardado(Long idFiltro){
        if (!this.objGestionarFiltroGatewayIntPort.existeFiltroPorId(idFiltro)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("filtros.filtro.noEncontrado", idFiltro);
        }
        return this.objGestionarFiltroGatewayIntPort.eliminarFiltro(idFiltro);
    }
}