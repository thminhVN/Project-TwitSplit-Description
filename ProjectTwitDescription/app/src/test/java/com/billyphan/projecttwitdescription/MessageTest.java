package com.billyphan.projecttwitdescription;

import com.billyphan.projecttwitdescription.exceptions.TextNullException;
import com.billyphan.projecttwitdescription.exceptions.WordTooLongNoWhiteSpaceException;
import com.billyphan.projecttwitdescription.utilities.MessageUtils;

import org.junit.Test;

import static com.billyphan.projecttwitdescription.model.TextQueue.LIMIT_OF_MESSAGE_SIZE;
import static org.junit.Assert.*;


import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class MessageTest {
    @Test
    public void testSplitMessage() {
        String testCase = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.";
        List<String> messageTrunks = null;
        try {
            messageTrunks = MessageUtils.split(testCase);
            String tmp = "";
            for (String messageTrunk : messageTrunks) {
                tmp += "\r\n" + messageTrunk;
            }
            System.out.print(tmp);
        } catch (TextNullException e) {
            assertTrue(e.getMessage(), false);
        } catch (WordTooLongNoWhiteSpaceException e) {
            assertTrue(e.getMessage(), false);
        }
        assertTrue(messageTrunks.size() == 2);
        assertTrue(messageTrunks.get(0).equals("1/2 I can't believe Tweeter now supports chunking"));
        assertTrue(messageTrunks.get(1).equals("2/2 my messages, so I don't have to do it myself."));
    }

    @Test
    public void testMessageLength() {
        String txt = "1/2 I can't believe Tweeter now supports chunking";
        assertTrue(txt.length() == 49);
    }

    @Test
    public void testSplitMessageNewTestCase() {
        String text = "Split messages will have a \"part indicator\" appended to the beginning of each section. In the example above, the message was split into two chunks, so the part indicators read \"1/2\" and \"2/2\". Be aware that these count toward the character limit";
        List<String> messageTrunks = null;
        try {
            messageTrunks = MessageUtils.split(text);
            String tmp = "";
            for (String messageTrunk : messageTrunks) {
                tmp += "\r\n" + messageTrunk;
                assertTrue(messageTrunk.length() <= LIMIT_OF_MESSAGE_SIZE);
            }
            System.out.print(tmp);
        } catch (TextNullException e) {
            assertTrue(e.getMessage(), false);
        } catch (WordTooLongNoWhiteSpaceException e) {
            assertTrue(e.getMessage(), false);
        }
    }
}
