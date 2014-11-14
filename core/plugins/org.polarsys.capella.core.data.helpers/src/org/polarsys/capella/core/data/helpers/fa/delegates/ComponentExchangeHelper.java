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
package org.polarsys.capella.core.data.helpers.fa.delegates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeAllocation;
import org.polarsys.capella.core.data.fa.ComponentExchangeAllocator;
import org.polarsys.capella.core.data.fa.ComponentExchangeEnd;
import org.polarsys.capella.core.data.fa.ComponentExchangeFunctionalExchangeAllocation;
import org.polarsys.capella.core.data.fa.ComponentExchangeRealization;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.helpers.information.delegates.AbstractEventOperationHelper;
import org.polarsys.capella.core.data.information.Partition;
import org.polarsys.capella.core.data.information.Port;
import org.polarsys.capella.common.data.helpers.modellingcore.delegates.AbstractTypeHelper;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.InformationsExchanger;

public class ComponentExchangeHelper {
  private static ComponentExchangeHelper instance;

  private ComponentExchangeHelper() {
    // do nothing
  }

  public static ComponentExchangeHelper getInstance() {
    if (instance == null)
      instance = new ComponentExchangeHelper();
    return instance;
  }

  public Object doSwitch(ComponentExchange element_p, EStructuralFeature feature_p) {
    Object ret = null;

    if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__ALLOCATED_FUNCTIONAL_EXCHANGES)) {
      ret = getFunctionalExchanges(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__INCOMING_COMPONENT_EXCHANGE_REALIZATIONS)) {
      ret = getIncomingComponentExchangeRealizations(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__OUTGOING_COMPONENT_EXCHANGE_REALIZATIONS)) {
      ret = getOutgoingComponentExchangeRealizations(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__OUTGOING_COMPONENT_EXCHANGE_FUNCTIONAL_EXCHANGE_ALLOCATIONS)) {
      ret = getOutgoingComponentExchangeFunctionalExchangeRealizations(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__SOURCE_PART)) {
      ret = getSourcePart(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__SOURCE_PORT)) {
      ret = getSourcePort(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__TARGET_PART)) {
      ret = getTargetPart(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__TARGET_PORT)) {
      ret = getTargetPort(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__ALLOCATOR_PHYSICAL_LINKS)) {
      ret = getAllocatorPhysicalLinks(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__REALIZED_COMPONENT_EXCHANGES)) {
      ret = getRealizedComponentExchanges(element_p);
    } else if (feature_p.equals(FaPackage.Literals.COMPONENT_EXCHANGE__REALIZING_COMPONENT_EXCHANGES)) {
      ret = getRealizingComponentExchanges(element_p);
    }

    // no helper found... searching in super classes...
    if (null == ret) {
      ret = AbstractTypeHelper.getInstance().doSwitch(element_p, feature_p);
    }
    if (null == ret) {
      ret = ExchangeSpecificationHelper.getInstance().doSwitch(element_p, feature_p);
    }
    if (null == ret) {
      ret = AbstractEventOperationHelper.getInstance().doSwitch(element_p, feature_p);
    }

    return ret;
  }

  protected List<ComponentExchangeRealization> getIncomingComponentExchangeRealizations(ComponentExchange element_p) {
    List<ComponentExchangeRealization> ret = new ArrayList<ComponentExchangeRealization>();
    for (AbstractTrace trace : element_p.getIncomingTraces()) {
      if (trace instanceof ComponentExchangeRealization) {
        ret.add((ComponentExchangeRealization) trace);
      }
    }
    return ret;
  }

  protected List<ComponentExchangeRealization> getOutgoingComponentExchangeRealizations(ComponentExchange element_p) {
    List<ComponentExchangeRealization> ret = new ArrayList<ComponentExchangeRealization>();
    for (AbstractTrace trace : element_p.getOutgoingTraces()) {
      if (trace instanceof ComponentExchangeRealization) {
        ret.add((ComponentExchangeRealization) trace);
      }
    }
    return ret;
  }

  protected List<FunctionalExchange> getFunctionalExchanges(ComponentExchange element_p) {
    List<FunctionalExchange> ret = new ArrayList<FunctionalExchange>();
    for (ComponentExchangeFunctionalExchangeAllocation item : element_p.getOutgoingComponentExchangeFunctionalExchangeAllocations()) {
      FunctionalExchange allocatedFunctionalExchange = item.getAllocatedFunctionalExchange();
      if (null != allocatedFunctionalExchange) {
        ret.add(allocatedFunctionalExchange);
      }
    }
    return ret;
  }

  protected List<ComponentExchangeFunctionalExchangeAllocation> getOutgoingComponentExchangeFunctionalExchangeRealizations(ComponentExchange element_p) {
    List<ComponentExchangeFunctionalExchangeAllocation> ret = new ArrayList<ComponentExchangeFunctionalExchangeAllocation>();
    for (AbstractTrace trace : element_p.getOutgoingTraces()) {
      if (trace instanceof ComponentExchangeFunctionalExchangeAllocation) {
        ret.add((ComponentExchangeFunctionalExchangeAllocation) trace);
      }
    }
    return ret;
  }

  protected Part getSourcePart(ComponentExchange element_p) {
    InformationsExchanger source = element_p.getSource();
    if (source instanceof ComponentExchangeEnd) {
      Partition partition = ((ComponentExchangeEnd) source).getPart();
      if (partition instanceof Part) {
        return (Part) partition;
      }
    } else if (source instanceof Part) {
      return (Part) source;
    }
    return null;
  }

  protected Port getSourcePort(ComponentExchange element_p) {
    InformationsExchanger source = element_p.getSource();
    if (source instanceof ComponentExchangeEnd) {
      return ((ComponentExchangeEnd) source).getPort();
    } else if (source instanceof Port) {
      return (Port) source;
    }
    return null;
  }

  protected Part getTargetPart(ComponentExchange element_p) {
    InformationsExchanger target = element_p.getTarget();
    if (target instanceof ComponentExchangeEnd) {
      Partition partition = ((ComponentExchangeEnd) target).getPart();
      if (partition instanceof Part) {
        return (Part) partition;
      }
    } else if (target instanceof Part) {
      return (Part) target;
    }
    return null;
  }

  protected Port getTargetPort(ComponentExchange element_p) {
    InformationsExchanger target = element_p.getTarget();
    if (target instanceof ComponentExchangeEnd) {
      return ((ComponentExchangeEnd) target).getPort();
    } else if (target instanceof Port) {
      return (Port) target;
    }
    return null;
  }

  protected List<PhysicalLink> getAllocatorPhysicalLinks(ComponentExchange element_p) {
    List<PhysicalLink> ret = new ArrayList<PhysicalLink>();
    for (AbstractTrace trace : element_p.getIncomingTraces()) {
      if (trace instanceof ComponentExchangeAllocation) {
        ComponentExchangeAllocator componentExchangeAllocator = ((ComponentExchangeAllocation)trace).getComponentExchangeAllocator();
        if (componentExchangeAllocator instanceof PhysicalLink) {
          ret.add((PhysicalLink) componentExchangeAllocator);
        }
      }
    }
    return ret;
  }

  protected List<ComponentExchange> getRealizedComponentExchanges(ComponentExchange element_p) {
    List<ComponentExchange> ret = new ArrayList<ComponentExchange>();
    for (AbstractTrace trace : element_p.getOutgoingTraces()) {
      if (trace instanceof ComponentExchangeRealization) {
        ret.add(((ComponentExchangeRealization) trace).getAllocatedComponentExchange());
      }
    }
    return ret;
  }

  protected List<ComponentExchange> getRealizingComponentExchanges(ComponentExchange element_p) {
    List<ComponentExchange> ret = new ArrayList<ComponentExchange>();
    for (AbstractTrace trace : element_p.getIncomingTraces()) {
      if (trace instanceof ComponentExchangeRealization) {
        ret.add(((ComponentExchangeRealization) trace).getAllocatingComponentExchange());
      }
    }
    return ret;
  }
}
