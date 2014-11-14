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
package org.polarsys.capella.core.platform.sirius.ui.actions;

import org.eclipse.jface.action.IAction;

import org.polarsys.capella.common.ui.actions.AbstractTigAction;
import org.polarsys.capella.core.platform.sirius.ui.commands.TransformLiteralNumericValueCommand;

public class TransformLiteralNumericValueAction extends AbstractTigAction {
		  public void run(IAction action_p) {
			    getExecutionManager().execute(new TransformLiteralNumericValueCommand(getSelectedElement()));
			  }

}
