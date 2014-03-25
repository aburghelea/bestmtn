package ro.endava.bestm.dto;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
public class SimpleResponse {

    private String content;

    public SimpleResponse() {
    }

    public SimpleResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }


    @Override
    public String toString() {
        return "SimpleResponse{" +
                "content='" + content + '\'' +
                '}';
    }
}
