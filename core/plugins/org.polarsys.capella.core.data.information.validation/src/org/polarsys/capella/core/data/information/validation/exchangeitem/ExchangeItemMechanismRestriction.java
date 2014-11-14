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

import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.model.utils.CapellaLayerCheckingExt;

/**
 * Physical Layer ExchangeItem mechanism can not be UNSET Applay's same for other layer ExchangeItem if referred by any element contained in Physical Layer
 */
public class ExchangeItemMechanismRestriction extends AbstractExchangeItemMechanismRestriction {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInCurrentLayer(CapellaElement element_p) {
    return CapellaLayerCheckingExt.isAOrInPhysicalLayer(element_p);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean checkIfReferedByOtherLayer() {
    return true;
  }

}
