package com.example.crgustil.service;

import com.example.crgustil.api.dto.CrgustilDto;
import com.example.crgustil.exception.RegraNegocioException;
import com.example.crgustil.model.entity.Crgustil;
import com.example.crgustil.model.repository.CrgustilRepository;
import com.example.crgustil.service.CrgustilService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/crgustil")
@RequiredArgsConstructor

public class CrgustilService {

    private CrgustilRepository repository;
    public CrgustilService (CrgustilRepository repository){
        this.repository = repository;
    }
    public List<Crgustil> getCrgustil() {
        return repository.findAll();
    }

    public static Optional<Crgustil> getCrgustilById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Crgustil salvar(Crgustil crgustil) {
        validar(crgustil);
        return repository.save(crgustil);
    }

    @Transactional
    public void excluir(Crgustil crgustil) {
        Objects.requireNonNull(crgustil.getId());
        repository.delete(crgustil);
    }

    public void validar(Crgustil crgustil) {
        if (crgustil.getDataVencimento() == null) {
            throw new RegraNegocioException("Data vencimento inválida");
        }
        if (crgustil.getValor() == null) {
            throw new RegraNegocioException("Valor inválido");
        }
        if (crgustil.getNome() == null) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (crgustil.getStatus() == null) {
            throw new RegraNegocioException("Status inválido");
        }
        if (crgustil.getTipoReceita() == null) {
            throw new RegraNegocioException("Tipo receita inválido");
        }

    }

}
