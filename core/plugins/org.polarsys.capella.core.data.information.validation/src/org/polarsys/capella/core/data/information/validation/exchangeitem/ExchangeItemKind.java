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
package org.polarsys.capella.core.data.information.validation.exchangeitem;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import org.polarsys.capella.core.data.information.ElementKind;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.validation.rule.AbstractValidationRule;

public class ExchangeItemKind extends AbstractValidationRule {

  @Override
  public IStatus validate(IValidationContext ctx_p) {
    EObject eObj = ctx_p.getTarget();
    EMFEventType eType = ctx_p.getEventType();
    if (eType == EMFEventType.NULL) {
      // filter ExchangeItem
      if (eObj instanceof ExchangeItem) {
        // collection of status message
        Collection<IStatus> statuses = new ArrayList<IStatus>();
        ExchangeItem exchangeItem = (ExchangeItem) eObj;
        ExchangeMechanism exchangeMechanism = exchangeItem.getExchangeMechanism();
        if (null != exchangeMechanism) {
          String exchangeItemKindName = exchangeMechanism.getName();
          
          // Operation kind
          if (exchangeItemKindName != null && exchangeMechanism == ExchangeMechanism.OPERATION) { 
            EList<ExchangeItemElement> elements = exchangeItem.getOwnedElements();
            for (ExchangeItemElement exchangeItemElement : elements) {
              ElementKind exchangeItemElementKind = exchangeItemElement.getKind();
              if (exchangeItemElementKind != ElementKind.PARAMETER) {
                //(ExchangeItem) of kind OPERATION should not have (ExchangeItemElement) other than PARAMETER as kind
                IStatus status =  ctx_p.createFailureStatus(new Object[] { exchangeItem, exchangeMechanism, exchangeItemElement.getName(), ElementKind.PARAMETER });
                statuses.add(status);
              }
            }
          }  
          // Other kinds
          if (exchangeItemKindName != null && exchangeMechanism != ExchangeMechanism.OPERATION) {
            EList<ExchangeItemElement> elements = exchangeItem.getOwnedElements();
            for (ExchangeItemElement exchangeItemElement : elements) {
              ElementKind exchangeItemElementKind = exchangeItemElement.getKind();
              if (exchangeItemElementKind != ElementKind.TYPE) {
                //(ExchangeItem) of kind 'X' should not have (ExchangeItemElement) other than TYPE as kind
                IStatus status = ctx_p.createFailureStatus(new Object[] { exchangeItem, exchangeMechanism, exchangeItemElement.getName(), ElementKind.TYPE });
                statuses.add(status);
              }
            }
          } 
        }
        if(statuses.size()>0){
          return ConstraintStatus.createMultiStatus(ctx_p, statuses);
        }
      }
    }
    
    // No conflict found
    return ctx_p.createSuccessStatus();
  }

}
