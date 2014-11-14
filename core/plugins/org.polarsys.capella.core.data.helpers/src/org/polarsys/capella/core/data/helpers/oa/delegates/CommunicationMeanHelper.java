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
package org.polarsys.capella.core.data.helpers.oa.delegates;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.core.data.helpers.fa.delegates.ComponentExchangeHelper;
import org.polarsys.capella.core.data.helpers.capellacore.delegates.NamedRelationshipHelper;
import org.polarsys.capella.core.data.oa.CommunicationMean;
import org.polarsys.capella.core.data.oa.Entity;
import org.polarsys.capella.core.data.oa.OaPackage;
import org.polarsys.capella.common.data.modellingcore.InformationsExchanger;

public class CommunicationMeanHelper {
  private static CommunicationMeanHelper instance;

  private CommunicationMeanHelper() {
    // do nothing
  }

  public static CommunicationMeanHelper getInstance() {
    if (instance == null)
      instance = new CommunicationMeanHelper();
    return instance;
  }

  public Object doSwitch(CommunicationMean element_p, EStructuralFeature feature_p) {
    Object ret = null;

    if (feature_p.equals(OaPackage.Literals.COMMUNICATION_MEAN__SOURCE_ENTITY)) {
      ret = getSourceEntity(element_p);
    } else if (feature_p.equals(OaPackage.Literals.COMMUNICATION_MEAN__TARGET_ENTITY)) {
      ret = getTargetEntity(element_p);
    }

    // no helper found... searching in super classes...
    if (null == ret) {
      ret = ComponentExchangeHelper.getInstance().doSwitch(element_p, feature_p);
    }
    if (null == ret) {
      ret = NamedRelationshipHelper.getInstance().doSwitch(element_p, feature_p);
    }

    return ret;
  }

  protected Entity getSourceEntity(CommunicationMean element_p) {
    InformationsExchanger source = element_p.getSource();
    if (source instanceof Entity) {
      return (Entity) source;
    }
    return null;
  }

  protected Entity getTargetEntity(CommunicationMean element_p) {
    InformationsExchanger target = element_p.getTarget();
    if (target instanceof Entity) {
      return (Entity) target;
    }
    return null;
  }
}
