package co.matisses.bcs.b1ws.ws.login;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Login createLogin() {
        return new Login();
    }

    public Logout createLogout() {
        return new Logout();
    }

    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    public MsgHeader createMsgHeader() {
        return new MsgHeader();
    }

    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

}
