package com.generation.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.crm.model.Paciente;
import com.generation.crm.repository.PacienteRepository;

import java.util.NoSuchElementException;


@Service
	public class PacienteService {

	    @Autowired
	    private PacienteRepository clienteRepository;

	    public Paciente verificarConvenio(Long clienteId) {

	        return clienteRepository.verificarConvenio(clienteId)
	                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));


	    }

} 

