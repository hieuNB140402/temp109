

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.util.ArrayList;
import java.util.HashMap;

import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementPath;


class DispatchHandler implements ElementHandler
{

    private boolean atRoot;

    private String path;

    private ArrayList pathStack;

    private ArrayList handlerStack;

    private HashMap handlers;


    private ElementHandler defaultHandler;

    public DispatchHandler()
    {
        atRoot = true;
        path = "/";
        pathStack = new ArrayList();
        handlerStack = new ArrayList();
        handlers = new HashMap();
    }


    public void addHandler(String handlerPath, ElementHandler handler)
    {
        handlers.put(handlerPath, handler);
    }


    public ElementHandler removeHandler(String handlerPath)
    {
        return (ElementHandler)handlers.remove(handlerPath);
    }


    public boolean containsHandler(String handlerPath)
    {
        return handlers.containsKey(handlerPath);
    }


    public ElementHandler getHandler(String handlerPath)
    {
        return (ElementHandler)handlers.get(handlerPath);
    }


    public int getActiveHandlerCount()
    {
        return handlerStack.size();
    }


    public void setDefaultHandler(ElementHandler handler)
    {
        defaultHandler = handler;
    }


    public void resetHandlers()
    {
        atRoot = true;
        path = "/";
        pathStack.clear();
        handlerStack.clear();
        handlers.clear();
        defaultHandler = null;
    }


    public String getPath()
    {
        return path;
    }


    public void onStart(ElementPath elementPath)
    {
        Element element = elementPath.getCurrent();


        pathStack.add(path);

        if (atRoot)
        {
            path = path + element.getName();
            atRoot = false;
        }
        else
        {
            path = path + "/" + element.getName();
        }

        if ((handlers != null) && (handlers.containsKey(path)))
        {
            ElementHandler handler = (ElementHandler)handlers.get(path);
            handlerStack.add(handler);


            handler.onStart(elementPath);
        }
        else
        {
            if (handlerStack.isEmpty() && (defaultHandler != null))
            {
                defaultHandler.onStart(elementPath);
            }
        }
    }

    public void onEnd(ElementPath elementPath)
    {
        if ((handlers != null) && (handlers.containsKey(path)))
        {
            ElementHandler handler = (ElementHandler)handlers.get(path);
            handlerStack.remove(handlerStack.size() - 1);

            handler.onEnd(elementPath);
        }
        else
        {
            if (handlerStack.isEmpty() && (defaultHandler != null))
            {
                defaultHandler.onEnd(elementPath);
            }
        }

        path = (String)pathStack.remove(pathStack.size() - 1);

        if (pathStack.size() == 0)
        {
            atRoot = true;
        }
    }
}

