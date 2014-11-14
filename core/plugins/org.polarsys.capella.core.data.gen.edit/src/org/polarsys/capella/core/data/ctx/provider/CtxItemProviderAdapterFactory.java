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
package org.polarsys.capella.core.data.ctx.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.polarsys.capella.common.data.activity.AbstractActivity;
import org.polarsys.capella.common.data.activity.ActivityGroup;
import org.polarsys.capella.common.data.activity.ActivityPackage;
import org.polarsys.capella.common.data.activity.util.ActivitySwitch;
import org.polarsys.capella.core.data.capellamodeller.provider.CapellaModellerEditPlugin;
import org.polarsys.capella.core.data.ctx.CtxFactory;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.ctx.util.CtxAdapterFactory;
import org.polarsys.kitalpha.emde.extension.ModelExtensionHelper;
import org.polarsys.kitalpha.emde.model.edit.provider.ChildCreationExtenderManager;
import org.polarsys.kitalpha.emde.model.edit.provider.NewChildDescriptorHelper;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CtxItemProviderAdapterFactory extends CtxAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This helps manage the child creation extenders.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(CapellaModellerEditPlugin.INSTANCE, CtxPackage.eNS_URI);

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtxItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemAnalysis} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemAnalysisItemProvider systemAnalysisItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemAnalysis}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemAnalysisAdapter() {
		if (systemAnalysisItemProvider == null) {
			systemAnalysisItemProvider = new SystemAnalysisItemProvider(this);
		}

		return systemAnalysisItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.System} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemItemProvider systemItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.System}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemAdapter() {
		if (systemItemProvider == null) {
			systemItemProvider = new SystemItemProvider(this);
		}

		return systemItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemFunction} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemFunctionItemProvider systemFunctionItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemFunction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemFunctionAdapter() {
		if (systemFunctionItemProvider == null) {
			systemFunctionItemProvider = new SystemFunctionItemProvider(this);
		}

		return systemFunctionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemFunctionPkg} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemFunctionPkgItemProvider systemFunctionPkgItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemFunctionPkg}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemFunctionPkgAdapter() {
		if (systemFunctionPkgItemProvider == null) {
			systemFunctionPkgItemProvider = new SystemFunctionPkgItemProvider(this);
		}

		return systemFunctionPkgItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemCommunicationHook} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemCommunicationHookItemProvider systemCommunicationHookItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemCommunicationHook}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemCommunicationHookAdapter() {
		if (systemCommunicationHookItemProvider == null) {
			systemCommunicationHookItemProvider = new SystemCommunicationHookItemProvider(this);
		}

		return systemCommunicationHookItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemCommunication} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemCommunicationItemProvider systemCommunicationItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemCommunication}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemCommunicationAdapter() {
		if (systemCommunicationItemProvider == null) {
			systemCommunicationItemProvider = new SystemCommunicationItemProvider(this);
		}

		return systemCommunicationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.Actor} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorItemProvider actorItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.Actor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createActorAdapter() {
		if (actorItemProvider == null) {
			actorItemProvider = new ActorItemProvider(this);
		}

		return actorItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.ActorCapabilityInvolvement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorCapabilityInvolvementItemProvider actorCapabilityInvolvementItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.ActorCapabilityInvolvement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createActorCapabilityInvolvementAdapter() {
		if (actorCapabilityInvolvementItemProvider == null) {
			actorCapabilityInvolvementItemProvider = new ActorCapabilityInvolvementItemProvider(this);
		}

		return actorCapabilityInvolvementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.ActorMissionInvolvement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorMissionInvolvementItemProvider actorMissionInvolvementItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.ActorMissionInvolvement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createActorMissionInvolvementAdapter() {
		if (actorMissionInvolvementItemProvider == null) {
			actorMissionInvolvementItemProvider = new ActorMissionInvolvementItemProvider(this);
		}

		return actorMissionInvolvementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.ActorPkg} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActorPkgItemProvider actorPkgItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.ActorPkg}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createActorPkgAdapter() {
		if (actorPkgItemProvider == null) {
			actorPkgItemProvider = new ActorPkgItemProvider(this);
		}

		return actorPkgItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.Mission} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MissionItemProvider missionItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.Mission}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMissionAdapter() {
		if (missionItemProvider == null) {
			missionItemProvider = new MissionItemProvider(this);
		}

		return missionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.MissionPkg} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MissionPkgItemProvider missionPkgItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.MissionPkg}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMissionPkgAdapter() {
		if (missionPkgItemProvider == null) {
			missionPkgItemProvider = new MissionPkgItemProvider(this);
		}

		return missionPkgItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemMissionInvolvement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemMissionInvolvementItemProvider systemMissionInvolvementItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemMissionInvolvement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemMissionInvolvementAdapter() {
		if (systemMissionInvolvementItemProvider == null) {
			systemMissionInvolvementItemProvider = new SystemMissionInvolvementItemProvider(this);
		}

		return systemMissionInvolvementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.Capability} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CapabilityItemProvider capabilityItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.Capability}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCapabilityAdapter() {
		if (capabilityItemProvider == null) {
			capabilityItemProvider = new CapabilityItemProvider(this);
		}

		return capabilityItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.CapabilityExploitation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CapabilityExploitationItemProvider capabilityExploitationItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.CapabilityExploitation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCapabilityExploitationAdapter() {
		if (capabilityExploitationItemProvider == null) {
			capabilityExploitationItemProvider = new CapabilityExploitationItemProvider(this);
		}

		return capabilityExploitationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.CapabilityPkg} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CapabilityPkgItemProvider capabilityPkgItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.CapabilityPkg}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createCapabilityPkgAdapter() {
		if (capabilityPkgItemProvider == null) {
			capabilityPkgItemProvider = new CapabilityPkgItemProvider(this);
		}

		return capabilityPkgItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemCapabilityInvolvement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemCapabilityInvolvementItemProvider systemCapabilityInvolvementItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemCapabilityInvolvement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemCapabilityInvolvementAdapter() {
		if (systemCapabilityInvolvementItemProvider == null) {
			systemCapabilityInvolvementItemProvider = new SystemCapabilityInvolvementItemProvider(this);
		}

		return systemCapabilityInvolvementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.OperationalActorRealization} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationalActorRealizationItemProvider operationalActorRealizationItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.OperationalActorRealization}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createOperationalActorRealizationAdapter() {
		if (operationalActorRealizationItemProvider == null) {
			operationalActorRealizationItemProvider = new OperationalActorRealizationItemProvider(this);
		}

		return operationalActorRealizationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.OperationalAnalysisRealization} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationalAnalysisRealizationItemProvider operationalAnalysisRealizationItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.OperationalAnalysisRealization}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createOperationalAnalysisRealizationAdapter() {
		if (operationalAnalysisRealizationItemProvider == null) {
			operationalAnalysisRealizationItemProvider = new OperationalAnalysisRealizationItemProvider(this);
		}

		return operationalAnalysisRealizationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.OperationalEntityRealization} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationalEntityRealizationItemProvider operationalEntityRealizationItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.OperationalEntityRealization}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createOperationalEntityRealizationAdapter() {
		if (operationalEntityRealizationItemProvider == null) {
			operationalEntityRealizationItemProvider = new OperationalEntityRealizationItemProvider(this);
		}

		return operationalEntityRealizationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.polarsys.capella.core.data.ctx.SystemContext} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemContextItemProvider systemContextItemProvider;

	/**
	 * This creates an adapter for a {@link org.polarsys.capella.core.data.ctx.SystemContext}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createSystemContextAdapter() {
		if (systemContextItemProvider == null) {
			systemContextItemProvider = new SystemContextItemProvider(this);
		}

		return systemContextItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<IChildCreationExtender> getChildCreationExtenders() {
		return childCreationExtenderManager.getChildCreationExtenders();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
		return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return childCreationExtenderManager;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (systemAnalysisItemProvider != null) systemAnalysisItemProvider.dispose();
		if (systemItemProvider != null) systemItemProvider.dispose();
		if (systemFunctionItemProvider != null) systemFunctionItemProvider.dispose();
		if (systemFunctionPkgItemProvider != null) systemFunctionPkgItemProvider.dispose();
		if (systemCommunicationHookItemProvider != null) systemCommunicationHookItemProvider.dispose();
		if (systemCommunicationItemProvider != null) systemCommunicationItemProvider.dispose();
		if (actorItemProvider != null) actorItemProvider.dispose();
		if (actorCapabilityInvolvementItemProvider != null) actorCapabilityInvolvementItemProvider.dispose();
		if (actorMissionInvolvementItemProvider != null) actorMissionInvolvementItemProvider.dispose();
		if (actorPkgItemProvider != null) actorPkgItemProvider.dispose();
		if (missionItemProvider != null) missionItemProvider.dispose();
		if (missionPkgItemProvider != null) missionPkgItemProvider.dispose();
		if (systemMissionInvolvementItemProvider != null) systemMissionInvolvementItemProvider.dispose();
		if (capabilityItemProvider != null) capabilityItemProvider.dispose();
		if (capabilityExploitationItemProvider != null) capabilityExploitationItemProvider.dispose();
		if (capabilityPkgItemProvider != null) capabilityPkgItemProvider.dispose();
		if (systemCapabilityInvolvementItemProvider != null) systemCapabilityInvolvementItemProvider.dispose();
		if (operationalActorRealizationItemProvider != null) operationalActorRealizationItemProvider.dispose();
		if (operationalAnalysisRealizationItemProvider != null) operationalAnalysisRealizationItemProvider.dispose();
		if (operationalEntityRealizationItemProvider != null) operationalEntityRealizationItemProvider.dispose();
		if (systemContextItemProvider != null) systemContextItemProvider.dispose();
	}

	/**
	 * A child creation extender for the {@link ActivityPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class ActivityChildCreationExtender implements IChildCreationExtender {
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends ActivitySwitch<Object> {
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseAbstractActivity(AbstractActivity object) {
				// begin-extension-code
				if (ModelExtensionHelper.getInstance().isExtensionModelDisabled(EcoreUtil.getRootContainer(object).eClass().getEPackage().getNsURI(), "http://www.polarsys.org/capella/core/ctx/0.8.0")) { //$NON-NLS-1$
					return null;				
				}
				// end-extension-code
                // begin-extension-code
                {
                    CommandParameter commandParameter = createChildParameter
                        (ActivityPackage.Literals.ABSTRACT_ACTIVITY__OWNED_NODES,
                         CtxFactory.eINSTANCE.createSystemFunction());
                    if (NewChildDescriptorHelper.isValidCommand(object, commandParameter)) {
                        newChildDescriptors.add(commandParameter);      
                    }
                }
                // end-extension-code



				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseActivityGroup(ActivityGroup object) {
				// begin-extension-code
				if (ModelExtensionHelper.getInstance().isExtensionModelDisabled(EcoreUtil.getRootContainer(object).eClass().getEPackage().getNsURI(), "http://www.polarsys.org/capella/core/ctx/0.8.0")) { //$NON-NLS-1$
					return null;				
				}
				// end-extension-code
                // begin-extension-code
                {
                    CommandParameter commandParameter = createChildParameter
                        (ActivityPackage.Literals.ACTIVITY_GROUP__OWNED_NODES,
                         CtxFactory.eINSTANCE.createSystemFunction());
                    if (NewChildDescriptorHelper.isValidCommand(object, commandParameter)) {
                        newChildDescriptors.add(commandParameter);      
                    }
                }
                // end-extension-code



				return null;
			}
 
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child) {
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
			ArrayList<Object> result = new ArrayList<Object>();
		   new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
		   return result;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public ResourceLocator getResourceLocator() {
			return CapellaModellerEditPlugin.INSTANCE;
		}
	}

}
