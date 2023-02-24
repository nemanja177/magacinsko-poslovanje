package com.magacin.service;

import java.util.List;

import com.magacin.domain.PoslovanGodina;

public interface PoslovnaGodinaInterface {

	List<PoslovanGodina> findAll();
	
	PoslovanGodina findPoslovanGodinaById(Long id);
	
	PoslovanGodina save(PoslovanGodina poslovnaGodina);
	
	void delete(Long Id);
	
}
