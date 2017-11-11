package project.model;

import java.util.List;

public class ClientValidationFailureData {

    private List<CodeAndMessage> data;

    public ClientValidationFailureData(List<CodeAndMessage> data) {
        this.data = data;
    }

    public List<CodeAndMessage> getData() {
        return data;
    }

}
