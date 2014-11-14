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
package org.polarsys.capella.core.business.queries.ctx;

import org.eclipse.emf.ecore.EClass;

import org.polarsys.capella.core.business.queries.interaction.AbstractCapability_InvolvedFunctionalChains;
import org.polarsys.capella.core.data.ctx.CtxPackage;

/**
 */
public class Capability_InvolvedFunctionalChains extends AbstractCapability_InvolvedFunctionalChains {

  /**
   * @see org.polarsys.capella.core.business.queries.capellacore.IBusinessQuery#getEClass()
   */
  public EClass getEClass() {
    return CtxPackage.Literals.CAPABILITY;
  }
}
