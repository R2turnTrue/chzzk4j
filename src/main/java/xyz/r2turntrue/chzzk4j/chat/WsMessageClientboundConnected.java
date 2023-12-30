package xyz.r2turntrue.chzzk4j.chat;

class WsMessageClientboundConnected extends WsMessageBase {
    static class Body {
        public String sid;
    }

    public WsMessageClientboundConnected.Body bdy = new Body();
    public int retCode;
    public String retMsg;
}
