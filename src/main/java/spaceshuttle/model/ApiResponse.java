package spaceshuttle.model;

public class ApiResponse {

    private boolean flag;
    private String respondCode;
    private String respondContent;
    private Object respondObject;



    public Object getRespondObject() {
        return respondObject;
    }

    public void setRespondObject(Object respondObject) {
        this.respondObject = respondObject;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRespondCode() {
        return respondCode;
    }

    public void setRespondCode(String respondCode) {
        this.respondCode = respondCode;
    }

    public String getRespondContent() {
        return respondContent;
    }

    public void setRespondContent(String respondContent) {
        this.respondContent = respondContent;
    }
}
