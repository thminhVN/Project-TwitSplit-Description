package com.billyphan.projecttwitdescription.utilities;

import com.billyphan.projecttwitdescription.exceptions.TextNullException;
import com.billyphan.projecttwitdescription.exceptions.WordTooLongNoWhiteSpaceException;
import com.billyphan.projecttwitdescription.model.MessageSpliter;
import com.billyphan.projecttwitdescription.model.TextQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class MessageUtils {

    public static List<String> split(String text) throws TextNullException, WordTooLongNoWhiteSpaceException {
        TextQueue textQueue = new TextQueue(text);

        if (textQueue.isNull()) throw new TextNullException();

        if (textQueue.hasWordLengthGreatThanTrunkLimit())
            throw new WordTooLongNoWhiteSpaceException();

        List<String> textTrunks;
        if (textQueue.isTextLengthLessThanEqualTrunkLimit()) {
            textTrunks = new ArrayList<>();
            textTrunks.add(text);
        } else {
            textTrunks = splitTextGreatThanLimit(textQueue);
        }
        return textTrunks;
    }

    private static List<String> splitTextGreatThanLimit(TextQueue textQueue) {
        MessageSpliter messageSpliter = new MessageSpliter();
        messageSpliter.setTextQueue(textQueue);
        while (true) {
            messageSpliter.compute();
            if (messageSpliter.hasNoTextOverflowLastTrunk()) {
                break;
            }
            messageSpliter.increaseNumOfTrunk();
        }
        return messageSpliter.toList();
    }
}
