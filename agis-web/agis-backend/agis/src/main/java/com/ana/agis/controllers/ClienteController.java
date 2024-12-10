package com.ana.agis.controllers;

import com.ana.agis.model.Cliente;
import com.ana.agis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint para cadastrar um cliente
    @PostMapping("/cadastro_cliente")
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Endpoint para editar um cliente
    @PostMapping("/edit_cliente")
    public ResponseEntity<Cliente> editarCliente(@RequestParam String cpf, @RequestBody Cliente clienteAtualizado) {
        return clienteRepository.findByCpf(cpf)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    // Adicione qualquer outro campo que possa ser atualizado aqui
                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para listar todos os clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Endpoint para buscar cliente por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
