package trade.wayruha.whitebit.ws;


import lombok.Data;

@Data
public class WSRequest {
    private final long id;
    private final String method;
    private final Object[] params;

    protected WSRequest(String method, Object... params) {
        this.id = 0;
        this.method = method;
        this.params = params;
    }
}
