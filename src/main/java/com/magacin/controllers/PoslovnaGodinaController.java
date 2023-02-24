//package com.magacin.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value ="api/poslovnaGodina")
//public class PoslovnaGodinaController {
//
//    @Autowired
//    private PoslovanGodinaInterface
//    private BusinessYearServiceInterface businessYearService;
//
//    @Autowired
//    private BusinessYearToBusinessYearDTOConverter toBusinessYearDTO;
//
//    @Autowired
//    private BusinessYearDTOToBusinessYear toBusinessYear;
//
//
//    @GetMapping
//    public ResponseEntity<List<BusinessYearDTO>> getAll(@RequestParam(defaultValue = "0") int pageNum) {
//
//        Page<BusinessYear> years = businessYearService.findAll(pageNum);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("totalPages",Integer.toString(years.getTotalPages()));
//        headers.add("access-control-expose-headers","totalPages");
//
//        return new ResponseEntity<>(toBusinessYearDTO.convert(years.getContent()), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/{id}")
//    public  ResponseEntity<BusinessYearDTO>getById(@PathVariable("id")Integer id){
//        if(id == 0)
//            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return  new ResponseEntity<>(new BusinessYearDTO(businessYearService.findOne(id)),HttpStatus.OK);
//    }
//
//    @PostMapping(consumes = "application/json")
//    public  ResponseEntity<?> create(@Validated @RequestBody BusinessYearDTO businessPartnerDTO, BindingResult result){
//        if(result.hasErrors()){
//            return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
//        }
//        BusinessYear entity = businessYearService.save(toBusinessYear.convert(businessPartnerDTO));
//
//        return new ResponseEntity<>(toBusinessYearDTO.convert(entity),HttpStatus.OK);
//    }
//
//    @PutMapping(value = "/{id}")
//    public  ResponseEntity<BusinessYearDTO> update(@RequestBody BusinessYearDTO dto,@PathVariable("id")Integer id){
//        if(businessYearService.findOne(id) == null)
//            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        BusinessYear entity = businessYearService.findOne(id);
//        entity=businessYearService.save(toBusinessYear.convert(dto));
//        return new ResponseEntity<>(toBusinessYearDTO.convert(entity),HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
//        if(businessYearService.findOne(id) == null)
//            return  new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//        businessYearService.delete(id);
//        return  new ResponseEntity(HttpStatus.OK);
//    }
//}
//
