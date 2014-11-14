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
package org.polarsys.capella.core.commands.preferences.service;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.validation.service.ModelValidationService;

import org.polarsys.capella.common.tools.report.EmbeddedMessage;
import org.polarsys.capella.common.tools.report.config.registry.ReportManagerRegistry;
import org.polarsys.capella.common.tools.report.util.IReportManagerDefaultComponents;
import org.polarsys.capella.core.commands.preferences.util.PreferencesHelper;
import org.polarsys.capella.core.preferences.Activator;

public abstract class AbstractPreferencesInitializer extends AbstractPreferenceInitializer {

  private static final Logger __logger = ReportManagerRegistry.getInstance().subscribe(IReportManagerDefaultComponents.UI);

  public static ScopedCapellaPreferencesStore preferencesManager;

  /*
	 * 
	 */
  IProject project;

  /**
	 * 
	 */
  public AbstractPreferencesInitializer(String pluginID) {
    super();
    this.project =
        PreferencesHelper.getSelectedEclipseProject() != null ? PreferencesHelper.getSelectedEclipseProject() : PreferencesHelper.getSelectedCapellaProject();
    preferencesManager = ScopedCapellaPreferencesStore.getInstance(pluginID);
    initializeDefaultPreferences();
    try {
    	 //force satrt of EMF Validation plugin before initializing ther default preferences scope
        ModelValidationService.getInstance().loadXmlConstraintDeclarations();
        PreferencesHelper.initializeCapellaPreferencesFromEPFFile();
	} catch (Exception e) {
		e.printStackTrace();
	}
   
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract void initializeDefaultPreferences();

  /**
   * @param key
   * @param value
   */
  public void putBoolean(String key, boolean value, Class scopeClass) {
    project = PreferencesHelper.getSelectedCapellaProject();
    ScopedCapellaPreferencesStore.getOptions().put(key, String.valueOf(value));
    if (project != null) {
      ScopedCapellaPreferencesStore.putBoolean(project, key, value);
    } else {
      ScopedCapellaPreferencesStore.putBoolean(null, key, value);
    }

    initializeAllOpenedProjects(key, value);
    new InstanceScope().getNode(Activator.PLUGIN_ID).putBoolean(key, value);
    new DefaultScope().getNode(Activator.PLUGIN_ID).putBoolean(key, value);
  }

  /**
   * @param key
   * @param value
   */
  public void putString(String key, String value, Class scopeClass) {
    if (scopeClass.equals(ProjectScope.class)) {
      project = PreferencesHelper.getSelectedCapellaProject();
      ScopedCapellaPreferencesStore.getOptions().put(key, String.valueOf(value));
      if (project != null) {
        ScopedCapellaPreferencesStore.putString(project, key, String.valueOf(value));
      }
    } else {
      ScopedCapellaPreferencesStore.putString(null, key, String.valueOf(value));
    }

    initializeAllOpenedProjects(key, value);

    new InstanceScope().getNode(Activator.PLUGIN_ID).put(key, String.valueOf(value));
    new DefaultScope().getNode(Activator.PLUGIN_ID).put(key, String.valueOf(value));
  }

  /**
   * @param key
   * @param value
   */
  public void putInt(String key, int value, Class scopeClass) {
    if (scopeClass.equals(ProjectScope.class)) {
      project = PreferencesHelper.getSelectedCapellaProject();
      ScopedCapellaPreferencesStore.getOptions().put(key, String.valueOf(value));
      if (project != null) {
        ScopedCapellaPreferencesStore.putInt(project, key, value);
      }
    } else {
      ScopedCapellaPreferencesStore.putInt(null, key, value);
    }

    initializeAllOpenedProjects(key, value);
    new InstanceScope().getNode(Activator.PLUGIN_ID).putInt(key, value);
    new DefaultScope().getNode(Activator.PLUGIN_ID).putInt(key, value);
  }

  /**
   * @param value
   * @param key
   */
  private void initializeAllOpenedProjects(String key, Object value) {
    try {
      IProject[] iProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

      // initialize default scope
      if (value instanceof Boolean) {
        ScopedCapellaPreferencesStore.putBoolean(null, key, ((Boolean) value).booleanValue());
      } else if (value instanceof String) {
        ScopedCapellaPreferencesStore.putString(null, key, ((String) value));
      }

      // inistilize project scopes
      for (IProject iProject : iProjects) {
        if (iProject.isOpen() && iProject.isAccessible() && (iProject.getNature(PreferencesHelper.CAPELLA_PROJECT_NATURE_ID) != null)) {
          if (value instanceof Boolean) {
            ScopedCapellaPreferencesStore.putBoolean(iProject, key, ((Boolean) value).booleanValue());
          } else if (value instanceof String) {
            ScopedCapellaPreferencesStore.putString(iProject, key, (String) value);
          }

        }

      }
    } catch (CoreException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("AbstractPreferencesInitializer.initializeAllOpenedProjects(..) _ "); //$NON-NLS-1$
      loggerMessage.append(exception_p.getMessage());
      __logger.warn(new EmbeddedMessage(loggerMessage.toString(), IReportManagerDefaultComponents.UI), exception_p);
    }

  }

  /**
   * @return
   */
  public static String getString(String key, boolean inProject) {
    return ScopedCapellaPreferencesStore.getString(true, key);

  }

  /**
   * @param key
   * @param value
   */
  public static boolean getBoolean(String key, boolean inProjectScope) {
    return ScopedCapellaPreferencesStore.getBoolean(true, key);
  }

  /**
   * @param key
   * @param value
   */
  public static int getInt(String key, boolean inProjectScope) {
    return ScopedCapellaPreferencesStore.getInt(true, key);
  }

  /**
   * @param preferenceShowCapellaProjectConcept_p
   * @param contentChild_p
   * @return
   */
  public static boolean getBoolean(String preferenceShowCapellaProjectConcept_p, Object contentChild_p) {
    return ScopedCapellaPreferencesStore.getBoolean(preferenceShowCapellaProjectConcept_p, contentChild_p);
  }

}
