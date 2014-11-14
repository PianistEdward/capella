/*******************************************************************************
 * Copyright (c) 2006, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.ui.toolkit.decomposition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * <code>DecompositionUtil</code> provides some utility methods for the graphical part of DecompositionGeneralViewer
 */
public class DecompositionUtil {

  private DecompositionGeneralViewer _decompositionGeneralViewer;
   static final String ONCE_USED = Messages.getString("LCDecomp.interface.onceused"); //$NON-NLS-1$
  static final String UNUSED = Messages.getString("LCDecomp.interface.unused"); //$NON-NLS-1$
  static final String MULTIPLE_USED = Messages.getString("LCDecomp.interface.multipleused"); //$NON-NLS-1$
  static Image _onceUsedImage;
  static Image _unUsedImage;
  static Image _multipleUsedImage;
  private static Shell currentShell;
  private static Listener _renameDecompListener;

  /**
   * 
   */
  public static final String INTERFACE_ONCE_ASSIGNED_ID = "ASSIGNED_ONCE"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String INTERFACE_UNASSIGNED_ID = "UNASSIGNED"; //$NON-NLS-1$
  /**
   * 
   */
  public static final String INTERFACE_TWICE_ASSIGNED_ID = "ASSIGNED_TWICE"; //$NON-NLS-1$

  /**
   * c'tor
   * @param decompositionGeneralViewer_p
   *          the {@link DecompositionGeneralViewer}
   * @deprecated used only for initializing images, use initializeImages() instead.
   */
  public DecompositionUtil(DecompositionGeneralViewer decompositionGeneralViewer_p) {
    this._decompositionGeneralViewer = decompositionGeneralViewer_p;
    try {
      ImageRegistry imgRegistry = _decompositionGeneralViewer.getDecompositionModel().getImgRegistry();
      _onceUsedImage = imgRegistry.get(DecompositionUtil.INTERFACE_ONCE_ASSIGNED_ID);
      _unUsedImage = imgRegistry.get(DecompositionUtil.INTERFACE_UNASSIGNED_ID);
      _multipleUsedImage = imgRegistry.get(DecompositionUtil.INTERFACE_TWICE_ASSIGNED_ID);
    } catch (Exception exception_p) {
      // ignore

    }
  }

  public static void initializeImages(ImageRegistry imageRegistry_p) {
    try {
      _onceUsedImage = imageRegistry_p.get(DecompositionUtil.INTERFACE_ONCE_ASSIGNED_ID);
      _unUsedImage = imageRegistry_p.get(DecompositionUtil.INTERFACE_UNASSIGNED_ID);
      _multipleUsedImage = imageRegistry_p.get(DecompositionUtil.INTERFACE_TWICE_ASSIGNED_ID);
    } catch (Exception exception_p) {
      // ignore

    }
  }

  /**
   * Gives the name of the Decomposition
   * @param list_p
   *          list of Decomposition
   * @return name of the decomposition
   */
  @SuppressWarnings("boxing")
  public static String getDecompositionName(List<Decomposition> list_p) {
    String name = Util.ZERO_LENGTH_STRING;
    if (list_p.size() == 1) {
      return name;
    }
    List<Integer> tmpList = parseDecompositionList(list_p);
    int index = tmpList.size();
    for (int i = 0; i < tmpList.size(); i++) {
      int x = tmpList.get(i);
      if (x > (i + 1)) {
        index = i;
        break;
      }
    }
    name = Messages.getString("LCDecompGeneralViewer.decomposition.name") + (index + 1); //$NON-NLS-1$
    return name;
  }

  private static List<Integer> parseDecompositionList(List<Decomposition> list_p) {
    List<Integer> tmp = new ArrayList<Integer>(1);
    for (Decomposition decomp : list_p) {
      if (decomp.getName().indexOf(Messages.getString("LCDecompGeneralViewer.decomposition.name")) != -1) { //$NON-NLS-1$
        int lastIndex = decomp.getName().lastIndexOf(" "); //$NON-NLS-1$
        Integer integ = new Integer(decomp.getName().substring(lastIndex + 1));
        if (integ.intValue() != 0)
          tmp.add(integ);
      }
    }

    Collections.sort(tmp);
    return tmp;
  }

