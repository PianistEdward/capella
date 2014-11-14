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
package org.polarsys.capella.core.validation.service;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;

import org.polarsys.capella.common.helpers.validation.ConstraintStatusDiagnostic;
import org.polarsys.capella.core.validation.CapellaValidatorAdapter;

/**
 */
public class EPFValidatorAdapter extends CapellaValidatorAdapter {

  /**
     * 
     */
  public EPFValidatorAdapter() {
  }

  /**
   * @see org.polarsys.capella.common.tig.model.validation.adapter.EValidatorAdapter#appendDiagnostics(org.eclipse.core.runtime.IStatus,
   *      org.eclipse.emf.common.util.DiagnosticChain)
   */
  @Override
  protected void appendDiagnostics(IStatus status_p, DiagnosticChain diagnostics_p) {
    // Deal recursively with multi status.
    if (status_p.isMultiStatus()) {
      IStatus[] children = status_p.getChildren();
      for (IStatus element : children) {
        appendDiagnostics(element, diagnostics_p);
      }
    } else if (status_p instanceof IConstraintStatus) {
      diagnostics_p.add(new ConstraintStatusDiagnostic((IConstraintStatus) status_p));
    }

  }

  /**
   * @see org.polarsys.capella.common.tig.model.validation.adapter.EValidatorAdapter#getValidator()
   */
  @Override
  public IBatchValidator getValidator() {

    return super.getValidator();
  }

}
