/**
 * Copyright (c) 2010 Development Gateway (www.developmentgateway.org)
 *
*/
package org.dgfoundation.amp.onepager.components.features.subsections;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.dgfoundation.amp.onepager.OnePagerUtil;
import org.dgfoundation.amp.onepager.components.features.items.AmpFundingItemFeaturePanel;
import org.dgfoundation.amp.onepager.components.features.tables.AmpDonorDisbursementsFormTableFeature;
import org.dgfoundation.amp.onepager.components.fields.AmpAjaxLinkField;
import org.dgfoundation.amp.onepager.components.fields.AmpButtonField;
import org.digijava.module.aim.dbentity.AmpFunding;
import org.digijava.module.aim.dbentity.AmpFundingDetail;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.util.CurrencyUtil;
import org.digijava.module.aim.util.FeaturesUtil;

/**
 * Displays the funding disbursements subsection
 * @author mpostelnicu@dgateway.org
 * since Nov 8, 2010
 */
public class AmpDonorDisbursementsSubsectionFeature extends
		AmpSubsectionFeaturePanel<AmpFunding> {

	protected AmpDonorDisbursementsFormTableFeature disbursementsTableFeature;
	
	public AmpDonorDisbursementsFormTableFeature getDisbursementsTableFeature() {
		return disbursementsTableFeature;
	}

	/**
	 * @param id
	 * @param fmName
	 * @param model
	 * @throws Exception
	 */
	public AmpDonorDisbursementsSubsectionFeature(String id,
			final IModel<AmpFunding> model, String fmName, int transactionType) throws Exception {
		super(id, fmName, model);
		disbursementsTableFeature = new AmpDonorDisbursementsFormTableFeature("disbursementsTableFeature", model, "Disbursements Table");
		add(disbursementsTableFeature);
		
		AmpAjaxLinkField addCommit=new AmpAjaxLinkField("addDisbursement","Add Disbursement","Add Disbursement") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AmpFundingDetail fd= new AmpFundingDetail();
				fd.setAmpFundingId(model.getObject());
				//fd.setTransactionAmount(0d);
				fd.setReportingDate(new Date(System.currentTimeMillis()));
			//	fd.setAdjustmentType(Constants.ACTUAL);
//				fd.setTransactionDate(new Date(System.currentTimeMillis()));
				fd.setAmpCurrencyId(CurrencyUtil.getCurrencyByCode(FeaturesUtil.getGlobalSettingValue( GlobalSettingsConstants.BASE_CURRENCY )));
				fd.setTransactionType(Constants.DISBURSEMENT);
				disbursementsTableFeature.getEditorList().addItem(fd);
				target.addComponent(disbursementsTableFeature);
				AmpFundingItemFeaturePanel parent = this.findParent(AmpFundingItemFeaturePanel.class);
				parent.getFundingInfo().checkChoicesRequired(disbursementsTableFeature.getEditorList().getCount());
				target.addComponent(parent.getFundingInfo());
				target.appendJavascript(OnePagerUtil.getToggleChildrenJS(parent.getFundingInfo()));
			}
		};
		add(addCommit);
	}

}
