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
package org.polarsys.capella.core.data.information.validation.dataValue;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.information.datavalue.AbstractComplexValue;
import org.polarsys.capella.core.data.information.datavalue.AbstractExpressionValue;

/**
 * AbstractComplexValue Value type check
 */
public class ComplexValueFamilyTypeCheck extends AbstractDataValueTypeCheck {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInstanceOf(EObject eObj_p) {
    if ((eObj_p instanceof AbstractComplexValue) && !(eObj_p instanceof AbstractExpressionValue)) {
      return true;
    }
    return false;
  }

}
