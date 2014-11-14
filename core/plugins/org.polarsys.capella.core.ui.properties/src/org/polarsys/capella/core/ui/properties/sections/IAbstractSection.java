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
package org.polarsys.capella.core.ui.properties.sections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.tabbed.ISection;

/**
 *
 */
public interface IAbstractSection extends IFilter, ISection {

  public void loadData(EObject object_p);

  public void setParentBackgroundColor(Color color_p);

}
