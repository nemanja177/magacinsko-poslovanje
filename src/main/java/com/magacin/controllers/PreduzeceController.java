package com.magacin.controllers;

import com.magacin.domain.Preduzece;
import com.magacin.service.PreduzeceInterface;
import com.magacin.service.dto.PreduzeceDTO;
import com.magacin.service.dto.support.PreduzeceDTOToPreduzece;
import com.magacin.service.dto.support.PreduzeceToPreduzeceDTO;
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
@RequestMapping(value = "api/preduzece")
@CrossOrigin("*")
public class PreduzeceController {

    private PreduzeceDTOToPreduzece toPreduzece;

    private PreduzeceToPreduzeceDTO toPreduzeceDTO;

    private PreduzeceInterface predInterface;

    @GetMapping
    public ResponseEntity<List<PreduzeceDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        List<Preduzece> preduzece = predInterface.findAll();

        return new ResponseEntity<>(toPreduzeceDTO.convert(preduzece), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PreduzeceDTO> getById(@PathVariable("id") Long id) {
        if (id == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new PreduzeceDTO(predInterface.findOne(id)), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody PreduzeceDTO preduzeceDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        Preduzece entity = predInterface.save(toPreduzece.convert(preduzeceDTO));

        return new ResponseEntity<>(toPreduzeceDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PreduzeceDTO> update(@RequestBody PreduzeceDTO stavkaPrometnogDTO, @PathVariable("id") Long id) {
        if (predInterface.findOne(id) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Preduzece entity = predInterface.findOne(id);
        entity = predInterface.save(toPreduzece.convert(stavkaPrometnogDTO));
        return new ResponseEntity<>(toPreduzeceDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (predInterface.findOne(id) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        predInterface.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
