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
package org.polarsys.capella.core.data.information.ui.quickfix.resolver;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.validation.ui.ide.quickfix.CapellaElementEditResolver;

/**
 * Set Name of the referenced value of current marker element
 */
public class AssociationAssociationKindResolver extends CapellaElementEditResolver {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditableInstance(EObject value_p) {

    if (value_p instanceof Association) {
      return true;
    }
    return false;
  }
  
  @Override
	protected String[] getResolvableRuleIds() {
		return noRuleIds;
	}

}
