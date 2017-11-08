package spaceshuttle;

import spaceshuttle.model.APIResponse;

public class APIResponseMother {
    public static APIResponse getDefaultAPIResponse() {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(true);
        return apiResponse;
    }
}
