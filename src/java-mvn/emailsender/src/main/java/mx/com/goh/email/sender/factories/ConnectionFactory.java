package mx.com.goh.email.sender.factories;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import mx.com.goh.email.sender.response.SendMailError;
import mx.com.goh.gcommons.exception.BusinessException;

/**
 * Class factory of objects {@link Transport}.<br>
 * Version control:<br>
 * - 1.0.0 | 26/04/2019 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

    /**
     * Private constructor for {@link ConnectionFactory} class.
     */
    private ConnectionFactory() {
        super();
    }

    /**
     * Factory for {@link Transport} objects.
     * 
     * @param message
     *            {@link Message} object.
     * @return {@link Transport} for the email connection.
     * @throws BusinessException
     *             Business error.
     */
    public static Transport getTransport(Session session) throws BusinessException {
        if (session == null) {
            throw new BusinessException(SendMailError.UNSPECIFIED_SESSION_CONFIGURATION);
        }
        try {
            return session.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            LOGGER.error(e);
            throw new BusinessException(e);
        }
    }
}
