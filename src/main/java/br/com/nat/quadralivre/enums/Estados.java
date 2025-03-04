package br.com.nat.quadralivre.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Estados {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String nome;

    Estados(String nome){
        this.nome = nome;
    }

    @JsonValue
    public String getNome(){
        return this.nome;
    }

    @JsonCreator
    public static Estados fromString(String estado){

        if(estado.isBlank()){
            throw new IllegalArgumentException("Necessário digitar um estado válido.");
        }

        return Arrays.stream(Estados.values())
                .filter(e -> e.name().equalsIgnoreCase(estado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Digite um estado válido. Ex.: SP ou RJ."));
    }
}
