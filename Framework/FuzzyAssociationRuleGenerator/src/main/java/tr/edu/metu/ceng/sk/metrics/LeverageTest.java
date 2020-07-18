
package tr.edu.metu.ceng.sk.metrics;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.metrics.Leverage;
import tr.edu.metu.ceng.sk.NamedItem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LeverageTest {

    /**
     * Tests the functionality of the evaluate-method.
     */
    @Test
    public final void testEvaluate() {
        double bodySupport = 0.7;
        ItemSet<NamedItem> body = new ItemSet<>();
        body.add(new NamedItem("a"));
        body.setSupport(bodySupport);
        double headSupport = 0.8;
        ItemSet<NamedItem> head = new ItemSet<>();
        head.add(new NamedItem("b"));
        head.setSupport(headSupport);
        double support = 0.5;
        AssociationRule<NamedItem> rule = new AssociationRule<>(body, head, support);
        assertEquals(support - (bodySupport * headSupport), new Leverage().evaluate(rule), 0);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the evaluate-method, when
     * passing null as a parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testEvaluateThrowsException() {
        new Leverage().evaluate(null);
    }

    /**
     * Tests the functionality of the minValue-method.
     */
    @Test
    public final void testMinValue() {
        assertEquals(-Double.MAX_VALUE, new Leverage().minValue(), 0);
    }

    /**
     * Tests the functionality of the maxValue-method.
     */
    @Test
    public final void testMaxValue() {
        assertEquals(1, new Leverage().maxValue(), 0);
    }

}
