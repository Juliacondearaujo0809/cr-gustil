package com.example.crgustil.api.dto;

import com.example.crgustil.model.entity.Crgustil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CrgustilDto {

    private Long id;;
    private String dataVencimento;
    private String valor;
    private String nome;
    private String tipoReceita;
    private String status;

    public static CrgustilDto create(Crgustil crgustil) {
        ModelMapper modelMapper = new ModelMapper();
        CrgustilDto dto = modelMapper.map(crgustil, CrgustilDto.class);
        dto.dataVencimento = String.valueOf(crgustil.getDataVencimento());
        dto.valor = crgustil.getValor();
        dto.nome = crgustil.getNome();
        dto.tipoReceita = crgustil.getTipoReceita();
        dto.status = crgustil.getStatus();

        return dto;
    }

}
