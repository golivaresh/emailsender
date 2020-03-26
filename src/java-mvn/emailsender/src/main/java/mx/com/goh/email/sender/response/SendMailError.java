package mx.com.goh.email.sender.response;

import mx.com.goh.gcommons.response.type.ErrorType;

/**
 * Enum class for the error email responses.<br>
 * Version Control: <br>
 * 1.0.0 | 16/04/2018 | Gustavo Olivares Hernandez
 * 
 * @author golivaresh
 * @version 1.0.0
 * @since 1.0.0
 */
public enum SendMailError implements ErrorType {
    UNSPECIFIED_FROM("UNDF-FROM", "'Sender mail' not specified."),
    FAILED_ADD_FROM("FAIL-FROM", "Error adding 'sender mail'."),
    UNSPECIFIED_TO("UNDF-TO", "'Recipient mail' not specified."),
    FAILED_ADD_TO("FAIL-TO", "Failed to add 'recipient email'."),
    FAILED_ADD_CC("FAIL-CC", "Error adding 'mail copy (cc)'."),
    FAILED_ADD_BCC("FAIL-BCC", "Error adding 'mail blind copy'."),
    FAILED_ADD_SUBJECT("FAIL-SUBJECT", "Failed to add 'message subject'."),
    FAILED_SET_BODYCONTENT("FAIL-CONTENT", "Error adding 'message body'."),
    FAILED_SET_ATTACHMENTS("FAIL-ATTACHMENTS", "Error adding 'attachments'."),
    FAILED_SET_CID("FAIL-CID", "Error adding 'Cid files'."),
    FAILED_SET_MCONTENT("FAIL-MCONTENT", "Error adding 'message content'."),
    FAILED_SAVE_MESSAGE("FAIL-SAVEMSG", "Error saving 'message content'."),
    UNSPECIFIED_SESSION_CONFIGURATION("UNDF-CONFSESSION", 
            "'Session settings' not specified."),
    UNSPECIFIED_MAIL_CONFIGURATION("UNDF-CONFMAIL", 
            "'Message settings' not specified."),
    
    FAILED_TO_SEND_EMAIL("FAIL-SEND", "An error occurred while sending the email."),
    
    FAILED_INITIALIZED_TRANSPORT("FAIL-INITTRANS", 
            "An error occurred while connecting to the email server."),
    FAILED_CLOSE_TRANSPORT("FAIL-CLOSETRANS", "An error occurred while closing the connection.")
    ;
    
    /** Code of the error. */
    private String code;
    /** Message of the error. */
    private String message;

    /**
     * Constructor of SendMailError.
     * 
     * @param code
     *            code to set.
     * @param message
     *            message to set.
     */
    private SendMailError(String code, String message) {
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
