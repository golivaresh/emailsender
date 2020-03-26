package mx.com.goh.email.sender.core;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;

import mx.com.goh.email.sender.dto.MailMessageDTO;
import mx.com.goh.email.sender.factories.ConnectionFactory;
import mx.com.goh.email.sender.factories.MessageFactory;
import mx.com.goh.email.sender.response.SendMailError;
import mx.com.goh.email.sender.response.SendMailSuccess;
import mx.com.goh.gcommons.exception.BusinessException;
import mx.com.goh.gcommons.response.Response;
import mx.com.goh.gcommons.response.factory.ResponseFactory;

/**
 * Core class for the send mail.<br>
 * Version Control:<br>
 * - 1.0.0 | 03/04/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MailSender {
    private static final Logger LOGGER = Logger.getLogger(MailSender.class);
    private Transport transport = null;
    private Session session;

    /**
     * Constructor of {@link MailSender} class.
     * 
     * @param session
     *            for the connect to server of email.
     * @throws BusinessException Business error.
     */
    public MailSender(final Session session) throws BusinessException {
        super();
        if (session == null) {
            throw new BusinessException(SendMailError.UNSPECIFIED_SESSION_CONFIGURATION);
        }
        this.session = session;
    }

    /**
     * Send the email.
     * 
     * @param mailMessageDTO
     *            Configuration for the email to send.
     * @return {@link Response} with the result for the email send.
     * @throws BusinessException Business error.
     */
    public Response<String> sendMail(MailMessageDTO mailMessageDTO) throws BusinessException {
        LOGGER.info("Sending email...");
        Message message = null;
        try {
            message = MessageFactory.getMessage(mailMessageDTO, session);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (BusinessException e) {
            LOGGER.error(e);
            return ResponseFactory.getFactory().getResponse(SendMailError.FAILED_TO_SEND_EMAIL);
        } catch (MessagingException e) {
            LOGGER.error(e);
            return ResponseFactory.getFactory().getResponse(SendMailError.FAILED_TO_SEND_EMAIL);
        } finally {
            close();
        }
        LOGGER.info("Email send correctly!!");
        return ResponseFactory.getFactory().getResponse(SendMailSuccess.MAIL_SENT_CORRECTLY);
    }

    /**
     * Initialize the email connection.
     * 
     * @throws BusinessException
     *             Business error.
     */
    public void connect() throws BusinessException {
        if (this.transport != null && this.transport.isConnected()) {
            LOGGER.info("The session has been inicialized!!");
        } else {
            try {
                this.transport = ConnectionFactory.getTransport(session);
                this.transport.connect();
                LOGGER.info("Session inicialized!!");
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.FAILED_INITIALIZED_TRANSPORT);
            }
        }
    }

    /**
     * Close the email connection.
     * 
     * @throws BusinessException
     *             Business error.
     */
    public void close() throws BusinessException {
        if (this.transport != null && this.transport.isConnected()) {
            try {
                this.transport.close();
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.FAILED_CLOSE_TRANSPORT);
            }
        }
    }
}
