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
package org.polarsys.capella.core.business.queries.information;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.Classifier;
import org.polarsys.capella.core.data.capellacore.ReuseLink;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.InformationPackage;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.Parameter;
import org.polarsys.capella.core.data.information.Service;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.oa.OperationalAnalysis;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.sharedmodel.GenericPkg;
import org.polarsys.capella.core.data.sharedmodel.SharedPkg;
import org.polarsys.capella.core.model.helpers.DataPkgExt;
import org.polarsys.capella.core.model.helpers.GenericPkgExt;
import org.polarsys.capella.core.model.helpers.InterfacePkgExt;
import org.polarsys.capella.core.model.helpers.OperationExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.model.helpers.query.CapellaQueries;
import org.polarsys.capella.core.model.utils.ListExt;

/**
 */
public class Parameter_Type implements IBusinessQuery {

	/**
	 * Get DataTypes from the parent Block Architecture
	 * @param arch
	 * @param type
	 * @return
	 */
	private List<CapellaElement> getElementsFromBlockArchitecture(BlockArchitecture arch, AbstractType type) {
	List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
	if (null != arch) {
		DataPkg dataPkg = DataPkgExt.getDataPkgOfBlockArchitecture(arch);
		if (dataPkg != null) {
			for (CapellaElement element : DataPkgExt.getAllTypesFromDataPkgForPropsNParams(dataPkg)) {
				if (element.equals(type))
					continue;
				availableElements.add(element);
			}
		}
	}
	return availableElements;
	}

