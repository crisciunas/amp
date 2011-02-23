/**
 * 
 */
package org.dgfoundation.amp.permissionmanager.components.features.sections;

import javax.swing.tree.TreeModel;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.model.IModel;
import org.dgfoundation.amp.onepager.util.AmpFMTypes;
import org.dgfoundation.amp.permissionmanager.components.features.models.AmpPMCheckBoxTree;
import org.dgfoundation.amp.permissionmanager.components.features.models.AmpTreeVisibilityModelBean;

/**
 * @author dan
 *
 */
public class AmpPMTreeVisibilityFieldPermission extends AmpPMBaseTreePanel implements IAjaxIndicatorAware  {

	private BaseTree tree;
	private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();
	
	public String getAjaxIndicatorMarkupId() {
		return indicatorAppender.getMarkupId();
	}

	/**
	 * @param id
	 * @param ampTreeVisibilityModel
	 * @param string
	 */
	public AmpPMTreeVisibilityFieldPermission(String id, IModel<TreeModel> iTreeModel, String fmName) {
		super(id, iTreeModel, fmName);
		
		tree = new AmpPMCheckBoxTree("tree", iTreeModel.getObject());//PMUtil.createTreeModel(ampTreeVisibilityModel)
        tree.getTreeState().setAllowSelectMultiple(true);
        add(tree);
        tree.getTreeState().collapseAll();
        add(indicatorAppender);
       
	}


	public void refreshTree(IModel<TreeModel> iTreeModel){
		tree.setModelObject(iTreeModel.getObject());
		tree.invalidateAll();
	}

	/* (non-Javadoc)
	 * @see org.dgfoundation.amp.permissionmanager.components.features.sections.AmpPMBaseTreePanel#getTree()
	 */
	@Override
	protected AbstractTree getTree() {
		// TODO Auto-generated method stub
		return tree;
	}
	

	/**
	 * @param id
	 * @param fmName
	 */
	public AmpPMTreeVisibilityFieldPermission(String id, String fmName) {
		super(id, fmName);
		// TODO Auto-generated constructor stub
	}

}
