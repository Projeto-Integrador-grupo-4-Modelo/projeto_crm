package com.generation.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.crm.model.Cliente;
import com.generation.crm.repository.ClienteRepository;


	@Service
	public class ClienteService {

	    @Autowired
	    private ClienteRepository clienteRepository;

	    public Cliente verificarConvenio(Long clienteId) {
	        return clienteRepository.findById(clienteId)
	                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


	    }

//	    public Cliente atualizarConvenio(Long clienteId, Boolean convenio) {
//	        Cliente cliente = clienteRepository.findById(clienteId)
//	                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
//
//	        cliente.setConvenio(convenio);
//	        clienteRepository.save(cliente);
//
//	        return cliente;
//	    }
} 

