package com.magacin.controllers;

import com.magacin.domain.StavkaPopisa;
import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.StavkaPopisaInterface;
import com.magacin.service.dto.StavkaPopisaDTO;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import com.magacin.service.dto.support.StavkaPopisaDTOToStavkaPopisa;
import com.magacin.service.dto.support.StavkaPopisaToStavkaPopisaDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "api/stavkaPopisa")
@CrossOrigin("*")
public class StavkaPopisaController {

    @Autowired
    private StavkaPopisaDTOToStavkaPopisa toStavkaPopisa;

    @Autowired
    private StavkaPopisaToStavkaPopisaDTO toStavkaPopisaDTO;

    @Autowired
    private StavkaPopisaInterface stavkaInterface;

    @GetMapping
    public ResponseEntity<List<StavkaPopisaDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<StavkaPopisa> stavka = stavkaInterface.findAll();

        return new ResponseEntity<>(toStavkaPopisaDTO.convert(stavka), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody StavkaPopisaDTO stavkaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        StavkaPopisa entity = stavkaInterface.save(toStavkaPopisa.convert(stavkaDTO));

        return new ResponseEntity<>(toStavkaPopisaDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StavkaPopisaDTO> update(@RequestBody StavkaPopisaDTO stavkaPopisaDTO, @PathVariable("id") Long id) {
        if (stavkaInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        StavkaPopisa entity = stavkaInterface.findOne(id);
        entity = stavkaInterface.save(toStavkaPopisa.convert(stavkaPopisaDTO));
        return new ResponseEntity<>(toStavkaPopisaDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (stavkaInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        stavkaInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
