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
package org.polarsys.capella.core.commands.preferences.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import org.polarsys.capella.core.commands.preferences.properties.PreferencesHandler;
import org.polarsys.capella.core.commands.preferences.service.IItemDescriptor;
import org.polarsys.capella.core.commands.preferences.service.PreferencesItemsRegistry;
import org.polarsys.capella.core.preferences.Activator;

/**
 * Preferences manager for the commands plug-in.
 */
public class ConfigurabilityPreferences {

  private static final IEclipsePreferences defaultScopPref = new DefaultScope().getNode(Activator.PLUGIN_ID);

  private static final IEclipsePreferences instanceScopPrefs = new InstanceScope().getNode(Activator.PLUGIN_ID);

  /**
   * Not instantiable, as all features are static.
   */
  private ConfigurabilityPreferences() {
    super();
  }

  /**
   * Saves the preferences.
   */
  public static void save() {
    try {

      new InstanceScope().getNode(Activator.PLUGIN_ID).flush();

      instanceScopPrefs.flush();

      PreferencesHandler.initializePreferenceCommands();

    } catch (BackingStoreException exception_p) {
      exception_p.printStackTrace();
    }
  }

  /**
   * Queries whether the specified constraint <code>ID</code> is disabled.
   * @param id the constraint ID
   * @return whether it is disabled
   */
  public static boolean isInstanceScopePreferenceItemEnabled(String id) {
    return instanceScopPrefs.getBoolean(id, true);
  }

  /**
   * Queries whether the specified constraint <code>ID</code> is disabled by default.
   * @param id the constraint ID
   * @return whether it is disabled
   */
  public static boolean isItemDisabledByDefault(String id) {
    return defaultScopPref.getBoolean(id, true);
  }

  /**
   * Sets whether the specified item <code>id</code> is disabled.
   * @param id the constraint ID
   * @param disabled whether it is disabled
   */
  public static void setItemEnabled(String id, boolean disabled) {

    final IItemDescriptor constraint = PreferencesItemsRegistry.getInstance().getDescriptor(id);
    instanceScopPrefs.put(id, Boolean.valueOf(disabled).toString());
    System.setProperty(id, Boolean.valueOf(disabled).toString());
    if (constraint != null) {
      // set its enablement from the new preference value
      constraint.setEnabled(!disabled);

    } else {
      // remove this preference to declutter the prefs.ini file
      defaultScopPref.put(id, Boolean.valueOf(disabled).toString());

    }
  }

  /**
	 * 
	 */
  public static void setItemDisabledDefault(String id, boolean disabled) {
    defaultScopPref.put(id, Boolean.valueOf(disabled).toString());
  }

  /**
	 * 
	 */
  public static void setInstanceScopePreferenceItem(String id, boolean disabled) {
    instanceScopPrefs.put(id, Boolean.valueOf(disabled).toString());
  }

}
