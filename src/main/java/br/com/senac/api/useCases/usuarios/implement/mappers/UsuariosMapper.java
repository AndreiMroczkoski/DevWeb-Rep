package br.com.senac.api.useCases.usuarios.implement.mappers;

import br.com.senac.api.entitys.Usuarios;
import br.com.senac.api.useCases.usuarios.domains.UsuariosRequestDom;
import br.com.senac.api.useCases.usuarios.domains.UsuariosResponseDom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuariosMapper {

    public static Usuarios usuariosRequestDomParaUsuarios(UsuariosRequestDom in){

        Usuarios out = new Usuarios();
        out.setLogin(in.getLogin());

        return out;
    }

    public static UsuariosResponseDom
        usuariosParaUsuariosResponseDom(Usuarios in){

        UsuariosResponseDom out = new UsuariosResponseDom();
        out.setId(in.getId());
        out.setLogin(in.getLogin());
        out.setSenha(in.getSenha());

        if(!in.getPermissoes().isEmpty()){
            out.setPermissoes(in.getPermissoes()
                    .stream()
                    .map(row -> row.getPermissao())
                    .toList());
        }

        return out;

    }
}
