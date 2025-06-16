

package com.document.allreader.allofficefilereader.fc.dom4j.rule;

import com.document.allreader.allofficefilereader.fc.dom4j.Node;

/**
 * <p>
 * <code>Rule</code> matches against DOM4J Node so that some action can be
 * performed such as in the XSLT processing model.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class Rule implements Comparable
{
    /** Holds value of property mode. */
    private String mode;

    /** Holds value of property importPrecedence. */
    private int importPrecedence;

    /** Holds value of property priority. */
    private double priority;

    /** Holds value of property appearenceCount. */
    private int appearenceCount;

    /** Holds value of property pattern. */
    private Pattern pattern;

    /** Holds value of property action. */
    private Action action;

    public Rule()
    {
        this.priority = Pattern.DEFAULT_PRIORITY;
    }

    public Rule(Pattern pattern)
    {
        this.pattern = pattern;
        this.priority = pattern.getPriority();
    }

    public Rule(Pattern pattern, Action action)
    {
        this(pattern);
        this.action = action;
    }

    /**
     * Constructs a new Rule with the same instance data as the given rule but a
     * different pattern.
     * 
     * @param that
     *            DOCUMENT ME!
     * @param pattern
     *            DOCUMENT ME!
     */
    public Rule(Rule that, Pattern pattern)
    {
        this.mode = that.mode;
        this.importPrecedence = that.importPrecedence;
        this.priority = that.priority;
        this.appearenceCount = that.appearenceCount;
        this.action = that.action;
        this.pattern = pattern;
    }

    public boolean equals(Object that)
    {
        if (that instanceof Rule)
        {
            return compareTo((Rule)that) == 0;
        }

        return false;
    }

    public int hashCode()
    {
        return importPrecedence + appearenceCount;
    }

    public int compareTo(Object that)
    {
        if (that instanceof Rule)
        {
            return compareTo((Rule)that);
        }

        return getClass().getName().compareTo(that.getClass().getName());
    }

    /**
     * Compares two rules in XSLT processing model order assuming that the modes
     * are equal.
     * 
     * @param that
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public int compareTo(Rule that)
    {
        int answer = this.importPrecedence - that.importPrecedence;

        if (answer == 0)
        {
            answer = (int)Math.round(this.priority - that.priority);

            if (answer == 0)
            {
                answer = this.appearenceCount - that.appearenceCount;
            }
        }

        return answer;
    }

    public String toString()
    {
        return super.toString() + "[ pattern: " + getPattern() + " action: " + getAction() + " ]";
    }

    /**
     * DOCUMENT ME!
     * 
     * @param node
     *            DOCUMENT ME!
     * 
     * @return true if the pattern matches the given DOM4J node.
     */
    public final boolean matches(Node node)
    {
        return pattern.matches(node);
    }

    /**
     * If this rule contains a union pattern then this method should return an
     * array of Rules which describe the union rule, which should contain more
     * than one rule. Otherwise this method should return null.
     * 
     * @return an array of the rules which make up this union rule or null if
     *         this rule is not a union rule
     */
    public Rule[] getUnionRules()
    {
        Pattern[] patterns = pattern.getUnionPatterns();

        if (patterns == null)
        {
            return null;
        }

        int size = patterns.length;
        Rule[] answer = new Rule[size];

        for (int i = 0; i < size; i++)
        {
            answer[i] = new Rule(this, patterns[i]);
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the type of node the pattern matches which by default should
     *         return ANY_NODE if it can match any kind of node.
     */
    public final short getMatchType()
    {
        return pattern.getMatchType();
    }

    /**
     * For patterns which only match an ATTRIBUTE_NODE or an ELEMENT_NODE then
     * this pattern may return the name of the element or attribute it matches.
     * This allows a more efficient rule matching algorithm to be performed,
     * rather than a brute force approach of evaluating every pattern for a
     * given Node.
     * 
     * @return the name of the element or attribute this pattern matches or null
     *         if this pattern matches any or more than one name.
     */
    public final String getMatchesNodeName()
    {
        return pattern.getMatchesNodeName();
    }

    /**
     * Getter for property mode.
     * 
     * @return Value of property mode.
     */
    public String getMode()
    {
        return mode;
    }

    /**
     * Setter for property mode.
     * 
     * @param mode
     *            New value of property mode.
     */
    public void setMode(String mode)
    {
        this.mode = mode;
    }

    /**
     * Getter for property importPrecedence.
     * 
     * @return Value of property importPrecedence.
     */
    public int getImportPrecedence()
    {
        return importPrecedence;
    }

    /**
     * Setter for property importPrecedence.
     * 
     * @param importPrecedence
     *            New value of property importPrecedence.
     */
    public void setImportPrecedence(int importPrecedence)
    {
        this.importPrecedence = importPrecedence;
    }

    /**
     * Getter for property priority.
     * 
     * @return Value of property priority.
     */
    public double getPriority()
    {
        return priority;
    }

    /**
     * Setter for property priority.
     * 
     * @param priority
     *            New value of property priority.
     */
    public void setPriority(double priority)
    {
        this.priority = priority;
    }

    /**
     * Getter for property appearenceCount.
     * 
     * @return Value of property appearenceCount.
     */
    public int getAppearenceCount()
    {
        return appearenceCount;
    }

    /**
     * Setter for property appearenceCount.
     * 
     * @param appearenceCount
     *            New value of property appearenceCount.
     */
    public void setAppearenceCount(int appearenceCount)
    {
        this.appearenceCount = appearenceCount;
    }

    /**
     * Getter for property pattern.
     * 
     * @return Value of property pattern.
     */
    public Pattern getPattern()
    {
        return pattern;
    }

    /**
     * Setter for property pattern.
     * 
     * @param pattern
     *            New value of property pattern.
     */
    public void setPattern(Pattern pattern)
    {
        this.pattern = pattern;
    }

    /**
     * Getter for property action.
     * 
     * @return Value of property action.
     */
    public Action getAction()
    {
        return action;
    }

    /**
     * Setter for property action.
     * 
     * @param action
     *            New value of property action.
     */
    public void setAction(Action action)
    {
        this.action = action;
    }
}

