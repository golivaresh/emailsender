package mx.com.goh.email.sender.factories;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import mx.com.goh.email.sender.dto.FileMailDTO;
import mx.com.goh.email.sender.dto.MailMessageDTO;
import mx.com.goh.email.sender.response.SendMailError;
import mx.com.goh.gcommons.exception.BusinessException;
import mx.com.goh.gcommons.file.util.FileUtils;

/**
 * Class factory of objects {@link Message}.<br>
 * Version control:<br>
 * - 1.0.0 | 25/04/2019 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MessageFactory {
    private static final Logger LOGGER = Logger.getLogger(MessageFactory.class);

    /**
     * Private constructor for {@link MessageFactory} class.
     */
    private MessageFactory() {
        super();
    }

    /**
     * Return the {@link Message} for send.
     * 
     * @param mailMessageDTO
     *            Message Data for send.
     * @param session
     *            {@link Session} object for the {@link Message}.
     * @return {@link Message} prepared for the send.
     * @throws BusinessException
     *             Business error.
     */
    public static final Message getMessage(final MailMessageDTO mailMessageDTO, final Session session)
            throws BusinessException {
        Message message = null;
        checkparams(mailMessageDTO, session);
        message = new MimeMessage(session);
        prepareMessageMail(message, mailMessageDTO);
        return message;
    }

    /**
     * Prepare the message by adding: the source email, the recipients and the
     * content of the message.
     * 
     * @param message
     *            Message to be configured.
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business error.
     */
    private static void prepareMessageMail(Message message, final MailMessageDTO mailMessageDTO)
            throws BusinessException {
        LOGGER.debug("Preparing message to send...");
        // Add From.
        setFrom(message, mailMessageDTO);
        // Add Recipients.
        setRecipients(message, mailMessageDTO);
        // Add Content of message.
        setContent(message, mailMessageDTO);

        LOGGER.debug("Saving changes...");
        try {
            message.saveChanges();
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_SAVE_MESSAGE);
        }
        LOGGER.debug("Changes have been saved!!");
    }

    /**
     * It establishes the sender of the mail, if there is no sender mail it
     * generates an exception.
     * 
     * @param message
     *            Message to be configured.
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business error.
     */
    private static void setFrom(final Message message, final MailMessageDTO mailMessageDTO) throws BusinessException {
        LOGGER.debug("Adding mail from the sender...");
        if (!StringUtils.isBlank(mailMessageDTO.getFrom())) {
            LOGGER.debug(String.format("\t+ Sender: [%s]", mailMessageDTO.getFrom()));
            try {
                message.setFrom(new InternetAddress(mailMessageDTO.getFrom()));
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.UNSPECIFIED_FROM);
            }
        } else {
            throw new BusinessException(SendMailError.FAILED_ADD_FROM);
        }
        LOGGER.debug("Sender's email added!!");
    }

    /**
     * It establishes the recipients of the mail, if no recipient generates an
     * exception.
     * 
     * @param message
     *            Message to be configured.
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business error.
     */
    private static void setRecipients(final Message message, final MailMessageDTO mailMessageDTO)
            throws BusinessException {
        LOGGER.debug("Adding recipients 'to'...");
        HashSet<String> to = (HashSet<String>) mailMessageDTO.getTo();
        if (to != null && !to.isEmpty()) {
            try {
                for (String val : to) {
                    LOGGER.debug(String.format("\t+ to: [%s]", val));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(val.trim()));
                }
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.FAILED_ADD_TO);
            }
        } else {
            throw new BusinessException(SendMailError.UNSPECIFIED_TO);
        }
        LOGGER.debug("The recipients 'to' have been added!!");

        LOGGER.debug("Adding recipients 'cc'...");
        HashSet<String> cc = (HashSet<String>) mailMessageDTO.getCc();
        if (cc != null && !cc.isEmpty()) {
            try {
                for (String val : cc) {
                    LOGGER.debug(String.format("\t+ cc: [%s]", val));
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(val.trim()));
                }
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.FAILED_ADD_CC);
            }
        }
        LOGGER.debug("The recipients 'cc' have been added!!");

        LOGGER.debug("Adding recipients 'bcc'...");
        HashSet<String> bcc = (HashSet<String>) mailMessageDTO.getBcc();
        if (bcc != null && !bcc.isEmpty()) {
            try {
                for (String val : bcc) {
                    LOGGER.debug(String.format("\t+ bcc: [%s]", val));
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(val.trim()));
                }
            } catch (MessagingException e) {
                LOGGER.error(e);
                throw new BusinessException(SendMailError.FAILED_ADD_BCC);
            }
        }
        LOGGER.debug("The recipients 'bcc' have been added!!");
    }

    /**
     * Set the content of the message.
     * 
     * @param message
     *            Message to be configured.
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business Error.
     */
    private static void setContent(final Message message, final MailMessageDTO mailMessageDTO)
            throws BusinessException {
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        StringBuilder textContent = new StringBuilder();

        LOGGER.debug("Adding subject...");
        try {
            message.setSubject(mailMessageDTO.getSubject());
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_ADD_SUBJECT);
        }
        LOGGER.debug(String.format("\t+ Subject: [%s]", mailMessageDTO.getSubject()));
        LOGGER.debug("Subject has been added!!");

        LOGGER.debug("Adding the body of the message...");
        try {
            textContent.append(mailMessageDTO.getContent());
            LOGGER.debug(String.format("\t+ Message body: [%s] [%s]", mailMessageDTO.getContent(),
                    mailMessageDTO.getContentCharset().name()));
            textContent.append(getHTMLFile(mailMessageDTO));
            messageBodyPart.setContent(textContent.toString(), getContentCharset(mailMessageDTO));
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_SET_BODYCONTENT);
        }
        LOGGER.debug("The body of the message has been added!!");

        setAttachmentsFiles(multipart, mailMessageDTO);

        setCidFiles(multipart, mailMessageDTO);

        LOGGER.debug("Adding content...");
        try {
            message.setContent(multipart);
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_SET_MCONTENT);
        }
        LOGGER.debug("The content has been added!!");
    }

    /**
     * Add attachments to the email.
     * 
     * @param multipart
     *            Object to which attachments are established
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business error.
     */
    private static void setAttachmentsFiles(final Multipart multipart, final MailMessageDTO mailMessageDTO)
            throws BusinessException {
        File file = null;
        int countFiles = 0;
        BodyPart messageBodyPart;
        LOGGER.debug("Adding attachments...");
        List<FileMailDTO> listFiles = mailMessageDTO.getListAttachments();
        try {
            for (FileMailDTO fileMailDTO : listFiles) {
                if (fileMailDTO != null && !StringUtils.isBlank(fileMailDTO.getFileName())
                        && !StringUtils.isBlank(fileMailDTO.getBase64())) {
                    file = FileUtils.getFileFromBase64(fileMailDTO.getFileName(), fileMailDTO.getBase64());

                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    messageBodyPart.setFileName(fileMailDTO.getFileName());
                    multipart.addBodyPart(messageBodyPart);
                    countFiles++;
                    LOGGER.debug(String.format("\t+ Aggregate: [%s]", fileMailDTO.getFileName()));
                }
            }
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_SET_ATTACHMENTS);
        } catch (IOException e) {
            LOGGER.error("Error al agregar el archivo: " + e);
            throw new BusinessException(SendMailError.FAILED_SET_ATTACHMENTS);
        }
        LOGGER.debug(String.format("\t+ [%s] attachments added!!", countFiles));
    }

    /**
     * Add the CID files to the mail.
     * 
     * @param multipart
     *            Object to which the CID files are established.
     * @param mailMessageDTO
     *            Configuration object.
     * @throws BusinessException
     *             Business error.
     */
    private static void setCidFiles(Multipart multipart, final MailMessageDTO mailMessageDTO) throws BusinessException {
        LOGGER.debug("Adding CID files to the message...");
        File file = null;
        int countFiles = 0;
        BodyPart messageBodyPart;
        try {
            for (FileMailDTO fileMailDTO : mailMessageDTO.getListCidFiles()) {
                if (fileMailDTO != null && !StringUtils.isBlank(fileMailDTO.getFileName())
                        && !StringUtils.isBlank(fileMailDTO.getBase64())
                        && !StringUtils.isBlank(fileMailDTO.getCid())) {
                    file = FileUtils.getFileFromBase64(fileMailDTO.getFileName(), fileMailDTO.getBase64());

                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    messageBodyPart.setHeader("Content-ID", "<" + fileMailDTO.getCid().trim() + ">");
                    multipart.addBodyPart(messageBodyPart);
                    countFiles++;
                    LOGGER.debug(String.format("\t+ CID added: [%s]", fileMailDTO.getFileName()));
                }
            }
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new BusinessException(SendMailError.FAILED_SET_CID);
        } catch (IOException e) {
            LOGGER.error("Error al agregar el CID: " + e);
            throw new BusinessException(SendMailError.FAILED_SET_CID);
        }
        LOGGER.debug(String.format("\t+ [%s] CID files added.", countFiles));
    }

    /**
     * Get the file for the html text.
     * 
     * @param mailMessageDTO
     *            Configuration object.
     * @return {@link StringBuilder} with the html text.
     */
    private static StringBuilder getHTMLFile(final MailMessageDTO mailMessageDTO) {
        StringBuilder content = new StringBuilder();
        FileMailDTO fileHTML = mailMessageDTO.getHtmlFileContent();
        if (fileHTML != null && !StringUtils.isBlank(fileHTML.getFileName())
                && !StringUtils.isBlank(fileHTML.getBase64())) {

            LOGGER.debug(String.format("\t+ html file content name: [%s] - charset: [%s]", fileHTML.getFileName(),
                    mailMessageDTO.getHtmlFileContentCharset().name()));

            try (InputStreamReader input = new InputStreamReader(
                    new ByteArrayInputStream(FileUtils.decodeBase64(fileHTML.getBase64())),
                    mailMessageDTO.getHtmlFileContentCharset()); BufferedReader in = new BufferedReader(input)) {
                String strFile;
                while ((strFile = in.readLine()) != null) {
                    content.append(strFile.trim());
                }
            } catch (FileNotFoundException e) {
                LOGGER.error(String.format("The specified file does not exist: [%s], " + "error: [%s]",
                        fileHTML.getFileName(), e));
            } catch (IOException e) {
                LOGGER.error(String.format("An error occurred while trying to read the file: [%s], error: [%s]",
                        fileHTML.getFileName(), e));
            }
        }
        return content;
    }

    /**
     * Gets the character set that is used for html mail.
     * 
     * @param mailMessageDTO
     *            Object configuration.
     * @return {@link String} Charset for html mail
     */
    private static String getContentCharset(final MailMessageDTO mailMessageDTO) {
        Charset charset = mailMessageDTO.getContentCharset();
        return "text/html; charset=" + charset.name();
    }

    /**
     * Validate objects that have been passed as parameters.
     * 
     * @param mailMessageDTO
     *            Configuration object.
     * @param session
     *            Session object.
     * @throws BusinessException
     *             Business error.
     */
    private static void checkparams(final MailMessageDTO mailMessageDTO, final Session session)
            throws BusinessException {
        if (mailMessageDTO == null) {
            throw new BusinessException(SendMailError.UNSPECIFIED_MAIL_CONFIGURATION);
        }
        if (session == null) {
            throw new BusinessException(SendMailError.UNSPECIFIED_SESSION_CONFIGURATION);
        }
    }
}
