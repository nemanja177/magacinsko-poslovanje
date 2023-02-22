package com.magacin.controllers;

import com.magacin.domain.Radnik;
import com.magacin.domain.StavkaPrometnogDokumenta;
import com.magacin.service.RadnikInterface;
import com.magacin.service.dto.RadnikDTO;
import com.magacin.service.dto.StavkaPrometnogDokumentaDTO;
import com.magacin.service.dto.support.RadnikDTOToRadnik;
import com.magacin.service.dto.support.RadnikToRadnikDTO;
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
@RequestMapping(value = "api/radnik")
@CrossOrigin("*")
public class RadnikController {

    @Autowired
    private RadnikInterface radnikInterface;

    @Autowired
    private RadnikDTOToRadnik toRadnik;

    @Autowired
    private RadnikToRadnikDTO toRadnikDTO;

    @GetMapping
    public ResponseEntity<List<RadnikDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<Radnik> radnik = radnikInterface.findAll();

        return new ResponseEntity<>(toRadnikDTO.convert(radnik), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody RadnikDTO stavkaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        Radnik entity = radnikInterface.save(toRadnik.convert(stavkaDTO));

        return new ResponseEntity<>(toRadnikDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RadnikDTO> update(@RequestBody RadnikDTO stavkaPrometnogDTO, @PathVariable("id") Long id) {
        if (radnikInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Radnik entity = radnikInterface.findOne(id);
        entity = radnikInterface.save(toRadnik.convert(stavkaPrometnogDTO));
        return new ResponseEntity<>(toRadnikDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (radnikInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        radnikInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
