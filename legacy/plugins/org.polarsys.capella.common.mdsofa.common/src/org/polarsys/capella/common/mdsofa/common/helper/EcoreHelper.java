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
package org.polarsys.capella.common.mdsofa.common.helper;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.polarsys.capella.common.mdsofa.common.constant.IEmfConstants;

/**
 * Ecore helper that enhances the {@link EcoreUtil} class.
 */
public class EcoreHelper {
  /**
   * Returns <code>true</code> if <code>eObject1_p</code> and <code>eObject2_p</code> are through {@link EcoreUtil#equals(Object)}.<br>
   * If not, an ultimate check is performed for {@link EClassifier} and {@link EAnnotation}.<br>
   * For {@link EClassifier}, classifier names and parent package name-space URIs are tested.<br>
   * For {@link EPackage}, name-space URIs are tested.<br>
   * For {@link EAnnotation}, annotation sources are tested.
   * @param eObject1_p
   * @param eObject2_p
   * @param compareWholeStructure_p Should both objects be compared as tree roots ? <code>true</code> if so.<br>
   *          This comparison takes significantly more time than the other one.
   * @return <code>true</code> means equals.
   */
  public static boolean equals(EObject eObject1_p, EObject eObject2_p, boolean compareWholeStructure_p) {
    boolean result = compareWholeStructure_p && EcoreUtil.equals(eObject1_p, eObject2_p);
    // If meta-models for given eObjects are not generated, the EcoreUtil.equals() method fails.
    // Give an ultimate chance to get the right result.
    if (!result) {
      if ((eObject1_p instanceof EPackage) && (eObject2_p instanceof EPackage)) {
        // Try to check packages based on name-space URIs.
        result = ((EPackage) eObject1_p).getNsURI().equals(((EPackage) eObject2_p).getNsURI());
      } else if ((eObject1_p instanceof ENamedElement) && (eObject2_p instanceof ENamedElement)) {
        // Try to check the named element names and the container values.
        ENamedElement eObject1 = (ENamedElement) eObject1_p;
        ENamedElement eObject2 = (ENamedElement) eObject2_p;
        if (eObject1.getName().equals(eObject2.getName())) {
          // If names are equals, check also containers.
          EObject eObject1Container = eObject1.eContainer();
          EObject eObject2Container = eObject2.eContainer();
          result = equals(eObject1Container, eObject2Container, compareWholeStructure_p);
        }
      } else if ((eObject1_p instanceof EAnnotation) && (eObject2_p instanceof EAnnotation)) {
        // Try to check annotation sources.
        EAnnotation eAnnotation1 = (EAnnotation) eObject1_p;
        EAnnotation eAnnotation2 = (EAnnotation) eObject2_p;
        result = eAnnotation1.getSource().equals(eAnnotation2.getSource());
      }
    }
    return result;
  }

  /**
   * Compare both objects for equality.<br>
   * Equivalent to calling {@link #equals(EObject, EObject, boolean)} with boolean value set to <code>true</code>.
   * @param eObject1_p
   * @param eObject2_p
   * @return
   */
  public static boolean equals(EObject eObject1_p, EObject eObject2_p) {
    return equals(eObject1_p, eObject2_p, true);
  }

  /**
   * Get static ecore package from serialized one.<br>
   * That implies that the corresponding ecore model has been generated once.
   * @param serializedPackage_p
   * @return null if no generated package could be found.
   */
  public static EPackage getStaticPackage(EPackage serializedPackage_p) {
    EPackage staticPackageInstance = null;
    // Get the equivalent from the Global EPackage registry.
    Object staticPackage = EPackage.Registry.INSTANCE.get(serializedPackage_p.getNsURI());
    if (null != staticPackage) {
      if (staticPackage instanceof EPackage) {
        staticPackageInstance = (EPackage) staticPackage;
      } else if (staticPackage instanceof EPackage.Descriptor) {
        staticPackageInstance = ((EPackage.Descriptor) staticPackage).getEPackage();
      }
    }
    return staticPackageInstance;
  }

  /**
   * Get static class from serialized one.
   * @param serializedClass_p
   * @return null if static class could not be found.
   */
  public static EClass getStaticClass(EClass serializedClass_p) {
    return getStaticClass(getStaticPackage(getRootPackage(serializedClass_p.getEPackage())), serializedClass_p);
  }

