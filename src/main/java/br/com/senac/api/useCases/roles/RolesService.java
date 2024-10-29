package br.com.senac.api.useCases.roles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("rolesService")
public class RolesService {

    public boolean validarPermissoes(String permissoes) {

        boolean autorized = false;

        // Pegar usuário e permissão do contexto
        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        var permissoesUsuario = authentication.getAuthorities();

        for (GrantedAuthority permissao : permissoesUsuario){

            if (permissoes.contains(permissao.getAuthority())
            || permissao.getAuthority().equals("ADMIN")){
                autorized = true;
            }

        }

        return autorized;
    }

}
