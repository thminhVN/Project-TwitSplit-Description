package com.billyphan.projecttwitdescription.model;

import com.billyphan.projecttwitdescription.exceptions.TextNullException;
import com.billyphan.projecttwitdescription.exceptions.WordTooLongNoWhiteSpaceException;
import com.billyphan.projecttwitdescription.utilities.MessageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class Message {
    private String mMessage;
    private List<String> mParts;

    public Message(String string) {
        this.mMessage = string;
        try {
            this.update(string);
        } catch (WordTooLongNoWhiteSpaceException e) {
            e.printStackTrace();
        } catch (TextNullException e) {
            e.printStackTrace();
        }
    }

    public void update(String s) throws WordTooLongNoWhiteSpaceException, TextNullException {
        this.mMessage = s;
        mParts = MessageUtils.split(s);
    }

    public List<String> getParts() {
        return mParts;
    }

    public String getMessage() {
        return mMessage;
    }

    @Override
    public String toString() {
       return mMessage;
    }

    public String getPartsString() {
        String text = "";
        if (mParts == null) return text;
        for (String mPart : mParts) {
            text += "\r\n" + mPart;
        }
        return text;
    }
}
