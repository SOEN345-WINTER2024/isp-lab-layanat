import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class IteratorTest {

    private List<String> list;
    private Iterator<String> itr;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add("cat");
        list.add("dog");
        list.add("bird");
        itr = list.iterator();
    }

    // Tests for hasNext()

    @Test
    public void testHasNext_C1_True() { // Test 1 - hasNext C1T
        assertTrue(itr.hasNext());
    }

    @Test
    public void testHasNext_C1_False() { // Test 2 - hasNext C1F
        itr.next();
        itr.next();
        assertFalse(itr.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testNext_C2() {
        list.add("donkey");
        itr.next(); // consume the cat
    }

    // Tests for next()

    @Test
    public void testNext_C1_True_C2_True() { // Test 4
        assertEquals("cat", itr.next());
    }

    @Test(expected = NoSuchElementException.class) // Test 5
    public void testNext_C1_False() {
        itr.next();
        itr.next();
        itr.next(); // this will throw NoSuchElementException
    }

    @Test
    public void testNext_C2_False() { // Test 6
        list = new ArrayList<>(Arrays.asList((String) null));
        itr = list.iterator();
        assertNull(itr.next());
    }

    @Test(expected = ConcurrentModificationException.class) // Test 7
    public void testNext_C5() {
        list.add("donkey");
        itr.next();
    }

    // Tests for remove()

    @Test
    public void testRemove_C1_True_C2_True_C3_True_C4_True_C5_True() { // Test 8
        itr.next();
        itr.remove();
        assertFalse(list.contains("cat"));
    }

    @Test
    public void testRemove_C1_False_C2_False_C3_True_C4_True_C5_True() { // Test 9
        itr.next();
        itr.next();
        itr.remove();
        assertFalse(list.contains("dog"));
    }

    @Test
    public void testRemove_C2_False() { // Test 10
        list.add(null);
        list.add("donkey");
        itr = list.iterator();
        itr.next();
        itr.next();
        itr.next();
        itr.remove();
        assertFalse(list.contains(null));
    }

    @Test(expected = UnsupportedOperationException.class) // Test 11
    public void testRemove_C3() {
        list = Collections.unmodifiableList(list);
        itr = list.iterator();
        itr.next();
        itr.remove();
    }

    @Test(expected = IllegalStateException.class) // Test 12
    public void testRemove_C4() {
        itr.remove();
    }

    @Test(expected = ConcurrentModificationException.class) // Test 13
    public void testRemove_C5() {
        itr.next();
        list.add("donkey");
        itr.remove();
    }
}
