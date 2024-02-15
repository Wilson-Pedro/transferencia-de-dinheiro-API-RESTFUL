package com.wamkti.wamk.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wamkti.wamk.exceptionhandler.exceptions.CpfExistenteException;
import com.wamkti.wamk.exceptionhandler.exceptions.MesmoClienteException;
import com.wamkti.wamk.exceptionhandler.exceptions.ObjectNotFoundException;
import com.wamkti.wamk.exceptionhandler.exceptions.SaldoInsuficienteException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Campo> campos = new ArrayList<>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

			campos.add(new Campo(nome, mensagem));
		}

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão inválidos! Por favor preencha-os corretamente");
		problema.setCampos(campos);

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Problema> ObjectNotFoundException() {

		HttpStatus status = HttpStatus.NOT_FOUND;

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Id não encontrado");

		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<Problema> SaldoInsuficienteException(){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Saldo insuficiente!");
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(MesmoClienteException.class)
	public ResponseEntity<Problema> MesmoClienteException(){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Você não pode fazer uma transação para você mesmo.");
		
		return ResponseEntity.status(status).body(problema);
	}
	
	@ExceptionHandler(CpfExistenteException.class)
	public ResponseEntity<Problema> cpfExistenteException(){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Você não pode fazer uma transação para você mesmo.");
		
		return ResponseEntity.status(status).body(problema);
	}
}
