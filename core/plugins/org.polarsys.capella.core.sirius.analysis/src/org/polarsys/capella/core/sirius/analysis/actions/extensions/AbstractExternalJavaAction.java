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
package org.polarsys.capella.core.sirius.analysis.actions.extensions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction2;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 */
public abstract class AbstractExternalJavaAction implements IExternalJavaAction2 {
  /**
   * Context tag.
   */
  protected static final String CONTEXT = "context"; //$NON-NLS-1$
  /**
   * Scope tag.
   */
  protected static final String SCOPE = "scope"; //$NON-NLS-1$
  /**
   * Wizard Message tag.
   */
  protected static final String WIZARD_MESSAGE = "wizardMessage"; //$NON-NLS-1$
  /**
   * Wizard Title tag.
   */
  protected static final String WIZARD_TITLE = "wizardTitle"; //$NON-NLS-1$  
  /**
   * Result Variable tag.
   */
  protected static final String RESULT_VARIABLE = "resultVariable"; //$NON-NLS-1$
  /**
   * Multiple tag.
   */
  protected static final String MULTIPLE = "multiple"; //$NON-NLS-1$
  /**
   * Initial Selection tag.
   */
  protected static final String INITIAL_SELECTION = "initialSelection"; //$NON-NLS-1$
  /**
   * Wizard Canceled tag.
   */
  protected static final String WIZARD_CANCELED = "WIZARD_CANCELED"; //$NON-NLS-1$
  /**
   * Graphical Context tag.
   */
  protected static final String GRAPHICAL_CONTEXT = "graphicalContext"; //$NON-NLS-1$
  /**
   * Variable tag.
   */
  protected static final String VARIABLE = "variable"; //$NON-NLS-1$
  /**
   * Value tag.
   */
  protected static final String VALUE = "value"; //$NON-NLS-1$
  /**
   * Message tag.
   */
  protected static final String MESSAGE = "message"; //$NON-NLS-1$

  protected static final String EXCHANGE_TYPE = "exchangeType"; //$NON-NLS-1$

  protected static final String SOURCE_IR = "sourceIR"; //$NON-NLS-1$
  protected static final String TARGET_IR = "targetIR"; //$NON-NLS-1$
  protected static final String MESSAGE_KIND = "messageKind"; //$NON-NLS-1$
  protected static final String TARGET_ON_EXCHANGE_ITEM = "targetOnExchangeItem"; //$NON-NLS-1$

  /**
   * Default implementation returns <code>true</code>.
   * @see org.eclipse.sirius.tools.api.ui.IExternalJavaAction#canExecute(java.util.Collection)
   */
  public boolean canExecute(Collection<? extends EObject> selections) {
    return true;
  }

  /**
   * Get active {@link Shell}.<br>
   * This method must be called from the UI Thread.
   * @return
   */
  protected Shell getShell() {
    return PlatformUI.getWorkbench().getDisplay().getActiveShell();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.sirius.tools.api.ui.IExternalJavaAction2#mayDeleteElements()
   */
  public boolean mayDeleteElements() {
	// @see Sirius-2816 - all java action that unherit from AbstractExternalJavaAction are not supposed to delete elements
	return false;
  }
}