	/*
	 * GetsAll the Classes, Collections, Signals and DataTypes contained by the Data Package
	 * (and all of its sub-packages) of the current Element's parent (can be a
	 * Component, a Block Architecture Decomposition package, or a Block
	 * Architecture root package).
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_11(Parameter currenParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currenParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currenParameter_p.getType();
			BlockArchitecture compArch = OperationExt.getRootBlockArchitecture(operation);
			availableElements.addAll(getElementsFromBlockArchitecture(compArch, type));

			Component comp = OperationExt.getRootComponent(operation);
			if (comp != null) {
				DataPkg dataPkg =  comp.getOwnedDataPkg();
				if (dataPkg != null) {
					for (CapellaElement element : DataPkgExt.getAllTypesFromDataPkgForPropsNParams(dataPkg)) {
						if (element.equals(type))
							continue;
						availableElements.add(element);
					}
				}
			}
		}
		return availableElements;
	}

	/*
	 * All the Classes, Collections, Signals and DataTypes contained by the Data Packages
	 * (and all of its sub-packages) of the current Element's parents hierarchy
	 * according to layer visibility and multiple decomposition rules.
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_12(Parameter currentParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currentParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currentParameter_p.getType();
			List<DataPkg> dataPkgList = OperationExt.getDataPkgsFromParentHierarchy(operation);
			for (DataPkg dataPkg : dataPkgList) {
				for (CapellaElement element : DataPkgExt.getAllTypesFromDataPkgForPropsNParams(dataPkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
			}
			availableElements.addAll(getRule_MQRY_Parameter_Type_12_1(currentParameter_p));
		}
		return availableElements;
	}

	/*
	 * layer visibility
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_12_1(Parameter currentParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currentParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currentParameter_p.getType();
			BlockArchitecture arch = OperationExt.getRootBlockArchitecture(operation);
			
			SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(currentParameter_p);
			OperationalAnalysis oa = SystemEngineeringExt.getOwnedOperationalAnalysis(systemEngineering);
			if (null != oa) {
				availableElements.addAll(getElementsFromBlockArchitecture(oa, type));
			}else{
				SystemAnalysis ca = SystemEngineeringExt.getOwnedSystemAnalysis(systemEngineering);
				availableElements.addAll(getElementsFromBlockArchitecture(ca, type));
			}
			
			if (null != arch) {
				if (null != oa && (arch instanceof LogicalArchitecture) || (arch instanceof PhysicalArchitecture) || (arch instanceof EPBSArchitecture)) {
					SystemAnalysis ctx = SystemEngineeringExt.getOwnedSystemAnalysis(systemEngineering);
					availableElements.addAll(getElementsFromBlockArchitecture(ctx, type));
				}
				if ((arch instanceof PhysicalArchitecture) || (arch instanceof EPBSArchitecture)) {
					LogicalArchitecture logArch = SystemEngineeringExt.getOwnedLogicalArchitecture(systemEngineering);
					availableElements.addAll(getElementsFromBlockArchitecture(logArch, type));
				}
				if ((arch instanceof EPBSArchitecture)) {
					PhysicalArchitecture physArch = SystemEngineeringExt.getOwnedPhysicalArchitecture(systemEngineering);
					availableElements.addAll(getElementsFromBlockArchitecture(physArch, type));
				}
			}
		}
		return availableElements;
	}

	/*
	 * All the Interfaces contained by the Interface Package (and all of its
	 * subpackages) of the current Element's parent (can be a Component, a
	 * Block Architecture Decomposition package, or a Block Architecture
	 * root package).
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_13(Parameter currentParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currentParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currentParameter_p.getType();
			BlockArchitecture compArch = OperationExt.getRootBlockArchitecture(operation);
			if (null != compArch) {
				for (CapellaElement element : InterfacePkgExt.getAllInterfaces(compArch.getOwnedInterfacePkg())) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
			} else {
				Component comp = OperationExt.getRootComponent(operation);
				if (comp != null) {
					for (CapellaElement element : InterfacePkgExt.getAllInterfaces(comp.getOwnedInterfacePkg())) {
						if (element.equals(type))
							continue;
						availableElements.add(element);
					}
				}
			}
		}
		return availableElements;
	}

	/*
	 * All the Interfaces contained by the Interface Packages (and all of its
	 * subpackages) of the current Element's parents hierarchy according to
	 * layer visibility and multiple decomposition rules.
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_14(Parameter currentParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currentParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currentParameter_p.getType();
			for (CapellaElement element : OperationExt.getOwnedInterfacesFromParentHierarchy(operation)) {
				if (element.equals(type))
					continue;
				availableElements.add(element);
			}
			availableElements.addAll(getRule_MQRY_Parameter_Type_14_1(currentParameter_p));
		}
		return availableElements;
	}

	/*
	 * layer visibility
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_14_1(Parameter currentParameter_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		EObject container = currentParameter_p.eContainer();
		if (container instanceof Operation) {
			Operation operation = (Operation) container;
			AbstractType type = currentParameter_p.getType();
			BlockArchitecture arch = OperationExt.getRootBlockArchitecture(operation);
			
			SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(currentParameter_p);
			OperationalAnalysis oa = SystemEngineeringExt.getOwnedOperationalAnalysis(systemEngineering);
			if (null != oa) {
				if (null != oa) {
					for (CapellaElement element : InterfacePkgExt.getAllInterfaces(oa.getOwnedInterfacePkg())) {
						if (element.equals(type))
							continue;
						availableElements.add(element);
					}
				}
			}else{
				SystemAnalysis ca = SystemEngineeringExt.getOwnedSystemAnalysis(systemEngineering);
				if (null != ca) {
					for (CapellaElement element : InterfacePkgExt.getAllInterfaces(ca.getOwnedInterfacePkg())) {
						if (element.equals(type))
							continue;
						availableElements.add(element);
					}
				}
			}
			
			if (null != arch) {
				
				if (null != oa && (arch instanceof LogicalArchitecture) || (arch instanceof PhysicalArchitecture) || (arch instanceof EPBSArchitecture)) {
					SystemAnalysis ctx = SystemEngineeringExt.getOwnedSystemAnalysis(systemEngineering);
					if (null != ctx) {
						for (CapellaElement element : InterfacePkgExt.getAllInterfaces(ctx.getOwnedInterfacePkg())) {
							if (element.equals(type))
								continue;
							availableElements.add(element);
						}
					}	
				}
				if ((arch instanceof PhysicalArchitecture) || (arch instanceof EPBSArchitecture)) {
					LogicalArchitecture logArch = SystemEngineeringExt.getOwnedLogicalArchitecture(systemEngineering);
					if (logArch != null) {
						for (CapellaElement element : InterfacePkgExt.getAllInterfaces(logArch.getOwnedInterfacePkg())) {
							if (element.equals(type))
								continue;
							availableElements.add(element);
						}
					}
				}
				if ((arch instanceof EPBSArchitecture)) {
					PhysicalArchitecture physArch = SystemEngineeringExt.getOwnedPhysicalArchitecture(systemEngineering);
					if (physArch != null) {
						for (CapellaElement element : InterfacePkgExt.getAllInterfaces(physArch.getOwnedInterfacePkg())) {
							if (element.equals(type))
								continue;
							availableElements.add(element);
						}
					}
				}
			}
		}
		return availableElements;
	}

	/*
	 * All the Classes, Collections, Signals and DataTypes contained by the Data Package (and
	 * all of its sub-packages) of the Shared Assets Package.
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_15(Parameter currenParameter_p, SystemEngineering systemEngineering_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);

		AbstractType type = currenParameter_p.getType();
		for (SharedPkg sharedPkg : SystemEngineeringExt.getSharedPkgs(systemEngineering_p)) {
			DataPkg dataPkg = sharedPkg.getOwnedDataPkg();
			if (dataPkg != null) {
				for (CapellaElement element : DataPkgExt.getAllTypesFromDataPkgForPropsNParams(dataPkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
			}
			GenericPkg pkg = sharedPkg.getOwnedGenericPkg();
			if (pkg != null) {
				for (CapellaElement element : GenericPkgExt.getAllDataTypes(pkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
				for (CapellaElement element : GenericPkgExt.getAllClasses(pkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
				for (CapellaElement element : GenericPkgExt.getAllInterfaces(pkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
			}
		}
		return availableElements;
	}

	/*
	 * All the Interfaces contained by the Interface Package (and all of its
	 * subpackages) of the Shared Assets Package.
	 */
	private List<CapellaElement> getRule_MQRY_Parameter_Type_16(Parameter currenParameter_p, SystemEngineering systemEngineering_p) {
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
		AbstractType type = currenParameter_p.getType();
		for (SharedPkg sharedPkg : SystemEngineeringExt.getSharedPkgs(systemEngineering_p)) {

			GenericPkg pkg = sharedPkg.getOwnedGenericPkg();
			if (pkg != null) {
				for (CapellaElement element : GenericPkgExt.getAllInterfaces(pkg)) {
					if (element.equals(type))
						continue;
					availableElements.add(element);
				}
			}
		}
		return availableElements;
	}

