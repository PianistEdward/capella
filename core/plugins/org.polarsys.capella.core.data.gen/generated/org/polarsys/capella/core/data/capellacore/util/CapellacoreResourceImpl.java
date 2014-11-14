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
package org.polarsys.capella.core.data.capellacore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.polarsys.capella.common.data.core.gen.xmi.impl.CapellaXMLSaveImpl;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.helpers.adapters.MDEAdapterFactory;
import org.polarsys.kitalpha.emde.xmi.XMIExtensionHelperImpl;
import org.polarsys.kitalpha.emde.xmi.XMIExtensionLoadImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.polarsys.capella.core.data.capellacore.util.CapellacoreResourceFactoryImpl
 * @generated
 */
public class CapellacoreResourceImpl extends XMIResourceImpl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */  
	private List<Object> lookupTable = new ArrayList<Object>();
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */  
	private XMLParserPool parserPool = new XMLParserPoolImpl();
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */  
	private Map<Object, Object> nameToFeatureMap = new HashMap<Object, Object>();

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public CapellacoreResourceImpl(URI uri) {
		super(uri);
	}

	/**
	   * {@inheritDoc}
	   * @generated
	   */
	  @Override
	  protected void attachedHelper(EObject eObject_p) {
	    // Make sure specified object has its id generated.
	    if (eObject_p instanceof ModelElement) {
	      setID(eObject_p, ((ModelElement) eObject_p).getId());
	    }
	    super.attachedHelper(eObject_p);
	  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected XMLSave createXMLSave() {
		return new CapellaXMLSaveImpl(createXMLHelper());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected XMLHelper createXMLHelper() {
		return new XMIExtensionHelperImpl(this);	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected XMLLoad createXMLLoad() {
		return new XMIExtensionLoadImpl((XMIExtensionHelperImpl) createXMLHelper());	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void init() {
		super.init();

		setTrackingModification(true);
		// Save Options
		getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");  //$NON-NLS-1$
		getDefaultSaveOptions().put(XMLResource.OPTION_CONFIGURATION_CACHE, Boolean.TRUE);
		getDefaultSaveOptions().put(XMLResource.OPTION_USE_CACHED_LOOKUP_TABLE, lookupTable);    
		getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);    
		getDefaultSaveOptions().put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);    
		getDefaultSaveOptions().put(XMLResource.OPTION_LINE_WIDTH, new Integer(80));
		getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());		        
		getDefaultSaveOptions().put(XMLResource.OPTION_FLUSH_THRESHOLD, Integer.valueOf(0x01000000));
		getDefaultSaveOptions().put(XMLResource.OPTION_USE_FILE_BUFFER, Boolean.TRUE);

		// Load Options
		getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_PARSER_POOL, parserPool);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, nameToFeatureMap);
		getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		getDefaultLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#getEObjectByID(java.lang.String)
	 * @generated
	 */
	@Override
	protected EObject getEObjectByID(String id) {
		if (idToEObjectMap == null)
			return super.getEObjectByID(id);
		return getIDToEObjectMap().get(id);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#getIntrinsicIDToEObjectMap()
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map getIntrinsicIDToEObjectMap() {
		return getIDToEObjectMap();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#doUnload()
	 * @generated
	 */
	@Override
	protected void doUnload() {
    	List< Resource > resourcesToNotUnload = (List<Resource>) MDEAdapterFactory.getResourceSet().getLoadOptions().get("hackDoremi");
		// Dirty hack to allow libraries to work until TIG is not modified to support multiple ResourceSet (as it should be according to the philosophy of session
		// management in DOREMI)
		// Explanation : because TIG does not follow the right way to use DOREMI session, when a session is closed, resources of libraries dependent of the model
		// session are unloaded. The result is that semantics resources of session become inconsistent.
		// Let say we have a project P1 referencing a library L1. We open session of L1, then close it. If we open the session of P1, the resources representing
		// L1 in semantic resources of P1 and L1 are different (which must not be the case) because doremi unload resource of L1 in context of P1 during the
		// close of L1.
		// To avoid that behavior, the implementation of session close defines in the resource set (key "hackDoremi") the set of resources not to be unloaded. 
    	if ((resourcesToNotUnload == null) || !resourcesToNotUnload.contains(this)) {
    		super.doUnload();
    	}
    	this.idToEObjectMap = null;

		// original code
		//super.doUnload();
		//this.idToEObjectMap = null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Dirty hack to allow libraries to work until TIG is not modified to support multiple ResourceSet
	 * (as it should be according to the philosophy of session management in DOREMI)
	 * Explanation : When we do not the unload (hack of doUnload()), we must discard the fact that the Resource is set as not loaded (pcr00116186)
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#setLoaded()
	 * @generated
	 */
	@Override
	protected Notification setLoaded(boolean isLoaded) {
		List<Resource> resourcesToNotUnload = (List<Resource>) MDEAdapterFactory.getResourceSet().getLoadOptions().get("hackDoremi");
		if ((resourcesToNotUnload == null) || !resourcesToNotUnload.contains(this)) {
			return super.setLoaded(isLoaded);
		} else {
			return null;
		}
	}
      //end-capella-code

	//end-capella-code
	
} //CapellacoreResourceImpl