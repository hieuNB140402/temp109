

package com.document.allreader.allofficefilereader.fc.openxml4j.util;

/**
 * An immutable object that could be defined as null.
 *
 * @author Julien Chable
 * @version 0.9
 */
public final class Nullable<E>
{

    private E value;

    /**
     * Constructor.
     */
    public Nullable()
    {
        // Do nothing
    }

    /**
     * Constructor.
     *
     * @param value
     *            The value to set to this nullable.
     */
    public Nullable(E value)
    {
        this.value = value;
    }

    /**
     * Get the store value if any.
     *
     * @return the store value
     */
    public E getValue()
    {
        return value;
    }

    /**
     * Get the status of this nullable.
     *
     * @return <b>true</b> if the nullable store a value (empty string is
     *         considered to be a value) else <b>false</>.
     */
    public boolean hasValue()
    {
        return value != null;
    }

    /**
     * Set the stored value to <i>null</i>.
     */
    public void nullify()
    {
        value = null;
    }
}
