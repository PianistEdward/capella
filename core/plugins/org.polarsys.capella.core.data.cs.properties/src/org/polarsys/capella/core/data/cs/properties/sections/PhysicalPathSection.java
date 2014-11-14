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
package org.polarsys.capella.core.data.cs.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.tools.internal.graphical.edit.policies.DeleteHelper;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.properties.controllers.PhysicalPathAllocatedComponentExchangesController;
import org.polarsys.capella.core.data.cs.properties.controllers.RealizedPhysicalPathsController;
import org.polarsys.capella.core.data.fa.properties.sections.ComponentExchangeAllocatorSection;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.ui.properties.fields.AbstractSemanticField;
import org.polarsys.capella.core.ui.properties.fields.MultipleSemanticField;
import org.polarsys.capella.common.tig.ef.command.AbstractReadWriteCommand;

/**
 * The PhysicalPath section.
 */
public class PhysicalPathSection extends ComponentExchangeAllocatorSection {

  private MultipleSemanticField _realizedPathsField;

  @Override
  public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);

    boolean displayedInWizard = isDisplayedInWizard();

    _realizedPathsField = new MultipleSemanticField(getReferencesGroup(), Messages.PhysicalPathSection_Realized_Label, getWidgetFactory(), new RealizedPhysicalPathsController());
    _realizedPathsField.setDisplayedInWizard(displayedInWizard);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.sections.AbstractSection#loadData(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  public void loadData(CapellaElement capellaElement_p) {
    super.loadData(capellaElement_p);

    if (null != _realizedPathsField) {
      _realizedPathsField.loadData(capellaElement_p, CsPackage.eINSTANCE.getPhysicalPath_OwnedPhysicalPathRealizations());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AbstractSemanticField> getSemanticFields() {
    List<AbstractSemanticField> fields = new ArrayList<AbstractSemanticField>();

    fields.addAll(super.getSemanticFields());
    fields.add(_realizedPathsField);

    return fields;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MultipleSemanticField createComponentExchangeAllocationsField() {
    final PhysicalPathAllocatedComponentExchangesController controller = new PhysicalPathAllocatedComponentExchangesController();
    return new MultipleSemanticField(getReferencesGroup(), Messages.ComponentExchangeAllocatorSection_ComponentExchangeAllocations_Label, getWidgetFactory(), controller) {
      /**
       * {@inheritDoc}
       * The synchronization of the delegations/allocations is now managed by {@link DeleteHelper} class
       */
      @Override
      protected void handleDeleteButtonClicked() {
        executeCommand(new AbstractReadWriteCommand() {
          @SuppressWarnings("synthetic-access")
          public void run() {
            doDeleteCommand(_semanticElement, _semanticFeature);
          }
        });
      }
    };
  }

  /**
   * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
   */
  @Override
  public boolean select(Object toTest) {
    EObject eObjectToTest = super.selection(toTest);
    return ((eObjectToTest != null) && (eObjectToTest.eClass() == CsPackage.eINSTANCE.getPhysicalPath()));
  }
}
