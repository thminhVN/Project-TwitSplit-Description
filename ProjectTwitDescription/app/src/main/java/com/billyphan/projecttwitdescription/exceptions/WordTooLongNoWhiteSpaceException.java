package com.billyphan.projecttwitdescription.exceptions;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class WordTooLongNoWhiteSpaceException extends Exception {
    public WordTooLongNoWhiteSpaceException() {
        super("Word in text too long, no whitespace");
    }
}
