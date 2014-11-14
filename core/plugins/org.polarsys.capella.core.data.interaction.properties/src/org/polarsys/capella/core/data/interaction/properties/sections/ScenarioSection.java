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
package org.polarsys.capella.core.data.interaction.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.polarsys.capella.core.data.core.properties.sections.NamedElementSection;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.core.data.interaction.properties.Messages;
import org.polarsys.capella.core.data.interaction.properties.controllers.Scenario_RealizedScenariosController;
import org.polarsys.capella.core.data.interaction.properties.fields.ScenarioKindGroup;
import org.polarsys.capella.core.data.interaction.properties.fields.ScenarioValueGroup;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.ui.properties.fields.AbstractSemanticField;
import org.polarsys.capella.core.ui.properties.fields.MultipleSemanticField;

/**
 * The Scenario section.
 */
public class ScenarioSection extends NamedElementSection {

  private ScenarioValueGroup _scenarioValueGroup;
  private ScenarioKindGroup _scenarioKindGroup;
  private MultipleSemanticField _realizedScenariosField;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);

    boolean displayedInWizard = isDisplayedInWizard();

    _scenarioValueGroup = new ScenarioValueGroup(_rootParentComposite, getWidgetFactory());
    _scenarioValueGroup.setDisplayedInWizard(displayedInWizard);

    _scenarioKindGroup = new ScenarioKindGroup(_rootParentComposite, getWidgetFactory(), true);
    _scenarioKindGroup.setDisplayedInWizard(displayedInWizard);

    _realizedScenariosField = new MultipleSemanticField(getReferencesGroup(),
        Messages.getString("ScenarioSection_RealizedScenarios_Label"), getWidgetFactory(), new Scenario_RealizedScenariosController()); //$NON-NLS-1$
    _realizedScenariosField.setDisplayedInWizard(displayedInWizard);
  }

  /**
   * @see org.polarsys.capella.core.ui.properties.sections.AbstractSection#loadData(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  @Override
  public void loadData(CapellaElement capellaElement_p) {
    super.loadData(capellaElement_p);

    _scenarioValueGroup.loadData(capellaElement_p);
    _scenarioKindGroup.loadData(capellaElement_p, InteractionPackage.eINSTANCE.getScenario_Kind());
    _realizedScenariosField.loadData(capellaElement_p, InteractionPackage.eINSTANCE.getScenario_OwnedScenarioRealization());
  }

  /**
   * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
   */
  @Override
  public boolean select(Object toTest) {
    EObject eObjectToTest = super.selection(toTest);
    return ((eObjectToTest != null) && (eObjectToTest.eClass() == InteractionPackage.eINSTANCE.getScenario()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AbstractSemanticField> getSemanticFields() {
    List<AbstractSemanticField> fields = new ArrayList<AbstractSemanticField>();

    fields.addAll(super.getSemanticFields());
    fields.add(_realizedScenariosField);
    fields.add(_scenarioKindGroup);
    fields.add(_scenarioValueGroup);

    return fields;
  }
}
