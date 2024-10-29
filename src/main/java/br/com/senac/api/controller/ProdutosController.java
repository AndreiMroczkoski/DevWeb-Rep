package br.com.senac.api.controller;


import br.com.senac.api.useCases.produtos.ProdutosService;
import br.com.senac.api.utils.paginacao.PaginacaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping("/carregar")
    public ResponseEntity<?> carregarProdutos(PaginacaoRequest paginacao){

        try{
            if (paginacao != null){
                return ResponseEntity.ok(produtosService.carregarProdutos(paginacao));
            }


            return ResponseEntity.ok(produtosService.carregarProdutos());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

    }

}
