package br.com.senac.api.config;

import br.com.senac.api.entitys.Usuarios;
import br.com.senac.api.jwt.TokenService;
import br.com.senac.api.useCases.usuarios.implement.repositorys.UsuariosRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UsuariosRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        String token = this.recoverToken(request);
        String login = tokenService.validarToken(token);

        if(login != null){
            Usuarios usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            //var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Buscar permissões do user no BD
            var authorities = usuario.getPermissoes()
                    .stream()
                    .map(permissao -> new SimpleGrantedAuthority(permissao.getPermissao()))
                    .toList();

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}