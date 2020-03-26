package mx.com.goh.email.sender.config;

import java.io.Serializable;

/**
 * Class for the session email configuration.<br>
 * Version control: <br>
 * - 1.0.0 | 03/04/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public class SessionConfig implements Serializable {

    /* Serial Version UID. */
    private static final long serialVersionUID = 1083740951745130252L;

    /* Username for the {@link MailSender} session Configuration. */
    private String userName;
    /* Password for the {@link MailSender} session Configuration. */
    private String password;
    /* Host for the {@link MailSender} connection. */
    private String host;
    /* Port fot the {@link MailSender} connection. */
    private String port;

    /**
     * If the true value, make the authentication with connection data, else make
     * authentication without credentials.
     */
    private boolean auth = false;
    /**
     * Return the enableStarttls.<br>
     * If set true value, use SMTP through TLS to connect and must use the TLS port
     * (587).<br>
     * In case of being false use SMTP through SSL to connect and must use the SSL
     * port (465).<br>
     * The default value is "false".
     */
    private boolean enableStarttls = false;

    /**
     * Return the userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the userName.
     * 
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Return the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     * 
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the host.
     * 
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Set the host.
     * 
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Return the port.
     * 
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Set the port.
     * 
     * @param port
     *            the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Return the auth.
     * 
     * @return the auth
     */
    public boolean isAuth() {
        return auth;
    }

    /**
     * Set the auth.<br>
     * If the true value, make the authentication with connection data, else make
     * authentication without credentials.
     * 
     * @param auth
     *            the auth to set
     */
    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    /**
     * Return the enableStarttls.<br>
     * If set true value, use SMTP through TLS to connect and must use the TLS port
     * (587).<br>
     * In case of being false use SMTP through SSL to connect and must use the SSL
     * port (465).<br>
     * The default value is "false".
     * 
     * @return the enableStarttls
     */
    public boolean isEnableStarttls() {
        return enableStarttls;
    }

    /**
     * Set the enableStarttls.
     * 
     * @param enableStarttls
     *            the enableStarttls to set
     */
    public void setEnableStarttls(boolean enableStarttls) {
        this.enableStarttls = enableStarttls;
    }
}
