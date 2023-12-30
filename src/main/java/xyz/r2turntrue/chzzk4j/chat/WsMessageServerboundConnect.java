package xyz.r2turntrue.chzzk4j.chat;

class WsMessageServerboundConnect extends WsMessageBase {
    public WsMessageServerboundConnect() {
        super(WsMessageTypes.Commands.CONNECT);
    }

    static class Body {
        public String accTkn;
        public String auth;
        public int devType = 2001;
        public String uid;
     }

     public Body bdy;
     public int tid = 1;
}
