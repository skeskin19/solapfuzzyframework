
package tr.edu.metu.ceng.sk.datastructure;

import de.mrapp.apriori.Transaction;
import de.mrapp.apriori.datastructure.TransactionalItemSet;
import tr.edu.metu.ceng.sk.NamedItem;
import tr.edu.metu.ceng.sk.DataIterator.TransactionImplementation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class TransactionalItemSetTest {

    /**
     * Tests, if all class members are correctly set by the default constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        TransactionalItemSet<NamedItem> transactionalItemSet = new TransactionalItemSet<>();
        Map<Integer, Transaction<NamedItem>> transactions = transactionalItemSet.getTransactions();
        assertNotNull(transactions);
        assertTrue(transactions.isEmpty());
    }

    /**
     * Tests, if all class members are correctly set by the constructor, which expects another item
     * set as a parameter.
     */
    @Test
    public final void testConstructorWithItemSetParameter() {
        NamedItem item = new NamedItem("a");
        Map<Integer, Transaction<NamedItem>> transactions = new HashMap<>();
        transactions.put(0, new TransactionImplementation("foo"));
        transactions.put(1, new TransactionImplementation("bar"));
        TransactionalItemSet<NamedItem> transactionalItemSet1 = new TransactionalItemSet<>();
        transactionalItemSet1.setSupport(0.5);
        transactionalItemSet1.add(item);
        transactionalItemSet1.setTransactions(transactions);
        TransactionalItemSet<NamedItem> transactionalItemSet2 = new TransactionalItemSet<>(
                transactionalItemSet1);
        assertEquals(transactionalItemSet1.getSupport(), transactionalItemSet2.getSupport(), 0);
        assertEquals(transactionalItemSet1.size(), transactionalItemSet2.size());
        assertEquals(transactionalItemSet1.first(), transactionalItemSet2.first());
        assertEquals(transactionalItemSet1.getTransactions().size(),
                transactionalItemSet2.getTransactions().size());
        assertEquals(transactionalItemSet1.getTransactions().get(0),
                transactionalItemSet2.getTransactions().get(0));
        assertEquals(transactionalItemSet1.getTransactions().get(1),
                transactionalItemSet2.getTransactions().get(1));
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * another item set as a parameter, if the item set is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorWithItemSetParameterThrowsException() {
        new TransactionalItemSet<>(null);
    }

    /**
     * Tests the functionality of the method, which allows to set the transactions.
     */
    @Test
    public final void testSetTransactions() {
        TransactionalItemSet<NamedItem> transactionalItemSet = new TransactionalItemSet<>();
        assertTrue(transactionalItemSet.getTransactions().isEmpty());
        Map<Integer, Transaction<NamedItem>> transactions = new HashMap<>();
        transactions.put(0, new TransactionImplementation("foo"));
        transactions.put(1, new TransactionImplementation("bar"));
        transactionalItemSet.setTransactions(transactions);
        assertEquals(transactions, transactionalItemSet.getTransactions());
    }

}
