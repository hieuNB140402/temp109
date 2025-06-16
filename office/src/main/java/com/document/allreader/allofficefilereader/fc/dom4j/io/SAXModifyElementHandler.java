

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;
import com.document.allreader.allofficefilereader.fc.dom4j.Element;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementHandler;
import com.document.allreader.allofficefilereader.fc.dom4j.ElementPath;

/**
 * This {@link org.dom4j.ElementHandler}is used to trigger {@link
 * ElementModifier} objects in order to modify (parts of) the Document on the
 * fly.
 * 
 * <p>
 * When an element is completely parsed, a copy is handed to the associated (if
 * any) {@link ElementModifier}that on his turn returns the modified element
 * that has to come in the tree.
 * </p>
 * 
 * @author Wonne Keysers (Realsoftware.be)
 */
class SAXModifyElementHandler implements ElementHandler
{
    private ElementModifier elemModifier;

    private Element modifiedElement;

    public SAXModifyElementHandler(ElementModifier elemModifier)
    {
        this.elemModifier = elemModifier;
    }

    public void onStart(ElementPath elementPath)
    {
        this.modifiedElement = elementPath.getCurrent();
    }

    public void onEnd(ElementPath elementPath)
    {
        try
        {
            Element origElement = elementPath.getCurrent();
            Element currentParent = origElement.getParent();

            if (currentParent != null)
            {
                // Clone sets parent + document to null
                Element clonedElem = (Element)origElement.clone();

                // Ask for modified element
                modifiedElement = elemModifier.modifyElement(clonedElem);

                if (modifiedElement != null)
                {
                    // Restore parent + document
                    modifiedElement.setParent(origElement.getParent());
                    modifiedElement.setDocument(origElement.getDocument());

                    // Replace old with new element in parent
                    int contentIndex = currentParent.indexOf(origElement);
                    currentParent.content().set(contentIndex, modifiedElement);
                }

                // Remove the old element
                origElement.detach();
            }
            else
            {
                if (origElement.isRootElement())
                {
                    // Clone sets parent + document to null
                    Element clonedElem = (Element)origElement.clone();

                    // Ask for modified element
                    modifiedElement = elemModifier.modifyElement(clonedElem);

                    if (modifiedElement != null)
                    {
                        // Restore parent + document
                        modifiedElement.setDocument(origElement.getDocument());

                        // Replace old with new element in parent
                        Document doc = origElement.getDocument();
                        doc.setRootElement(modifiedElement);
                    }

                    // Remove the old element
                    origElement.detach();
                }
            }

            // Put the new element on the ElementStack, it might get pruned by
            // the PruningDispatchHandler
            if (elementPath instanceof ElementStack)
            {
                ElementStack elementStack = ((ElementStack)elementPath);
                elementStack.popElement();
                elementStack.pushElement(modifiedElement);
            }
        }
        catch(Exception ex)
        {
            throw new SAXModifyException(ex);
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @return Returns the modified Element.
     */
    protected Element getModifiedElement()
    {
        return modifiedElement;
    }
}

