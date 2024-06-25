package com.kidzcubicle.uaps.web.exception.handler;

import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;

public class EmailAlreadyUsedException extends BadRequestAlertException {


    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "user-management", "email-already-used");

    }

}
