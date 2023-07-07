package trade.wayruha.whitebit.ws;

import lombok.Data;

@Data
public class WSResponse<T> {
    private long id;
    private T result;
    private Error error;

    @Data
    public static class Error {
        private String message;
        private int code;
    }
}
