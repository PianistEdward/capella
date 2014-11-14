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
package org.polarsys.capella.core.sirius.ui.copylayout;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.description.ColorDescription;

import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeRealization;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.fa.FunctionalExchangeRealization;
import org.polarsys.capella.core.data.information.Port;
import org.polarsys.capella.core.data.information.PortRealization;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.TraceableElement;

/**
 *
 */
public class CapellaTraceabilityLayoutDataKey extends CapellaDecoratorLayoutDataKey {

  public CapellaTraceabilityLayoutDataKey(AbstractCapellaLayoutDataKey key_p, EObject semantic_p) {
    super(key_p);

    _semantic = semantic_p;

    if (_parent instanceof CapellaDecoratorLayoutDataKey) {
      for (Object decoration : ((CapellaDecoratorLayoutDataKey) _parent).getDecorations()) {
        if (decoration instanceof EObject) {
          EObject traced = retrieveLinkedEObject(((EObject) decoration));
          //By using this, we remove also mapping association
          if (traced != null) {
            addDecoration(traced);
          }
        }
      }
    }

  }

  @Override
  public EObject getSemantic() {
    return _semantic;
  }

  /**
   * Look for an {@link EObject} linked to given one by a
   * traceability/refinement link.
   */
  protected EObject retrieveLinkedEObject(EObject semantic) {
    EObject source = null;
    if ((semantic instanceof EClass) || (semantic instanceof ColorDescription)) {
      return semantic;

    } else if (semantic instanceof TraceableElement) {
      TraceableElement sourceOfTrace = (TraceableElement) semantic;

      for (AbstractTrace trace : sourceOfTrace.getOutgoingTraces()) {
        if (isValidTrace(trace)) {
          source = trace.getTargetElement();
        }
      }
    }
    if ((source == null) && (semantic instanceof Part)) {
      Part part = (Part) semantic;
      EObject obj = retrieveLinkedEObject(part.getAbstractType());
      if (obj instanceof Component) {
        Component linkedComponent = (Component) obj;
        for (Object ate : linkedComponent.getAbstractTypedElements()) {
          if (ate instanceof Part) {
            return (Part) ate;
          }
        }
      }
    }

    return source;
  }

  /**
   * @param trace_p
   * @return
   */
  private boolean isValidTrace(AbstractTrace trace_p) {
    if ((trace_p.getSourceElement() == null) || (trace_p.getTargetElement() == null)) {
      return false;
    }
    if (trace_p.getSourceElement() instanceof Port) {
      return trace_p instanceof PortRealization;

    } else if (trace_p.getSourceElement() instanceof ComponentExchange) {
      return trace_p instanceof ComponentExchangeRealization;

    } else if (trace_p.getSourceElement() instanceof FunctionalExchange) {
      return trace_p instanceof FunctionalExchangeRealization;

    }

    return true;
  }

}
