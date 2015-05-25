/**
 * Copyright (c) 2010 Development Gateway (www.developmentgateway.org)
 *
 */
package org.dgfoundation.amp.onepager.components.features.subsections;

import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.dgfoundation.amp.onepager.components.AmpComponentPanel;
import org.dgfoundation.amp.onepager.components.features.items.AmpAgreementItemPanel;
import org.dgfoundation.amp.onepager.components.fields.AmpCategorySelectFieldPanel;
import org.dgfoundation.amp.onepager.components.fields.AmpDatePickerFieldPanel;
import org.dgfoundation.amp.onepager.components.fields.AmpFundingSummaryPanel;
import org.dgfoundation.amp.onepager.components.fields.AmpTextAreaFieldPanel;
import org.dgfoundation.amp.onepager.components.fields.AmpTextFieldPanel;
import org.dgfoundation.amp.onepager.events.FundingSectionSummaryEvent;
import org.dgfoundation.amp.onepager.models.ValueToSetModel;
import org.digijava.module.aim.dbentity.AmpFunding;
import org.digijava.module.aim.dbentity.AmpFundingMTEFProjection;
import org.digijava.module.aim.helper.GlobalSettingsConstants;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;

/**
 * @author mpostelnicu@dgateway.org since Nov 4, 2010
 */
public class AmpDonorFundingInfoSubsectionFeature extends
		AmpSubsectionFeaturePanel<AmpFunding> {

	
	private AmpCategorySelectFieldPanel financingInstrument;
	private AmpCategorySelectFieldPanel typeOfAssistance;
	private AmpTextAreaFieldPanel loanTerms;
	public void checkChoicesRequired(int size) {
		if (size > 0) {
			financingInstrument.getChoiceContainer().setRequired(true);
			typeOfAssistance.getChoiceContainer().setRequired(true);
		} else {
			financingInstrument.getChoiceContainer().setRequired(false);
			typeOfAssistance.getChoiceContainer().setRequired(false);
		}
	}
	
	/**
	 * @param id
	 * @param fmName
	 * @param model
	 * @throws Exception
	 */
	public AmpDonorFundingInfoSubsectionFeature(String id,
			final IModel<AmpFunding> model, String fmName,final AmpFundingSummaryPanel sp) throws Exception {
		super(id, fmName, model, false, true);
		
		financingInstrument = new AmpCategorySelectFieldPanel(
				"financingInstrument",
				CategoryConstants.FINANCING_INSTRUMENT_KEY,
				new PropertyModel<AmpCategoryValue>(model,
						"financingInstrument"),
				CategoryConstants.FINANCING_INSTRUMENT_NAME, true, false);		
		add(financingInstrument);
		
		// LoanTerms
		typeOfAssistance = new AmpCategorySelectFieldPanel(
				"typeOfAssistance", CategoryConstants.TYPE_OF_ASSISTENCE_KEY,
				new PropertyModel<AmpCategoryValue>(model, "typeOfAssistance"),
				CategoryConstants.TYPE_OF_ASSISTENCE_NAME, true, false);
		
		//for issue AMP-16434 both selects where made smaller 
		financingInstrument.getChoiceContainer().add(new AttributeModifier("style", "max-width: 210px!important;"));
		typeOfAssistance.getChoiceContainer().add(new AttributeModifier("style", "max-width: 210px!important;"));
	
	     financingInstrument.getChoiceContainer().add(new AttributeModifier("style", "max-width: 210px!important;"));
	     typeOfAssistance.getChoiceContainer().add(new AttributeModifier("style", "max-width: 210px!important;"));
		loanTerms =  new AmpTextAreaFieldPanel("loanTerms", 
				          new PropertyModel<String>(model, "loanTerms"), 
				          "Loan Terms", false, false, false);
		
		loanTerms.getTextAreaContainer().add(new AttributeModifier("style", "width: 225px; height: 65px;"));          	
			
		AmpCategoryValue value = (AmpCategoryValue) typeOfAssistance.getChoiceContainer().getModelObject();
		boolean isLoan = (value == null ? false : value.getValue().equals(CategoryConstants.TYPE_OF_ASSISTANCE_LOAN.getValueKey()));
		loanTerms.getTextAreaContainer().setVisible(isLoan);
	    loanTerms.getTitleLabel().setVisible(isLoan);  
	    add(loanTerms);
	    
		typeOfAssistance.getChoiceContainer().add(new AjaxFormComponentUpdatingBehavior("onchange") {        
			private static final long serialVersionUID = -6492252081340597543L;
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				AmpCategoryValue value = (AmpCategoryValue) typeOfAssistance.getChoiceContainer().getModelObject();
				boolean isLoan = (value == null ? false : (value.getValue().equals(CategoryConstants.TYPE_OF_ASSISTANCE_LOAN.getValueKey())));
				loanTerms.getTextAreaContainer().setVisible(isLoan);
        	    loanTerms.getTitleLabel().setVisible(isLoan);            	
            	target.add(loanTerms);
            	AmpFundingSummaryPanel l=
            	findParent(AmpFundingSummaryPanel.class);
				send(getPage(), Broadcast.BREADTH,new FundingSectionSummaryEvent(target));	
            }
        });
		//
		
		add(typeOfAssistance);
		final AmpCategorySelectFieldPanel modeOfPayment;

        PropertyModel<AmpCategoryValue> fundingStatusModel = new PropertyModel<AmpCategoryValue>(model, "fundingStatus");
        AmpCategorySelectFieldPanel fundingStatus = new AmpCategorySelectFieldPanel(
                "fundingStatus", CategoryConstants.FUNDING_STATUS_KEY,
                fundingStatusModel,
                CategoryConstants.FUNDING_STATUS_NAME, true, false);
        fundingStatus.getChoiceContainer().setRequired(false);
        add(fundingStatus);

        if ("true".equalsIgnoreCase(FeaturesUtil.getGlobalSettingValue(GlobalSettingsConstants.LINK_MODE_OF_PAYMENT_TO_FUNDING_STATUS))){
            ((DropDownChoice)fundingStatus.getChoiceContainer()).setNullValid(true);
            modeOfPayment = new AmpCategorySelectFieldPanel(
                    "modeOfPayment", CategoryConstants.MODE_OF_PAYMENT_KEY,
                    new PropertyModel<AmpCategoryValue>(model, "modeOfPayment"),
                    CategoryConstants.MODE_OF_PAYMENT_NAME, true, true, false,
                    new ValueToSetModel<AmpCategoryValue>(fundingStatusModel));

            fundingStatus.getChoiceContainer().add(
                new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        target.add(modeOfPayment);
                        send(getPage(), Broadcast.BREADTH,new FundingSectionSummaryEvent(target));
                    }
                });
        } else {
            modeOfPayment = new AmpCategorySelectFieldPanel(
                    "modeOfPayment", CategoryConstants.MODE_OF_PAYMENT_KEY,
                    new PropertyModel<AmpCategoryValue>(model, "modeOfPayment"),
                    CategoryConstants.MODE_OF_PAYMENT_NAME, true, false);

            fundingStatus.getChoiceContainer().add(
                    new AjaxFormComponentUpdatingBehavior("onchange") {
                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            send(getPage(), Broadcast.BREADTH,new FundingSectionSummaryEvent(target));
                        }
                    });
        }
        modeOfPayment.getChoiceContainer().setRequired(false);
        
        modeOfPayment.getChoiceContainer().add(
                new AjaxFormComponentUpdatingBehavior("onchange") {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        send(getPage(), Broadcast.BREADTH,new FundingSectionSummaryEvent(target));
                    }
                });
        add(modeOfPayment);

		AmpTextFieldPanel<String> financingId = new AmpTextFieldPanel<String>(
				"financingId",
				new PropertyModel<String>(model, "financingId"),
				"Funding Organization Id");
