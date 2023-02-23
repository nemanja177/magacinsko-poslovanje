package com.magacin.controllers;

import com.magacin.domain.Preduzece;
import com.magacin.domain.PrometMagacinskeKartice;
import com.magacin.service.PrometMagacinskeKarticeInterface;
import com.magacin.service.dto.PreduzeceDTO;
import com.magacin.service.dto.PrometMagacinskeKarticeDTO;
import com.magacin.service.dto.support.PrometMagacinskeKarticeDTOToPrometMagacinskeKartice;
import com.magacin.service.dto.support.PrometMagacinskeKarticeToPrometMagacinskeKarticeDTO;
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
@RequestMapping(value = "api/prometMagacinskeKartice")
@CrossOrigin("*")
public class PrometMagacinskeKarticeController {

    @Autowired
    private PrometMagacinskeKarticeDTOToPrometMagacinskeKartice toPmk;

    @Autowired
    private PrometMagacinskeKarticeToPrometMagacinskeKarticeDTO toPmkDTO;

    @Autowired
    private PrometMagacinskeKarticeInterface pmkInterface;

    @GetMapping
    public ResponseEntity<List<PrometMagacinskeKarticeDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<PrometMagacinskeKartice> pmk = pmkInterface.findAll();

        return new ResponseEntity<>(toPmkDTO.convert(pmk), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PrometMagacinskeKarticeDTO> getById(@PathVariable("id") Long id) {
        if (id == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new PrometMagacinskeKarticeDTO(pmkInterface.findOne(id)), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody PrometMagacinskeKarticeDTO preduzeceDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        PrometMagacinskeKartice entity = pmkInterface.save(toPmk.convert(preduzeceDTO));

        return new ResponseEntity<>(toPmkDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PrometMagacinskeKarticeDTO> update(
        @RequestBody PrometMagacinskeKarticeDTO stavkaPrometnogDTO,
        @PathVariable("id") Long id
    ) {
        if (pmkInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PrometMagacinskeKartice entity = pmkInterface.findOne(id);
        entity = pmkInterface.save(toPmk.convert(stavkaPrometnogDTO));
        return new ResponseEntity<>(toPmkDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (pmkInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        pmkInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
