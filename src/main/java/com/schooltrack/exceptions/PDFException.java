package com.schooltrack.exceptions;

public class PDFException extends Exception{
    public PDFException(String message) {
        super(message);
    }
    
    public PDFException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PDFException(Throwable cause) {
        super(cause);
    }
}
