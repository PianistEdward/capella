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
package org.polarsys.capella.common.re.ui.subcommands.properties;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.common.flexibility.properties.property.AbstractProperty;
import org.polarsys.capella.common.flexibility.properties.schema.IEditableProperty;
import org.polarsys.capella.common.flexibility.properties.schema.IPropertyContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class AllElementsProperty extends AbstractProperty implements IEditableProperty {

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue(IPropertyContext context_p) {

    IContext context = (IContext) context_p.getSourceAsList(IContext.class).iterator().next();
    Collection<EObject> objects = (Collection) context_p.getSourceAsList(EObject.class);

    if (objects.isEmpty()) {
      objects = (Collection) ((TransitionContext) context).get(ITransitionConstants.TRANSITION_SOURCES);
    }
    if ((objects != null) && (objects instanceof Collection) && (!((Collection) objects).isEmpty())) {
      EObject value = (EObject) ((Collection) objects).iterator().next();
      return value.eResource();
    }
    return Collections.emptyList();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus validate(Object newValue_p, IPropertyContext context_p) {
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getType() {
    return Collection.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object toType(Object value_p, IPropertyContext context_p) {
    return value_p;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(IPropertyContext context_p) {
    //Nothing here
  }

}
