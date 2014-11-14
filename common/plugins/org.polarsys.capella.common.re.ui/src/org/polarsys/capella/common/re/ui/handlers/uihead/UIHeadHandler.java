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
package org.polarsys.capella.common.re.ui.handlers.uihead;

import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.transposer.SharedWorkflowActivityParameter;
import org.polarsys.capella.core.transition.common.ui.handlers.filter.FilteringUIDifferencesHandler;
import org.polarsys.capella.core.transition.common.ui.handlers.uihead.IUIHeadHandler;
import org.polarsys.capella.common.re.ui.handlers.scope.ScopeUIHandler;

/**
 *
 */
public class UIHeadHandler implements IUIHeadHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillParameter(SharedWorkflowActivityParameter parameter_p) {

    //Add UI Options handler
    GenericParameter<IHandler> param = new GenericParameter<IHandler>(ITransitionConstants.SCOPE_HANDLER, new ScopeUIHandler(), "Options handler"); //$NON-NLS-1$
    parameter_p.addSharedParameter(param);

    //Add UI Filtering handler
    GenericParameter<IHandler> param2 =
        new GenericParameter<IHandler>(ITransitionConstants.FILTERING_DIFFERENCES_HANDLER, new FilteringUIDifferencesHandler(),
            "Filtering differences UI handler"); //$NON-NLS-1$
    parameter_p.addSharedParameter(param2);

  }

}
