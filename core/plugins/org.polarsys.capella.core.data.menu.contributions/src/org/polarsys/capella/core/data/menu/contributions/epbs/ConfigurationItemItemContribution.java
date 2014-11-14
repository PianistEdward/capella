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
package org.polarsys.capella.core.data.menu.contributions.epbs;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.data.cs.CsFactory;
import org.polarsys.capella.core.data.epbs.ConfigurationItem;
import org.polarsys.capella.core.data.epbs.ConfigurationItemKind;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.epbs.EpbsPackage;
import org.polarsys.capella.core.data.information.PartitionableElement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.common.menu.dynamic.CreationHelper;
import org.polarsys.capella.common.menu.dynamic.contributions.IMDEMenuItemContribution;

public class ConfigurationItemItemContribution implements IMDEMenuItemContribution {

  /**
   * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#executionContribution()
   */
  public Command executionContribution(final EditingDomain editingDomain_p, ModelElement containerElement_p, final ModelElement createdElement_p,
      EStructuralFeature feature_p) {
    if (createdElement_p instanceof ConfigurationItem) {
      EObject partOwner =
          (containerElement_p instanceof ConfigurationItem) ? containerElement_p : EcoreUtil2.getFirstContainer(containerElement_p,
              EpbsPackage.Literals.CONFIGURATION_ITEM);
      if (partOwner == null) {
        EObject arch = EcoreUtil2.getFirstContainer(containerElement_p, EpbsPackage.Literals.EPBS_ARCHITECTURE);
        if (arch != null) {
          partOwner = ((EPBSArchitecture) arch).getOwnedEPBSContext();
        }
      }

      CompoundCommand cmd = new CompoundCommand();

      if (((PartitionableElement) createdElement_p).getRepresentingPartitions().size() == 0) {

        if (partOwner != null) {

          // Creates the part.
          final Command createPartCmd =
              CreateChildCommand.create(editingDomain_p, partOwner, new CommandParameter(createdElement_p,
                  CapellacorePackage.Literals.CLASSIFIER__OWNED_FEATURES, CsFactory.eINSTANCE.createPart()), Collections.EMPTY_LIST);
          cmd.append(createPartCmd);

          // Sets the part name.
          final Command setPartNameCmd = new CommandWrapper() {
            @Override
            public Command createCommand() {
              Collection<?> res = createPartCmd.getResult();
              if (res.size() == 1) {
                Object createdPart = res.iterator().next();
                if (createdPart instanceof EObject) {
                  return new SetCommand(editingDomain_p, (EObject) createdPart, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME,
                      ((AbstractNamedElement) createdElement_p).getName());
                }
              }
              return new IdentityCommand();
            }
          };
          cmd.append(setPartNameCmd);

          // Sets the part type.
          Command setPartTypeCmd = new CommandWrapper() {
            @Override
            public Command createCommand() {
              Collection<?> res = setPartNameCmd.getResult();
              if (res.size() == 1) {
                Object createdPart = res.iterator().next();
                if (createdPart instanceof EObject) {
                  return new SetCommand(editingDomain_p, (EObject) createdPart, ModellingcorePackage.Literals.ABSTRACT_TYPED_ELEMENT__ABSTRACT_TYPE,
                      createdElement_p);
                }
              }
              return null;
            }
          };
          cmd.append(setPartTypeCmd);

        }
      }

      //Set the name if kind is already set
      ConfigurationItem item = (ConfigurationItem) createdElement_p;
      if (item.getKind() != ConfigurationItemKind.UNSET) {
        if (createdElement_p instanceof AbstractNamedElement) {
          String name = ((AbstractNamedElement) createdElement_p).getName();
          if ((name == null) || name.startsWith(createdElement_p.eClass().getName())) {
            cmd.append(CreationHelper.getNamingCommand(editingDomain_p, (AbstractNamedElement) createdElement_p, containerElement_p, feature_p, item.getKind()
                .getName()));
          }
        }
      }
      return cmd;
    }
    return new IdentityCommand();
  }

  /**
   * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#selectionContribution()
   */
  public boolean selectionContribution(ModelElement modelElement_p, EClass cls_p, EStructuralFeature feature_p) {
    return true;
  }

  /**
   * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#getMetaclass()
   */
  public EClass getMetaclass() {
    return EpbsPackage.Literals.CONFIGURATION_ITEM;
  }
}
