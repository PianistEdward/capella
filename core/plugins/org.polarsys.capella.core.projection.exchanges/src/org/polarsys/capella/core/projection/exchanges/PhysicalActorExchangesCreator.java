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
package org.polarsys.capella.core.projection.exchanges;

import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;

/**
 *
 */
public class PhysicalActorExchangesCreator implements IExchangesCreator {
  /**
   * The component which will be the starting point of the exchanges creation
   */
  protected Component _component;

  private Part _part = null;
  
  /**
   * Constructor
   * @param component_p the component which will be the starting point of the exchanges creation
   */
  public PhysicalActorExchangesCreator(Component component_p, Part part_p) {
    _component = component_p;
    _part = part_p;
  }
  
  /**
   * Create the exchanges which are needed to be created automatically.<br>
   * The type of exchange may be different between 2 implementations. It can be a component exchange, a physical link,... 
   */
  public void createExchanges() {
    new DefaultExchangesCreator(_component).createExchanges();
    new NodePhysicalComponentExchangesCreator(_component, _part).createExchanges();
  }
}
