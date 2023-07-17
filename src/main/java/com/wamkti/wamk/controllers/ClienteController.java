package com.wamkti.wamk.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<Page<Cliente>> paginar(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction){
		Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
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
		Cliente cliente = clienteService.findById(id);
//		if(clienteO.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
//		}
		cliente.add(linkTo(methodOn(ClienteController.class).listarClientes()).withSelfRel());
		return ResponseEntity.ok(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarCLiente(@PathVariable Long id, 
			@RequestBody @Valid ClienteMinDTO clienteMinDTO){
		Cliente cliente = clienteService.findById(id);
		
		BeanUtils.copyProperties(clienteMinDTO, cliente);
		return ResponseEntity.status(HttpStatus.OK).body(clienteService.save(cliente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarCliente(@PathVariable Long id){
		Cliente cliente = clienteService.findById(id);
		
		clienteService.delete(cliente);
		return ResponseEntity.status(HttpStatus.OK).body("Cliente Deletado!");
	}
	
	@PutMapping("/{clienteTransfereId}/transferencia")
	public ResponseEntity<Object> transferirValor(
			@PathVariable Long clienteTransfereId,
			@Valid @RequestBody Transferencia transferencia){
		Long cT = clienteTransfereId;
		Long cR = transferencia.getClienteRecebeId();
		if(cT == cR) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um cliente não pode transferir dinheiro para ele mesmo!");
		
		int comaracao = clienteService.validarTransferencia(clienteTransfereId, transferencia);
		if(comaracao < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Você não pode transferir valores maiores do que o seu saldo!");
		
		Cliente cliente = clienteService.Transferir(clienteTransfereId, transferencia, transferencia.getClienteRecebeId());
		return ResponseEntity.ok(clienteService.atualizar(cliente));
	}
}

/*
 if(cT == cR) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um cliente não pode transferir dinheiro para ele mesmo!");
		}
		int comaracao = clienteService.validarTransferencia(clienteTransfereId, transferencia);
		if(comaracao < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Você não pode transferir valores maiores do que o seu saldo!");
		}
		Cliente cliente = clienteService.Transferir(clienteTransfereId, transferencia);
		return ResponseEntity.ok(clienteService.atualizar(cliente));
 */
