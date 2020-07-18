
package tr.edu.metu.ceng.sk.modules;

import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.datastructure.TransactionalItemSet;
import de.mrapp.apriori.modules.FrequentItemSetMinerModule;
import tr.edu.metu.ceng.sk.AbstractDataTest;
import tr.edu.metu.ceng.sk.DataIterator;
import tr.edu.metu.ceng.sk.NamedItem;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class FrequentItemSetMinerModuleTest extends AbstractDataTest {

    /**
     * Tests the functionality of the method, which allows to find frequent item sets, when using a
     * specific input file.
     *
     * @param fileName               The file name of the input file as a {@link String}. The file
     *                               name may neither be null, nor empty
     * @param minSupport             The support, which must at least be reached item sets to be
     *                               considered frequent, as a {@link Double} value
     * @param actualFrequentItemSets The frequent item sets, which are contained by the input file,
     *                               as a two-dimensional {@link String} array. The array may not be
     *                               null
     * @param actualSupports         The supports of the frequent item sets, which are contained by
     *                               the input file, as a {@link Double} array. The array may not be
     *                               null
     */
    private void testFindFrequentItemSets(@NotNull final String fileName,
                                          final double minSupport,
                                          @NotNull final String[][] actualFrequentItemSets,
                                          @NotNull double[] actualSupports) {
        File inputFile = getInputFile(fileName);
        FrequentItemSetMinerModule<NamedItem> frequentItemSetMiner = new FrequentItemSetMinerModule<>();
        Map<Integer, TransactionalItemSet<NamedItem>> frequentItemSets = frequentItemSetMiner
                .findFrequentItemSets(() -> new DataIterator(inputFile), minSupport);
        int frequentItemSetCount = 0;

        for (Map.Entry<Integer, TransactionalItemSet<NamedItem>> entry : frequentItemSets
                .entrySet()) {
            int key = entry.getKey();
            ItemSet<NamedItem> itemSet = entry.getValue();
            int index = 0;

            for (NamedItem item : itemSet) {
                assertEquals(actualFrequentItemSets[frequentItemSetCount][index], item.getName());
                index++;
            }

            assertEquals(actualSupports[frequentItemSetCount], itemSet.getSupport(), 0);
            assertEquals(itemSet.hashCode(), key);
            frequentItemSetCount++;
        }

        assertEquals(actualFrequentItemSets.length, frequentItemSetCount);
    }

    /**
     * Tests the functionality of the method, which allows to find frequent item sets, when using
     * the first input file.
     */
    @Test
    public final void testFindFrequentItemSets1() {
        testFindFrequentItemSets(INPUT_FILE_1, 0.5, FREQUENT_ITEM_SETS_1, SUPPORTS_1);
    }

    /**
     * Tests the functionality of the method, which allows to find frequent item sets, when using
     * the second input file.
     */
    @Test
    public final void testFindFrequentItemSets2() {
        testFindFrequentItemSets(INPUT_FILE_2, 0.25, FREQUENT_ITEM_SETS_2, SUPPORTS_2);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * find frequent item sets, if the iterator, which is passed as a parameter, is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFindFrequentItemSetsThrowsExceptionWhenIteratorIsNull() {
        new FrequentItemSetMinerModule<>().findFrequentItemSets(null, 0.5);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * find frequent item sets, if the minimum support, which is passed as a parameter, is less than
     * 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFindFrequentItemSetsThrowsExceptionWhenMinSupportIsLessThanZero() {
        File inputFile = getInputFile(INPUT_FILE_1);
        new FrequentItemSetMinerModule<NamedItem>()
                .findFrequentItemSets(() -> new DataIterator(inputFile), -0.1);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * find frequent item sets, if the minimum support, which is passed as a parameter, is greater
     * than 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFindFrequentItemSetsThrowsExceptionWhenMinSupportIsGreaterThanOne() {
        File inputFile = getInputFile(INPUT_FILE_1);
        new FrequentItemSetMinerModule<NamedItem>()
                .findFrequentItemSets(() -> new DataIterator(inputFile), 1.1);
    }

}
