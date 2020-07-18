
package tr.edu.metu.ceng.sk.datastructure;

import org.junit.Test;

import de.mrapp.apriori.Filter;
import de.mrapp.apriori.FrequentItemSets;
import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.Sorting;
import tr.edu.metu.ceng.sk.*;

import java.util.Comparator;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;


public class FrequentItemSetsTest {

    /**
     * Tests, if all class members are set correctly by the constructor.
     */
    @Test
    public final void testConstructor() {
        SortedSet<ItemSet<NamedItem>> frequentItemSets = new FrequentItemSets<>(
                Comparator.reverseOrder());
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.add(new NamedItem("a"));
        itemSet1.setSupport(0.5);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        itemSet2.add(new NamedItem("b"));
        itemSet2.setSupport(0.6);
        frequentItemSets.add(itemSet1);
        frequentItemSets.add(itemSet2);
        assertEquals(2, frequentItemSets.size());
        assertEquals(itemSet2, frequentItemSets.first());
        assertEquals(itemSet1, frequentItemSets.last());
    }

    /**
     * Tests the functionality of the clone-method.
     */
    @Test
    public final void testClone() {
        FrequentItemSets<NamedItem> frequentItemSets1 = new FrequentItemSets<>(
                Comparator.reverseOrder());
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.add(new NamedItem("a"));
        itemSet1.setSupport(0.5);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        itemSet2.add(new NamedItem("b"));
        itemSet2.setSupport(0.6);
        frequentItemSets1.add(itemSet1);
        frequentItemSets1.add(itemSet2);
        FrequentItemSets<NamedItem> frequentItemSets2 = frequentItemSets1.clone();
        assertEquals(frequentItemSets1.size(), frequentItemSets2.size());
        assertEquals(frequentItemSets1.first(), frequentItemSets2.first());
        assertEquals(frequentItemSets1.last(), frequentItemSets2.last());
    }

    /**
     * Tests the functionality of the method, which allows to create a string, which contains
     * information about frequent item sets.
     */
    @Test
    public final void testFormatFrequentItemSets() {
        NamedItem item1 = new NamedItem("a");
        NamedItem item2 = new NamedItem("b");
        double support1 = 0.3;
        double support2 = 0.7;
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.add(item1);
        itemSet1.setSupport(support1);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        itemSet2.add(item2);
        itemSet2.setSupport(support2);
        FrequentItemSets<NamedItem> frequentItemSets = new FrequentItemSets<>(
                Comparator.reverseOrder());
        frequentItemSets.add(itemSet1);
        frequentItemSets.add(itemSet2);
        assertEquals(
                "[" + itemSet2 + " (support = " + support2 + "),\n" + itemSet1 + " (support = " +
                        support1 + ")]",
                FrequentItemSets.formatFrequentItemSets(frequentItemSets));
    }

    /**
     * Tests the functionality of the method, which allows to create a string, which contains
     * information about frequent item sets, if the no frequent item sets are available.
     */
    @Test
    public final void testFormatFrequentItemSetsIfEmpty() {
        FrequentItemSets<NamedItem> frequentItemSets = new FrequentItemSets<>(
                Comparator.reverseOrder());
        assertEquals("[]", FrequentItemSets.formatFrequentItemSets(frequentItemSets));
    }

    /**
     * Tests the functionality of the method, which allows to sort frequent item sets.
     */
    @Test
    public final void testSort() {
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.add(new NamedItem("a"));
        itemSet1.setSupport(0.5);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        itemSet1.add(new NamedItem("b"));
        itemSet2.add(new NamedItem("c"));
        itemSet2.setSupport(0.4);
        FrequentItemSets<NamedItem> frequentItemSets = new FrequentItemSets<>(
                Sorting.forItemSets());
        frequentItemSets.add(itemSet1);
        frequentItemSets.add(itemSet2);
        assertEquals(itemSet1, frequentItemSets.first());
        assertEquals(itemSet2, frequentItemSets.last());
        Sorting<ItemSet> sorting = Sorting.forItemSets().withOrder(Sorting.Order.ASCENDING);
        FrequentItemSets<NamedItem> sortedFrequentItemSets = frequentItemSets.sort(sorting);
        assertEquals(itemSet2, sortedFrequentItemSets.first());
        assertEquals(itemSet1, sortedFrequentItemSets.last());
    }


    /**
     * Tests the functionality of the method, which allows to filter frequent item sets.
     */
    @Test
    public final void testFilter() {
        ItemSet<NamedItem> itemSet1 = new ItemSet<>();
        itemSet1.add(new NamedItem("a"));
        itemSet1.setSupport(0.5);
        ItemSet<NamedItem> itemSet2 = new ItemSet<>();
        itemSet1.add(new NamedItem("b"));
        itemSet2.add(new NamedItem("c"));
        itemSet2.setSupport(0.4);
        FrequentItemSets<NamedItem> frequentItemSets = new FrequentItemSets<>(
                Sorting.forItemSets());
        frequentItemSets.add(itemSet1);
        frequentItemSets.add(itemSet2);
        assertEquals(itemSet1, frequentItemSets.first());
        assertEquals(itemSet2, frequentItemSets.last());
        Filter<ItemSet> filter = Filter.forItemSets().bySize(2);
        FrequentItemSets<NamedItem> filteredFrequentItemSets = frequentItemSets.filter(filter);
        assertEquals(1, filteredFrequentItemSets.size());
        assertEquals(itemSet1, filteredFrequentItemSets.first());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * filter frequent item sets, if the given predicate is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFilterThrowsExceptionIfPredicateIsNull() {
        new FrequentItemSets<>(null).filter(null);
    }

}
