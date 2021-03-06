package com.anjlab.eclipse.tapestry5.views.context;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

import com.anjlab.eclipse.tapestry5.TapestryContext;
import com.anjlab.eclipse.tapestry5.TapestryFile;
import com.anjlab.eclipse.tapestry5.views.TreeObject;
import com.anjlab.eclipse.tapestry5.views.TreeParent;

public class TapestryContextContentProvider implements ITreeContentProvider
{
    private TreeParent invisibleRoot;
    private TapestryContext context;

    public TapestryContextContentProvider(TapestryContext context)
    {
        this.context = context;
    }

    private void initialize()
    {
        invisibleRoot = new TreeParent("", new Object());
        
        if (context != null)
        {
            for (TapestryFile relatedFile : context.getFiles())
            {
                invisibleRoot.addChild(new TreeObject(relatedFile.getName(), relatedFile));
            }
        }
    }

    public TapestryContext getContext()
    {
        return context;
    }
    
    public Object[] getElements(Object parent)
    {
        if (parent instanceof IViewSite || parent == null)
        {
            if (invisibleRoot == null)
            {
                initialize();
            }
            return getChildren(invisibleRoot);
        }
        return getChildren(parent);
    }

    public Object getParent(Object child)
    {
        if (child instanceof TreeObject)
        {
            return ((TreeObject) child).getParent();
        }
        return null;
    }

    public Object[] getChildren(Object parent)
    {
        if (parent instanceof TreeParent)
        {
            return ((TreeParent) parent).getChildren();
        }
        return new Object[0];
    }

    public boolean hasChildren(Object parent)
    {
        if (parent instanceof TreeParent)
        {
            return ((TreeParent) parent).hasChildren();
        }
        return false;
    }
    
    public boolean hasElements()
    {
        return getElements(null).length > 0;
    }
    
    public void inputChanged(Viewer v, Object oldInput, Object newInput)
    {
    }

    public void dispose()
    {
    }

}