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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.polarsys.capella.core.business.abstractqueries.helpers.CapellaElementsHelperForBusinessQueries;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.information.Collection;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.InformationPackage;
import org.polarsys.capella.core.data.information.datatype.DatatypePackage;

/**
 * This is the query for collections indexes.
 */
public class Collection_Index extends AbstractValue_Type {

  /**
   * @see org.polarsys.capella.core.business.abstractqueries.CapellaElement_CurrentAndHigherLevelsQuery#getDataFromLevel(org.polarsys.capella.core.data.cs.BlockArchitecture,
   *      org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  public List<CapellaElement> getDataFromLevel(DataPkg dataPkg_p, CapellaElement capellaElement_p) {
    return CapellaElementsHelperForBusinessQueries.getOnlyDiscreteDatatypes(super.getDataFromLevel(dataPkg_p, capellaElement_p));
  }

  /**
   * @see org.polarsys.capella.core.business.queries.capellacore.core.business.queries.IBusinessQuery#getCurrentElements(org.polarsys.capella.core.common.model.CapellaElement, boolean)
   */
  @Override
  public List<CapellaElement> getCurrentElements(CapellaElement element_p, boolean onlyGenerated_p) {
    List<CapellaElement> currentElements = new ArrayList<CapellaElement>();
    if (!systemEngineeringExists(element_p)) {
      return currentElements;
    }
    if (element_p instanceof Collection) {
      Collection collection = (Collection) element_p;
      if (collection.getIndex() != null) {
        currentElements.addAll(collection.getIndex());
      }
    }
    return currentElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.capellacore.IBusinessQuery#getEClass()
   */
  public EClass getEClass() {
    return InformationPackage.Literals.COLLECTION;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.capellacore.IBusinessQuery#getEStructuralFeatures()
   */
  @Override
  public List<EReference> getEStructuralFeatures() {
    return Collections.singletonList(InformationPackage.Literals.COLLECTION__INDEX);
  }

  /**
   * @see org.polarsys.capella.core.business.queries.information.AbstractValue_Type#getAvailableEClassForType()
   */
  @Override
  protected List<EClass> getAvailableEClassForType() {
    return Collections.singletonList(DatatypePackage.Literals.DATA_TYPE);
  }

}
