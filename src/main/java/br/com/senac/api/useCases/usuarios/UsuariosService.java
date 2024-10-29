package br.com.senac.api.useCases.usuarios;

import br.com.senac.api.entitys.Roles;
import br.com.senac.api.entitys.Usuarios;
import br.com.senac.api.jwt.TokenService;
import br.com.senac.api.useCases.usuarios.domains.UsuarioLoginRequestDom;
import br.com.senac.api.useCases.usuarios.domains.UsuarioLoginResponseDom;
import br.com.senac.api.useCases.usuarios.domains.UsuariosRequestDom;
import br.com.senac.api.useCases.usuarios.domains.UsuariosResponseDom;
import br.com.senac.api.useCases.usuarios.implement.mappers.UsuariosMapper;
import br.com.senac.api.useCases.usuarios.implement.repositorys.UsuariosRepository;
import br.com.senac.api.useCases.usuarios.implement.repositorys.UsuariosRolesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuariosService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosRolesRespository usuariosRolesRespository;

    public UsuariosResponseDom cadastrarUsuario(UsuariosRequestDom usuario) throws Exception {
        Optional<Usuarios> resultLogin
                    = usuariosRepository.findByLogin(usuario.getLogin());

        if(resultLogin.isPresent()){
            throw new Exception("Usuário já está cadastrado na base de dados!");
        }

        Usuarios usuarioPersist
                = UsuariosMapper.usuariosRequestDomParaUsuarios(usuario);

        usuarioPersist.setSenha(passwordEncoder.encode(usuario.getSenha()));

        if(usuario.getPermissoes() == null || usuario.getPermissoes().isEmpty()){
            usuarioPersist
                    .setPermissoes(Collections.singletonList(new Roles("USER")));
        }
        Usuarios usuariosPersitResult = usuariosRepository.save(usuarioPersist);

        String token = tokenService.gerarToken(usuariosPersitResult);

        UsuariosResponseDom response = UsuariosMapper
                .usuariosParaUsuariosResponseDom(usuariosPersitResult);

        response.setToken(token);

        return response;
    }

    public UsuarioLoginResponseDom loginUsuario(UsuarioLoginRequestDom usuarioLoginRequestDom) throws Exception {
        Optional<Usuarios> usuariosResult =
                usuariosRepository.findByLogin(usuarioLoginRequestDom.getLogin());
        if(!usuariosResult.isPresent()){
            throw new Exception("Usuário não encontrado!");
        }

        Usuarios usuario = usuariosResult.get();

        if(passwordEncoder.matches(
                usuarioLoginRequestDom.getSenha(),
                usuario.getSenha())){
            String token = tokenService.gerarToken(usuario);

            return new UsuarioLoginResponseDom(
                    usuario.getLogin(),
                    token,
                    usuario.getSenha());
        }

        throw new Exception("Senha invalida!");
    }

    public UsuariosResponseDom carregarById(Long id) throws Exception {
        if(id == null || id < 0) {
           throw new Exception("Id do usuario não informado");
        }

        Optional<Usuarios> result = usuariosRepository.findById(id);
        if(result.isPresent()){
          return UsuariosMapper
                  .usuariosParaUsuariosResponseDom(result.get());
        }

        throw new Exception("Usuario não encontrado");
    }

    public List<UsuariosResponseDom> carregarAll() throws Exception {
        List<Usuarios> result = usuariosRepository.findAll();

        if(!result.isEmpty()){
            return result
                    .stream()
                    .map(UsuariosMapper::usuariosParaUsuariosResponseDom)
                    .toList();
        }

        throw new Exception("Sem usuarios para listar");
    }
}
