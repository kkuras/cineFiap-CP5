package br.com.fiap.cineFiap.mapper;

import br.com.fiap.cineFiap.dto.SalaRequestDTO;
import br.com.fiap.cineFiap.dto.SalaResponseDTO;
import br.com.fiap.cineFiap.models.Sala;

public class SalaMapper {

    public static SalaResponseDTO toRecordDTO(Sala sala) {
        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getPreco(),
                sala.getDataExclusao()
        );
    }

    public static Sala recordToEntity (SalaRequestDTO dto) {
        Sala sala = new Sala();
        sala.setId(dto.id());
        sala.setNome(dto.nome());
        sala.setPreco(dto.preco());
        sala.setDataExclusao(dto.dataExclusao());
        return sala;
    }
}
