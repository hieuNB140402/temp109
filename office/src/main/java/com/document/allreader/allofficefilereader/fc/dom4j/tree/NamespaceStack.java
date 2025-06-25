

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.document.allreader.allofficefilereader.fc.dom4j.QName;


/**
 * NamespaceStack implements a stack of namespaces and optionally maintains a
 * cache of all the fully qualified names (<code>QName</code>) which are in
 * scope. This is useful when building or navigating a <i>dom4j </i> document.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.13 $
 */
public class NamespaceStack
{
    /** The factory used to create new <code>Namespace</code> instances */
    private DocumentFactory documentFactory;

    /** The Stack of namespaces */
    private ArrayList namespaceStack = new ArrayList();

    /** The cache of qualifiedNames to QNames per namespace context */
    private ArrayList namespaceCacheList = new ArrayList();

    /**
     * A cache of current namespace context cache of mapping from qualifiedName
     * to QName
     */
    private Map currentNamespaceCache;

    /**
     * A cache of mapping from qualifiedName to QName before any namespaces are
     * declared
     */
    private Map rootNamespaceCache = new HashMap();

    /** Caches the default namespace defined via xmlns="" */
    private Namespace defaultNamespace;

    public NamespaceStack()
    {
        this.documentFactory = DocumentFactory.getInstance();
    }

