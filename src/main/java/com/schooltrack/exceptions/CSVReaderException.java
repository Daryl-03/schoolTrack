package com.schooltrack.exceptions;

public class CSVReaderException extends Exception{
    public CSVReaderException(String message) {
        super(message);
    }
    
    public CSVReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
