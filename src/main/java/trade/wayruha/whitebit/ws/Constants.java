package trade.wayruha.whitebit.ws;

class Constants {
    public static long WEB_SOCKET_RECONNECTION_DELAY_MS = 10_000;

    static final int MAX_ORDER_BOOK_DEPTH = 100;
    static final String WS_ERROR_PARAM = "error";
    static final String WS_RESULT_PARAM = "result";
    static final String WS_STATUS_PARAM = "status";
    static final String WS_PARAMS_PARAM = "params";

    static final String WS_STATUS_SUCCESS = "success";
    static final String WS_PONG_TEXT = "pong";
}
