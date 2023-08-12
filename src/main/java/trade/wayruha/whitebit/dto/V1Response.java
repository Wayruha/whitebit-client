package trade.wayruha.whitebit.dto;

import lombok.Data;

@Data
public class V1Response<T> {
    private boolean success;
    private String message;
    private T result;
}
