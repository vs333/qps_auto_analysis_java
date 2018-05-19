package com.showjoy.qps.model;

import java.io.Serializable;
import java.util.List;

public class DingMsg implements Serializable {
    private static final long serialVersionUID = 8424474204018051093L;

    private String msgtype;
    private Text text;
    private At at;

    public DingMsg() {
    }

    public DingMsg(String msgtype, Text text, At at) {
        this.msgtype = msgtype;
        this.text = text;
        this.at = at;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public At getAt() {
        return at;
    }

    public void setAt(At at) {
        this.at = at;
    }

    public static class At {
        private List<String> atMobiles;
        private boolean isAtAll;

        public At() {
        }

        public At(List<String> atMobiles, boolean isAtAll) {
            this.atMobiles = atMobiles;
            this.isAtAll = isAtAll;
        }
        public At(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setIsAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }

        public boolean getIsAtAll() {
            return isAtAll;
        }

        public void setAtAll(boolean atAll) {
            isAtAll = atAll;
        }
    }
    public static class Text {

        public Text() {
        }

        public Text(String content) {
            this.content = content;
        }

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
