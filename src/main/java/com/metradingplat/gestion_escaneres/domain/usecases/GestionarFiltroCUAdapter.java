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
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.filter.category.notFound");
        }
        return enumsFiltro.stream()
            .map(this.objGestorFactoryFiltro::obtenerInfomracionFiltro)
            .collect(Collectors.toList());
    }

    @Override
    public Filtro obtenerFiltroPorDefecto(EnumFiltro enumFiltro){
        if (!this.objGestorFactoryFiltro.validarEnumFiltro(enumFiltro)) {
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.filter.type.notFound");
        }
        return this.objGestorFactoryFiltro.obtenerFiltroConValoresPorDefecto(enumFiltro);
    }

    @Override
    public List<Filtro> obtenerFiltros(Long idEscaner){
        if (!this.objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)){
            this.objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }

        List<Filtro> filtrosGuardados = this.objGestionarFiltroGatewayIntPort.obtenerFiltrosGuardados(idEscaner);
        List<Filtro> filtrosARetornar = new ArrayList<Filtro>();

        for (Filtro filtro : filtrosGuardados) {
            Map<EnumParametro, Valor> valoresSeleccionados = extraerValoresSeleccionados(filtro);

            filtrosARetornar.add(objGestorFactoryFiltro.crearFiltroConValoresSeleccionados(
                filtro.getEnumFiltro(), valoresSeleccionados));
        }

        return filtrosARetornar;
    }

    @Override
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros) {
        if (!objGestionarEscanerGatewayIntPort.existeEscanerPorId(idEscaner)) {
            objFormateadorResultadosIntPort.errorEntidadNoExiste("validation.scanner.id.notFound", idEscaner);
        }

        List<ResultadoValidacion> errores = new ArrayList<ResultadoValidacion>();
        List<Filtro> filtrosCreados = new ArrayList<Filtro>();

        for (Filtro filtro : filtros) {
            Map<EnumParametro, Valor> valoresSeleccionados = extraerValoresSeleccionados(filtro);

            List<ResultadoValidacion> erroresFiltro = objGestorFactoryFiltro.validarValoresSeleccionados(
                filtro.getEnumFiltro(), valoresSeleccionados);

            if (!erroresFiltro.isEmpty()) {
                errores.addAll(erroresFiltro);
                continue;
            }

            filtrosCreados.add(objGestorFactoryFiltro.crearFiltroConValoresSeleccionados(
                filtro.getEnumFiltro(), valoresSeleccionados));
        }


        if (!errores.isEmpty()) {
            objFormateadorResultadosIntPort.errorValidacionFiltro(errores);
        }

        objGestionarFiltroGatewayIntPort.guardarFiltros(idEscaner, filtrosCreados);

        return filtrosCreados;

    }

    private Map<EnumParametro, Valor> extraerValoresSeleccionados(Filtro filtro) {
        return filtro.getParametros().stream()
            .collect(Collectors.toMap(
                Parametro::getEnumParametro,
                Parametro::getObjValorSeleccionado
            ));
    }
}