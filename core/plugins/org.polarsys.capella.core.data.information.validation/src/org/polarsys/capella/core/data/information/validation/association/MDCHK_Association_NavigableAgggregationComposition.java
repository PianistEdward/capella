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
package org.polarsys.capella.core.data.information.validation.association;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import org.polarsys.capella.core.data.information.AggregationKind;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.validation.rule.AbstractValidationRule;

/**
 * 
 *
 */
public class MDCHK_Association_NavigableAgggregationComposition extends AbstractValidationRule {

  /**
   * A <code>Property</code> of an <code>Association</code>, with kind = AGGREGATION or COMPOSITION, must be navigable
   * 
   * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  public IStatus validate(IValidationContext ctx_p) {
    EObject eObj = ctx_p.getTarget();
    EMFEventType eType = ctx_p.getEventType();
    Collection<IStatus> statuses = new ArrayList<IStatus>();
    if (eType == EMFEventType.NULL) {
      if (eObj instanceof Association){
        Association currentAssociation = (Association) eObj;
        if (currentAssociation.getOwnedMembers() != null){
          for (Property aProperty : currentAssociation.getOwnedMembers()){
            if ((aProperty.getAggregationKind().equals(AggregationKind.COMPOSITION) 
                || aProperty.getAggregationKind().equals(AggregationKind.AGGREGATION))
                && !currentAssociation.getNavigableMembers().contains(aProperty)) {
              IStatus status = createFailureStatus(ctx_p, new Object[] { aProperty.getName(), currentAssociation.getName()});
              statuses.add(status);
            }
          }
        }
      }
    }
    if (statuses.isEmpty())
      return ctx_p.createSuccessStatus();
    return ConstraintStatus.createMultiStatus(ctx_p, statuses);
  }

}
