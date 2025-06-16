

package com.document.allreader.allofficefilereader.fc.dom4j.tree;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.dom4j.Namespace;


/**
 * <p>
 * <code>NamespaceCache</code> caches instances of
 * <code>DefaultNamespace</code> for reuse both across documents and within
 * documents.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Maarten Coene
 * @author Brett Finnell
 * @version $Revision: 1.15 $
 */
public class NamespaceCache
{
    private static final String CONCURRENTREADERHASHMAP_CLASS = "EDU.oswego.cs.dl.util.concurrent.ConcurrentReaderHashMap";

    /**
     * Cache of {@link Map}instances indexed by URI which contain caches of
     * {@link Namespace}for each prefix
     */
    protected static Map cache;

    /**
     * Cache of {@link Namespace}instances indexed by URI for default
     * namespaces with no prefixes
     */
    protected static Map noPrefixCache;

    static
    {
        /* Try the java.util.concurrent.ConcurrentHashMap first. */
        try
        {
            Class clazz = Class.forName("java.util.concurrent.ConcurrentHashMap");
            Constructor construct = clazz.getConstructor(new Class[]{Integer.TYPE, Float.TYPE,
                Integer.TYPE});
            cache = (Map)construct.newInstance(new Object[]{new Integer(11), new Float(0.75f),
                new Integer(1)});
            noPrefixCache = (Map)construct.newInstance(new Object[]{new Integer(11),
                new Float(0.75f), new Integer(1)});
        }
        catch(Throwable t1)
        {
            /* Try to use the util.concurrent library (if in classpath) */
            try
            {
                Class clazz = Class.forName(CONCURRENTREADERHASHMAP_CLASS);
                cache = (Map)clazz.newInstance();
                noPrefixCache = (Map)clazz.newInstance();
            }
            catch(Throwable t2)
            {
                /* If previous implementations fail, use internal one */
                cache = new ConcurrentReaderHashMap();
                noPrefixCache = new ConcurrentReaderHashMap();
            }
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the namespace for the given prefix and uri
     */
    public Namespace get(String prefix, String uri)
    {
        Map uriCache = getURICache(uri);
        WeakReference ref = (WeakReference)uriCache.get(prefix);
        Namespace answer = null;

        if (ref != null)
        {
            answer = (Namespace)ref.get();
        }

        if (answer == null)
        {
            synchronized(uriCache)
            {
                ref = (WeakReference)uriCache.get(prefix);

                if (ref != null)
                {
                    answer = (Namespace)ref.get();
                }

                if (answer == null)
                {
                    answer = createNamespace(prefix, uri);
                    uriCache.put(prefix, new WeakReference(answer));
                }
            }
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the name model for the given name and namepsace
     */
    public Namespace get(String uri)
    {
        WeakReference ref = (WeakReference)noPrefixCache.get(uri);
        Namespace answer = null;

        if (ref != null)
        {
            answer = (Namespace)ref.get();
        }

        if (answer == null)
        {
            synchronized(noPrefixCache)
            {
                ref = (WeakReference)noPrefixCache.get(uri);

                if (ref != null)
                {
                    answer = (Namespace)ref.get();
                }

                if (answer == null)
                {
                    answer = createNamespace("", uri);
                    noPrefixCache.put(uri, new WeakReference(answer));
                }
            }
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the cache for the given namespace URI. If one does not currently
     *         exist it is created.
     */
    protected Map getURICache(String uri)
    {
        Map answer = (Map)cache.get(uri);

        if (answer == null)
        {
            synchronized(cache)
            {
                answer = (Map)cache.get(uri);

                if (answer == null)
                {
                    answer = new ConcurrentReaderHashMap();
                    cache.put(uri, answer);
                }
            }
        }

        return answer;
    }

    /**
     * A factory method to create {@link Namespace}instance
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return a newly created {@link Namespace}instance.
     */
    protected Namespace createNamespace(String prefix, String uri)
    {
        return new Namespace(prefix, uri);
    }
}

