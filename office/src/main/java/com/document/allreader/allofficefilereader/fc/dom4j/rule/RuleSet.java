

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import java.util.ArrayList;
import java.util.Collections;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;


/**
 * <p>
 * <code>RuleSet</code> manages a set of rules which are sorted in order of
 * relevance according to the XSLT defined conflict resolution policy. This
 * makes finding the correct rule for a DOM4J Node using the XSLT processing
 * model efficient as the rules can be evaluated in order of priority.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class RuleSet
{
    /** An unordered list of Rule objects */
    private ArrayList rules = new ArrayList();

    /** A lazily evaluated and cached array of rules sorted */
    private Rule[] ruleArray;

    public RuleSet()
    {
    }

    public String toString()
    {
        return super.toString() + " [RuleSet: " + rules + " ]";
    }

    /**
     * Performs an XSLT processing model match for the rule which matches the
     * given Node the best.
     * 
     * @param node
     *            is the DOM4J Node to match against
     * 
     * @return the matching Rule or no rule if none matched
     */
    public Rule getMatchingRule(Node node)
    {
        Rule[] matches = getRuleArray();

        for (int i = matches.length - 1; i >= 0; i--)
        {
            Rule rule = matches[i];

            if (rule.matches(node))
            {
                return rule;
            }
        }

        return null;
    }

    public void addRule(Rule rule)
    {
        rules.add(rule);
        ruleArray = null;
    }

    public void removeRule(Rule rule)
    {
        rules.remove(rule);
        ruleArray = null;
    }

    /**
     * Adds all the rules to this RuleSet from the given other rule set.
     * 
     * @param that
     *            DOCUMENT ME!
     */
    public void addAll(RuleSet that)
    {
        rules.addAll(that.rules);
        ruleArray = null;
    }

    /**
     * Returns an array of sorted rules.
     * 
     * @return the rules as a sorted array in ascending precendence so that the
     *         rules at the end of the array should be used first
     */
    protected Rule[] getRuleArray()
    {
        if (ruleArray == null)
        {
            Collections.sort(rules);

            int size = rules.size();
            ruleArray = new Rule[size];
            rules.toArray(ruleArray);
        }

        return ruleArray;
    }
}

