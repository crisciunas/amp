/**
 * @author dan
 *
 * 
 */
package org.dgfoundation.amp.visibility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.digijava.module.aim.dbentity.AmpFeaturesVisibility;
import org.digijava.module.aim.dbentity.AmpFieldsVisibility;
import org.digijava.module.aim.dbentity.AmpModulesVisibility;
import org.digijava.module.aim.dbentity.AmpTemplatesVisibility;
import org.digijava.module.aim.util.FeaturesUtil;

import bsh.util.Util;

/**
 * @author dan
 * 
 */
public class AmpTreeVisibility {

	/**
	 * 
	 */
	private AmpObjectVisibility root;

	// key-> name
	// object-> AmpTreeVisibility(template,module,feature,field)

	private HashMap items;

	private LinkedHashMap sorteditems;

	public HashMap getItems() {
		/*
		 * TreeMap mySet=new TreeMap(FeaturesUtil.ALPHA_AMP_TREE_ORDER);
		 * mySet.putAll(this.getItems()); LinkedHashMap sortedItems=new
		 * LinkedHashMap(); sortedItems.putAll(mySet); return sortedItems;
		 */
		return items;
	}

	public void setItems(HashMap items) {
		this.items = items;
	}

	public AmpObjectVisibility getRoot() {
		return root;
	}

	public void setRoot(AmpObjectVisibility root) {
		this.root = root;
	}

	public AmpTreeVisibility() {
		super();
		items = new HashMap();
		sorteditems = new LinkedHashMap();
		// root=new AmpObjectVisibility();
		// TODO Auto-generated constructor stub
	}

	public boolean buildAmpTreeVisibilityMultiLevel(
			AmpObjectVisibility ampObjVis) {
		this.root = ampObjVis;
		this.setItems(new LinkedHashMap());
		boolean existSubmodules = false;
		for (Iterator it = ampObjVis.getAllItems().iterator(); it.hasNext();) {
			boolean notEmpty = false;
			AmpModulesVisibility module = (AmpModulesVisibility) it.next();
			AmpTreeVisibility moduleNode = new AmpTreeVisibility();
			if (!module.getSubmodules().isEmpty())
				existSubmodules = true;
			if (module.getParent() == null) {
				moduleNode.setRoot(module);
				moduleNode.setItems(new LinkedHashMap());
				getModuleTree(moduleNode);
				notEmpty = true;
			}

			else {
				existSubmodules = true;
				;// //System.out.println(" si NU are
					// submodule:::"+module.getName());
			}
			if (notEmpty)
				this.getItems().put(module.getName(), moduleNode);
		}
		return existSubmodules;
	}

	public int getModuleTree(AmpTreeVisibility moduleNode) {
		AmpModulesVisibility module = (AmpModulesVisibility) moduleNode
				.getRoot();
		if (module.getSubmodules().isEmpty()) {
			for (Iterator jt = module.getSortedAlphaItems().iterator(); jt
					.hasNext();) // getItems
			{
				AmpFeaturesVisibility feature = (AmpFeaturesVisibility) jt
						.next();
				AmpTreeVisibility featureNode = new AmpTreeVisibility();
				featureNode.setRoot(feature);
				featureNode.setItems(new LinkedHashMap());
				for (Iterator kt = feature.getSortedAlphaItems().iterator(); kt
						.hasNext();)// getItems
				{
					AmpFieldsVisibility field = (AmpFieldsVisibility) kt.next();
					AmpTreeVisibility fieldNode = new AmpTreeVisibility();
					fieldNode.setRoot(field);
					fieldNode.setItems(null);
					featureNode.getItems().put(field.getName(), fieldNode);
				}
				moduleNode.getItems().put(feature.getName(), featureNode);
			}
			return 0;
		}

		for (Iterator it = module.getSortedAlphaSubModules().iterator(); it
				.hasNext();) {
			AmpModulesVisibility moduleAux = (AmpModulesVisibility) it.next();
			AmpTreeVisibility moduleNodeAux = new AmpTreeVisibility();
			moduleNodeAux.setRoot(moduleAux);
			{
				getModuleTree(moduleNodeAux);
			}
			moduleNode.getItems().put(moduleAux.getName(), moduleNodeAux);
		}
		return 1;
	}

