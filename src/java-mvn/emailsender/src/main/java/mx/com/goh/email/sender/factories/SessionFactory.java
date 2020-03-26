package mx.com.goh.email.sender.factories;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.com.goh.email.sender.config.SessionConfig;
import mx.com.goh.email.sender.response.SendMailError;
import mx.com.goh.gcommons.exception.BusinessException;

/**
 * Class factory of the objects {@link SessionFactory}.<br>
 * Version control:<br>
 * - 1.0.0 | 25/04/2019 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SessionFactory {
    private static final Logger LOGGER = Logger.getLogger(SessionFactory.class);

    /**
     * Private constructor of {@link SessionFactory} class.
     */
    private SessionFactory() {
        super();
    }

    /**
     * Get the session object.
     * 
     * @param sessionConfig
     *            Configuration session object.
     * @return {@link Session} of the email connection.
     * @throws BusinessException
     *             Business error.
     */
    public static final Session getSession(final SessionConfig sessionConfig) throws BusinessException {
        LOGGER.info("Configuring email sessión...");
        Properties props;

        if (StringUtils.isEmpty(sessionConfig.getHost()) || StringUtils.isEmpty(sessionConfig.getPort())) {
            throw new BusinessException(SendMailError.UNSPECIFIED_SESSION_CONFIGURATION);
        }
        props = new Properties();

        props.put("mail.smtp.auth", String.valueOf(sessionConfig.isAuth()));
        props.put("mail.smtp.host", sessionConfig.getHost());
        props.put("mail.smtp.port", sessionConfig.getPort());
        if (sessionConfig.isEnableStarttls()) {
            props.put("mail.smtp.starttls.enable", true);
        } else {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        if (sessionConfig.isAuth()) {
            LOGGER.info("Session auth configuring...");
            return Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sessionConfig.getUserName(), sessionConfig.getPassword());
                }
            });
        } else {
            LOGGER.info("Simple session configuring...");
            return Session.getDefaultInstance(props, null);
        }
    }
}
