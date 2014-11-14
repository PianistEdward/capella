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
package org.polarsys.capella.core.business.queries.information;

import org.eclipse.emf.ecore.EClass;
import org.polarsys.capella.core.data.information.InformationPackage;

/**
 * This is the query for collections Inherited Collections.
 * @deprecated Replaced by class Collection_InheritedCollection
 */
@Deprecated
public class CollectionInheritedCollections extends GeneralizableElement_AbstractInheritedType {
  /**
   * @see org.polarsys.capella.core.business.queries.capellacore.IBusinessQuery#getEClass()
   */
  public EClass getEClass() {
    return InformationPackage.Literals.COLLECTION;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.information.GeneralizableElement_AbstractInheritedType#getAvailableEclassForSuperType()
   */
  @Override
  protected EClass getAvailableEclassForSuperType() {
    return InformationPackage.Literals.COLLECTION;
  }
}
