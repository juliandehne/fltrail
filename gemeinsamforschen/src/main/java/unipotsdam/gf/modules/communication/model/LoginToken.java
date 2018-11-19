package unipotsdam.gf.modules.communication.model;

public class LoginToken {
    private String loginToken;


    public LoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public LoginToken( ) {
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
