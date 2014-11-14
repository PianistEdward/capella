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
package org.polarsys.capella.core.data.ctx.validation.capability;

import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

import org.polarsys.capella.core.data.interaction.AbstractCapability;
import org.polarsys.capella.core.data.interaction.AbstractCapabilityInclude;
import org.polarsys.capella.core.validation.rule.AbstractValidationRule;

/**
 * Ensures that a capability doesn't include itself.
 */
public class MDCHK_Capability_Inclusion_1 extends AbstractValidationRule {
  /**
   * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  public IStatus validate(IValidationContext ctx) {
    EObject eObj = ctx.getTarget();
    EMFEventType eType = ctx.getEventType();
    if (eType == EMFEventType.NULL) {
      if (eObj instanceof AbstractCapability) {
        AbstractCapability capability = (AbstractCapability) eObj;
        EList<AbstractCapabilityInclude> includedCapabilities = capability.getIncludes();
        Iterator<AbstractCapabilityInclude> iterator = includedCapabilities.iterator();
        while (iterator.hasNext()) {
          AbstractCapabilityInclude next = iterator.next();
          AbstractCapability includedCapa = next.getIncluded();
          if (includedCapa == capability) {
            return createFailureStatus(ctx, new Object[] { capability.getName() });
          }
        }
        return ctx.createSuccessStatus();
      }
    }
    return ctx.createSuccessStatus();
  }
}
