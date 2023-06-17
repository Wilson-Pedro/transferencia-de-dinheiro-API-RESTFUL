package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.ClienteMinDTO;
import com.wamkti.wamk.entities.dtos.ClienteRecodDTO;
import com.wamkti.wamk.services.ClienteService;
import com.wamkti.wamk.transferencia.Transferencia;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Object> salvarCliente(@Valid @RequestBody ClienteRecodDTO clienteRecodDTO) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(clienteRecodDTO, cliente);
		List<Cliente> clientes = clienteService.findAll();
		for(Cliente c : clientes) {
			if(cliente.getCpf().equals(c.getCpf())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um cliente com este cpf!");
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listarClientes(){
		List<Cliente> clientes = clienteService.findAll();
		if(!clientes.isEmpty()) {
			for(Cliente cliente : clientes) {
				Long id = cliente.getId();
				cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(id)).withSelfRel());
			}
		}
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<Cliente>> paginar(Pageable pageable){
		Page<Cliente> clientes = clienteService.findAllPagable(pageable);
		if(!clientes.isEmpty()) {
			for(Cliente cliente : clientes) {
				Long id = cliente.getId();
				cliente.add(linkTo(methodOn(ClienteController.class).buscarCliente(id)).withSelfRel());
			}
		}
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarCliente(@PathVariable Long id){
		Optional<Cliente> clienteO = clienteService.findById(id);
		if(clienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		clienteO.get().add(linkTo(methodOn(ClienteController.class).listarClientes()).withSelfRel());
		return ResponseEntity.ok(clienteO.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarCLiente(@PathVariable Long id, 
			@RequestBody @Valid ClienteMinDTO clienteMinDTO){
		Optional<Cliente> clienteO = clienteService.findById(id);
		if(clienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		var cliente = clienteO.get();
		BeanUtils.copyProperties(clienteMinDTO, cliente);
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.save(cliente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarCliente(@PathVariable Long id){
		Optional<Cliente> clienteO = clienteService.findById(id);
		if(clienteO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		clienteService.delete(clienteO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Cliente Deletado!");
	}
	
	@PutMapping("/{clienteTransfereId}/transferencia/{clienteRecebeId}")
	public ResponseEntity<Object> transferirValor(
			@PathVariable Long clienteTransfereId,
			@PathVariable Long clienteRecebeId,
			@Valid @RequestBody Transferencia transferencia){
		Long cT = clienteTransfereId;
		Long cR = clienteRecebeId;
		if(cT == cR) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um cliente não pode transferir dinheiro para ele mesmo!");
		}
		Optional<Cliente> clienteTransfereO = clienteService.findById(clienteTransfereId);
		Optional<Cliente> clienteRecebeO = clienteService.findById(clienteRecebeId);
		BigDecimal valor_cliente = clienteTransfereO.get().getValor();
		BigDecimal valor_transferido = transferencia.getValor();
		BigDecimal valor_atual = valor_cliente.subtract(valor_transferido);
		var cliente = new Cliente();
		BeanUtils.copyProperties(clienteTransfereO.get(), cliente);
		cliente.setValor(valor_atual);
		clienteService.Transferir(clienteRecebeO.get(), valor_transferido);
		return ResponseEntity.ok(clienteService.save(cliente));
		
	}
}
