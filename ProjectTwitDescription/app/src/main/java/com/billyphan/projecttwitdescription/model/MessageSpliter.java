package com.billyphan.projecttwitdescription.model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class MessageSpliter {
    private static final int OFFSET_OF_INDICATOR = 1;
    private int mNumOfTrunk;
    private int mNextTrunkIndex;
    private TextQueue mTextQueue;
    private List<String> mTextTrunks;
    private boolean mTextOverflowLastTrunk;

    public MessageSpliter() {
        this.mNumOfTrunk = 0;
        this.mTextTrunks = new ArrayList<>();
        this.mTextOverflowLastTrunk = false;
        this.mNextTrunkIndex = 0;
        this.mTextQueue = null;
    }

    public MessageSpliter setTextQueue(TextQueue text) {
        this.mTextQueue = text;
        this.mNumOfTrunk = text.getMinNumOfTrunk();
        return this;
    }

    public boolean hasNoTextOverflowLastTrunk() {
        return !mTextOverflowLastTrunk;
    }

    public List<String> toList() {
        return this.mTextTrunks;
    }

    @SuppressLint("DefaultLocale")
    public void compute() {
        this.reset();
        while (mTextQueue.hasText()) {
            this.append(mTextQueue.pop());
            if (mNextTrunkIndex > this.mNumOfTrunk) {
                this.mTextOverflowLastTrunk = true;
                break;
            }
        }
    }

    private void append(String word) {
        if (mNextTrunkIndex == 0) {
            this.appendWordToNewTrunk(word);
            return;
        }
        if (isLengthOfNextTrunkLessThanEqualLimit(word)) {
            this.appendWordToLastTrunk(word);
        } else {
            this.appendWordToNewTrunk(word);
        }
    }

    private void appendWordToLastTrunk(String word) {
        String text = mTextTrunks.get(mNextTrunkIndex - 1) + " " + word;
        mTextTrunks.remove(mNextTrunkIndex - 1);
        mTextTrunks.add(text);
    }

    @SuppressLint("DefaultLocale")
    private void appendWordToNewTrunk(String word) {
        String text = String.format("%d/%d %s", this.mNextTrunkIndex + OFFSET_OF_INDICATOR, this.mNumOfTrunk, word);
        mTextTrunks.add(text);
        this.mNextTrunkIndex++;
    }

    public void reset() {
        mTextQueue.reload();
        mTextTrunks.clear();
        this.mTextOverflowLastTrunk = false;
        this.mNextTrunkIndex = 0;
    }

    public boolean isLengthOfNextTrunkLessThanEqualLimit(String word) {
        String textOfLastTrunk = mTextTrunks.get(mNextTrunkIndex - 1);
        return textOfLastTrunk.length() + 1 + word.length() // 1 for space between last trunk and word
                <= TextQueue.LIMIT_OF_MESSAGE_SIZE;
    }

    public void increaseNumOfTrunk() {
        this.mNumOfTrunk++;
    }
}