//		financingId.getNewLine().setVisible(false);
		
		financingId.getTextContainer().add(
				new AjaxFormComponentUpdatingBehavior("onchange") {

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						// TODO Auto-generated method stub
//						target.add(sp.getFinancingIdInfo());
						send(getPage(), Broadcast.BREADTH,new FundingSectionSummaryEvent(target));	
					}
				});
		add(financingId);
		
		 final PropertyModel<Date> funClassificationDateModel = new PropertyModel<Date>(
	                model, "fundingClassificationDate");
	        AmpDatePickerFieldPanel date = new AmpDatePickerFieldPanel("fundingClassificationDate", funClassificationDateModel, null, "Funding Classification Date");
	        //date.getDate().setRequired(true);
			add(date);

		if (model != null && model.getObject() != null && 
			model.getObject().getFundingDetails() != null &&
			model.getObject().getFundingDetails().size() > 0)
			checkChoicesRequired(model.getObject().getFundingDetails().size());
		else
			checkChoicesRequired(0);
		
		add(new AmpAgreementItemPanel("agreement", model, "Agreement"));
		
		
		//info section configuration
		AmpComponentPanel showTypeOfAssistance = new AmpComponentPanel(
				"showTypeOfAssistance", "Show "
						+ CategoryConstants.TYPE_OF_ASSISTENCE_NAME
						+ " in summary") {

			@Override
			protected void onConfigure() {
				// TODO Auto-generated method stub
				super.onConfigure();
				sp.getTypeOfAssistanceInfo().setVisible(this.isVisible());
			}

		};
        add(showTypeOfAssistance);

		AmpComponentPanel showFinancingInstrument = new AmpComponentPanel(
				"showFinancingInstrument", "Show "
						+ CategoryConstants.FINANCING_INSTRUMENT_NAME
						+ " in summary") {

			@Override
			protected void onConfigure() {
				super.onConfigure();
				sp.getFinancingInstrumentInfo().setVisible(this.isVisible());
			}

		};
        add(showFinancingInstrument);

		AmpComponentPanel showFinancingId = new AmpComponentPanel(
				"showFinancingId", "Show Financing id in summary") {

			@Override
			protected void onConfigure() {
				super.onConfigure();
				sp.getFinancingIdInfo().setVisible(this.isVisible());
			}

		};
				
		add(showFinancingId);

		AmpComponentPanel showFundingStatus = new AmpComponentPanel(
				"showFundingStatus", "Show "
						+ CategoryConstants.FUNDING_STATUS_NAME
						+ " in summary") {

			@Override
			protected void onConfigure() {
				super.onConfigure();
				sp.getFundingStatusInfo().setVisible(this.isVisible());
			}

		};
		
		add(showFundingStatus);
		
		AmpComponentPanel showModeOfPayment = new AmpComponentPanel(
				"showModeOfPayment", "Show "
						+ CategoryConstants.MODE_OF_PAYMENT_NAME
						+ " in summary") {

			@Override
			protected void onConfigure() {
				super.onConfigure();
				sp.getModeOfPaymentInfo().setVisible(this.isVisible());
			}

		};
		add(showModeOfPayment);
		

	}
}