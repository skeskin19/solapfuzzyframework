
package tr.edu.metu.ceng.sk.tasks;

import de.mrapp.apriori.Apriori.Configuration;
import de.mrapp.apriori.Transaction;
import de.mrapp.apriori.datastructure.TransactionalItemSet;
import de.mrapp.apriori.modules.FrequentItemSetMiner;
import de.mrapp.apriori.modules.FrequentItemSetMinerModule;
import de.mrapp.apriori.tasks.FrequentItemSetMinerTask;
import tr.edu.metu.ceng.sk.AbstractDataTest;
import tr.edu.metu.ceng.sk.DataIterator;
import tr.edu.metu.ceng.sk.NamedItem;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FrequentItemSetMinerTaskTest extends AbstractDataTest {

    /**
     * A class, which implements the interface {@link FrequentItemSetMiner} for testing purposes.
     */
    private class FrequentItemSetMinerMock implements FrequentItemSetMiner<NamedItem> {

        /**
         * A list, which contains the minimum supports, which have been passed when invoking the
         * {@link FrequentItemSetMinerMock#findFrequentItemSets(Iterable, double)} method.
         */
        private final List<Double> minSupports = new LinkedList<>();

        @NotNull
        @Override
        public Map<Integer, TransactionalItemSet<NamedItem>> findFrequentItemSets(
                @NotNull final Iterable<Transaction<NamedItem>> iterable, final double minSupport) {
            minSupports.add(minSupport);
            return new HashMap<>();
        }

    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a configuration as a parameter, if the configuration is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorWithConfigurationParameterThrowsException() {
        new FrequentItemSetMinerTask<>(null);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a configuration and a frequent item set miner as parameters, if the configuration is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorWithConfigurationAndFrequentItemSetMinerParameterThrowsExceptionWhenConfigurationIsNull() {
        new FrequentItemSetMinerTask<>(null, new FrequentItemSetMinerModule<>());
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the constructor, which expects
     * a configuration and a frequent item set miner as parameters, if the frequent item set miner
     * is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorWithConfigurationAndFrequentItemSetMinerParameterThrowsExceptionWhenFrequentItemSetMinerIsNull() {
        new FrequentItemSetMinerTask<>(mock(Configuration.class), null);
    }

    /**
     * Tests the functionality of the method, which allows to find a specific number of frequent
     * item sets.
     */
    @Test
    public final void testFindFrequentItemSets() {
        double minSupport = 0.5;
        double maxSupport = 0.8;
        double supportDelta = 0.1;
        Configuration configuration = mock(Configuration.class);
        when(configuration.getFrequentItemSetCount()).thenReturn(1);
        when(configuration.getMinSupport()).thenReturn(minSupport);
        when(configuration.getMaxSupport()).thenReturn(maxSupport);
        when(configuration.getSupportDelta()).thenReturn(supportDelta);
        FrequentItemSetMinerMock frequentItemSetMinerMock = new FrequentItemSetMinerMock();
        FrequentItemSetMinerTask<NamedItem> frequentItemSetMinerTask = new FrequentItemSetMinerTask<>(
                configuration, frequentItemSetMinerMock);
        File file = getInputFile(INPUT_FILE_1);
        frequentItemSetMinerTask.findFrequentItemSets(() -> new DataIterator(file));
        assertEquals(Math.round((maxSupport - minSupport) / supportDelta) + 1,
                frequentItemSetMinerMock.minSupports.size(), 0);

        for (int i = 0; i < frequentItemSetMinerMock.minSupports.size(); i++) {
            double support = frequentItemSetMinerMock.minSupports.get(i);
            assertEquals(maxSupport - (i * supportDelta), support, 0.01);
        }
    }

    /**
     * Tests the functionality of the method, which allows to find a specific number of frequent
     * item sets, if no specific number of frequent item sets should be found.
     */
    @Test
    public final void testFindFrequentItemSetsWhenFrequentItemSetCountIsZero() {
        double minSupport = 0.5;
        Configuration configuration = mock(Configuration.class);
        when(configuration.getFrequentItemSetCount()).thenReturn(0);
        when(configuration.getMinSupport()).thenReturn(minSupport);
        FrequentItemSetMinerMock frequentItemSetMinerMock = new FrequentItemSetMinerMock();
        FrequentItemSetMinerTask<NamedItem> frequentItemSetMinerTask = new FrequentItemSetMinerTask<>(
                configuration, frequentItemSetMinerMock);
        File file = getInputFile(INPUT_FILE_1);
        frequentItemSetMinerTask.findFrequentItemSets(() -> new DataIterator(file));
        assertEquals(1, frequentItemSetMinerMock.minSupports.size());
        assertEquals(minSupport, frequentItemSetMinerMock.minSupports.get(0), 0);
    }

}
