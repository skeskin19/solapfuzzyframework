
package tr.edu.metu.ceng.sk.operators;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.Metric;
import de.mrapp.apriori.operators.HarmonicMean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HarmonicMeanTest {

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the add-methods, when the
     * metric, which is passed as an argument, is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddThrowsExceptionWhenMetricIsNull() {
        new HarmonicMean().add(null);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the add methods, when the
     * weight, which is passes as an argument, is not greater than 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testAddThrowsExceptionWhenWeightIsNotGreaterThanZero() {
        new HarmonicMean().add(mock(Metric.class), 0);
    }

    /**
     * Tests the functionality of the evaluate-method, when using equal weights.
     */
    @Test
    public final void testAverageWithEqualWeights() {
        Metric metric = mock(Metric.class);
        when(metric.evaluate(any())).thenReturn(20d, 5d);
        HarmonicMean harmonicMean = new HarmonicMean().add(metric).add(metric);
        assertEquals(8, harmonicMean.evaluate(mock(AssociationRule.class)), 0);
    }

    /**
     * Tests the functionality of the evaluate-method, when using different weights.
     */
    @Test
    public final void testAverageWithDifferentWeights() {
        Metric metric = mock(Metric.class);
        when(metric.evaluate(any())).thenReturn(3d, 6d);
        HarmonicMean harmonicMean = new HarmonicMean().add(metric, 1.0).add(metric, 2.0);
        assertEquals(4.5, harmonicMean.evaluate(mock(AssociationRule.class)), 0);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the evaluate-method, when
     * passing null as a parameter.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testEvaluateThrowsExceptionWhenRuleIsNull() {
        new HarmonicMean().add(mock(Metric.class)).evaluate(null);
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown by the evaluate-method, when no
     * metrics have been added.
     */
    @Test(expected = IllegalStateException.class)
    public final void testEvaluateThrowsExceptionWhenNoMetricsAdded() {
        new HarmonicMean().evaluate(mock(AssociationRule.class));
    }

}
