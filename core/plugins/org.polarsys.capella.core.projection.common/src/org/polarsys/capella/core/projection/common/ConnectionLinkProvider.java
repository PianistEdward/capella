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
package org.polarsys.capella.core.projection.common;

import org.eclipse.emf.ecore.EClass;

import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.tiger.ITracelinkProvider;

/**
 *
 */
public class ConnectionLinkProvider implements ITracelinkProvider {

  /**
   * @see org.polarsys.capella.core.tiger.ITracelinkProvider#getTraceLinkType()
   */
  public EClass getTraceLinkType() {
    return FaPackage.Literals.COMPONENT_EXCHANGE_REALIZATION;
  }

}
