package mx.com.goh.email.sender.response;

import mx.com.goh.gcommons.response.type.SuccessType;

/**
 * Enum class for the success email responses.<br>
 * Version Control: <br>
 * 1.0.0 | 16/04/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public enum SendMailSuccess implements SuccessType {
    MAIL_SENT_CORRECTLY("MAIL-SEND", "Email successfully sent.");

    /** Code of the error. */
    private String code;
    /** Message of the error. */
    private String message;

    /**
     * Constructor of SendMailSuccess.
     * 
     * @param code
     *            code to set.
     * @param message
     *            message to set.
     */
    private SendMailSuccess(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.com.goh.gcommons.response.type.IEnumResponse#getCode()
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.com.goh.gcommons.response.type.IEnumResponse#getMessage()
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
