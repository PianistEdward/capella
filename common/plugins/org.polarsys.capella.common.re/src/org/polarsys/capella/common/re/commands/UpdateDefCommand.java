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
package org.polarsys.capella.common.re.commands;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

import org.polarsys.capella.common.re.launcher.ReLauncher;
import org.polarsys.capella.common.re.launcher.UpdateDefLauncher;

/**
 */
public class UpdateDefCommand extends ReCommand {

  /**
   * @param _rootElement_p
   * @param progressMonitor_p
   */
  public UpdateDefCommand(Collection<Object> selection_p, IProgressMonitor progressMonitor_p) {
    super(selection_p, progressMonitor_p);
  }

  @Override
  public String getName() {
    return getClass().getName();
  }

  @Override
  protected ReLauncher createLauncher() {
    return new UpdateDefLauncher();
  }

}
