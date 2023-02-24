package com.magacin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magacin.domain.PoslovanGodina;
import com.magacin.repository.PoslovanGodinaRepository;


@Service
public class PoslovnaGodinaService implements PoslovnaGodinaInterface  {
	
	@Autowired
	private PoslovanGodinaRepository poslovnaGodinaRepository;

	@Override
	public List<PoslovanGodina> findAll() {
		return poslovnaGodinaRepository.findAll();
	}

	@Override
	public PoslovanGodina findPoslovanGodinaById(Long id) {
		return poslovnaGodinaRepository.findPoslovanGodinaById(id);
	}

	@Override
	public PoslovanGodina save(PoslovanGodina poslovnaGodina) {
		return poslovnaGodinaRepository.save(poslovnaGodina);
	}

	@Override
	public void delete(Long Id) {
		poslovnaGodinaRepository.deleteById(Id);
	}

	

}
