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
package org.polarsys.capella.shared.id.handler;

import org.eclipse.emf.ecore.EObject;

/**
 */
public interface IIdHandler {

  /**
   * @param id
   * @param scope
   * @return
   */
  EObject getEObject(String id, IScope scope);

  /**
   * @param object
   * @return
   */
  String getId(EObject object);
}
