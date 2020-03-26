package mx.com.goh.email.sender.dto;

import java.io.Serializable;

/**
 * Class DTO for the email files.<br>
 * Version control: <br>
 * - 1.0.0 | 04/03/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileMailDTO implements Serializable {
    /** Serial Version UID. */
    private static final long serialVersionUID = 2721307736425422725L;
    /** File name. */
    private String fileName;
    /** File CID. */
    private String cid;
    /** File in Base 64 */
    private String base64;

    /**
     * 
     * Constructor of FileMailDTO.
     * 
     * @param fileName
     *            File Name.
     * @param base64
     *            String encode in Base64.
     */
    public FileMailDTO(String fileName, String base64) {
        super();
        this.fileName = fileName;
        this.base64 = base64;
    }

    /**
     * Constructor of the {@link FileMailDTO} class to initialize filePath, cid
     * and base64 variables.
     * 
     * @param fileName
     *            File path.
     * @param cid
     *            File Name.
     * @param base64
     *            String encode in Base64.
     */
    public FileMailDTO(String fileName, String cid, String base64) {
        this.fileName = fileName;
        this.cid = cid;
        this.base64 = base64;
    }

    /**
     * @return the fileName.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the cid.
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid
     *            the cid to set.
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * @return the base64.
     */
    public String getBase64() {
        return base64;
    }

    /**
     * @param base64
     *            the base64 to set.
     */
    public void setBase64(String base64) {
        this.base64 = base64;
    }

}
