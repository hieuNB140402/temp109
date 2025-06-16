

package com.document.allreader.allofficefilereader.fc.dom4j;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

/**
 * <p>
 * <code>XPath</code> represents an XPath expression after it has been parsed
 * from a String.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.20 $
 */
public interface XPath extends NodeFilter
{
    /**
     * <p>
     * <code>getText</code> will return the textual version of the XPath
     * expression.
     * </p>
     * 
     * @return the textual format of the XPath expression.
     */
    String getText();

    /**
     * <p>
     * <code>matches</code> returns true if the given node matches the XPath
     * expression. To be more precise when evaluating this XPath expression on
     * the given node the result set must include the node.
     * </p>
     * 
     * @param node
     *            DOCUMENT ME!
     * 
     * @return true if the given node matches this XPath expression
     */
    boolean matches(Node node);

    /**
     * <p>
     * <code>evaluate</code> evaluates an XPath expression and returns the
     * result as an {@link Object}. The object returned can either be a {@link
     * List} of {@link Node}instances, a {@link Node}instance, a {@link
     * String} or a {@link Number}instance depending on the XPath expression.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return the value of the XPath expression as a {@link List}of {@link
     *         Node} instances, a {@link Node}instance, a {@link String}or a
     *         {@link Number}instance depending on the XPath expression.
     */
    Object evaluate(Object context);

    /**
     * <p>
     * <code>selectObject</code> evaluates an XPath expression and returns the
     * result as an {@link Object}. The object returned can either be a {@link
     * List} of {@link Node}instances, a {@link Node}instance, a {@link
     * String} or a {@link Number}instance depending on the XPath expression.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return the value of the XPath expression as a {@link List}of {@link
     *         Node} instances, a {@link Node}instance, a {@link String}or a
     *         {@link Number}instance depending on the XPath expression.
     * 
     * @deprecated please use evaluate(Object) instead. WILL BE REMOVED IN
     *             dom4j-1.6 !!
     */
    Object selectObject(Object context);

    /**
     * <p>
     * <code>selectNodes</code> performs this XPath expression on the given
     * {@link Node}or {@link List}of {@link Node}s instances appending all
     * the results together into a single list.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return the results of all the XPath evaluations as a single list
     */
    List selectNodes(Object context);

    /**
     * <p>
     * <code>selectNodes</code> evaluates the XPath expression on the given
     * {@link Node}or {@link List}of {@link Node}s and returns the result as
     * a <code>List</code> of <code>Node</code> s sorted by the sort XPath
     * expression.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * @param sortXPath
     *            is the XPath expression to sort by
     * 
     * @return a list of <code>Node</code> instances
     */
    List selectNodes(Object context, XPath sortXPath);

    /**
     * <p>
     * <code>selectNodes</code> evaluates the XPath expression on the given
     * {@link Node}or {@link List}of {@link Node}s and returns the result as
     * a <code>List</code> of <code>Node</code> s sorted by the sort XPath
     * expression.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * @param sortXPath
     *            is the XPath expression to sort by
     * @param distinct
     *            specifies whether or not duplicate values of the sort
     *            expression are allowed. If this parameter is true then only
     *            distinct sort expressions values are included in the result
     * 
     * @return a list of <code>Node</code> instances
     */
    List selectNodes(Object context, XPath sortXPath, boolean distinct);

    /**
     * <p>
     * <code>selectSingleNode</code> evaluates this XPath expression on the
     * given {@link Node}or {@link List}of {@link Node}s and returns the
     * result as a single <code>Node</code> instance.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return a single matching <code>Node</code> instance
     */
    Node selectSingleNode(Object context);

    /**
     * <p>
     * <code>valueOf</code> evaluates this XPath expression and returns the
     * textual representation of the results using the XPath string() function.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return the string representation of the results of the XPath expression
     */
    String valueOf(Object context);

    /**
     * <p>
     * <code>numberValueOf</code> evaluates an XPath expression and returns
     * the numeric value of the XPath expression if the XPath expression results
     * is a number, or null if the result is not a number.
     * </p>
     * 
     * @param context
     *            is either a node or a list of nodes on which to evalute the
     *            XPath
     * 
     * @return the numeric result of the XPath expression or null if the result
     *         is not a number.
     */
    Number numberValueOf(Object context);

    /**
     * Retrieve a boolean-value interpretation of this XPath expression when
     * evaluated against a given context.
     * 
     * <p>
     * The boolean-value of the expression is determined per the
     * <code>boolean(..)</code> core function as defined in the XPath
     * specification. This means that an expression that selects zero nodes will
     * return <code>false</code>, while an expression that selects
     * one-or-more nodes will return <code>true</code>.
     * </p>
     * 
     * @param context
     *            The node, nodeset or Context object for evaluation. This value
     *            can be null
     * 
     * @return The boolean-value interpretation of this expression.
     * 
     * @since 1.5
     */
    boolean booleanValueOf(Object context);

    /**
     * <p>
     * <code>sort</code> sorts the given List of Nodes using this XPath
     * expression as a {@link java.util.Comparator}.
     * </p>
     * 
     * @param list
     *            is the list of Nodes to sort
     */
    void sort(List list);

    /**
     * <p>
     * <code>sort</code> sorts the given List of Nodes using this XPath
     * expression as a {@link java.util.Comparator}and optionally removing
     * duplicates.
     * </p>
     * 
     * @param list
     *            is the list of Nodes to sort
     * @param distinct
     *            if true then duplicate values (using the sortXPath for
     *            comparisions) will be removed from the List
     */
    void sort(List list, boolean distinct);

    /**
     * DOCUMENT ME!
     * 
     * @return the current function context
     * /
    FunctionContext getFunctionContext();

    /**
     * Sets the function context to be used when evaluating XPath expressions
     * 
     * @param functionContext
     *            DOCUMENT ME!
     * /
    void setFunctionContext(FunctionContext functionContext);

    /**
     * DOCUMENT ME!
     * 
     * @return the current namespace context
     */
    NamespaceContext getNamespaceContext();

    /**
     * Sets the namespace context to be used when evaluating XPath expressions
     * 
     * @param namespaceContext
     *            DOCUMENT ME!
     */
    void setNamespaceContext(NamespaceContext namespaceContext);

    /**
     * <p>
     * Sets the current NamespaceContext from a Map where the keys are the
     * String namespace prefixes and the values are the namespace URIs.
     * </p>
     * 
     * <p>
     * For example:
     * 
     * <pre>
     * Map uris = new HashMap();
     * uris.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
     * uris.put("m", "urn:xmethodsBabelFish");
     * XPath xpath = document
     *         .createXPath("SOAP-ENV:Envelope/SOAP-ENV:Body/m:BabelFish");
     * xpath.setNamespaceURIs(uris);
     * Node babelfish = xpath.selectSingleNode(document);
     * </pre>
     * 
     * </p>
     * 
     * @param map
     *            the map containing the namespace mappings
     */
    void setNamespaceURIs(Map map);

    /**
     * DOCUMENT ME!
     * 
     * @return the current variable context
     */
    //VariableContext getVariableContext();

    /**
     * Sets the variable context to be used when evaluating XPath expressions
     * 
     * @param variableContext
     *            DOCUMENT ME!
     */
    //void setVariableContext(VariableContext variableContext);
}

