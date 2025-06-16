

package com.document.allreader.allofficefilereader.fc.dom4j.util;

/**
 * <p>
 * <code>SimpleSingleton</code> is an implementation of the SingletonStrategy
 * interface used to provide common factory access for the same object instance.
 * This implementation will create a new instance from the class specified and
 * will not create a new one unless it is reset.
 * </p>
 * 
 * @author <a href="mailto:ddlucas@users.sourceforge.net">David Lucas </a>
 * @version $Revision: 1.3 $
 */

public class SimpleSingleton implements SingletonStrategy
{
    private String singletonClassName = null;

    private Object singletonInstance = null;

    public SimpleSingleton()
    {
    }

    public Object instance()
    {
        return singletonInstance;
    }

    public void reset()
    {
        if (singletonClassName != null)
        {
            Class clazz = null;
            try
            {
                clazz = Thread.currentThread().getContextClassLoader()
                    .loadClass(singletonClassName);
                singletonInstance = clazz.newInstance();
            }
            catch(Exception ignore)
            {
                try
                {
                    clazz = Class.forName(singletonClassName);
                    singletonInstance = clazz.newInstance();
                }
                catch(Exception ignore2)
                {
                }
            }

        }
    }

    public void setSingletonClassName(String singletonClassName)
    {
        this.singletonClassName = singletonClassName;
        reset();
    }

}

