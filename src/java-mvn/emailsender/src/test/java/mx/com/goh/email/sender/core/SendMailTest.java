package mx.com.goh.email.sender.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Session;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import mx.com.goh.email.sender.config.SessionConfig;
import mx.com.goh.email.sender.dto.FileMailDTO;
import mx.com.goh.email.sender.dto.MailMessageDTO;
import mx.com.goh.email.sender.factories.SessionFactory;
import mx.com.goh.gcommons.exception.BusinessException;
import mx.com.goh.gcommons.file.util.FileUtils;

public class SendMailTest {
    private static final Logger LOGGER = Logger.getLogger(SendMailTest.class);
    private MailSender mailSender;
    private MailMessageDTO mailMessageDTO;
    private SessionConfig sessionConfig;
    private Session session;
    private Encoder encoder = Base64.getEncoder();
    String contextPath = System.getProperty("user.dir");

    @Before
    public void initMail() throws IOException {
        // Start Get example folder.
        Path paths = Paths.get(contextPath);
        String examplePath = paths.getParent().getParent().getParent().toString() + "\\example\\";
        // End Get example folder.

        print("Start initMail...");

        // Start session configuration
        print("***** Start session configuration... *****");
        sessionConfig = new SessionConfig();
        sessionConfig.setUserName("username@mail.com");
        sessionConfig.setPassword("secretpassword");
        sessionConfig.setHost("smtp.gmail.com");
        sessionConfig.setPort("587");
        sessionConfig.setEnableStarttls(true);
        sessionConfig.setAuth(true);
        print("***** End session configuration... *****");
        // End session configuration

        // Start email configuration
        print("***** Start Email configuration... *****");
        mailMessageDTO = new MailMessageDTO();
        Set<String> to = new HashSet<>();
        Set<String> cc = new HashSet<>();
        Set<String> bcc = new HashSet<>();
        StringBuilder content = new StringBuilder();

        StringBuilder exampleHtml = FileUtils.readLinesFromFile(examplePath + "example.html");
        print("exampleHtml: " + exampleHtml);
        FileMailDTO htmlFileContent = new FileMailDTO("example.html",
                encoder.encodeToString(exampleHtml.toString().getBytes()));
        List<FileMailDTO> listCidFiles = new ArrayList<>();
        List<FileMailDTO> listAttachments = new ArrayList<>();

        to.add("to@mail.com");
        cc.add("cc@mail.es");
        bcc.add("bcc@mail.com");
        content.append("---Este correo es de prueba---");
        StringBuilder icons8Html100 = FileUtils.readLinesFromFile(examplePath + "icons8-html-100.txt");
        print("icons8Html100: " + icons8Html100);
        listCidFiles.add(new FileMailDTO("icons8-html-100.png", "img01", icons8Html100.toString()));

        StringBuilder icons8Html = FileUtils.readLinesFromFile(examplePath + "attachment01.txt");
        print("icons8Html: " + icons8Html);
        listAttachments
                .add(new FileMailDTO("attachment01.txt", encoder.encodeToString(icons8Html.toString().getBytes())));

        StringBuilder attachment02 = FileUtils.readLinesFromFile(examplePath + "attachment02.txt");
        print("attachment02: " + attachment02);
        listAttachments
                .add(new FileMailDTO("attachment02.txt", encoder.encodeToString(attachment02.toString().getBytes())));

        mailMessageDTO.setFrom("tavooo.oh@gmail.com");
        mailMessageDTO.setTo(to);
        mailMessageDTO.setCc(cc);
        mailMessageDTO.setBcc(bcc);
        mailMessageDTO.setContentCharset(StandardCharsets.UTF_8);
        mailMessageDTO.setContent(content);
        mailMessageDTO.setHtmlFileContentCharset(StandardCharsets.UTF_8);
        mailMessageDTO.setHtmlFileContent(htmlFileContent);
        mailMessageDTO.setListCidFiles(listCidFiles);
        mailMessageDTO.setSubject("Mensage de prueba -golivaresh-");
        mailMessageDTO.setListAttachments(listAttachments);
        print("***** Start Email configuration... *****");
        // End email configuration

        print("End initMail!!\n");
    }

    @Test
    public void sendMailFailed() {
        try {
            print("Start sendMailFailed...");

            print("new Session configuring...");
            session = SessionFactory.getSession(sessionConfig);
            print("Session created!!");

            print("new MailSender...");
            mailSender = new MailSender(session);
            print("Connecting...");
            mailSender.connect();

            print("Sending...");
            mailSender.sendMail(mailMessageDTO);

            print("closing...");
            mailSender.close();
            print("Start sendMailFailed...");
        } catch (BusinessException e) {
            print(e);
            print(e.getExceptionResponse().getCode());
            print(e.getExceptionResponse().getMessage());
        }
    }

    private void print(Object x) {
        LOGGER.info(x);
    }
}
