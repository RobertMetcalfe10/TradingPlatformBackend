package com.tradingPlatform.DataObjects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "UserInfo")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserInfo {

    @Id
    private int id;
    @Field
    private String userName;
    private Account account;
    private boolean loggedIn;

    @PersistenceConstructor
    public UserInfo(String userName){
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    private UserInfo(Builder builder) {
        userName = builder.userName;
        account = builder.account;
        loggedIn = builder.loggedIn;
    }

    public static final class Builder {
        private String userName;
        private Account account;
        private boolean loggedIn;

        public Builder() {
        }

        public Builder userName(String val) {
            userName = val;
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
        return userName+"\n"+account+"\n"+loggedIn;
    }
}
