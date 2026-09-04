package br.edu.univassouras.vasscommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    public record RespostaErro(String erro) {
    }

    @ExceptionHandler(CadastroNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarNaoEncontrado(CadastroNaoEncontradoException excecao) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespostaErro(excecao.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaErro> tratarIdInvalido(IllegalArgumentException excecao) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RespostaErro(excecao.getMessage()));
    }
}