  /**
   * Get static class from given containing static package and given serialized form.
   * @param staticPackage_p
   * @param serializedClass_p
   * @return null if no corresponding static class could be found.
   */
  private static EClass getStaticClass(EPackage staticPackage_p, EClass serializedClass_p) {
    EClass result = null;
    // Search for classifiers.
    for (Iterator<EClassifier> classifiers = staticPackage_p.getEClassifiers().iterator(); classifiers.hasNext() && (null == result);) {
      EClassifier classifier = classifiers.next();
      if (equals(classifier, serializedClass_p)) {
        result = (EClass) classifier;
      }
    }
    // Search in sub-packages.
    if (null == result) {
      for (Iterator<EPackage> subpackages = staticPackage_p.getESubpackages().iterator(); subpackages.hasNext() && (null == result);) {
        result = getStaticClass(subpackages.next(), serializedClass_p);
      }
    }
    return result;
  }

  /**
   * Get root package for given one.<br>
   * Root package being the eldest parent package.
   * @param package_p
   * @return
   */
  public static EPackage getRootPackage(EPackage package_p) {
    EPackage result = null;
    EPackage rootPackage = package_p;
    while (null != rootPackage) {
      result = rootPackage;
      rootPackage = result.getESuperPackage();
    }
    return result;
  }

  /**
   * Get root package for specified element.<br>
   * Root package being the eldest parent package.
   * @param element_p
   * @return
   */
  public static EPackage getRootPackage(ENamedElement element_p) {
    EPackage ownerPackage = getContainingPackage(element_p);
    // Precondition.
    if (null == ownerPackage) {
      return null;
    }
    return EcoreHelper.getRootPackage(ownerPackage);
  }

  /**
   * Get containing package for specified element.
   * @param element_p
   * @return
   */
  public static EPackage getContainingPackage(ENamedElement element_p) {
    EPackage ownerPackage = null;
    // Precondition.
    if (null == element_p) {
      return ownerPackage;
    }
    // Get owner package of specified element.
    if (element_p instanceof EClassifier) {
      ownerPackage = ((EClassifier) element_p).getEPackage();
    } else {
      ownerPackage = ((EClassifier) element_p.eContainer()).getEPackage();
    }
    return ownerPackage;
  }

  /**
   * Get generated package Java name for given package.
   * @param genModelRelativePath_p
   * @param package_p
   * @return
   */
  public static String getImportedPackageName(EPackage package_p) {
    String result = null;
    // Load generation model.
    GenModel genModel = loadGenModel(package_p);
    // Precondition.
    if (null == genModel) {
      return result;
    }
    // Filter to genPackages.
    Collection<GenPackage> genPackages = genModel.getAllGenPackagesWithClassifiers();
    for (GenPackage genPackage : genPackages) {
      // Found corresponding genPackage, get its Java interface name.
      if (equals(package_p, genPackage.getEcorePackage(), false)) {
        result = genPackage.getQualifiedPackageName();
        break;
      }
    }
    return result;
  }

  /**
   * Get the generation model for specified element.
   * @param element_p
   * @return <code>null</code> if no generation model found for specified element.<br>
   *         That means the plug-in that hosts the specified element is not installed or 'generated_package' extension is missing.
   */
  public static GenModel loadGenModel(EPackage package_p) {
    GenModel result = null;
    // Precondition.
    if (null == package_p) {
      return result;
    }
    EPackage rootPackage = getRootPackage(package_p);
    String rootPackageNsUri = rootPackage.getNsURI();
    IConfigurationElement configurationElement =
                                                 ExtensionPointHelper.getConfigurationElement(IEmfConstants.GENERATED_PACKAGE_EXTENSION_POINT_PLUGIN_ID,
                                                                                              IEmfConstants.GENERATED_PACKAGE_EXTENSION_POINT_SHORT_ID,
                                                                                              IEmfConstants.GENERATED_PACKAGE_EXTENSION_POINT_ATT_URI,
                                                                                              rootPackageNsUri);
    // Could not find the extension in target platform.
    if (null == configurationElement) {
      return result;
    }
    // Load root package generation model.
    IPath genModelRelativePath = new Path(configurationElement.getContributor().getName());
    genModelRelativePath = genModelRelativePath.append(configurationElement.getAttribute(IEmfConstants.GENERATED_PACKAGE_EXTENSION_POINT_ATT_GEN_MODEL));
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.getResource(FileHelper.getFileFullUri(genModelRelativePath.toString()), true);
    if (null != resource) {
      result = (GenModel) resource.getContents().get(0);
    }
    return result;
  }
}