  /**
   * Gives name for the TargetComponent from the list.
   * @param list_p
   *          the list of TargetComponents
   * @return name of the TargetComponent
   */
  @SuppressWarnings("boxing")
  public static String getTargetComponentName(List<DecompositionComponent> list_p) {
    String name = Util.ZERO_LENGTH_STRING;
    List<Integer> tmpList = parseTargetComponentsList(list_p);
    int index = tmpList.size();
    for (int i = 0; i < tmpList.size(); i++) {
      if (tmpList.get(i) > (i + 1)) {
        index = i;
        break;
      }
    }

    name = Messages.getString("LCDecompGeneralViewer.targetcomponent.name") + (index + 1); //$NON-NLS-1$
    return name;
  }

  /**
   * @param list_p
   * @return
   */
  private static List<Integer> parseTargetComponentsList(List<DecompositionComponent> list_p) {
    List<Integer> tmp = new ArrayList<Integer>(1);
    for (DecompositionComponent comp : list_p) {
      if (comp.getName().indexOf(Messages.getString("LCDecompGeneralViewer.targetcomponent.name")) != -1) { //$NON-NLS-1$
        int lastIndex = comp.getName().lastIndexOf(" "); //$NON-NLS-1$
        Integer integ = new Integer(comp.getName().substring(lastIndex + 1));
        tmp.add(integ);
      }
    }

    Collections.sort(tmp);
    return tmp;
  }

  /**
   * Gets the double click listener for renaming a decomposition on a tab item
   * @param item_p
   *          the TabItem to be renamed
   * @return Listener for Mouse Double Click
   */
  static Listener getRenameDecompListener(final CTabItem item_p) {
    if (_renameDecompListener != null)
      item_p.removeListener(SWT.MouseDoubleClick, _renameDecompListener);
    _renameDecompListener = new Listener() {

      public void handleEvent(Event event_p) {
        String newName = (String) event_p.data;
        Decomposition decomposition = (Decomposition) item_p.getData();
        if (decomposition.getDecompositionModel().renameDecomposition(decomposition, newName)) {
          item_p.setText(newName);
          TreeViewer vv = (TreeViewer) item_p.getData(IDecompositionDataConstants.TARGET_TREEVIEWER_DATA);
          vv.setSelection(vv.getSelection());
        }
      }
    };
    return _renameDecompListener;
  }

  /*
   * Gives listener for showing the status of DecompositionItem
   */
  static Listener getLabelListener(final TreeViewer viewer_p) {
    // Listener for showing status of interfaces
    Listener labelListener = new Listener() {
      Tree tree = null;

      public void handleEvent(Event event) {
        tree = viewer_p.getTree();
        Label label = (Label) event.widget;
        Shell shell = label.getShell();
        switch (event.type) {
          case SWT.MouseDown: {
            Event e = new Event();
            e.item = (TreeItem) label.getData("_TREEITEM"); //$NON-NLS-1$
            // Assuming table is single select, set the selection as if
            // the mouse down event went through to the table
            tree.setSelection(new TreeItem[] { (TreeItem) e.item });
            tree.notifyListeners(SWT.Selection, e);
            shell.dispose();
            tree.setFocus();
            break;
          }
          case SWT.MouseExit:
            shell.dispose();
          break;
        }
      }
    };
    return labelListener;
  }

