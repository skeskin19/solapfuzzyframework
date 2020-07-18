
package tr.edu.metu.ceng.sk;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import de.mrapp.apriori.Transaction;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class DataIteratorTest extends AbstractDataTest {

    /**
     * The data, which is contained by the first input file.
     */
    private static final String[][] DATA_1 = {{"bread", "butter", "sugar"}, {"coffee", "milk", "sugar"}, {"bread", "coffee", "milk", "sugar"}, {"coffee", "milk"}};

    /**
     * The data, which is contained by the second input file.
     */
    private static final String[][] DATA_2 = {{"beer", "chips", "wine"}, {"beer", "chips"}, {"pizza", "wine"}, {"chips", "pizza"}};


    /**
     * Tests, if the data, which is contained by an input file, is iterated correctly.
     *
     * @param fileName   The file name of the input file as a {@link String}
     * @param actualData The data, which is contained by the input file, as a two-dimensional {@link
     *                   String} array. The array may not be null
     */
    private void testIterator(@NotNull final String fileName,
                              @NotNull final String[][] actualData) {
        File inputFile = getInputFile(fileName);
        DataIterator iterator = new DataIterator(inputFile);
        int transactionCount = 0;

        while (iterator.hasNext()) {
            Transaction<NamedItem> transaction = iterator.next();
            String[] line = actualData[transactionCount];
            int index = 0;

            for (NamedItem item : transaction) {
                assertEquals(line[index], item.getName());
                index++;
            }

            transactionCount++;
        }

        assertEquals(DATA_1.length, transactionCount);
    }

    /**
     * Tests, if the data of the first input file is iterated correctly.
     */
    @Test
    public final void testIterator1() {
        testIterator(INPUT_FILE_1, DATA_1);
    }

    /**
     * Tests, if the data of the second input file is iterated correctly.
     */
    @Test
    public final void testIterator2() {
        testIterator(INPUT_FILE_2, DATA_2);
    }

}
