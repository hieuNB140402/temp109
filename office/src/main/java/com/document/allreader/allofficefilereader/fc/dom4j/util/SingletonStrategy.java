

package com.document.allreader.allofficefilereader.fc.dom4j.util;

/**
 * <p>
 * <code>SingletonStrategy</code> is an interface used to provide common
 * factory access for the same object based on an implementation strategy for
 * singleton. Right now there are two that accompany this interface:
 * SimpleSingleton and PerThreadSingleton. This will replace previous usage of
 * ThreadLocal to allow for alternate strategies.
 * </p>
 * 
 * @author <a href="mailto:ddlucas@users.sourceforge.net">David Lucas </a>
 * @version $Revision: 1.2 $
 */
public interface SingletonStrategy {
    /**
     * return a singleton instance of the class specified in setSingletonClass
     */
    Object instance();

    /**
     * reset the instance to a new instance for the implemented strategy
     */
    void reset();

    /**
     * set a singleton class name that will be used to create the singleton
     * based on the strategy implementation of this interface. The default
     * constructor of the class will be used and must be public.
     */
    void setSingletonClassName(String singletonClassName);
}

