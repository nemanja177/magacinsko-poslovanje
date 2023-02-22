package com.magacin.controllers;

import com.magacin.service.dto.support.PreduzeceDTOToPreduzece;
import com.magacin.service.dto.support.PreduzeceToPreduzeceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/preduzece")
@CrossOrigin("*")
public class PreduzeceController {

    @Autowired
    private PreduzeceDTOToPreduzece toPreduzece;

    @Autowired
    private PreduzeceToPreduzeceDTO toPreduzeceDTO;
}
