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
package org.polarsys.capella.core.data.information.datatype.properties.controllers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.business.queries.capellacore.BusinessQueriesProvider;
import org.polarsys.capella.core.data.helpers.information.services.DataTypeExt;
import org.polarsys.capella.core.data.information.InformationRealization;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datatype.DatatypePackage;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.ui.properties.controllers.AbstractMultipleSemanticFieldController;

/**
 */
public class DataTypeController extends AbstractMultipleSemanticFieldController {

  /**
   * @see org.polarsys.capella.core.ui.properties.fields.custom.properties.widgets.SimpleEditableSemanticField#loadData(org.polarsys.capella.core.data.information.datatype.BooleanType,
   *      org.eclipse.emf.ecore.EReference)
   */
  @Override
  public List<EObject> loadValues(CapellaElement semanticElement_p, EStructuralFeature semanticFeature_p) {
    List<EObject> result = new ArrayList<EObject>();
    List<EObject> values = super.loadValues(semanticElement_p, semanticFeature_p);
    if (null != values) {
      for (Object value : values) {
        result.add(((InformationRealization) value).getTargetElement());
      }
    }
    return result;

  }

  /**
   * @see org.polarsys.capella.core.ui.properties.controllers.custom.properties.controllers.AbstractMultipleSemanticFieldController#getReadOpenValuesQuery(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  protected IBusinessQuery getReadOpenValuesQuery(CapellaElement semanticElement_p) {
    return BusinessQueriesProvider.getInstance().getContribution(semanticElement_p.eClass(),
      DatatypePackage.Literals.DATA_TYPE__OWNED_INFORMATION_REALIZATIONS);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.controllers.custom.properties.controllers.AbstractMultipleSemanticFieldController#doAddOperationInWriteOpenValues(org.polarsys.capella.core.data.capellacore.CapellaElement,
   *      org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.emf.ecore.EObject)
   */
  @Override
  protected void doAddOperationInWriteOpenValues(CapellaElement semanticElement_p, EStructuralFeature semanticFeature_p, EObject object_p) {
    DataTypeExt.addRealizedInformation((DataType) semanticElement_p, (CapellaElement) object_p);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.controllers.custom.properties.controllers.AbstractMultipleSemanticFieldController#doRemoveOperationInWriteOpenValues(org.polarsys.capella.core.data.capellacore.CapellaElement,
   *      org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.emf.ecore.EObject)
   */
  @Override
  protected void doRemoveOperationInWriteOpenValues(CapellaElement semanticElement_p, EStructuralFeature semanticFeature_p, EObject object_p) {
    DataTypeExt.removeRealizedInformation((DataType) semanticElement_p, (CapellaElement) object_p);
  }
}
