package example.qlhs.exception;

import example.qlhs.dto.response.ErrorsData;

import java.util.Map;

public class CustomValidationException extends RuntimeException {
    private Map<String,String> errors;

    private ErrorsData errorsData;

    public CustomValidationException(){};

    public CustomValidationException(Map<String, String> errors) {
        this.errors = errors;
    };


    public CustomValidationException(ErrorsData errorsData) {this.errorsData = errorsData;};

    public CustomValidationException(Map<String, String> errors, ErrorsData errorsData) {
        this.errors = errors;
        this.errorsData = errorsData;
    }

    public ErrorsData getErrorsData() {
        return errorsData;
    }

    public Map<String, String> getErrors() {
        return errors;
    }


}
