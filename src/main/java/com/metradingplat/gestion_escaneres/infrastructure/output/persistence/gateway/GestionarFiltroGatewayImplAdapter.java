package com.metradingplat.gestion_escaneres.infrastructure.output.persistence.gateway;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.gestion_escaneres.domain.models.Filtro;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.entitys.EscanerEntity;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.entitys.FiltroEntity;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.mappers.FiltroMapperPersistencia;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.repositories.EscanerRepositoryInt;
import com.metradingplat.gestion_escaneres.infrastructure.output.persistence.repositories.FiltroRepositoryInt;

@Service
@RequiredArgsConstructor
public class GestionarFiltroGatewayImplAdapter implements GestionarFiltroGatewayIntPort {

    private final FiltroRepositoryInt objFiltroRepository;
    private final EscanerRepositoryInt objEscanerRepository;
    private final FiltroMapperPersistencia objMapper;

    @Override
    @Transactional(readOnly = true)
    public Boolean existeFiltroPorId(Long idFiltro) {
        return this.objFiltroRepository.existsById(idFiltro);
    }

    @Override
    @Transactional(readOnly = true)
    public Filtro obtenerFiltroGuardado(Long idFiltro) {
        FiltroEntity entity = this.objFiltroRepository.findById(idFiltro).get();
        Filtro filtro = this.objMapper.mappearEntityAFiltro(entity);
        return filtro;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filtro> obtenerFiltrosGuardados(Long idEscaner) {
        List<FiltroEntity> entities = this.objFiltroRepository.findFiltrosByEscanerId(idEscaner);
        List<Filtro> filtros = this.objMapper.mappearListaEntityAFiltro(entities);
        return filtros;
    }

    @Override
    @Transactional
    public Filtro guardarFiltro(Long idEscaner, Filtro objFiltro) {
        FiltroEntity filtroEntity = this.objMapper.mappearFiltroAEntity(objFiltro);
        EscanerEntity escanerEntity = this.objEscanerRepository.findById(idEscaner).get();
        escanerEntity.getFiltros().add(filtroEntity);
        escanerEntity.asociarTodo();
        EscanerEntity savedEscaner = this.objEscanerRepository.save(escanerEntity);
        FiltroEntity savedFiltro = savedEscaner.getFiltros().get(savedEscaner.getFiltros().size() - 1);
        return this.objMapper.mappearEntityAFiltro(savedFiltro);
    }

    @Override
    @Transactional
    public List<Filtro> guardarFiltros(Long idEscaner, List<Filtro> filtros) {
        EscanerEntity escanerEntity = this.objEscanerRepository.findById(idEscaner).get();
        escanerEntity.getFiltros().clear();
        List<FiltroEntity> filtroEntities = this.objMapper.mappearListaFiltroAEntity(filtros);
        escanerEntity.getFiltros().addAll(filtroEntities);
        escanerEntity.asociarTodo();
        EscanerEntity savedEscaner = this.objEscanerRepository.save(escanerEntity);
        return this.objMapper.mappearListaEntityAFiltro(savedEscaner.getFiltros());
    }

    @Override
    @Transactional
    public Boolean eliminarFiltro(Long idFiltro) {
        this.objFiltroRepository.deleteById(idFiltro);
        Boolean respuesta = !this.objFiltroRepository.existsById(idFiltro);
        return respuesta;
    }
}
