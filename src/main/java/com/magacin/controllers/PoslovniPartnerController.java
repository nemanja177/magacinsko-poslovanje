package com.magacin.controllers;

import com.magacin.domain.PoslovniPartner;
import com.magacin.service.PoslovniPartnerDTOToPoslovniPartner;
import com.magacin.service.PoslovniPartnerInterface;
import com.magacin.service.PoslovniPartnerToPoslovniPartnerDTOConverter;
import com.magacin.service.PreduzeceInterface;
import com.magacin.service.dto.PoslovniPartnerDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/businessPartners")
@CrossOrigin("*")
public class PoslovniPartnerController {

    @Autowired
    private PoslovniPartnerInterface businessPartnerService;

    @Autowired
    private PreduzeceInterface placeService;

    @Autowired
    PoslovniPartnerToPoslovniPartnerDTOConverter toBusinessPartnerDTO;

    @Autowired
    PoslovniPartnerDTOToPoslovniPartner toBusinessPartner;

    @GetMapping
    public ResponseEntity<List<PoslovniPartnerDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
        Page<PoslovniPartner> partners = businessPartnerService.findAll(pageNum);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPages", Integer.toString(partners.getTotalPages()));
        headers.add("access-control-expose-headers", "totalPages");

        return new ResponseEntity<>(toBusinessPartnerDTO.convert(partners.getContent()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PoslovniPartnerDTO> getById(@PathVariable("id") Integer id) {
        if (id == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new PoslovniPartnerDTO(businessPartnerService.findOne(Long.valueOf(id))), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Validated @RequestBody PoslovniPartnerDTO businessPartnerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
        }
        PoslovniPartner entity = businessPartnerService.save(toBusinessPartner.convert(businessPartnerDTO));

        return new ResponseEntity<>(toBusinessPartnerDTO.convert(entity), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PoslovniPartnerDTO> update(@RequestBody PoslovniPartnerDTO dto, @PathVariable("id") Integer id) {
        if (businessPartnerService.findOne(Long.valueOf(id)) == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        PoslovniPartner entity = businessPartnerService.findOne(Long.valueOf(id));
        entity = businessPartnerService.save(toBusinessPartner.convert(dto));
        return new ResponseEntity<>(toBusinessPartnerDTO.convert(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (businessPartnerService.findOne(Long.valueOf(id)) == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        businessPartnerService.delete(Long.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
