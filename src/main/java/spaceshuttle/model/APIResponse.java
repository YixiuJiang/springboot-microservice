package spaceshuttle.model;

/**
 * Created by shixunliu on 21/10/17.
 */
public class APIResponse {

public class APIResponse {
    private boolean success;
    private String errorCode;
    private String errorMessage;

    private Object responseObject;

    public APIResponse() {

    }

    public void clearAll() {
        setResponseObject(null);
        setErrorMessage(null);
        setErrorCode(null);
        setSuccess(false);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
