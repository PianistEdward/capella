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
package org.polarsys.capella.common.ui.toolkit.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;

/**
 * Base class to implement a resizable {@link TitleAreaDialog} that contains a viewer.
 */
public abstract class AbstractViewerDialog extends TitleAreaDialog {
  /**
   * Dialog title.
   */
  private String _dialogTitle;
  /**
   * Shell title.
   */
  private String _shellTitle;
  /**
   * Dialog message.
   */
  private String _dialogMessage;

  /**
   * Constructor.
   * @param parentShell_p
   * @param title_p title displayed as wizard title.
   * @param message_p message displayed as wizard message.
   * @param shellTitle_p title displayed as shell title (i.e window title).
   */
  public AbstractViewerDialog(Shell parentShell_p, String title_p, String message_p, String shellTitle_p) {
    super(parentShell_p);
    _dialogTitle = (null == title_p) ? ICommonConstants.EMPTY_STRING : title_p;
    _dialogMessage = (null == message_p) ? ICommonConstants.EMPTY_STRING : message_p;
    _shellTitle = (null == shellTitle_p) ? ICommonConstants.EMPTY_STRING : shellTitle_p;
    setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
  }

  /**
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell(Shell newShell_p) {
    super.configureShell(newShell_p);
    newShell_p.setText(_shellTitle);
  }

  /**
   * Constrain given viewer to have a "nice" presentation.<br>
   * Preferred size.x / 2 is used as width hint to constraint specified viewer.<br> {@link #constrainViewer(TreeViewer, int, int)}
   */
  protected void constrainViewer(TreeViewer viewer_p, int heightHint_p) {
    Control tree = viewer_p.getControl();
    Point computeSize = tree.computeSize(SWT.DEFAULT, heightHint_p);
    constrainViewer(viewer_p, heightHint_p, computeSize.x);
  }

  /**
   * Constrain given viewer to have a "nice" presentation.
   * @param viewer_p
   * @param heightHint_p
   * @param widthHint_p
   */
  protected void constrainViewer(TreeViewer viewer_p, int heightHint_p, int widthHint_p) {
    GridData gridData = (GridData) viewer_p.getControl().getLayoutData();
    gridData.heightHint = convertHeightInCharsToPixels(heightHint_p);
    gridData.widthHint = widthHint_p; //in 3.6 it seems to compute too much pixels width. convertWidthInCharsToPixels(widthHint_p);
  }

  /**
   * Overridden to add accelerators. {@inheritDoc}
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    // Create OK and Cancel buttons.
    createButton(parent, IDialogConstants.OK_ID, Messages.AbstractViewerDialog_OK_Title, false);
    createButton(parent, IDialogConstants.CANCEL_ID, Messages.AbstractViewerDialog_CANCEL_Title, false);
  }

  /**
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea(Composite parent_p) {
    Composite dialogAreaComposite = (Composite) super.createDialogArea(parent_p);

    ScrolledComposite scrolled = new ScrolledComposite(dialogAreaComposite,SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER|SWT.RESIZE);
    scrolled.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
    
    Composite composite = new Composite(scrolled, SWT.NONE|SWT.RESIZE);
    GridLayout layout = new GridLayout();
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    composite.setFont(dialogAreaComposite.getFont());

    doCreateDialogArea(composite);
    customizeDialogAppearance();
    
    scrolled.setContent(composite);
    scrolled.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
   	scrolled.setLayout(new GridLayout());
   	scrolled.setExpandVertical(true);
   	scrolled.setExpandHorizontal(true); 
   	scrolled.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    scrolled.setVisible(true);

    return parent_p;
  }

  /**
   * Creates a {@link Label} and sets layout data with a {@link GridData} : <code>new GridData(GridData.FILL, GridData.BEGINNING, false, false)</code>.
   * @param parent_p
   */
  protected Label createLabel(Composite parent_p, String title_p) {
    Label label = new Label(parent_p, SWT.NONE);
    label.setText(title_p);
    label.setFont(parent_p.getFont());
    label.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false));
    return label;
  }

  /**
   * Creates a {@link Text} and sets layout data with a {@link GridData} : <code>new GridData(GridData.FILL, GridData.BEGINNING, true, false)</code>.
   * @param parent_p
   * @return the text widget
   */
  protected Text createText(Composite parent_p) {
    Text text = new Text(parent_p, SWT.SINGLE | SWT.BORDER);
    text.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    return text;
  }

  /**
   * Customize dialog settings as title, message, image...
   */
  protected void customizeDialogAppearance() {
    // Title.
    setTitle(_dialogTitle);
    // Message.
    setMessage(_dialogMessage);
  }

  /**
   * Do create dialog area content.<br>
   * Called by {@link #createDialogArea(Composite)}.
   * @param dialogAreaComposite_p
   */
  protected abstract void doCreateDialogArea(Composite dialogAreaComposite_p);

  /**
   * Method used to get a result from an end-user selection.<br>
   * Depending on implementations, returned type may change.
   * @return
   */
  protected abstract Object getResult();

  /**
   * Get viewer height hint value to constraint tree viewers.
   * @return default value is <code>20</code>.
   */
  protected int getViewerHeighthint() {
    return 20; // Magic value to get something nice.
  }
}
