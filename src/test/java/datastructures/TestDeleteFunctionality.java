package datastructures;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

import static org.junit.Assert.fail;

/**
 * This class should contain all the tests you implement to verify that
 * your 'delete' method behaves as specified.
 *
 * This test _extends_ your TestDoubleLinkedList class. This means that when
 * you run this test, not only will your tests run, all of the ones in
 * TestDoubleLinkedList will also run.
 *
 * This also means that you can use any helper methods defined within
 * TestDoubleLinkedList here. In particular, you may find using the
 * 'assertListMatches' and 'makeBasicList' helper methods to be useful.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteFunctionality extends TestDoubleLinkedList {
    @Test(timeout=SECOND)
    public void testDeleteHappyCase() {
        IList<String> list = makeBasicList();
        String val = list.delete(1);
        this.assertListMatches(new String[] {"a", "c"}, list);
        assertEquals(val, "b");

        try {
            list.delete(-1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Okay
        }

        try {
            list.delete(10);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Okay
        }

    }

    @Test(timeout=SECOND)
    public void testDeleteEmptyAndNullCase() {
        IList<String> list = new DoubleLinkedList<String>();
        try {
            list.delete(0);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Okay
        }
        list.add("a");
        list.add(null);
        list.add("c");
        assertEquals(list.delete(1), null);
    }

    @Test(timeout=SECOND)
    public void testDeleteEdgeCase() {
        IList<String> list = makeBasicList();
        list.delete(0);
        this.assertListMatches(new String[] {"b", "c"}, list);
        list.delete(1);
        this.assertListMatches(new String[] {"b"}, list);
        list.delete(0);
        this.assertListMatches(new String[] {}, list);
    }

    public void testDeleteWithAdd() {
        IList<String> list = new DoubleLinkedList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.delete(0);
        list.delete(0);
        list.delete(0);
        list.add("d");
        list.add("e");
        list.add("f");
        this.assertListMatches(new String[] {"d", "e", "f"}, list);
    }
}