    public NamespaceStack(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    /**
     * Pushes the given namespace onto the stack so that its prefix becomes
     * available.
     * 
     * @param namespace
     *            is the <code>Namespace</code> to add to the stack.
     */
    public void push(Namespace namespace)
    {
        namespaceStack.add(namespace);
        namespaceCacheList.add(null);
        currentNamespaceCache = null;

        String prefix = namespace.getPrefix();

        if ((prefix == null) || (prefix.length() == 0))
        {
            defaultNamespace = namespace;
        }
    }

    /**
     * Pops the most recently used <code>Namespace</code> from the stack
     * 
     * @return Namespace popped from the stack
     */
    public Namespace pop()
    {
        return remove(namespaceStack.size() - 1);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the number of namespaces on the stackce stack.
     */
    public int size()
    {
        return namespaceStack.size();
    }

    /**
     * Clears the stack
     */
    public void clear()
    {
        namespaceStack.clear();
        namespaceCacheList.clear();
        rootNamespaceCache.clear();
        currentNamespaceCache = null;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param index
     *            DOCUMENT ME!
     * 
     * @return the namespace at the specified index on the stack
     */
    public Namespace getNamespace(int index)
    {
        return (Namespace)namespaceStack.get(index);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            DOCUMENT ME!
     * 
     * @return the namespace for the given prefix or null if it could not be
     *         found.
     */
    public Namespace getNamespaceForPrefix(String prefix)
    {
        if (prefix == null)
        {
            prefix = "";
        }

        for (int i = namespaceStack.size() - 1; i >= 0; i--)
        {
            Namespace namespace = (Namespace)namespaceStack.get(i);

            if (prefix.equals(namespace.getPrefix()))
            {
                return namespace;
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            DOCUMENT ME!
     * 
     * @return the URI for the given prefix or null if it could not be found.
     */
    public String getURI(String prefix)
    {
        Namespace namespace = getNamespaceForPrefix(prefix);

        return (namespace != null) ? namespace.getURI() : null;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param namespace
     *            DOCUMENT ME!
     * 
     * @return true if the given prefix is in the stack.
     */
    public boolean contains(Namespace namespace)
    {
        String prefix = namespace.getPrefix();
        Namespace current = null;

        if ((prefix == null) || (prefix.length() == 0))
        {
            current = getDefaultNamespace();
        }
        else
        {
            current = getNamespaceForPrefix(prefix);
        }

        if (current == null)
        {
            return false;
        }

        if (current == namespace)
        {
            return true;
        }

        return namespace.getURI().equals(current.getURI());
    }

    public QName getQName(String namespaceURI, String localName, String qualifiedName)
    {
        if (localName == null)
        {
            localName = qualifiedName;
        }
        else if (qualifiedName == null)
        {
            qualifiedName = localName;
        }

        if (namespaceURI == null)
        {
            namespaceURI = "";
        }

        String prefix = "";
        int index = qualifiedName.indexOf(":");

        if (index > 0)
        {
            prefix = qualifiedName.substring(0, index);

            if (localName.trim().length() == 0)
            {
                localName = qualifiedName.substring(index + 1);
            }
        }
        else if (localName.trim().length() == 0)
        {
            localName = qualifiedName;
        }

        Namespace namespace = createNamespace(prefix, namespaceURI);

        return pushQName(localName, qualifiedName, namespace, prefix);
    }

    public QName getAttributeQName(String namespaceURI, String localName, String qualifiedName)
    {
        if (qualifiedName == null)
        {
            qualifiedName = localName;
        }

        Map map = getNamespaceCache();
        QName answer = (QName)map.get(qualifiedName);

        if (answer != null)
        {
            return answer;
        }

        if (localName == null)
        {
            localName = qualifiedName;
        }

        if (namespaceURI == null)
        {
            namespaceURI = "";
        }

        Namespace namespace = null;
        String prefix = "";
        int index = qualifiedName.indexOf(":");

        if (index > 0)
        {
            prefix = qualifiedName.substring(0, index);
            namespace = createNamespace(prefix, namespaceURI);

            if (localName.trim().length() == 0)
            {
                localName = qualifiedName.substring(index + 1);
            }
        }
        else
        {
            // attributes with no prefix have no namespace
            namespace = Namespace.NO_NAMESPACE;

            if (localName.trim().length() == 0)
            {
                localName = qualifiedName;
            }
        }

        answer = pushQName(localName, qualifiedName, namespace, prefix);
        map.put(qualifiedName, answer);

        return answer;
    }

    /**
     * Adds a namepace to the stack with the given prefix and URI
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     */
    public void push(String prefix, String uri)
    {
        if (uri == null)
        {
            uri = "";
        }

        Namespace namespace = createNamespace(prefix, uri);
        push(namespace);
    }

    /**
     * Adds a new namespace to the stack
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Namespace addNamespace(String prefix, String uri)
    {
        Namespace namespace = createNamespace(prefix, uri);
        push(namespace);

        return namespace;
    }

    /**
     * Pops a namepace from the stack with the given prefix and URI
     * 
     * @param prefix
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Namespace pop(String prefix)
    {
        if (prefix == null)
        {
            prefix = "";
        }

        Namespace namespace = null;

        for (int i = namespaceStack.size() - 1; i >= 0; i--)
        {
            Namespace ns = (Namespace)namespaceStack.get(i);

            if (prefix.equals(ns.getPrefix()))
            {
                remove(i);
                namespace = ns;

                break;
            }
        }

        if (namespace == null)
        {
            System.out.println("Warning: missing namespace prefix ignored: " + prefix);
        }

        return namespace;
    }

    public String toString()
    {
        return super.toString() + " Stack: " + namespaceStack.toString();
    }

    public DocumentFactory getDocumentFactory()
    {
        return documentFactory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    public Namespace getDefaultNamespace()
    {
        if (defaultNamespace == null)
        {
            defaultNamespace = findDefaultNamespace();
        }

        return defaultNamespace;
    }


    // -------------------------------------------------------------------------

    /**
     * Adds the QName to the stack of available QNames
     * 
     * @param localName
     *            DOCUMENT ME!
     * @param qualifiedName
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * @param prefix
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected QName pushQName(String localName, String qualifiedName, Namespace namespace,
        String prefix)
    {
        if ((prefix == null) || (prefix.length() == 0))
        {
            this.defaultNamespace = null;
        }

        return createQName(localName, qualifiedName, namespace);
    }

    /**
     * Factory method to creeate new QName instances. By default this method
     * interns the QName
     * 
     * @param localName
     *            DOCUMENT ME!
     * @param qualifiedName
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected QName createQName(String localName, String qualifiedName, Namespace namespace)
    {
        return documentFactory.createQName(localName, namespace);
    }

    /**
     * Factory method to creeate new Namespace instances. By default this method
     * interns the Namespace
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param namespaceURI
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected Namespace createNamespace(String prefix, String namespaceURI)
    {
        return documentFactory.createNamespace(prefix, namespaceURI);
    }

    /**
     * Attempts to find the current default namespace on the stack right now or
     * returns null if one could not be found
     * 
     * @return DOCUMENT ME!
     */
    protected Namespace findDefaultNamespace()
    {
        for (int i = namespaceStack.size() - 1; i >= 0; i--)
        {
            Namespace namespace = (Namespace)namespaceStack.get(i);

            if (namespace != null)
            {
                String prefix = namespace.getPrefix();

                if ((prefix == null) || (namespace.getPrefix().length() == 0))
                {
                    return namespace;
                }
            }
        }

        return null;
    }

    /**
     * Removes the namespace at the given index of the stack
     * 
     * @param index
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected Namespace remove(int index)
    {
        Namespace namespace = (Namespace)namespaceStack.remove(index);
        namespaceCacheList.remove(index);
        defaultNamespace = null;
        currentNamespaceCache = null;

        return namespace;
    }

    protected Map getNamespaceCache()
    {
        if (currentNamespaceCache == null)
        {
            int index = namespaceStack.size() - 1;

            if (index < 0)
            {
                currentNamespaceCache = rootNamespaceCache;
            }
            else
            {
                currentNamespaceCache = (Map)namespaceCacheList.get(index);

                if (currentNamespaceCache == null)
                {
                    currentNamespaceCache = new HashMap();
                    namespaceCacheList.set(index, currentNamespaceCache);
                }
            }
        }

        return currentNamespaceCache;
    }
}

