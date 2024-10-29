package br.com.senac.api.controller;

import br.com.senac.api.entitys.Produtos;
import br.com.senac.api.entitys.Usuarios;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/helloWord")
public class HelloWordController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Gson gson;

    Logger logger = LoggerFactory.getLogger(HelloWordController.class);

    @GetMapping("/teste")
    public ResponseEntity<String> teste (@RequestParam int t1){
        logger.info("T1: {} T2: {}", t1, 2);

        Usuarios usuarioContext = (Usuarios) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        logger.info("Login: {}, Senha: {}", usuarioContext.getLogin(),
                usuarioContext.getSenha());

        return ResponseEntity.ok("Olá Mundo");
    }

    @PostMapping("/criar_mensagem")
    public ResponseEntity<?> criarMensagem(@RequestBody Map<String, String> input) {

        String mensagem = input.get("mensagem");

        jmsTemplate.convertAndSend("teste_fila", mensagem);

        return ResponseEntity.ok("");

    }

    @PostMapping("/criar_mensagem_produto")
    public ResponseEntity<?> geraMensagemProduto(@RequestBody Produtos produto) {

        jmsTemplate.convertAndSend("teste_fila_objeto", gson.toJson(produto));

        return ResponseEntity.ok("");
    }


}
