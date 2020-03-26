package mx.com.goh.email.sender.dto;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DTO class for the email message definition.<br>
 * Version control: <br>
 * - 1.0.0 | 03/04/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public class MailMessageDTO implements Serializable {
    /** Serial Version UID. */
    private static final long serialVersionUID = 1910731082547185181L;
    /** Sender's email. */
    private String from;
    /** Recipients list. */
    private Set<String> to;
    /** Copy mailing list. */
    private Set<String> cc;
    /** Blind Copy Mailing List. */
    private Set<String> bcc;
    /** Mail subject. */
    private String subject;
    /** Email Content. */
    private StringBuilder content;
    /** Attachment list. */
    private List<FileMailDTO> listAttachments;
    /** File html content. */
    private FileMailDTO htmlFileContent;
    /** Cid file list. */
    private List<FileMailDTO> listCidFiles;
    /** Charset of the content, default is ISO_8859_1. */
    private transient Charset contentCharset;
    /** Charset of the html file content, default is ISO_8859_1. */
    private transient Charset htmlFileContentCharset;

    /**
     * @return the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to.
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set.
     */
    public void setTo(Set<String> to) {
        this.to = to;
    }

    /**
     * @return the cc.
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * @param cc
     *            the cc to set.
     */
    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    /**
     * @return the bcc.
     */
    public Set<String> getBcc() {
        return bcc;
    }

    /**
     * @param bcc
     *            the bcc to set.
     */
    public void setBcc(Set<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * @return the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content.
     */
    public StringBuilder getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set.
     */
    public void setContent(StringBuilder content) {
        this.content = content;
    }

    /**
     * @return the listAttachments.
     */
    public List<FileMailDTO> getListAttachments() {
        List<FileMailDTO> tmpList = new ArrayList<>();
        if (this.listAttachments != null) {
            tmpList.addAll(this.listAttachments);
        }
        return tmpList;
    }

    /**
     * @param listAttachments
     *            the listAttachments to set.
     */
    public void setListAttachments(List<FileMailDTO> listAttachments) {
        if (listAttachments != null) {
            this.listAttachments = new ArrayList<>(listAttachments);
        }
    }

    /**
     * @return the htmlFileContent.
     */
    public FileMailDTO getHtmlFileContent() {
        return htmlFileContent;
    }

    /**
     * @param htmlFileContent
     *            the htmlFileContent to set.
     */
    public void setHtmlFileContent(FileMailDTO htmlFileContent) {
        this.htmlFileContent = htmlFileContent;
    }

    /**
     * @return the listCidFiles.
     */
    public List<FileMailDTO> getListCidFiles() {
        List<FileMailDTO> tmpList = new ArrayList<>();
        if (this.listCidFiles != null) {
            tmpList.addAll(this.listCidFiles);
        }
        return tmpList;
    }

    /**
     * @param listCidFiles
     *            the listCidFiles to set.
     */
    public void setListCidFiles(List<FileMailDTO> listCidFiles) {
        if (listCidFiles != null) {
            this.listCidFiles = new ArrayList<>(listCidFiles);
        }

    }

    /**
     * Return the contentCharset.<br>
     * If the charset of the html content is not defined, by default it is
     * established: {@link StandardCharsets.ISO_8859_1} (latin alphabet).
     * 
     * @return the contentCharset.
     */
    public Charset getContentCharset() {
        if (this.contentCharset == null) {
            this.contentCharset = StandardCharsets.ISO_8859_1;
        }
        return contentCharset;
    }

    /**
     * Set the contentCharset.<br>
     * If the charset of the email content is not defined, by default it is
     * established: {@link StandardCharsets.ISO_8859_1} (latin alphabet).
     * 
     * @param contentCharset
     *            the contentCharset to set.
     */
    public void setContentCharset(Charset contentCharset) {
        this.contentCharset = contentCharset;
    }

    /**
     * Return the htmlFileContentCharset.<br>
     * If the charset of the html content is not defined, by default it is
     * established: {@link StandardCharsets.ISO_8859_1} (latin alphabet).
     * 
     * @return the htmlFileContentCharset.
     */
    public Charset getHtmlFileContentCharset() {
        if (this.htmlFileContentCharset == null) {
            this.htmlFileContentCharset = StandardCharsets.ISO_8859_1;
        }
        return htmlFileContentCharset;
    }

    /**
     * Set the htmlFileContentCharset.<br>
     * Example: {@link StandardCharsets}.UTF_8
     * 
     * @param htmlFileContentCharset
     *            the htmlFileContentCharset to set.
     */
    public void setHtmlFileContentCharset(Charset htmlFileContentCharset) {
        this.htmlFileContentCharset = htmlFileContentCharset;
    }

}
