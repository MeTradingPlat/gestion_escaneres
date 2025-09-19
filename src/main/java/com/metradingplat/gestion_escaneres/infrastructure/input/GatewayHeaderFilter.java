package com.metradingplat.gestion_escaneres.infrastructure.input;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GatewayHeaderFilter extends OncePerRequestFilter {

    private final FuenteMensajesIntPort objFuenteMensajes;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("X-Gateway-Passed");

        if (header == null || !header.equals("true")) {
            String mensaje = this.objFuenteMensajes.internacionalizarMensaje("filtro.acceso.gateway.denegado");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, mensaje);
            return;
        }

        filterChain.doFilter(request, response);
    }
}