  /*
   * Gives listener for tooltips in tableitem
   */
  static Listener getTreeListener(final TreeViewer viewer_p, final Listener labelListener) {
    // Listener for showing status of interfaces
    Listener treeListener = new Listener() {
      Shell tip = null;
      Label label = null;
      Tree tree = null;

      public void handleEvent(Event event) {
        tree = viewer_p.getTree();
        switch (event.type) {
          case SWT.Dispose:
          case SWT.KeyDown:
          case SWT.MouseMove: {
            if (tip == null)
              break;
            tip.dispose();
            tip = null;
            label = null;
            break;
          }
          case SWT.MouseHover: {
            TreeItem item = tree.getItem(new Point(event.x, event.y));
            if (item != null) {
              if (tip != null && !tip.isDisposed())
                tip.dispose();
              tip = new Shell(tree.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
              tip.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
              FillLayout layout = new FillLayout();
              layout.marginWidth = 2;
              tip.setLayout(layout);

              try {

                label = new Label(tip, SWT.NONE);
                label.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
                label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
                label.setData("_TREEITEM", item); //$NON-NLS-1$
                Object data = item.getData();
                if (data instanceof DecompositionItem) {
                  label.setText(((DecompositionItem) data).getStatusMessage());
                } else {
                  label.setText(item.getText());
                }
                label.addListener(SWT.MouseExit, labelListener);
                label.addListener(SWT.MouseDown, labelListener);
                label.addListener(SWT.MouseUp, labelListener);
                Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                Rectangle rect = item.getBounds(0);
                Point pt = tree.toDisplay(rect.x, rect.y);
                tip.setBounds(pt.x, pt.y, size.x, size.y);
                tip.setVisible(true);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
    };
    return treeListener;
  }

  /**
   * This method is used to display light bulbs when a tree item is selected on the tree
   * @param viewer_p
   *          the tree viewer
   */
  static void addListenerForSynthesisCheck(final TreeViewer viewer_p) {
    final Tree tree = viewer_p.getTree();

    tree.addListener(SWT.Selection, new Listener() {
      Shell shell = null;
      Label label = null;

      @SuppressWarnings("synthetic-access")
      public void handleEvent(Event arg0_p) {

        Event e = new Event();
        TreeItem item = null;
        if (currentShell != null) {
          currentShell.dispose();
          currentShell = null;
        }
        if (tree.getSelection().length > 0) {
          item = tree.getSelection()[0];
        }
        if (item != null) {
          e.item = item;
          if (shell != null && !shell.isDisposed())
            shell.dispose();
          shell = new Shell(tree.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
          currentShell = shell;
          shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
          FillLayout layout = new FillLayout();
          layout.marginWidth = 0;
          shell.setLayout(layout);

          Object data = item.getData();
          label = new Label(shell, SWT.CENTER);
          label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
          label.setText(" "); //$NON-NLS-1$

          if (data instanceof DecompositionItem) {
            DecompositionItem decompositionItem = (DecompositionItem) data;
            switch (decompositionItem.getStatus()) {
              case DecompositionItem.ASSIGNED: {

                label.setImage(_onceUsedImage);
                label.setToolTipText(ONCE_USED);
                break;
              }
              case DecompositionItem.AMBIGUOUS: {
                label.setImage(_multipleUsedImage);
                label.setToolTipText(MULTIPLE_USED);
                break;
              }
              case DecompositionItem.UNASSIGNED: {

                label.setImage(_unUsedImage);
                label.setToolTipText(UNUSED);
                break;
              }
              default:
              break;
            }
          } else {
            shell.dispose();
            shell = null;
          }

          if (shell != null && !shell.isDisposed()) {
            Rectangle rect = item.getBounds(0);
            Point pt = tree.toDisplay(rect.x, rect.y);
            shell.setLocation(pt.x - 20, pt.y);
            shell.pack();
            shell.setVisible(true);
          }
        }
      }
    });
  }

  /*
   * This method is used to show a ContentProposal on the text field.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  void addContentProposal(List list_p, final Text text_p) {
    char[] autoActivationCharacters = new char[] { '#', '(' };
    try {
      KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space"); //$NON-NLS-1$
      ContentProposalAdapter adapter =
                                       new ContentProposalAdapter(text_p, new TextContentAdapter(), new DecompositionContentProposalProvider(list_p),
                                                                  keyStroke, autoActivationCharacters);
      adapter.addContentProposalListener(new IContentProposalListener() {

        public void proposalAccepted(IContentProposal proposal_p) {
          DecompositionReuseContentProposal proposal = (DecompositionReuseContentProposal) proposal_p;
          DecompositionComponent comp = proposal.getComponent();
          text_p.setText(comp.getName());
          text_p.setData(comp);

        }

      });
    } catch (ParseException exception_p) {
      // do nothing
    }
  }

  public static boolean isValidName(String name_p, Decomposition decomposition_p) {
    if(name_p == null || name_p.length() == 0) {
      return false;
    }
    for (DecompositionComponent comp : decomposition_p.getTargetComponents()) {
      if (comp.getName().equals(name_p)) {
        return false;
      }
    }
    return true;
  }
}
