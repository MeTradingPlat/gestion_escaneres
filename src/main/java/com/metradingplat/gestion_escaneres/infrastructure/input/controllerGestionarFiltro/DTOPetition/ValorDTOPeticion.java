package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "enumTipoValor", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ValorIntegerDTOPeticion.class, name = "INTEGER"),
        @JsonSubTypes.Type(value = ValorFloatDTOPeticion.class, name = "FLOAT"),
        @JsonSubTypes.Type(value = ValorStringDTOPeticion.class, name = "STRING"),
        @JsonSubTypes.Type(value = ValorCondicionalDTOPeticion.class, name = "CONDICIONAL")
})
public class ValorDTOPeticion {
    @NotNull(message = "valor.enumTipoValor.empty")
    private EnumTipoValor enumTipoValor;
}