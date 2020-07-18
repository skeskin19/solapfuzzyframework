
package tr.edu.metu.ceng.sk.metrics;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.metrics.Lift;
import tr.edu.metu.ceng.sk.NamedItem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LiftTest {

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
        assertEquals(support / (bodySupport * headSupport), new Lift().evaluate(rule), 0);
    }

    /**
     * Tests the functionality of the evaluate-method, when dividing by zero.
     */
    @Test
    public final void testEvaluateDivisionByZero() {
        ItemSet<NamedItem> body = new ItemSet<>();
        body.add(new NamedItem("a"));
        body.setSupport(0.7);
        ItemSet<NamedItem> head = new ItemSet<>();
        head.add(new NamedItem("b"));
        AssociationRule<NamedItem> rule = new AssociationRule<>(body, head, 0.5);
        assertEquals(0, new Lift().evaluate(rule), 0);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the evaluate-method, when
     * passing null as a parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testEvaluateThrowsException() {
        new Lift().evaluate(null);
    }

    /**
     * Tests the functionality of the minValue-method.
     */
    @Test
    public final void testMinValue() {
        assertEquals(0, new Lift().minValue(), 0);
    }

    /**
     * Tests the functionality of the maxValue-method.
     */
    @Test
    public final void testMaxValue() {
        assertEquals(Double.MAX_VALUE, new Lift().maxValue(), 0);
    }

}
