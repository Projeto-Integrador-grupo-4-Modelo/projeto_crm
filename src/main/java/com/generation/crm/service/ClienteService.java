package com.generation.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.crm.model.Cliente;
import com.generation.crm.repository.ClienteRepository;

import java.util.NoSuchElementException;


@Service
	public class ClienteService {

	    @Autowired
	    private ClienteRepository clienteRepository;

	    public Cliente verificarConvenio(Long clienteId) {

	        return clienteRepository.verificarConvenio(clienteId)
	                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));


	    }

} 

