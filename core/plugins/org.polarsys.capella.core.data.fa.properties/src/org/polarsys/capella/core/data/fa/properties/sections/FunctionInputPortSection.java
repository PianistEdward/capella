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
package org.polarsys.capella.core.data.fa.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.business.queries.capellacore.BusinessQueriesProvider;
import org.polarsys.capella.core.data.core.properties.sections.NamedElementSection;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.properties.controllers.Port_RealizedPortsController;
import org.polarsys.capella.core.data.information.InformationPackage;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.ui.properties.controllers.AbstractMultipleSemanticFieldController;
import org.polarsys.capella.core.ui.properties.fields.AbstractSemanticField;
import org.polarsys.capella.core.ui.properties.fields.MultipleSemanticField;

/**
 * The FunctionInputPort section.
 */
public class FunctionInputPortSection extends NamedElementSection {

  private MultipleSemanticField _incomingExchangeItemsField;
  private MultipleSemanticField _realizedFunctionInputPortsField;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);

    boolean displayedInWizard = isDisplayedInWizard();

    _incomingExchangeItemsField = new MultipleSemanticField(getReferencesGroup(), Messages.FunctionInputPortSection_IncomingExchangeItems_Label, getWidgetFactory(), new AbstractMultipleSemanticFieldController() {
      /**
       * {@inheritDoc}
       */
      @Override
      protected IBusinessQuery getReadOpenValuesQuery(CapellaElement semanticElement_p) {
        return BusinessQueriesProvider.getInstance().getContribution(semanticElement_p.eClass(), FaPackage.eINSTANCE.getFunctionInputPort_IncomingExchangeItems());
      }
    });
    _incomingExchangeItemsField.setDisplayedInWizard(displayedInWizard);

    _realizedFunctionInputPortsField = new MultipleSemanticField(getReferencesGroup(),
        Messages.FunctionInputPortSection_RealizedFunctionInputPorts_Label, getWidgetFactory(), new Port_RealizedPortsController());
    _realizedFunctionInputPortsField.setDisplayedInWizard(displayedInWizard);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.sections.AbstractSection#loadData(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  public void loadData(CapellaElement capellaElement_p) {
    super.loadData(capellaElement_p);

    _incomingExchangeItemsField.loadData(capellaElement_p, FaPackage.eINSTANCE.getFunctionInputPort_IncomingExchangeItems());
    _realizedFunctionInputPortsField.loadData(capellaElement_p, InformationPackage.eINSTANCE.getPort_OwnedPortRealizations());
  }

  /**
   * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
   */
  @Override
  public boolean select(Object toTest) {
    EObject eObjectToTest = super.selection(toTest);
    return ((eObjectToTest != null) && (eObjectToTest.eClass() == FaPackage.eINSTANCE.getFunctionInputPort()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AbstractSemanticField> getSemanticFields() {
    List<AbstractSemanticField> fields = new ArrayList<AbstractSemanticField>();

    fields.addAll(super.getSemanticFields());
    fields.add(_incomingExchangeItemsField);
    fields.add(_realizedFunctionInputPortsField);

    return fields;
  }
}
