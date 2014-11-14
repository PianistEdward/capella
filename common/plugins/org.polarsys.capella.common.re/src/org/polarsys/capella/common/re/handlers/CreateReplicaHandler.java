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
package org.polarsys.capella.common.re.handlers;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polarsys.capella.common.re.commands.CreateReplicaCommand;
import org.polarsys.capella.common.tig.ef.command.ICommand;

/**
 */
public class CreateReplicaHandler extends AbstractReHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ICommand createCommand(Collection<Object> selection_p, IProgressMonitor progressMonitor_p) {
    return new CreateReplicaCommand(selection_p, progressMonitor_p) {

    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(Object evaluationContext_p) {
    super.setEnabled(evaluationContext_p);
  }

}
