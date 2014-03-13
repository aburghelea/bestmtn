package ro.endava.bestm.dto;

public class SimpleResponse {

    private final long id;
    private final String content;

    public SimpleResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
