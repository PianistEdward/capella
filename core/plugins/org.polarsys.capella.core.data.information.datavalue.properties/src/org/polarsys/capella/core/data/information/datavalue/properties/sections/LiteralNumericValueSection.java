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
package org.polarsys.capella.core.data.information.datavalue.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.polarsys.capella.core.data.information.datavalue.DatavaluePackage;
import org.polarsys.capella.core.data.information.datavalue.properties.Messages;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.ui.properties.controllers.SimpleSemanticFieldController;
import org.polarsys.capella.core.ui.properties.fields.AbstractSemanticField;
import org.polarsys.capella.core.ui.properties.fields.SimpleSemanticField;
import org.polarsys.capella.core.ui.properties.fields.TextValueGroup;

/**
 * The LiteralNumericValue section.
 */
public class LiteralNumericValueSection extends DataValueSection {

  private TextValueGroup _valueGroup;
  private SimpleSemanticField _unitField;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);

    boolean displayedInWizard = isDisplayedInWizard();

    _valueGroup = new TextValueGroup(_rootParentComposite, Messages.getString("StringValueGroup.ValueLabel"), getWidgetFactory(), true, false); //$NON-NLS-1$
    _valueGroup.setDisplayedInWizard(isDisplayedInWizard());

    _unitField = new SimpleSemanticField(getReferencesGroup(), Messages.getString("NumericValue.Unit.Label"), getWidgetFactory(), new SimpleSemanticFieldController()); //$NON-NLS-1$
    _unitField.setDisplayedInWizard(displayedInWizard);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.sections.AbstractSection#loadData(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  public void loadData(CapellaElement capellaElement_p) {
    super.loadData(capellaElement_p);

    _valueGroup.loadData(capellaElement_p, DatavaluePackage.eINSTANCE.getLiteralNumericValue_Value());
    _unitField.loadData(capellaElement_p, DatavaluePackage.eINSTANCE.getNumericValue_Unit());
  }

  /**
   * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
   */
  @Override
  public boolean select(Object toTest) {
    EObject eObjectToTest = super.selection(toTest);
    return ((eObjectToTest != null) && (eObjectToTest.eClass() == DatavaluePackage.eINSTANCE.getLiteralNumericValue()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AbstractSemanticField> getSemanticFields() {
    List<AbstractSemanticField> fields = new ArrayList<AbstractSemanticField>();

    fields.addAll(super.getSemanticFields());
    fields.add(_unitField);
    fields.add(_valueGroup);

    return fields;
  }
}
