
package tr.edu.metu.ceng.sk.operators;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.Metric;
import de.mrapp.apriori.operators.ArithmeticMean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ArithmeticMeanTest {

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the add-methods, when the
     * metric, which is passed as an argument, is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddThrowsExceptionWhenMetricIsNull() {
        new ArithmeticMean().add(null);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the add methods, when the
     * weight, which is passes as an argument, is not greater than 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddThrowsExceptionWhenWeightIsNotGreaterThanZero() {
        new ArithmeticMean().add(mock(Metric.class), 0);
    }

    /**
     * Tests the functionality of the evaluate-method, when using equal weights.
     */
    @Test
    public final void testEvaluateWithEqualWeights() {
        Metric metric = mock(Metric.class);
        when(metric.evaluate(any())).thenReturn(3d, 5d);
        ArithmeticMean arithmeticMean = new ArithmeticMean().add(metric).add(metric);
        assertEquals(4, arithmeticMean.evaluate(mock(AssociationRule.class)), 0);
    }

    /**
     * Tests the functionality of the evaluate-method, when using different weights.
     */
    @Test
    public final void testEvaluateWithDifferentWeights() {
        Metric metric = mock(Metric.class);
        when(metric.evaluate(any())).thenReturn(3d, 5d);
        ArithmeticMean arithmeticMean = new ArithmeticMean().add(metric, 2.0).add(metric, 1.0);
        assertEquals(11d / 3d, arithmeticMean.evaluate(mock(AssociationRule.class)), 0);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the evaluate-method, when
     * passing null as a parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testEvaluateThrowsExceptionWhenRuleIsNull() {
        new ArithmeticMean().add(mock(Metric.class)).evaluate(null);
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown by the evaluate-method, when no
     * metrics have been added.
     */
    @Test(expected = IllegalStateException.class)
    public final void testEvaluateThrowsExceptionWhenNoMetricsAdded() {
        new ArithmeticMean().evaluate(mock(AssociationRule.class));
    }

}
