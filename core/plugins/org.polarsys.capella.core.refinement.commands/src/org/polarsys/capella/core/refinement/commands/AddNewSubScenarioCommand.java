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
package org.polarsys.capella.core.refinement.commands;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polarsys.capella.common.helpers.operations.LongRunningListenersRegistry;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.refinement.subscenario.SubScenarioUtils;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.tig.ef.command.AbstractReadWriteCommand;

/**
 */
public class AddNewSubScenarioCommand extends AbstractReadWriteCommand {

  /**
   * Currently selected element
   */
  private ModelElement _modelElement = null;

  /**
   * Progress monitor
   */
  private IProgressMonitor _progressMonitor = null;

  /**
   * Constructor
   */
  public AddNewSubScenarioCommand(ModelElement modelElement_p, IProgressMonitor progressMonitor_p) {
    _modelElement = modelElement_p;
    _progressMonitor = progressMonitor_p;
  }

  /**
   * @see org.polarsys.capella.common.command.ICommand#execute(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void run() {
    LongRunningListenersRegistry.getInstance().operationStarting(getClass());
    try {
      if (_modelElement != null) {
        SubScenarioUtils.addNewSubScenario((Scenario) _modelElement, _progressMonitor);
      }
    } finally {
      // Send long running operation events.
      // Operation has finished.
      LongRunningListenersRegistry.getInstance().operationEnded(getClass());
    }
  }

  /**
   * @see org.polarsys.capella.common.command.ICommand#getLabel()
   */
  @Override
  public String getName() {
    return "Add new sub Scenario"; //$NON-NLS-1$
  }
}
