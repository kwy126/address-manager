package com.circle.util.json;

import java.io.Serializable;

/**
 * Created by keweiyang on 2017/6/5.
 */
public class JsonReturn implements Serializable {
    private boolean head;//json头
    private Object body;//json主体

    public static final boolean HEAD_SUCCESS = true;
    public static final boolean HEAD_FAILURE = false;

    public JsonReturn(boolean head, Object body) {
        this.head = head;
        this.body = body;
    }

    public static JsonReturn build(boolean head, Object body) {
        return new JsonReturn(head, body);
    }

    public static JsonReturn buildSuccess(Object body) {
        return build(HEAD_SUCCESS, body);
    }

    public static JsonReturn buildFailure(Object object) {
        return build(HEAD_FAILURE, object);
    }

    public static JsonReturn buildSuccessWithEmptyBody() {
        return build(HEAD_SUCCESS, "");
    }

    public static JsonReturn buildFailureWithEmptyBody() {
        return build(HEAD_FAILURE, "");
    }
    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "JsonReturn{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
