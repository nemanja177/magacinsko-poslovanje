package com.magacin.controllers;

import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.StavkaPrometnogDokumentaInterface;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import com.magacin.service.dto.support.StavkaPrometnogDokumentaDTOtoStavkaPrometnogDokumenta;
import com.magacin.service.dto.support.StavkaPrometnogDokumentaToStavkaPromentogDokumentaDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/stavkaPrometnogDokumenta")
@CrossOrigin("*")
public class StavkaPrometnogDokumentaController {

    @Autowired
    private StavkaPrometnogDokumentaInterface stavkaInterface;

    @Autowired
    StavkaPrometnogDokumentaDTOtoStavkaPrometnogDokumenta toStavkaPrometnog;

    @Autowired
    StavkaPrometnogDokumentaToStavkaPromentogDokumentaDTO toStavkaPrometnogDTO;

    @GetMapping
    public ResponseEntity<List<StavkaPrometnogDokumentaDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<StavkaPrometnogDokumenta> partners = stavkaInterface.findAll();

        return new ResponseEntity<>(toStavkaPrometnogDTO.convert(partners), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody StavkaPrometnogDokumentaDTO stavkaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        StavkaPrometnogDokumenta entity = stavkaInterface.save(toStavkaPrometnog.convert(stavkaDTO));

        return new ResponseEntity<>(toStavkaPrometnogDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StavkaPrometnogDokumentaDTO> update(
        @RequestBody StavkaPrometnogDokumentaDTO stavkaPrometnogDTO,
        @PathVariable("id") Long id
    ) {
        if (stavkaInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        StavkaPrometnogDokumenta entity = stavkaInterface.findOne(id);
        entity = stavkaInterface.save(toStavkaPrometnog.convert(stavkaPrometnogDTO));
        return new ResponseEntity<>(toStavkaPrometnogDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (stavkaInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        stavkaInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
