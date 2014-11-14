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
package org.polarsys.capella.core.transition.system.topdown.rules.oa.oe2system;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.handlers.selection.EClassSelectionContext;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.topdown.handlers.transformation.TopDownTransformationHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class EntityRule extends org.polarsys.capella.core.transition.system.topdown.rules.oa.EntityRule {

  @Override
  protected void retrieveContainer(EObject element_p, List<EObject> result_p, IContext context_p) {
  }

  @Override
  public EClass getTargetType(EObject element_p, IContext context_p) {
    EObject object =
        TopDownTransformationHelper.getInstance(context_p).getBestTracedElement(element_p, context_p, new EClassSelectionContext(CsPackage.Literals.COMPONENT));
    if (object != null) {
      return object.eClass();
    }
    return CtxPackage.Literals.SYSTEM;
  }

  @Override
  protected EObject transformDirectElement(EObject element_p, IContext context_p) {
    EClass targetType = getTargetType(element_p, context_p);
    if (CtxPackage.Literals.SYSTEM.isSuperTypeOf(targetType)) {
      // Retrieve the existing architecture if any
      EObject root = TransformationHandlerHelper.getInstance(context_p).getLevelElement(element_p, context_p);

      BlockArchitecture target =
          (BlockArchitecture) TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(root, context_p, CsPackage.Literals.BLOCK_ARCHITECTURE);
      if (target instanceof SystemAnalysis) {
        SystemAnalysis analysis = (SystemAnalysis) target;
        if (analysis.getOwnedSystem() != null) {
          return analysis.getOwnedSystem();
        }
      }
    }
    EObject res = super.transformDirectElement(element_p, context_p);
    return res;
  }

  @Override
  protected EObject getBestContainer(EObject element_p, EObject result_p, IContext context_p) {
    //We don't care traceability, we return default container
    return null;
  }

  @Override
  protected EObject getDefaultContainer(EObject element_p, EObject result_p, IContext context_p) {
    EObject root = TransformationHandlerHelper.getInstance(context_p).getLevelElement(element_p, context_p);
    BlockArchitecture target =
        (BlockArchitecture) TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(root, context_p, CsPackage.Literals.BLOCK_ARCHITECTURE,
            element_p, result_p);

    EClass targetType = getTargetType(element_p, context_p);
    if (CtxPackage.Literals.SYSTEM.isSuperTypeOf(targetType)) {
      return target;
    }

    return BlockArchitectureExt.getActorPkg(target);
  }

}
