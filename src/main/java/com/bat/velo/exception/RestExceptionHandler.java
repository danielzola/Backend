package com.bat.velo.exception;

import com.bat.velo.dto.CommonRs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RestExceptionHandler {
            
    
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class.getName());
	
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<CommonRs> handleException(Exception ex) {

        if (ex instanceof ResourceNotFoundException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                    HttpStatus.NOT_FOUND);
        else if (ex instanceof ResourceForbiddenException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
                    HttpStatus.FORBIDDEN);
        else if (ex instanceof ResourceNotAllowedException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        else if (ex instanceof BadRequestException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                    HttpStatus.BAD_REQUEST);
        else if (ex instanceof ResourceConflictException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.CONFLICT.value(), ex.getMessage()),
                    HttpStatus.CONFLICT);
        else if (ex instanceof ConversionFailedException || ex instanceof MethodArgumentTypeMismatchException)
            return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), "bad request"),
                    HttpStatus.BAD_REQUEST);
        else {

            //ex.printStackTrace(); need tag ERROR to help debugging
            logger.error("Exception : ", ex);

            return new ResponseEntity<>(new CommonRs(HttpStatus.INTERNAL_SERVER_ERROR.value(), "system error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
