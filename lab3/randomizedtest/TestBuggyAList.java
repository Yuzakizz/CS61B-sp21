package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.om.Item;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<Integer>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        aListNoResizing.addLast(4);
        aListNoResizing.addLast(5);
        aListNoResizing.addLast(6);
        buggyAList.addLast(4);
        buggyAList.addLast(5);
        buggyAList.addLast(6);
        assertEquals(buggyAList.size(), aListNoResizing.size());
        assertEquals(buggyAList.removeLast(), aListNoResizing.removeLast());
        assertEquals(buggyAList.removeLast(), aListNoResizing.removeLast());
        assertEquals(buggyAList.removeLast(), aListNoResizing.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size1 = L.size();
                int size2 = buggyAList.size();
                assertEquals(size1, size2);
            } else if (operationNumber == 2) {
                //getLast
                if (L.size() <= 0 || buggyAList.size() <= 0) {
                    continue;
                }
                assertEquals(L.getLast(), buggyAList.getLast());
            } else if (operationNumber == 3) {
                //removeLast
                if (L.size() <= 0 || buggyAList.size() <= 0) {
                    continue;
                }
                int correct = L.removeLast();
                int wrong = buggyAList.removeLast();
                assertEquals(correct, wrong);
            }
        }
    }
}
