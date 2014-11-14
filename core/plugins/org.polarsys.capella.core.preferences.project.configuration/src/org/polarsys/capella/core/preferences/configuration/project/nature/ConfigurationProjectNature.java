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
package org.polarsys.capella.core.preferences.configuration.project.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 */
public class ConfigurationProjectNature implements IProjectNature {
  /*
	 * 
	 */
  public static final String NATURE_ID = "org.polarsys.capella.core.preferences.project.nature.configNature"; //$NON-NLS-1$

  /*
	 * 
	 */
  private IProject project;

  /**
   * {@inheritDoc}
   */
  public void configure() throws CoreException {

  }

  /**
   * {@inheritDoc}
   */
  public void deconfigure() throws CoreException {

  }

  /**
   * Get the corresponding Modeling project.
   * @param project The original project
   * @return an optional ModelingProject (none if this project is not a modeling project).
   */
  public static IProjectNature hasConfigurationProject(IProject project) {
    IProjectNature nature = null;

    if (project != null) {
      try {
        nature = project.getNature(NATURE_ID);
      } catch (CoreException e) {
        /* does nothing */
      }
    }

    if (nature instanceof ConfigurationProjectNature) {
      return nature;
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public IProject getProject() {
    return project;
  }

  /**
   * {@inheritDoc}
   */
  public void setProject(IProject project) {
    this.project = project;
  }
}
