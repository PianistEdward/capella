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
package org.polarsys.capella.core.data.information.validation.port;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import org.polarsys.capella.core.data.information.Port;
import org.polarsys.capella.core.data.information.PortRealization;
import org.polarsys.capella.core.validation.rule.AbstractValidationRule;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.TraceableElement;

/**
 * Checks port realizations consistency.
 */
public class MDCHK_Port_PortRealization extends AbstractValidationRule {
  /**
   * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  public IStatus validate(IValidationContext ctx_p) {
    EObject eObj = ctx_p.getTarget();
    EMFEventType eType = ctx_p.getEventType();

    if (eType == EMFEventType.NULL) {
      if (eObj instanceof Port) {
        Port port = (Port) eObj;

        for (AbstractTrace trace : port.getIncomingTraces()) {
          TraceableElement sourceElement = trace.getSourceElement();
          if ((trace instanceof PortRealization) && !(sourceElement instanceof Port)) {
            return createFailureStatus(ctx_p, new Object[] { port.getName() });
          }
        }
      }
    }
    return ctx_p.createSuccessStatus();
  }
}
