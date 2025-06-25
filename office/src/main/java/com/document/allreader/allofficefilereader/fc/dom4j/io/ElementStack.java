

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementPath;

class ElementStack implements ElementPath
{

    protected Element[] stack;

    protected int lastElementIndex = -1;

    private DispatchHandler handler = null;

    public ElementStack()
    {
        this(50);
    }

    public ElementStack(int defaultCapacity)
    {
        stack = new Element[defaultCapacity];
    }

    public void setDispatchHandler(DispatchHandler dispatchHandler)
    {
        this.handler = dispatchHandler;
    }

    public DispatchHandler getDispatchHandler()
    {
        return this.handler;
    }


    public void clear()
    {
        lastElementIndex = -1;
    }


    public Element peekElement()
    {
        if (lastElementIndex < 0)
        {
            return null;
        }

        return stack[lastElementIndex];
    }


    public Element popElement()
    {
        if (lastElementIndex < 0)
        {
            return null;
        }

        return stack[lastElementIndex--];
    }

    public void pushElement(Element element)
    {
        int length = stack.length;

        if (++lastElementIndex >= length)
        {
            reallocate(length * 2);
        }

        stack[lastElementIndex] = element;
    }


    protected void reallocate(int size)
    {
        Element[] oldStack = stack;
        stack = new Element[size];
        System.arraycopy(oldStack, 0, stack, 0, oldStack.length);
    }


    public int size()
    {
        return lastElementIndex + 1;
    }

    public Element getElement(int depth)
    {
        Element element;

        try
        {
            element = (Element)stack[depth];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            element = null;
        }

        return element;
    }

    public String getPath()
    {
        if (handler == null)
        {
            setDispatchHandler(new DispatchHandler());
        }

        return handler.getPath();
    }

    public Element getCurrent()
    {
        return peekElement();
    }

    public void addHandler(String path, ElementHandler elementHandler)
    {
        this.handler.addHandler(getHandlerPath(path), elementHandler);
    }

    public void removeHandler(String path)
    {
        this.handler.removeHandler(getHandlerPath(path));
    }


    public boolean containsHandler(String path)
    {
        return this.handler.containsHandler(path);
    }

    private String getHandlerPath(String path)
    {
        String handlerPath;

        if (this.handler == null)
        {
            setDispatchHandler(new DispatchHandler());
        }

        if (path.startsWith("/"))
        {
            handlerPath = path;
        }
        else if (getPath().equals("/"))
        {
            handlerPath = getPath() + path;
        }
        else
        {
            handlerPath = getPath() + "/" + path;
        }

        return handlerPath;
    }
}

