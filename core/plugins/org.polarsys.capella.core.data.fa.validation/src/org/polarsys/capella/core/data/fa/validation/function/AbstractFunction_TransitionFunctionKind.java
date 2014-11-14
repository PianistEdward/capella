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
package org.polarsys.capella.core.data.fa.validation.function;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionRealization;
import org.polarsys.capella.core.validation.rule.AbstractValidationRule;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;

/**
 * Checks functions allocation consistency.
 */
public class AbstractFunction_TransitionFunctionKind extends AbstractValidationRule {
  /**
   * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  public IStatus validate(IValidationContext ctx_p) {
    EObject eObj = ctx_p.getTarget();
    EMFEventType eType = ctx_p.getEventType();
    if (eType == EMFEventType.NULL) {
      if (eObj instanceof AbstractFunction) {
        AbstractFunction function = (AbstractFunction) eObj;
        for (AbstractTrace trace : function.getOutgoingTraces()) {
          if (trace instanceof FunctionRealization) {
            AbstractFunction source = ((FunctionRealization) trace).getAllocatingFunction();
            AbstractFunction target = ((FunctionRealization) trace).getAllocatedFunction();
            if (source != null && target != null && !source.getKind().equals(target.getKind())) {
              return createFailureStatus(ctx_p, new Object[] { source.getName(), source.getKind().getName(), target.getName(), target.getKind().getName() });
            }
          }
        }
      }
    }
    return ctx_p.createSuccessStatus();
  }
}
