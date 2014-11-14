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
package org.polarsys.capella.core.platform.sirius.ui.preferences;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.emf.ecore.EClass;

import org.polarsys.capella.core.commands.preferences.service.AbstractPreferencesInitializer;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.epbs.EpbsPackage;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.capellamodeller.CapellamodellerPackage;
import org.polarsys.capella.core.data.oa.OaPackage;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.platform.sirius.ui.actions.CapellaActionsActivator;

/**
 * Preferences linked to protected elements.
 */
public class ProtectedElementsPreferences extends AbstractPreferencesInitializer implements IProtectedElementsPreferences {

  /**
   * @param pluginID_p
   */
  public ProtectedElementsPreferences() {
    super(CapellaActionsActivator.PLUGIN_ID);

  }

  /**
   * Delete restriction preference.
   */
  public static final String PREFERENCE_DELETE_RESTRICTION = "Delete_Restriction_"; //$NON-NLS-1$

  /**
   * Get preference representation title for given {@link EClass}
   * @param class_p
   * @return
   */
  protected String getPreferenceTitle(EClass class_p) {
    return new PreferenceTitleHelper().getPreferenceTitle(class_p);
  }

  /**
   * Called by eclipse preferences framework. {@inheritDoc}
   */
  @Override
  public void initializeDefaultPreferences() {

    // Project
    initializeDefaultProtectedElementPreference(CapellamodellerPackage.Literals.PROJECT, 0);
    // SystemEngineering
    initializeDefaultProtectedElementPreference(CapellamodellerPackage.Literals.SYSTEM_ENGINEERING, 1);
    // OperationalAnalysis
    initializeDefaultProtectedElementPreference(OaPackage.Literals.OPERATIONAL_ANALYSIS, 2);
    // SystemAnalysis
    initializeDefaultProtectedElementPreference(CtxPackage.Literals.SYSTEM_ANALYSIS, 3);
    // LogicalArchitecture
    initializeDefaultProtectedElementPreference(LaPackage.Literals.LOGICAL_ARCHITECTURE, 4);
    // PhysicalAchitecture
    initializeDefaultProtectedElementPreference(PaPackage.Literals.PHYSICAL_ARCHITECTURE, 5);
    // EPBSArchitecture
    initializeDefaultProtectedElementPreference(EpbsPackage.Literals.EPBS_ARCHITECTURE, 6);
    // OperationalContext
    initializeDefaultProtectedElementPreference(OaPackage.Literals.OPERATIONAL_CONTEXT, 7);
    // SystemContext
    initializeDefaultProtectedElementPreference(CtxPackage.Literals.SYSTEM_CONTEXT, 8);
    // LogicalContext
    initializeDefaultProtectedElementPreference(LaPackage.Literals.LOGICAL_CONTEXT, 9);
    // PhysicalContext
    initializeDefaultProtectedElementPreference(PaPackage.Literals.PHYSICAL_CONTEXT, 10);
    // PhysicalContext
    initializeDefaultProtectedElementPreference(EpbsPackage.Literals.EPBS_CONTEXT, 11);
    // System
    initializeDefaultProtectedElementPreference(CtxPackage.Literals.SYSTEM, 12);
  }

  /**
   * Initialize a default protected element preference with given parameters.
   * @param preferenceStore_p
   * @param class_p
   * @param index_p
   */
  protected void initializeDefaultProtectedElementPreference(EClass class_p, int index_p) {
    putBoolean(CapellaActionsActivator.getDefault().getPreference(index_p), true, ProjectScope.class);
    putString(CapellaActionsActivator.getDefault().getPreferenceId(index_p), CapellaActionsActivator.getDefault().getPreferenceValue(class_p), ProjectScope.class);
    putString(CapellaActionsActivator.getDefault().getPreferenceTitle(index_p), getPreferenceTitle(class_p), DefaultScope.class);
  }

}
