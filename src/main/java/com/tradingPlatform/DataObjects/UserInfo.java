package com.tradingPlatform.DataObjects;

public class UserInfo {

    private String userName;
    private String publicKey;
    private Account account;
    private boolean loggedIn;

    private UserInfo(Builder builder) {
        userName = builder.userName;
        publicKey = builder.publicKey;
        account = builder.account;
        loggedIn = builder.loggedIn;
    }

    public static final class Builder {
        private String userName;
        private String publicKey;
        private Account account;
        private boolean loggedIn;

        public Builder() {
        }

        public Builder userName(String val) {
            userName = val;
            return this;
        }

        public Builder publicKey(String val) {
            publicKey = val;
            return this;
        }

        public Builder account(Account val) {
            account = val;
            return this;
        }

        public Builder loggedIn(boolean val) {
            loggedIn = val;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }

    public String toString() {
        return userName+"\n"+publicKey+"\n"+account+"\n"+loggedIn;
    }
}
