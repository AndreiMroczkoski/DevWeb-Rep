package br.com.senac.api.useCases.usuarios.domains;

import java.util.List;

public class UsuariosRequestDom {
    private String login;
    private String senha;
    private List<String> permissoes;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
}
