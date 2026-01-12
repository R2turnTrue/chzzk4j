package xyz.r2turntrue.chzzk4j.chat;

class WsMessageServerboundConnect extends WsMessageBase {
    public WsMessageServerboundConnect() {
        super(WsMessageTypes.Commands.CONNECT);
    }

    static class Body {
        public String accTkn;
        public String auth;
        public String devName = "Google Chrome/141.0.0.0";
        public int devType = 2001;
        public String libVer = "4.9.3";
        public String locale = "ko-KR";
        public String osVer = "Windows/10";
        public String timezone = "Asia/Seoul";
        public String uid;
     }

     public Body bdy;
     public int tid = 1;
}
