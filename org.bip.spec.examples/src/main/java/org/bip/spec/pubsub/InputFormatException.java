package org.bip.spec.pubsub;

public class InputFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InputFormatException(String strMessage) {
        super(strMessage);
    }

    public String toString() {
        return "InputFormatException";
    }
}
