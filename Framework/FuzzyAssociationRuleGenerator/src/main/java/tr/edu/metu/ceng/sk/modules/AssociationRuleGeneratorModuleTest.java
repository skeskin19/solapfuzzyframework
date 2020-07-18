
package tr.edu.metu.ceng.sk.modules;

import de.mrapp.apriori.AssociationRule;
import de.mrapp.apriori.ItemSet;
import de.mrapp.apriori.RuleSet;
import de.mrapp.apriori.metrics.Confidence;
import de.mrapp.apriori.metrics.Leverage;
import de.mrapp.apriori.metrics.Lift;
import de.mrapp.apriori.modules.AssociationRuleGeneratorModule;
import de.mrapp.apriori.modules.FrequentItemSetMinerModule;
import tr.edu.metu.ceng.sk.*;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AssociationRuleGeneratorModuleTest extends AbstractDataTest {

    /**
     * The association rules, which result from the frequent item sets, which are contained by the
     * first input file.
     */
    private static final String[] RULES_1 = {"[milk] -> [coffee]", "[bread] -> [sugar]", "[coffee] -> [milk]", "[coffee, sugar] -> [milk]", "[milk, sugar] -> [coffee]"};

    /**
     * The supports of the rules, which result from the frequent item sets, which are contained by
     * the first input file.
     */
    private static final double[] RULE_SUPPORTS_1 = {0.75, 0.5, 0.75, 0.5, 0.5};

    /**
     * The confidences of the rules, which result from the frequent item sets, which are contained
     * by the first input file.
     */
    private static final double[] RULE_CONFIDENCES_1 = {1.0, 1.0, 1.0, 1.0, 1.0};

    /**
     * The lifts of the rules, which result from the frequent item sets, which are contained by
     * the first input file.
     */
    private static final double[] RULE_LIFTS_1 = {1.33, 1.33, 1.33, 1.33, 1.33};

    /**
     * The leverages of the rules, which result from the frequent item sets, which are contained by
     * the first input file.
     */
    private static final double[] RULE_LEVERAGES_1 = {0.19, 0.12, 0.19, 0.12, 0.12};


    /**
     * The association rules, which result from the frequent item sets, which are contained by the
     * second input file.
     */
    private static final String[] RULES_2 = {"[beer] -> [chips]", "[beer, wine] -> [chips]", "[chips, wine] -> [beer]"};

    /**
     * The supports of the rules, which result from the frequent item sets, which are contained by
     * the second input file.
     */
    private static final double[] RULE_SUPPORTS_2 = {0.5, 0.25, 0.25};

    /**
     * The confidences of the rules, which result from the frequent item sets, which are contained
     * by the second input file.
     */
    private static final double[] RULE_CONFIDENCES_2 = {1.0, 1.0, 1.0};

    /**
     * The lifts of the rules, which result from the frequent item sets, which are contained by
     * the first second file.
     */
    private static final double[] RULE_LIFTS_2 = {1.33, 1.33, 2.0};

    /**
     * The leverages of the rules, which result from the frequent item sets, which are contained by
     * the first second file.
     */
    private static final double[] RULE_LEVERAGES_2 = {0.12, 0.06, 0.12};

    /**
     * Creates and returns a map, which contains the frequent item sets as returned by the method
     * {@link FrequentItemSetMinerModule#findFrequentItemSets(Iterator, double)}.
     *
     * @param frequentItemSets The frequent item sets, which should be added to the map, as a
     *                         two-dimensional {@link String} array. The array may not be null
     * @param supports         The supports of the frequent item sets, which should be added to the
     *                         map, as a {@link Double} array. The array may not be null
     * @return The map, which has been created, as an instance of the type {@link Map}. The map may
     * not be null
     */
    @NotNull
    private Map<Integer, ItemSet<NamedItem>> createFrequentItemSets(
            @NotNull final String[][] frequentItemSets, @NotNull final double[] supports) {
        Map<Integer, ItemSet<NamedItem>> map = new HashMap<>();
        int index = 0;

        for (String[] frequentItemSet : frequentItemSets) {
            ItemSet<NamedItem> itemSet = new ItemSet<>();
            double support = supports[index];
            itemSet.setSupport(support);

            for (String item : frequentItemSet) {
                NamedItem namedItem = new NamedItem(item);
                itemSet.add(namedItem);
            }

            map.put(itemSet.hashCode(), itemSet);
            index++;
        }

        return map;
    }

    /**
     * Tests the functionality of the method, which allows to generate association rules from
     * frequent item sets.
     *
     * @param frequentItemSets  The frequent item sets, the association rules should be generated
     *                          from, as a two-dimensional {@link String} array. The array may not
     *                          be null
     * @param supports          The supports of the frequent item sets, the association rules should
     *                          be generated from, as a {@link Double} array. The array may not be
     *                          null
     * @param minConfidence     The confidence, association rules must at least reach to be
     *                          considered "interesting", as a {@link Double} value
     * @param actualRules       The actual rules, which can be generated from the frequent item
     *                          sets, as a {@link String} array. The array may not be null
     * @param actualSupports    The actual supports of the rules, which can be generated from the
     *                          frequent item sets, as a {@link Double} array. The array may not be
     *                          null
     * @param actualConfidences The actual confidences of the rules, which can be generated from the
     *                          frequent item sets, as a {@link Double} array. The array may not be
     *                          null
     * @param actualLifts       The actual lifts of the rules, which can be generated from the
     *                          frequent item sets, as a {@link Double} array. The array may not be
     *                          null
     * @param actualLeverages   The actual leverages of the rules, which can be generated from the
     *                          frequent item sets, as a {@link Double} array. The array may not be
     *                          null
     */
    private void testGenerateAssociationRules(@NotNull final String[][] frequentItemSets,
                                              @NotNull final double[] supports,
                                              final double minConfidence,
                                              @NotNull final String[] actualRules,
                                              @NotNull final double[] actualSupports,
                                              @NotNull final double[] actualConfidences,
                                              @NotNull final double[] actualLifts,
                                              @NotNull final double[] actualLeverages) {
        AssociationRuleGeneratorModule<NamedItem> associationRuleGenerator = new AssociationRuleGeneratorModule<>();
        Map<Integer, ItemSet<NamedItem>> map = createFrequentItemSets(
                frequentItemSets, supports);
        RuleSet<NamedItem> ruleSet = associationRuleGenerator
                .generateAssociationRules(map, minConfidence);
        int ruleCount = 0;
        int index = 0;

        for (AssociationRule<NamedItem> rule : ruleSet) {
            assertEquals(actualRules[index], rule.toString());
            assertEquals(actualSupports[index], rule.getSupport(), 0);
            assertEquals(actualConfidences[index], new Confidence().evaluate(rule), 0);
            assertEquals(actualLifts[index], new Lift().evaluate(rule), 0.01);
            assertEquals(actualLeverages[index], new Leverage().evaluate(rule), 0.01);
            index++;
            ruleCount++;
        }

        assertEquals(actualRules.length, ruleCount);
    }

    /**
     * Tests the functionality of the method, which allows to generate association rules, when using
     * the frequent item sets, which are contained by the first input file.
     */
    @Test
    public final void testGenerateAssociationRules1() {
        testGenerateAssociationRules(FREQUENT_ITEM_SETS_1, SUPPORTS_1, 1.0, RULES_1,
                RULE_SUPPORTS_1, RULE_CONFIDENCES_1, RULE_LIFTS_1, RULE_LEVERAGES_1);
    }

    /**
     * Tests the functionality of the method, which allows to generate association rules, when using
     * the frequent item sets, which are contained by the second input file.
     */
    @Test
    public final void testGenerateAssociationRules2() {
        testGenerateAssociationRules(FREQUENT_ITEM_SETS_2, SUPPORTS_2, 0.75, RULES_2,
                RULE_SUPPORTS_2, RULE_CONFIDENCES_2, RULE_LIFTS_2, RULE_LEVERAGES_2);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * generate association rules, if the iterator, which is passed as a parameter, is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGenerateAssociationRulesThrowsExceptionWhenIteratorIsNull() {
        new AssociationRuleGeneratorModule<>().generateAssociationRules(null, 0.5);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * generate association rules, if the minimum confidence, which is passed as a parameter, is
     * less than 0.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGenerateAssociationRulesThrowsExceptionWhenMinConfidenceIsLessThanZero() {
        new AssociationRuleGeneratorModule<>().generateAssociationRules(new HashMap<>(), -0.1);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * generate association rules, if the minimum confidence, which is passed as a parameter, is
     * greater than 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGenerateAssociationRulesThrowsExceptionWhenMinConfidenceIsGreaterThanOne() {
        new AssociationRuleGeneratorModule<>().generateAssociationRules(new HashMap<>(), 1.1);
    }

}
