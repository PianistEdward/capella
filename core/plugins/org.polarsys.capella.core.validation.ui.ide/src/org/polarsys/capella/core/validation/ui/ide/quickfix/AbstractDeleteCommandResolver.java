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
package org.polarsys.capella.core.validation.ui.ide.quickfix;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.statushandlers.StatusManager;

import org.polarsys.capella.core.platform.sirius.ui.commands.CapellaDeleteCommand;
import org.polarsys.capella.core.validation.ui.ide.PluginActivator;
import org.polarsys.capella.common.helpers.adapters.MDEAdapterFactory;
import org.polarsys.capella.common.tig.ef.command.AbstractReadWriteCommand;

/**
 * Delete Element(s), with confirmation (ok:yes, cancel:no). Also delete the marker.
 */
public abstract class AbstractDeleteCommandResolver extends AbstractCapellaMarkerResolution {

  /**
   * QF implementations must override this method to give the element(s) to delete.
   * @param obj_p
   * @return the Object to delete or a Collection of Objects to delete, <code>null</code> or an empty Collection if no element to delete.
   */
  public abstract Object getElementToDelete(Object obj_p);

  /**
   * {@inheritDoc}
   */
  @Override
  public void run(IMarker marker_p) {
    // Get ModelElement associated to the marker.
    List<EObject> modelElements = getModelElements(marker_p);
    if (modelElements.isEmpty()) {
      return;
    }
    final Object modelElement = modelElements.get(0);
    // Get element(s) to delete from QF implementation.
    Object elementToDelete = getElementToDelete(modelElement);
    final Collection<?>[] listOfElementsToDelete = new Collection<?>[1];
    if (null == elementToDelete) {
      return;
    } else if (elementToDelete instanceof Collection<?>) {
      if (((Collection<?>) elementToDelete).isEmpty()) {
        return;
      }
      listOfElementsToDelete[0] = (Collection<?>) elementToDelete;
    } else {
      listOfElementsToDelete[0] = Collections.singletonList(elementToDelete);
    }

    final boolean mustDeleteMarker[] = { false };
    AbstractReadWriteCommand abstrctCommand = new AbstractReadWriteCommand() {
      @Override
      public void run() {
        // Ask user for confirmation.
        boolean confirmDeletion = CapellaDeleteCommand.confirmDeletion(MDEAdapterFactory.getExecutionManager(), listOfElementsToDelete[0]);
        if (confirmDeletion) {
          CapellaDeleteCommand command = new CapellaDeleteCommand(MDEAdapterFactory.getExecutionManager(), listOfElementsToDelete[0], false, false, true);
          if (command.canExecute()) {
            command.execute();
            // Element (s) deleted -> delete maker too.
            mustDeleteMarker[0] = true;
          }
        }

      }
    };
    MDEAdapterFactory.getExecutionManager().execute(abstrctCommand);

    // Remove the marker if the element is deleted.
    if (mustDeleteMarker[0] == true) {
      try {
        marker_p.delete();
      } catch (CoreException exception_p) {
        // no nothing
      }
    }

  }
  
  
  @Override
  public void run(IMarker[] markers, IProgressMonitor monitor) {
  
    final Set<Object> toDelete = new HashSet<Object>();
    
    for (IMarker marker : markers){
      // Get ModelElement associated to the marker.
      List<EObject> modelElements = getModelElements(marker);
      if (modelElements.isEmpty()) {
        return;
      }
      
      Object elementToDelete = getElementToDelete(modelElements.get(0));
      
      if (elementToDelete instanceof Collection<?>) {
        if (((Collection<?>) elementToDelete).isEmpty()) {
          return;
        }
        toDelete.addAll((Collection<?>) elementToDelete);
      } else if (elementToDelete != null) {
        toDelete.add(elementToDelete);
      }
    }
    
    final AtomicReference<Boolean> mustDeleteMarker = new AtomicReference<Boolean>(Boolean.FALSE);
    
    if (toDelete.isEmpty()){
      mustDeleteMarker.set(Boolean.TRUE);
    } else {
      AbstractReadWriteCommand abstrctCommand = new AbstractReadWriteCommand() {
        @Override
        public void run() {
          // Ask user for confirmation.
          boolean confirmDeletion = CapellaDeleteCommand.confirmDeletion(MDEAdapterFactory.getExecutionManager(), toDelete);
          if (confirmDeletion) {
            CapellaDeleteCommand command = new CapellaDeleteCommand(MDEAdapterFactory.getExecutionManager(), toDelete, false, false, true);
            if (command.canExecute()) {
              command.execute();
              // Element (s) deleted -> delete maker too.
              mustDeleteMarker.set(Boolean.TRUE);
            }
          }
        }
      };
      MDEAdapterFactory.getExecutionManager().execute(abstrctCommand);
    }

    // Remove the marker if the element is deleted.
    if (mustDeleteMarker.get().booleanValue()) {
      for (IMarker marker : markers){
        if (marker.exists()){
          try {
            marker.delete();
          } catch (CoreException exception_p) {
            StatusManager.getManager().handle(new Status(IStatus.ERROR, PluginActivator.getDefault().getPluginId(), exception_p.getMessage(), exception_p));
          }
        }
      }
    } 
  }
  
  
  
}
