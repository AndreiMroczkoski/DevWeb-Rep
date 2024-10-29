package br.com.senac.api.controller;

import br.com.senac.api.useCases.usuarios.UsuariosService;
import br.com.senac.api.useCases.usuarios.domains.UsuarioLoginRequestDom;
import br.com.senac.api.useCases.usuarios.domains.UsuarioLoginResponseDom;
import br.com.senac.api.useCases.usuarios.domains.UsuariosRequestDom;
import br.com.senac.api.useCases.usuarios.domains.UsuariosResponseDom;
import br.com.senac.api.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioLoginRequestDom usuario){
        try{
            UsuarioLoginResponseDom usuarioResponse =
                    usuariosService.loginUsuario(usuario);

           return ResponseEntity.ok(usuarioResponse);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parseResponse(e.getMessage()));
        }
    }
    @PostMapping("/cadastro")
    public ResponseEntity<?>
        cadastrarUsuario(@RequestBody UsuariosRequestDom usuario){

        try{
            UsuariosResponseDom response =
                    usuariosService.cadastrarUsuario(usuario);

            return ResponseEntity.ok(response);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parseResponse(e.getMessage()));
        }
    }

    @GetMapping("/carregar/{id}")
    public ResponseEntity<?> carregarById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(usuariosService.carregarById(id));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parseResponse(e.getMessage()));
        }
    }

    @GetMapping("/carregar")
    @PreAuthorize("@rolesService.validarPermissoes('ADMIN')")
    public ResponseEntity<?> carregarTodos(){
        try {
            return ResponseEntity.ok(usuariosService.carregarAll());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parseResponse(e.getMessage()));
        }
    }
}
