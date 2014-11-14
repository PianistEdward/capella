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
package org.polarsys.capella.core.validation.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import org.polarsys.capella.core.commands.preferences.util.PreferencesHelper;
import org.polarsys.capella.core.platform.sirius.ui.actions.CapellaValidateAction;
import org.polarsys.capella.core.validation.ui.actions.EPFValidationAction;
import org.polarsys.capella.common.menu.dynamic.AbstractActionProvider;
import org.polarsys.capella.common.menu.dynamic.contributions.ActionContributionProvider;

/**
 */
public class DynamicActionProvider extends AbstractActionProvider {

  /**
   * 
   */
  private ValidateAction _defaultValidationAction;

  /*
   * 
   */
  private List<ValidateAction> _userValidationActions;

  /*
   * 
   */
  private ImageDescriptor imageDescriptor;

  /*
   * 
   */
  private ISelectionProvider selectionProvider;

  /**
   * 
   */
  public DynamicActionProvider() {
    // Initialize the action provider to force it to load menu contributors.
    ActionContributionProvider.getInstance();
  }

  /**
   * @see org.polarsys.capella.common.mdsofa.rootasset.ui.workbench.action.navigator.AbstractActionProvider#initActions(org.eclipse.swt.widgets.Shell,
   *      org.eclipse.ui.IWorkbenchPage, org.eclipse.jface.viewers.ISelectionProvider)
   */
  @Override
  protected void initActions(Shell shell_p, IWorkbenchPage page_p, ISelectionProvider _selectionProvider) {
    this.selectionProvider = _selectionProvider;

    imageDescriptor = CapellaValidationUIActivator.getDefault().getImageDescriptor(CapellaValidationUIActivator.IMG_ENABLED_VALIDATE);
    _userValidationActions = new ArrayList<ValidateAction>();
    _defaultValidationAction = createDefaultValidation(); // createValidationAction(false, null, selectionProvider, imageDescriptor);
    for (IFile file : PreferencesHelper.retrieveUserDefinedPreferenceFiles(selectionProvider, EPFValidationAction.EPF_EXTNAME)) {
      _userValidationActions.add(createValidationAction(false, file, selectionProvider, imageDescriptor));
    }
  }

  /**
   * @return
   */
  public ValidateAction createDefaultValidation() {

    imageDescriptor = CapellaValidationUIActivator.getDefault().getImageDescriptor(CapellaValidationUIActivator.IMG_ENABLED_VALIDATE);

    CapellaValidateAction validationAction = new CapellaValidateAction();

    validationAction.setImageDescriptor(imageDescriptor);
    validationAction.setText("Validate Model"); //$NON-NLS-1$

    validationAction.setActiveWorkbenchPart(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
    selectionProvider.addSelectionChangedListener(validationAction);
    // Set the current selection otherwise at first run, selection is lost.
    ISelection selection = selectionProvider.getSelection();
    if (!selection.isEmpty()) {
      validationAction.selectionChanged(new SelectionChangedEvent(selectionProvider, selection));
    }

    return validationAction;

  }

  /**
   * @return
   */
  protected IContributionItem createContributionItem() {

    if (_userValidationActions.size() > 0) {
      IMenuManager menuManager = new MenuManager("Validate Model", imageDescriptor, "ID"); //$NON-NLS-1$ //$NON-NLS-2$
      menuManager.add(_defaultValidationAction);
      menuManager.add(new Separator());
      for (ValidateAction action : _userValidationActions) {
        menuManager.add(action);
      }
      return menuManager;
    }

    imageDescriptor = CapellaValidationUIActivator.getDefault().getImageDescriptor(CapellaValidationUIActivator.IMG_ENABLED_VALIDATE);
    _userValidationActions = new ArrayList<ValidateAction>();
    _defaultValidationAction = createDefaultValidation(); // createValidationAction(true, null, selectionProvider, imageDescriptor);

    IMenuManager menu_p = new MenuManager("Validate Model", imageDescriptor, "ID"); //$NON-NLS-1$ //$NON-NLS-2$
    menu_p.add(new Separator());
    menu_p.add(_defaultValidationAction);
    return menu_p;

  }

  /**
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu(IMenuManager menu_p) {
    menu_p.insertAfter(ICommonMenuConstants.GROUP_NEW, createContributionItem());
  }

  /**
   * @see org.polarsys.capella.common.mdsofa.rootasset.ui.workbench.action.navigator.AbstractActionProvider#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars(IActionBars actionBars_p) {
    // Do nothing.
  }

  /**
   * @param isRootAction
   * @param file_p
   * @param selectionProvider_p
   * @param imageDescriptor_p
   * @return
   */
  protected ValidateAction createValidationAction(boolean isRootAction, IFile file_p, ISelectionProvider selectionProvider_p, ImageDescriptor imageDescriptor_p) {
    ValidateAction validationAction = new EPFValidationAction(isRootAction, file_p);

    validationAction.setImageDescriptor(imageDescriptor_p);

    validationAction.setActiveWorkbenchPart(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
    selectionProvider_p.addSelectionChangedListener(validationAction);
    // Set the current selection otherwise at first run, selection is lost.
    ISelection selection = selectionProvider_p.getSelection();
    if (!selection.isEmpty()) {
      validationAction.selectionChanged(new SelectionChangedEvent(selectionProvider_p, selection));
      validationAction.setEnabled(true);
    }

    return validationAction;

  }

  /**
   * Add given contribution item in specified menu.
   * @param menu_p
   * @param groupId_p
   * @param item_p
   */
  protected void addContributionItem(IMenuManager menu_p, String groupId_p, IContributionItem item_p) {
    // Append the action to a group if provided...
    if (null != groupId_p) {
      menu_p.appendToGroup(groupId_p, item_p);
    } else {
      menu_p.add(item_p);
    }
  }

  /**
   * Add given action in specified menu manager.
   * @param menu_p
   * @param groupId_p
   * @param action_p
   */
  protected void addAction(IMenuManager menu_p, String groupId_p, IAction action_p) {
    // Override the action contribution item to force the context menu to be
    // refreshed even if the selected object has not changed.
    addContributionItem(menu_p, groupId_p, new DynamicActionContributionItem(action_p));
  }
}