	public void buildAmpTreeVisibility(AmpObjectVisibility ampObjVis) {
		// buildAmpTreeVisibilityMultiLevel(ampObjVis);
		this.root = ampObjVis;
		this.setItems(new HashMap());
		if (ampObjVis.getAllItems() != null)
			if (ampObjVis.getAllItems().iterator() != null)
				for (Iterator it = ampObjVis.getAllItems().iterator(); it
						.hasNext();) {
					AmpModulesVisibility module = (AmpModulesVisibility) it
							.next();
					AmpTreeVisibility moduleNode = new AmpTreeVisibility();
					moduleNode.setRoot(module);
					for (Iterator jt = module.getItems().iterator(); jt
							.hasNext();) {
						AmpFeaturesVisibility feature = (AmpFeaturesVisibility) jt
								.next();
						AmpTreeVisibility featureNode = new AmpTreeVisibility();
						featureNode.setRoot(feature);
						for (Iterator kt = feature.getItems().iterator(); kt
								.hasNext();) {
							AmpFieldsVisibility field = (AmpFieldsVisibility) kt
									.next();
							AmpTreeVisibility fieldNode = new AmpTreeVisibility();
							fieldNode.setRoot(field);
							fieldNode.setItems(null);
							featureNode.getItems().put(field.getName(),
									fieldNode);
						}
						moduleNode.getItems().put(feature.getName(),
								featureNode);
					}
					this.getItems().put(module.getName(), moduleNode);
				}
		this.root = ampObjVis;
	}

	public void displayVisibilityTreeForDebug(AmpTreeVisibility atv) {
		for (Iterator it = atv.getItems().values().iterator(); it.hasNext();) {
			Object obj = it.next();
			AmpTreeVisibility treeNode = (AmpTreeVisibility) obj;
			displayRecTreeForDebug(treeNode);
		}

	}

	public void displayRecTreeForDebug(AmpTreeVisibility atv) {
		if (atv.getItems() == null)
			return;
		if (atv.getItems().isEmpty())
			return;
		for (Iterator it = atv.getItems().values().iterator(); it.hasNext();) {
			AmpTreeVisibility auxTree = (AmpTreeVisibility) it.next();
			displayRecTreeForDebug(auxTree);
		}
	}

	public boolean isVisibleObject(AmpObjectVisibility aov) {
		if (this.getRoot().getTemplates().contains(aov.getId())) {
			return true;
		}
		return false;
	}

	public boolean isVisibleId(Long id) {
		for (Iterator it = this.getRoot().getTemplates().iterator(); it
				.hasNext();) {

			AmpTemplatesVisibility x = (AmpTemplatesVisibility) it.next();
			if (x.getId().compareTo(id) == 0)
				return true;

		}
		return false;
	}

	public AmpFieldsVisibility getFieldByNameFromRoot(String fieldName) {

		for (Iterator it = this.getItems().values().iterator(); it.hasNext();) {
			AmpTreeVisibility module = (AmpTreeVisibility) it.next();
			for (Iterator jt = module.getItems().values().iterator(); jt
					.hasNext();) {
				AmpTreeVisibility feature = (AmpTreeVisibility) jt.next();
				if (feature.getItems().containsKey(fieldName))
					return (AmpFieldsVisibility) ((AmpTreeVisibility) feature
							.getItems().get(fieldName)).getRoot();
			}
		}
		return null;
	}

	public AmpFeaturesVisibility getFeatureByNameFromRoot(String featureName) {
		for (Iterator it = this.getItems().values().iterator(); it.hasNext();) {
			AmpTreeVisibility module = (AmpTreeVisibility) it.next();
			if (module.getItems().containsKey(featureName))
				return (AmpFeaturesVisibility) ((AmpTreeVisibility) module
						.getItems().get(featureName)).getRoot();

		}
		return null;
	}

	public AmpTreeVisibility getFeatureTreeByNameFromRoot(String featureName) {
		for (Iterator it = this.getItems().values().iterator(); it.hasNext();) {
			AmpTreeVisibility module = (AmpTreeVisibility) it.next();
			if (module.getItems().containsKey(featureName))
				return (AmpTreeVisibility) module.getItems().get(featureName);

		}
		return null;
	}

	public AmpModulesVisibility getModuleByNameFromRoot(String moduleName) {
		if (this.getItems().containsKey(moduleName))
			return (AmpModulesVisibility) ((AmpTreeVisibility) this.getItems()
					.get(moduleName)).getRoot();
		return null;
	}

	public AmpTreeVisibility getModuleTreeByNameFromRoot(String moduleName) {
		if (this.getItems().containsKey(moduleName))
			return (AmpTreeVisibility) this.getItems().get(moduleName);
		return null;
	}

	public LinkedHashMap getSorteditems() {
		TreeMap mySet = new TreeMap(items);
		sorteditems.putAll(mySet);
		return sorteditems;
	}
	
	public void setSorteditems(LinkedHashMap sorteditems) {
		this.sorteditems = sorteditems;
	}

}