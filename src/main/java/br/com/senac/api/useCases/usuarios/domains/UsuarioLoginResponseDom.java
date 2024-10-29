package br.com.senac.api.useCases.usuarios.domains;

public class UsuarioLoginResponseDom {
    private String login;
    private String token;
    private String senha;

    public UsuarioLoginResponseDom(String login, String token, String senha) {
        this.login = login;
        this.token = token;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
