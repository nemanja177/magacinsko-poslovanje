package com.magacin.controllers;

import com.magacin.domain.PoslovanGodina;
import com.magacin.service.PoslovnaGodinaInterface;
import com.magacin.service.dto.PoslovnaGodinaDTO;
import com.magacin.service.dto.support.PoslovnaGodinaDTOToPoslovnaGodina;
import com.magacin.service.dto.support.PoslovnaGodinaToPoslovnaGodinaDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/poslovnaGodina")
public class PoslovnaGodinaController {

    @Autowired
    private PoslovnaGodinaInterface poslovanGodinaInterface;

    private PoslovnaGodinaInterface businessYearService;

    //
    @Autowired
    private PoslovnaGodinaToPoslovnaGodinaDTO toBusinessYearDTO;

    @Autowired
    private PoslovnaGodinaDTOToPoslovnaGodina toBusinessYear;

    @GetMapping
    public ResponseEntity<List<PoslovnaGodinaDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        Page<PoslovanGodina> years = businessYearService.findAll(pageNum);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", Integer.toString(years.getTotalPages()));
        headers.add("access-control-expose-headers", "totalPages");

        List<PoslovnaGodinaDTO> dtoList = years
            .getContent()
            .stream()
            .map(poslovanGodina -> toBusinessYearDTO.convert(poslovanGodina))
            .collect(Collectors.toList());

        return ResponseEntity.ok().headers(headers).body(dtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PoslovnaGodinaDTO> getById(@PathVariable("id") Integer id) {
        if (id == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new PoslovnaGodinaDTO(businessYearService.findPoslovanGodinaById(Long.valueOf(id))), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody PoslovnaGodinaDTO businessPartnerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        PoslovanGodina entity = businessYearService.save(toBusinessYear.convert(businessPartnerDTO));

        return new ResponseEntity<>(toBusinessYearDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PoslovnaGodinaDTO> update(@RequestBody PoslovnaGodinaDTO dto, @PathVariable("id") Integer id) {
        if (businessYearService.findPoslovanGodinaById(Long.valueOf(id)) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PoslovanGodina entity = businessYearService.findPoslovanGodinaById(Long.valueOf(id));
        entity = businessYearService.save(toBusinessYear.convert(dto));
        return new ResponseEntity<>(toBusinessYearDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (businessYearService.findPoslovanGodinaById(Long.valueOf(id)) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        businessYearService.delete(Long.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
