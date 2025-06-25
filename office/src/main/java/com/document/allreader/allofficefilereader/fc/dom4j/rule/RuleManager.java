

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import java.util.HashMap;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.Node;
import com.document.allreader.allofficefilereader.fc.dom4j.rule.pattern.NodeTypePattern;


/**
 * <p>
 * <code>RuleManager</code> manages a set of rules such that a rule can be
 * found for a given DOM4J Node using the XSLT processing model.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class RuleManager
{
    /** Map of modes indexed by mode */
    private HashMap modes = new HashMap();

    /**
     * A counter so that rules can be ordered by the order in which they were
     * added to the rule base
     */
    private int appearenceCount;

    /** Holds value of property valueOfAction. */
    private Action valueOfAction;

    public RuleManager()
    {
    }

    /**
     * DOCUMENT ME!
     * 
     * @param modeName
     *            DOCUMENT ME!
     * 
     * @return the Mode instance for the given mode name. If one does not exist
     *         then it will be created.
     */
    public Mode getMode(String modeName)
    {
        Mode mode = (Mode)modes.get(modeName);

        if (mode == null)
        {
            mode = createMode();
            modes.put(modeName, mode);
        }

        return mode;
    }

    public void addRule(Rule rule)
    {
        rule.setAppearenceCount(++appearenceCount);

        Mode mode = getMode(rule.getMode());
        Rule[] childRules = rule.getUnionRules();

        if (childRules != null)
        {
            for (int i = 0, size = childRules.length; i < size; i++)
            {
                mode.addRule(childRules[i]);
            }
        }
        else
        {
            mode.addRule(rule);
        }
    }

    public void removeRule(Rule rule)
    {
        Mode mode = getMode(rule.getMode());
        Rule[] childRules = rule.getUnionRules();

        if (childRules != null)
        {
            for (int i = 0, size = childRules.length; i < size; i++)
            {
                mode.removeRule(childRules[i]);
            }
        }
        else
        {
            mode.removeRule(rule);
        }
    }

    /**
     * Performs an XSLT processing model match for the rule which matches the
     * given Node the best.
     * 
     * @param modeName
     *            is the name of the mode associated with the rule if any
     * @param node
     *            is the DOM4J Node to match against
     * 
     * @return the matching Rule or no rule if none matched
     */
    public Rule getMatchingRule(String modeName, Node node)
    {
        Mode mode = (Mode)modes.get(modeName);

        if (mode != null)
        {
            return mode.getMatchingRule(node);
        }
        else
        {
            System.out.println("Warning: No Mode for mode: " + mode);

            return null;
        }
    }

    public void clear()
    {
        modes.clear();
        appearenceCount = 0;
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the default value-of action which is used in the default rules
     *         for the pattern "text()|&#64;"
     */
    public Action getValueOfAction()
    {
        return valueOfAction;
    }

    /**
     * Sets the default value-of action which is used in the default rules for
     * the pattern "text()|&#64;"
     * 
     * @param valueOfAction
     *            DOCUMENT ME!
     */
    public void setValueOfAction(Action valueOfAction)
    {
        this.valueOfAction = valueOfAction;
    }


    // -------------------------------------------------------------------------

    /**
     * A factory method to return a new {@link Mode}instance which should add
     * the necessary default rules
     * 
     * @return DOCUMENT ME!
     */
    protected Mode createMode()
    {
        Mode mode = new Mode();
        addDefaultRules(mode);

        return mode;
    }

    /**
     * Adds the default stylesheet rules to the given {@link Mode}instance
     * 
     * @param mode
     *            DOCUMENT ME!
     */
    protected void addDefaultRules(final Mode mode)
    {
        // add an apply templates rule
        Action applyTemplates = new Action()
        {
            public void run(Node node) throws Exception
            {
                if (node instanceof Element)
                {
                    mode.applyTemplates((Element)node);
                }
                else if (node instanceof Document)
                {
                    mode.applyTemplates((Document)node);
                }
            }
        };

        Action valueOf = getValueOfAction();

        addDefaultRule(mode, NodeTypePattern.ANY_DOCUMENT, applyTemplates);
        addDefaultRule(mode, NodeTypePattern.ANY_ELEMENT, applyTemplates);

        if (valueOf != null)
        {
            addDefaultRule(mode, NodeTypePattern.ANY_ATTRIBUTE, valueOf);
            addDefaultRule(mode, NodeTypePattern.ANY_TEXT, valueOf);
        }
    }

    protected void addDefaultRule(Mode mode, Pattern pattern, Action action)
    {
        Rule rule = createDefaultRule(pattern, action);
        mode.addRule(rule);
    }

    protected Rule createDefaultRule(Pattern pattern, Action action)
    {
        Rule rule = new Rule(pattern, action);
        rule.setImportPrecedence(-1);

        return rule;
    }
}