	/**
	 * <p>
	 * Gets All the Classes ,Collections, Signals and DataTypes contained by the Data Package
	 * (and all of its sub-packages) of the current Element's parent (can be a
	 * Component, a Component Architecture Decomposition package, or a Component
	 * Architecture root package). All the Classes, Signals and DataTypes
	 * contained by the Data Packages (and all of its sub-packages) of the
	 * current Element's parents hierarchy according to layer visibility and
	 * multiple decomposition rules. All the Interfaces contained by the
	 * Interface Package (and all of its subpackages) of the current Element's
	 * parent (can be a Component, a Component Architecture Decomposition
	 * package, or a Component Architecture root package). All the Interfaces
	 * contained by the Interface Packages (and all of its subpackages) of the
	 * current Element's parents hierarchy according to layer visibility and
	 * multiple decomposition rules. All the Classes, Signals and DataTypes
	 * contained by the Data Package (and all of its sub-packages) of the Shared
	 * Assets Package. All the Interfaces contained by the Interface Package
	 * (and all of its subpackages) of the Shared Assets Package.
	 * </p>
	 * <p>
	 * Except The current type itself
	 * </p>
	 * <p>
	 * Refer MQRY_Parameter_Type_1
	 * </p>
	 * 
	 * @see org.polarsys.capella.core.business.queries.capellacore.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.common.model.CapellaElement)
	 */
	public List<CapellaElement> getAvailableElements(CapellaElement element_p) {
	  Classifier classifierToRemove = null;
		SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(element_p);
		List<CapellaElement> availableElements = new ArrayList<CapellaElement>();
		boolean isParameterFromSharedPkg = false;
		if (null == systemEngineering) {
			SharedPkg sharedPkg = SystemEngineeringExt.getSharedPkg(element_p);
			if (sharedPkg != null) {
			  for (ReuseLink link : sharedPkg.getReuseLinks()) {
			    if (SystemEngineeringExt.getSystemEngineering(link) != null) {
			      systemEngineering = SystemEngineeringExt.getSystemEngineering(link);
			      isParameterFromSharedPkg = true;
			      break;
			    }
			  }
			}
			if (systemEngineering == null)
				return availableElements;
		}

		if (element_p instanceof Parameter) {
			Parameter parameter = (Parameter) element_p;
			
			//find the classifier to remove
			EObject eContainer = parameter.eContainer();
			if (eContainer != null && eContainer instanceof Service) {
			  EObject eContainer2 = eContainer.eContainer();
			  if (eContainer2 != null && eContainer2 instanceof Classifier) {
          classifierToRemove = (Classifier) eContainer2;
        }
      }
			
			boolean isFromService = (parameter.eContainer()!=null && parameter.eContainer() instanceof Service);
			
			if (!isParameterFromSharedPkg) {
				availableElements.addAll(getRule_MQRY_Parameter_Type_11(parameter));
				availableElements.addAll(getRule_MQRY_Parameter_Type_12(parameter));
				if (!isFromService) {
	        availableElements.addAll(getRule_MQRY_Parameter_Type_13(parameter));
		      availableElements.addAll(getRule_MQRY_Parameter_Type_14(parameter));
				}
			}
			availableElements.addAll(getRule_MQRY_Parameter_Type_15(parameter, systemEngineering));
			if (!isFromService) {
			  availableElements.addAll(getRule_MQRY_Parameter_Type_16(parameter, systemEngineering));
			}
		}
		
    // remove duplicate
    availableElements = ListExt.removeDuplicates(availableElements);
    
		//remove the parent Classifier
		availableElements.remove(classifierToRemove);
		

		return availableElements;
	}

