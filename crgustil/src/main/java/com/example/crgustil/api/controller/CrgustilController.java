package com.example.crgustil.api.controller;

import com.example.crgustil.api.dto.CrgustilDto;

import com.example.crgustil.exception.RegraNegocioException;
import com.example.crgustil.model.entity.Crgustil;
import com.example.crgustil.service.CrgustilService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/crgustil")
public class CrgustilController {

    private CrgustilService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Crgustil> crgustils = service.getCrgustil();
        return ResponseEntity.ok(crgustils.stream().map(CrgustilDto::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Crgustil> crgustil = service.getCrgustilById(id);
        if (!crgustil.isPresent()) {
            return new ResponseEntity("Não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(crgustil.map(CrgustilDto::create));
    }

    @PostMapping()
    public ResponseEntity post(CrgustilDto dto) {
        try {
           Crgustil crgustil = new Crgustil();
           crgustil.setId(dto.getId());
           crgustil.setNome(dto.getNome());
           crgustil.setValor(dto.getValor());
           crgustil.setDataVencimento(dto.getDataVencimento());
           crgustil.setTipoReceita(dto.getTipoReceita());
           crgustil.setStatus(dto.getStatus());
           crgustil = service.salvar(crgustil);
            return new ResponseEntity(crgustil, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CrgustilDto dto) {
        if (!service.getCrgustilById(id).isPresent()) {
            return new ResponseEntity("Não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Crgustil crgustil = converter(dto);
            crgustil.setId(id);
            service.salvar(crgustil);
            return ResponseEntity.ok(crgustil);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Crgustil> crgustil = service.getCrgustilById(id);
        if (!crgustil.isPresent()) {
            return new ResponseEntity("Não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(crgustil.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Crgustil converter(CrgustilDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        Crgustil crgustil = modelMapper.map(dto, Crgustil.class);
        if (dto.getId() != null) {
            Optional<Crgustil> crgustilid = service.getCrgustilById(dto.getId());
            if (!crgustilid.isPresent()) {
                crgustil.setId(null);
            } else {
                crgustil.setId(crgustil.getId());
            }
        }
        return crgustil;
    }
}
