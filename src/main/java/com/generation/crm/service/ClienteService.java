package com.generation.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.crm.model.Cliente;
import com.generation.crm.repository.ClienteRepository;


	@Service
	public class ClienteService {

	    @Autowired
	    private ClienteRepository clienteRepository;

	    public Object verificarConvenio(Long clienteId) {
	        Cliente cliente = clienteRepository.findById(clienteId)
	                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

	        if (cliente.getConvenio() != null && cliente.getConvenio()) {
	            return true;
	        } else {
	            return "O cliente não tem convênio. Oferecemos opções particulares com descontos exclusivos!";
	        }
	    }

	    public String atualizarConvenio(Long clienteId, Boolean convenio) {
	        Cliente cliente = clienteRepository.findById(clienteId)
	                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

	        cliente.setConvenio(convenio);
	        clienteRepository.save(cliente);

	        return "Status do convênio atualizado com sucesso!";
	    }
} 

