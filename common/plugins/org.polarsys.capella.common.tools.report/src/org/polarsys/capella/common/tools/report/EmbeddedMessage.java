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
package org.polarsys.capella.common.tools.report;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.polarsys.capella.common.helpers.validation.IValidationConstants;
import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;

/**
 * A log message that is associated to one or more model elements.
 * 
 */
public class EmbeddedMessage {
  private String _label;
  private String _componentName;
  private List<Object> _capellaElements = new ArrayList<Object>();
  private String _info; // additional info FIXME this is just a workaround for the information view to display the message source
  
  
  /**
   * IMarker attribute that will contain related object URIs,
   * separated by ICommonConstants.LINE_SEPARATOR
   */
  public static final String AFFECTED_OBJECTS_URI = "affectedUri"; //$NON-NLS-1$
  
  /**
   * Constructor without object list
   * @param label_p : Title message
   * @param componentName_p : Component name
   */
  public EmbeddedMessage(String label_p, String componentName_p) {
    setLabel(label_p);
    setComponentName(componentName_p);
  }

  public EmbeddedMessage(String label_p, String componentName_p, List<Object> capellaElements_p) {
    this(label_p, componentName_p);
    setCapellaElements(capellaElements_p);
  }

  @SuppressWarnings("unchecked")
  public EmbeddedMessage(String label_p, String componentName_p, Object capellaElements_p) {
    this(label_p, componentName_p);
    if (capellaElements_p!=null) {
      if (capellaElements_p instanceof List<?>) {
        setCapellaElements((List<Object>)capellaElements_p);
      } else {
        List<Object> objets = new ArrayList<Object>();
        if (capellaElements_p instanceof Object[]) {
          for (Object obj : (Object[])capellaElements_p) {
            if (obj instanceof List<?>) {
              objets.addAll((List<?>)obj);
            } else {
              objets.add(obj);
            }
          }
        } else {
          objets.add(capellaElements_p);
        }
        setCapellaElements(objets);
      }
    }
  }

  @SuppressWarnings("nls")
  @Override
  public String toString() {
    StringBuffer containsMessage = new StringBuffer();
    containsMessage.append("[" + getComponentName() + "]\t");
    // Why 9
    if (getComponentName().length() < 9) {
      containsMessage.append('\t');
    }
    containsMessage.append(getLabel());
    // Not required in case of console output
    if (_capellaElements != null && _capellaElements.size() > 0) {
      containsMessage.append(" - Object(s) list : \n");
      for (Object theElement : _capellaElements) {
        if (theElement != null) {

          containsMessage.append(getName(theElement));
          containsMessage.append("\n");
        }
      }
    }
    return containsMessage.toString();
  }

  /**
   * @param theElement
   * @param name
   * @return
   */
  private String getName(Object theElement) {
    String name = null;

    try {
      name = (String) theElement.getClass().getMethod("getFullLabel", new Class[] {}).invoke(theElement, (Object[]) new Class[] {}); //$NON-NLS-1$
      
    } catch (Throwable exception_p) {
    } finally {
      if (name == null) {
        name = theElement.toString();
      }
    }
    return name;
  }

  public String getComponentName() {
    return _componentName;
  }

  public void setComponentName(String componentName_p) {
    _componentName = componentName_p;
  }

  public String getLabel() {
    return _label;
  }

  public void setLabel(String label_p) {
    _label = label_p;
  }

  public List<Object> getCapellaElements() {
    return _capellaElements;
  }

  public void setCapellaElements(List<Object> capellaElements_p) {
    _capellaElements = capellaElements_p;
  }
  
  public void setInfo(String info_p){
    _info = info_p;
  }

  @Override
  public boolean equals(Object ob) {
    if(ob instanceof EmbeddedMessage)
    {
      EmbeddedMessage newOb = (EmbeddedMessage)ob;
      boolean notEquElts = true;
      
      for (Object currentElt : this.getCapellaElements()) {
        for (Object newObElt : newOb.getCapellaElements()) {
          notEquElts = !currentElt.equals(newObElt);
        }
      }
      
      return  (
                this.getComponentName().equals(newOb.getComponentName()) &&
                this.getLabel().equals(newOb.getLabel()) &&
                !notEquElts
               );
    } 
    return false;
  }
  
  /**
   * Adapt the message and its specific features into the target element
   */
  public void adapt(Object target) {
    if (target!=null && target instanceof IMarker) {
      String uris = ICommonConstants.EMPTY_STRING;
      IMarker marker = (IMarker) target;
      for(Object object : getCapellaElements()) {
        if (object instanceof EObject) {
          EObject obj = (EObject) object;
          uris += EcoreUtil.getURI(obj) + ICommonConstants.LINE_SEPARATOR;
        }
      }
     
      try {
        if (uris.length() > 0){
          marker.setAttribute(AFFECTED_OBJECTS_URI, uris);
        }
        
        // FIXME this is a workaround to display some arbitrary info on the marker view..
        if (_info != null){
          marker.setAttribute(IValidationConstants.TAG_RULE_ID, _info);
        }
        
      } catch (CoreException exception_p) {
        //FIXME //Nothing to do
      }
    }
  }
}
