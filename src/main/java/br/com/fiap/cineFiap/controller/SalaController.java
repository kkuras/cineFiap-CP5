package br.com.fiap.cineFiap.controller;

import br.com.fiap.cineFiap.dto.SalaRequestDTO;
import br.com.fiap.cineFiap.dto.SalaResponseDTO;
import br.com.fiap.cineFiap.exceptions.FilmeNaoExisteException;
import br.com.fiap.cineFiap.mapper.SalaMapper;
import br.com.fiap.cineFiap.models.Sala;
import br.com.fiap.cineFiap.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private SalaService service = new SalaService();

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody SalaRequestDTO dto){
        try {
            Sala sala = SalaMapper.recordToEntity(dto);
            service.cadastrar(sala);
            return ResponseEntity.status(HttpStatus.CREATED).body("sala cadastro");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu erro no cadastro: " + e.getMessage());
        }

    }



    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarPorId(@PathVariable Long id) {

        try {
            Sala sala = service.buscarPorId(id);

            SalaResponseDTO responseDTO = SalaMapper.toRecordDTO(sala);
            return ResponseEntity.ok(responseDTO);
        } catch (FilmeNaoExisteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




    public List<SalaResponseDTO> salasEmCartaz(){
        return service.listar()
                .stream()
                .map(SalaMapper::toRecordDTO)
                .toList();
    }

    @PutMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@PathVariable Long id, @RequestBody SalaRequestDTO dto) {
        try {
            Sala sala = SalaMapper.recordToEntity(dto);
            sala.setId(id);
            service.alterar(sala);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (FilmeNaoExisteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Sala>> listar() {
        return ResponseEntity.ok(service.listar());
    }



}