	/**
	 * <p>
	 * Gets the type of current Parameter
	 * </p>
	 * <p>
	 * Refer MQRY_Parameter_Type_1
	 * </p>
	 * 
	 * @see org.polarsys.capella.core.business.queries.capellacore.core.business.queries.IBusinessQuery#getCurrentElements(org.polarsys.capella.core.common.model.CapellaElement,
	 *      boolean)
	 */
	public List<CapellaElement> getCurrentElements(CapellaElement element_p, boolean onlyGenerated_p) {
		SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(element_p);
		List<CapellaElement> currentElements = new ArrayList<CapellaElement>();

		if (null == systemEngineering) {
			SharedPkg sharedPkg = SystemEngineeringExt.getSharedPkg(element_p);
			for (ReuseLink link : sharedPkg.getReuseLinks()) {
				if (SystemEngineeringExt.getSystemEngineering(link) != null) {
					systemEngineering = SystemEngineeringExt.getSystemEngineering(link);
					break;
				}
			}
			if (systemEngineering == null)
				return currentElements;
		}

		if (element_p instanceof Parameter) {
			Parameter parameter = (Parameter) element_p;
			AbstractType type = parameter.getType();
			if (null != type)
				currentElements.add((CapellaElement) type);
		}
		return currentElements;
	}

	public EClass getEClass() {
		return InformationPackage.Literals.PARAMETER;
	}

	public List<EReference> getEStructuralFeatures() {
    return Collections.singletonList(ModellingcorePackage.Literals.ABSTRACT_TYPED_ELEMENT__ABSTRACT_TYPE);
	}
}
