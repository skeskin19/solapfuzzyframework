
package tr.edu.metu.ceng.sk.metrics;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.metrics.Support;
import tr.edu.metu.ceng.sk.NamedItem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SupportTest {

    /**
     * Tests the functionality of the evaluate-method.
     */
    @Test
    public final void testEvaluate() {
        ItemSet<NamedItem> body = new ItemSet<>();
        body.add(new NamedItem("a"));
        ItemSet<NamedItem> head = new ItemSet<>();
        head.add(new NamedItem("b"));
        double support = 0.5;
        AssociationRule<NamedItem> rule = new AssociationRule<>(body, head, support);
        assertEquals(support, new Support().evaluate(rule), 0);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the evaluate-method, when
     * passing null as a parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testEvaluateThrowsException() {
        new Support().evaluate(null);
    }

    /**
     * Tests the functionality of the minValue-method.
     */
    @Test
    public final void testMinValue() {
        assertEquals(0, new Support().minValue(), 0);
    }

    /**
     * Tests the functionality of the maxValue-method.
     */
    @Test
    public final void testMaxValue() {
        assertEquals(1, new Support().maxValue(), 0);
    }

}
