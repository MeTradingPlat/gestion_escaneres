package com.metradingplat.gestion_escaneres.infrastructure.configuration;

import com.metradingplat.gestion_escaneres.application.output.FormateadorResultadosIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestionarEscanerGatewayIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestionarEstadoEscanerGatewayIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestionarFiltroGatewayIntPort;
import com.metradingplat.gestion_escaneres.application.output.GestorEstrategiaFiltroIntPort;
import com.metradingplat.gestion_escaneres.domain.usecases.GestionarEscanerCUAdapter;
import com.metradingplat.gestion_escaneres.domain.usecases.GestionarEstadoEscanerCUAdapter;
import com.metradingplat.gestion_escaneres.domain.usecases.GestionarFiltroCUAdapter;
import com.metradingplat.gestion_escaneres.domain.usecases.GestionarMercadoCUAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigurations {

    // Input Port Beans (Use Cases)
    @Bean
    public GestionarEscanerCUAdapter gestionarEscanerCUIntPort(GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort, GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGateWayIntPort, FormateadorResultadosIntPort objFormateadorResultadosIntPort) {
        return new GestionarEscanerCUAdapter(objGestionarEscanerGatewayIntPort, objGestionarEstadoEscanerGateWayIntPort, objFormateadorResultadosIntPort);
    }

    @Bean
    public GestionarEstadoEscanerCUAdapter gestionarEstadoEscanerCUIntPort(GestionarEstadoEscanerGatewayIntPort objGestionarEstadoEscanerGatewayIntPort, GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort, FormateadorResultadosIntPort objFormateadorResultados) {
        return new GestionarEstadoEscanerCUAdapter(objGestionarEstadoEscanerGatewayIntPort, objGestionarEscanerGatewayIntPort, objFormateadorResultados);
    }

    @Bean
    public GestionarFiltroCUAdapter gestionarFiltroCUIntPort(GestionarFiltroGatewayIntPort objGestionarFiltroGatewayIntPort,  GestionarEscanerGatewayIntPort objGestionarEscanerGatewayIntPort, GestorEstrategiaFiltroIntPort objGestorFactoryFiltro, FormateadorResultadosIntPort objFormateadorResultadosIntPort) {
        return new GestionarFiltroCUAdapter(objGestionarFiltroGatewayIntPort, objGestionarEscanerGatewayIntPort, objGestorFactoryFiltro, objFormateadorResultadosIntPort);
    }

    @Bean
    public GestionarMercadoCUAdapter gestionarMercadoCUIntPort() {
        return new GestionarMercadoCUAdapter();
    }
}