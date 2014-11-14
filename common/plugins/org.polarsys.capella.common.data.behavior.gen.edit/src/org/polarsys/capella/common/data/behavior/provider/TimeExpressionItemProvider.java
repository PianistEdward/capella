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
package org.polarsys.capella.common.data.behavior.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.polarsys.capella.common.data.behavior.BehaviorPackage;
import org.polarsys.capella.common.data.behavior.TimeExpression;
import org.polarsys.capella.common.data.modellingcore.provider.AbstractTypedElementItemProvider;
import org.polarsys.capella.common.model.copypaste.SharedInitializeCopyCommand;
import org.polarsys.kitalpha.emde.extension.ExtensionModelManager;
import org.polarsys.kitalpha.emde.extension.ModelExtensionHelper;

/**
 * This is the item provider adapter for a {@link org.polarsys.capella.common.data.behavior.TimeExpression} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TimeExpressionItemProvider
	extends AbstractTypedElementItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IItemPropertyDescriptor observationsPropertyDescriptor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IItemPropertyDescriptor expressionPropertyDescriptor;

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeExpressionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void checkChildCreationExtender(Object object) {
		super.checkChildCreationExtender(object);
		if (object instanceof EObject) {
			EObject eObject = (EObject) object;
			// Process BehaviorPackage.Literals.TIME_EXPRESSION__OBSERVATIONS
			if (observationsPropertyDescriptor != null) {
				Object observationsValue = eObject.eGet(BehaviorPackage.Literals.TIME_EXPRESSION__OBSERVATIONS, true);
				if (observationsValue != null && observationsValue instanceof EObject && ModelExtensionHelper.getInstance().isExtensionModelDisabled((EObject) observationsValue)) {
					itemPropertyDescriptors.remove(observationsPropertyDescriptor);
				} else if (observationsValue == null && ExtensionModelManager.getAnyType(eObject, BehaviorPackage.Literals.TIME_EXPRESSION__OBSERVATIONS) != null) {
					itemPropertyDescriptors.remove(observationsPropertyDescriptor);				  					
				} else if (itemPropertyDescriptors.contains(observationsPropertyDescriptor) == false) {
					itemPropertyDescriptors.add(observationsPropertyDescriptor);
				}
			}
			// Process BehaviorPackage.Literals.TIME_EXPRESSION__EXPRESSION
			if (expressionPropertyDescriptor != null) {
				Object expressionValue = eObject.eGet(BehaviorPackage.Literals.TIME_EXPRESSION__EXPRESSION, true);
				if (expressionValue != null && expressionValue instanceof EObject && ModelExtensionHelper.getInstance().isExtensionModelDisabled((EObject) expressionValue)) {
					itemPropertyDescriptors.remove(expressionPropertyDescriptor);
				} else if (expressionValue == null && ExtensionModelManager.getAnyType(eObject, BehaviorPackage.Literals.TIME_EXPRESSION__EXPRESSION) != null) {
					itemPropertyDescriptors.remove(expressionPropertyDescriptor);				  					
				} else if (itemPropertyDescriptors.contains(expressionPropertyDescriptor) == false) {
					itemPropertyDescriptors.add(expressionPropertyDescriptor);
				}
			}
		}		
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addObservationsPropertyDescriptor(object);
			addExpressionPropertyDescriptor(object);
		}
		// begin-extension-code
		checkChildCreationExtender(object);
		// end-extension-code
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Observations feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addObservationsPropertyDescriptor(Object object) {
		// begin-extension-code
		observationsPropertyDescriptor = createItemPropertyDescriptor
		// end-extension-code		
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TimeExpression_observations_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TimeExpression_observations_feature", "_UI_TimeExpression_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 BehaviorPackage.Literals.TIME_EXPRESSION__OBSERVATIONS,
				 true,
				 false,
				 true,
				 null,
				 null,
		// begin-extension-code
				 null);
		itemPropertyDescriptors.add(observationsPropertyDescriptor);
		// end-extension-code
	}

	/**
	 * This adds a property descriptor for the Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExpressionPropertyDescriptor(Object object) {
		// begin-extension-code
		expressionPropertyDescriptor = createItemPropertyDescriptor
		// end-extension-code		
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TimeExpression_expression_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_TimeExpression_expression_feature", "_UI_TimeExpression_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 BehaviorPackage.Literals.TIME_EXPRESSION__EXPRESSION,
				 true,
				 false,
				 true,
				 null,
				 null,
		// begin-extension-code
				 null);
		itemPropertyDescriptors.add(expressionPropertyDescriptor);
		// end-extension-code
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
	 String[] result = new String[] { null };

    	//begin-capella-code
		String label = ((TimeExpression)object).getName();
		//end-capella-code
	  
	
			result[0] = label == null || label.length() == 0 ?
			//begin-capella-code
			"[" + getString("_UI_TimeExpression_type") + "]" : label; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			//end-capella-code

		return result[0];

	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	// begin-capella-code
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Command createInitializeCopyCommand(EditingDomain domain_p, EObject owner_p, Helper helper_p) {
		return new SharedInitializeCopyCommand(domain_p, owner_p, helper_p);
	}
	// end-capella-code
}
