package com.magacin.controllers;

import com.magacin.domain.PrometniDokument;
import com.magacin.domain.StavkaPopisa;
import com.magacin.service.PrometniDokumentInterface;
import com.magacin.service.dto.PrometniDokumentDTO;
import com.magacin.service.dto.StavkaPopisaDTO;
import com.magacin.service.dto.support.PrometniDokumentDTOToPrometniDokument;
import com.magacin.service.dto.support.PrometniDokumentToPrometniDokumentDTO;
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
@RequestMapping(value = "api/prometniDokument")
@CrossOrigin("*")
public class PrometniDokumentController {

    @Autowired
    private PrometniDokumentInterface dokInterface;

    @Autowired
    private PrometniDokumentDTOToPrometniDokument toDokument;

    @Autowired
    private PrometniDokumentToPrometniDokumentDTO toDokumentDTO;

    @GetMapping
    public ResponseEntity<List<PrometniDokumentDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<PrometniDokument> stavka = dokInterface.findAll();

        return new ResponseEntity<>(toDokumentDTO.convert(stavka), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody PrometniDokumentDTO dokDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        PrometniDokument entity = dokInterface.save(toDokument.convert(dokDTO));

        return new ResponseEntity<>(toDokumentDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PrometniDokumentDTO> update(@RequestBody PrometniDokumentDTO dokDTO, @PathVariable("id") Long id) {
        if (dokInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PrometniDokument entity = dokInterface.findOne(id);
        entity = dokInterface.save(toDokument.convert(dokDTO));
        return new ResponseEntity<>(toDokumentDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (dokInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        dokInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
