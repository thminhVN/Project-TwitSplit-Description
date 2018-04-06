package com.billyphan.projecttwitdescription.model;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class TextQueue {
    public static final int LIMIT_OF_MESSAGE_SIZE = 50;
    private final String mText;
    private Queue<String> mTextSplits;

    public TextQueue(String text) {
        this.mText = text;
        this.mTextSplits = new ArrayDeque<>();
        if (mText != null)
            if (mText.length() > 0)
                this.reload();
    }

    public boolean isNull() {
        return mTextSplits.size() <= 0;
    }

    public boolean hasWordLengthGreatThanTrunkLimit() {
        for (String textSplit : this.mTextSplits) {
            if (textSplit.length() > LIMIT_OF_MESSAGE_SIZE)
                return true;
        }
        return false;
    }

    public boolean isTextLengthLessThanEqualTrunkLimit() {
        return mText.length() <= LIMIT_OF_MESSAGE_SIZE;
    }

    public boolean hasText() {
        return this.mTextSplits.size() > 0;
    }

    public String pop() {
        return this.mTextSplits.poll();
    }

    public void reload() {
        this.mTextSplits.clear();
        String tmps[] = mText.split(" ");
        for (String tmp : tmps) {
            this.mTextSplits.add(tmp);
        }
    }

    public int getMinNumOfTrunk() {
        return (int) Math.ceil((float) this.mText.length() / LIMIT_OF_MESSAGE_SIZE);
    }
}
