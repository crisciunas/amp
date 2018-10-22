// Generated by delombok at Mon Mar 24 00:10:06 EET 2014
package org.digijava.module.aim.dbentity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants;
import org.digijava.kernel.ampapi.endpoints.activity.InterchangeDependencyResolver;
import org.digijava.kernel.translator.TranslatorWorker;
import org.digijava.module.aim.annotations.interchange.ActivityFieldsConstants;
import org.digijava.module.aim.annotations.interchange.Interchangeable;
import org.digijava.module.aim.annotations.translation.TranslatableClass;
import org.digijava.module.aim.annotations.translation.TranslatableField;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.util.Output;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.REQUIRED_ALWAYS;

@TranslatableClass(displayName = "Funding")
public class AmpFunding implements Serializable, Versionable, Cloneable, AuditableEntity {
    //IATI-check: not ignored!
    private static final long serialVersionUID = 1L;
    
    @Interchangeable(fieldTitle = "Funding ID")
    private Long ampFundingId;
    @Interchangeable(fieldTitle = "Donor Organization ID", pickIdOnly = true, importable = true,
            required = ActivityEPConstants.REQUIRED_ALWAYS)
    private AmpOrganisation ampDonorOrgId;
    @Interchangeable(fieldTitle="Activity ID", pickIdOnly = true, importable = false)
    private AmpActivityVersion ampActivityId;
    private Long crsTransactionNo;
    @Interchangeable(fieldTitle="Financing ID",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Funding Organization Id", importable=true)
    private String financingId;
    private String fundingTermsCode;
    private Date plannedStartDate;
    private Date plannedCompletionDate;
    @Interchangeable(fieldTitle="Actual Start Date",fmPath="/Activity Form/Planning/Actual Start Date", importable=true)
    private Date actualStartDate;
    @Interchangeable(fieldTitle="Actual Completion Date",fmPath="/Activity Form/Planning/Actual Completion Date", importable=true)
    private Date actualCompletionDate;
    @Interchangeable(fieldTitle="Original Completion Date",fmPath="/Activity Form/Planning/Original Completion Date",required="/Activity Form/Planning/Required Validator for Original Completion Date", importable=true)
    private Date originalCompDate;
    private Date lastAuditDate;
    @Interchangeable(fieldTitle="Reporting Date", importable=true)
    private Date reportingDate;
    
    @Interchangeable(fieldTitle="Conditions",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Conditions", importable=true)
    @TranslatableField
    private String conditions;
    
    @Interchangeable(fieldTitle="Donor Objective",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Donor Objective", importable=true)
    @TranslatableField
    private String donorObjective;
    private String language;
    private String version;
    private String calType;
    private String comments;
    private Date signatureDate;
    
    @Interchangeable(fieldTitle = ActivityFieldsConstants.FUNDING_DETAILS, importable = true)
    private Set<AmpFundingDetail> fundingDetails;
    
    @Interchangeable(fieldTitle = ActivityFieldsConstants.MTEF_PROJECTIONS, importable = true, 
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/MTEF Projections")
    private Set<AmpFundingMTEFProjection> mtefProjections;
    
    @Interchangeable(fieldTitle="Active",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Active", importable = true)
    private Boolean active;
    @Interchangeable(fieldTitle="Delegated Cooperation",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Delegated Cooperation", importable = true)
    private Boolean delegatedCooperation;
    @Interchangeable(fieldTitle="Delegated Partner",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Delegated Partner", importable=true)
    private Boolean delegatedPartner;

    // private AmpModality modalityId;
    
    @Interchangeable(fieldTitle = "Type of Assistance", required = REQUIRED_ALWAYS, 
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Type of Assistence", 
                     discriminatorOption = CategoryConstants.TYPE_OF_ASSISTENCE_KEY, importable = true,
                     pickIdOnly = true, 
                     dependencies = {InterchangeDependencyResolver.TRANSACTION_PRESENT_KEY})
    private AmpCategoryValue typeOfAssistance;
    
    @Interchangeable(fieldTitle = "Financing Instrument", required = REQUIRED_ALWAYS,
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Financing Instrument", 
                     discriminatorOption = CategoryConstants.FINANCING_INSTRUMENT_KEY, importable = true,
                     pickIdOnly = true, dependencies = {InterchangeDependencyResolver.TRANSACTION_PRESENT_KEY})
    private AmpCategoryValue financingInstrument;
    
    @Interchangeable(fieldTitle="Funding Status", fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Funding Status", 
                     discriminatorOption = CategoryConstants.FUNDING_STATUS_KEY, importable=true, pickIdOnly=true)
    private AmpCategoryValue fundingStatus;
    
    @Interchangeable(fieldTitle="Mode of Payment", fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Mode of Payment", 
                     discriminatorOption = CategoryConstants.MODE_OF_PAYMENT_KEY, importable=true, pickIdOnly=true)
    private AmpCategoryValue modeOfPayment;
    
    @Interchangeable(fieldTitle="Concessionality Level", fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Concessionality Level", 
             discriminatorOption = CategoryConstants.CONCESSIONALITY_LEVEL_KEY, importable=true, pickIdOnly=true)
    private AmpCategoryValue concessionalityLevel;
    
    @Interchangeable(fieldTitle="Loan Terms",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Loan Terms", importable=true)
    private String loanTerms;
    @Interchangeable(fieldTitle="Group Versioned Funding", importable=true)
    private Long groupVersionedFunding;
    private Float capitalSpendingPercentage;
    
    @Interchangeable(fieldTitle="Agreement",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Agreement", importable=true, dependencies = {
            InterchangeDependencyResolver.AGREEMENT_CODE_PRESENT_KEY,
            InterchangeDependencyResolver.AGREEMENT_TITLE_PRESENT_KEY})
    private AmpAgreement agreement;

    @Interchangeable(fieldTitle = "Source Role", importable = true, pickIdOnly = true,
            required = ActivityEPConstants.REQUIRED_ALWAYS)
    private AmpRole sourceRole;
    @Interchangeable(fieldTitle="Funding Classification Date",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Funding Classification Date", importable=true)
    private Date fundingClassificationDate;
    @Interchangeable(fieldTitle="Effective Funding Date",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Effective Funding Date", importable=true)
    private Date effectiveFundingDate;
    @Interchangeable(fieldTitle="Funding Closing Date",fmPath="/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Funding Closing Date", importable=true)
    private Date fundingClosingDate;

    @Interchangeable(fieldTitle = "Ratification Date",
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Ratification Date",
            importable = true)
    private Date ratificationDate;

    @Interchangeable(fieldTitle = "Grace Period",
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Grace Period",
            importable = true)
    private Integer gracePeriod;

    @Interchangeable(fieldTitle = "Interest Rate",
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Interest Rate",
            importable = true)
    private Float interestRate;

    @Interchangeable(fieldTitle = "Maturity",
            fmPath = "/Activity Form/Funding/Funding Group/Funding Item/Funding Classification/Maturity",
            importable = true)
    private Date maturity;
    
    private Integer orderNumber;
    private Integer index;
    
    @Override
    public boolean equalsForVersioning(Object obj) {
        AmpFunding auxFunding = (AmpFunding)obj;
        if (this.groupVersionedFunding != null && this.groupVersionedFunding.equals(auxFunding.getGroupVersionedFunding())) {
            return true;
        }
        return false;
    }
    
    @Override
    public Object getValue() {
        // Compare fields from AmpFunding.
        StringBuffer ret = new StringBuffer();
        ret.append("-Type of Assistance:" + (this.typeOfAssistance != null ? this.typeOfAssistance.getEncodedValue() : ""));
        ret.append("-Financing Instrument:" + (this.financingInstrument != null ? this.financingInstrument.getEncodedValue() : ""));
        ret.append("-Funding classification date:" + (this.financingInstrument != null ? this.financingInstrument.getEncodedValue() : ""));
        ret.append("-Conditions:" + (this.conditions == null ? "" : this.conditions.trim()));
        ret.append("-Donor Objective:" + (this.donorObjective == null ? "" : this.donorObjective.trim()));
        ret.append("-Active:" + this.active);
        ret.append("-Delegated Cooperation:" + this.delegatedCooperation);
        ret.append("-Delegated Partner:" + this.delegatedPartner);
        ret.append("-Mode Of Payment:" + (this.modeOfPayment != null ? this.modeOfPayment.getEncodedValue() : ""));
        ret.append("-Concessionality Level:" + (this.concessionalityLevel != null ? this.concessionalityLevel.getEncodedValue() : ""));
        ret.append("-Funding Status:" + (this.fundingStatus != null ? this.fundingStatus.getEncodedValue() : ""));
        ret.append("-Funding Status:" + (this.financingId != null ? this.financingId : ""));
        if (this.agreement != null)
            ret.append("-Agreement:" + this.agreement.getValue());
        // Compare fields from AmpFundingDetail.
        List<AmpFundingDetail> auxDetails = new ArrayList<AmpFundingDetail>(this.fundingDetails);
        Collections.sort(auxDetails, fundingDetailsComparator);
        Iterator<AmpFundingDetail> iter = auxDetails.iterator();
        while (iter.hasNext()) {
            AmpFundingDetail auxDetail = iter.next();
            ret.append(auxDetail.getTransactionType() + "-" + auxDetail.getTransactionAmount() + "-" + auxDetail.getAmpCurrencyId() + "-" + auxDetail.getTransactionDate());
            if (auxDetail.getPledgeid() != null) ret.append(auxDetail.getPledgeid().getId());
            ret.append("-" + auxDetail.getDisbOrderId());
            if (auxDetail.getContract() != null) ret.append("-" + auxDetail.getContract().getId());
            ret.append("-" + auxDetail.getExpCategory());
            ret.append("-" + auxDetail.getDisbursementOrderRejected());
            if (auxDetail.getRecipientOrg() != null) ret.append("- recipient " + auxDetail.getRecipientOrg().getAmpOrgId() + " with role of " + auxDetail.getRecipientRole().getAmpRoleId());
        }

        // Compare fields from AmpFundingMTEFProjection.
        List<AmpFundingMTEFProjection> auxMTEFProjection = new ArrayList<AmpFundingMTEFProjection>(this
                .mtefProjections);
        Collections.sort(auxMTEFProjection, fundingMTEFProjectionComparator);
        for (AmpFundingMTEFProjection projection : auxMTEFProjection) {
            ret.append(projection.getTransactionType() + "-" + projection.getAmount() + "-"
                    + projection.getAmpCurrencyId() + "-" + projection.getProjectionDate());
            if (projection.getAdjustmentType() != null) {
                ret.append("-" + projection.getAdjustmentType().getId());
            }
        }
        return ret.toString();
    }
    // Compare by transaction type, then amount, then date.
    // (transient in order to be wicket friendly, no need to serialize this field)
    private transient Comparator fundingDetailsComparator = new Comparator(){
        
        
        public int compare(Object o1, Object o2) {
            AmpFundingDetail aux1 = (AmpFundingDetail)o1;
            AmpFundingDetail aux2 = (AmpFundingDetail)o2;
            if (aux1.getTransactionType().equals(aux2.getTransactionType())) {
                if (aux1.getTransactionAmount().equals(aux2.getTransactionAmount())) {
                    return aux1.getTransactionDate().compareTo(aux2.getTransactionDate());
                } else {
                    return aux1.getTransactionAmount().compareTo(aux2.getTransactionAmount());
                }
            } else {
                return aux1.getTransactionType().compareTo(aux2.getTransactionType());
            }
        }
    };

    private transient Comparator fundingMTEFProjectionComparator = Comparator.comparing(
            AmpFundingMTEFProjection::getTransactionType)
            .thenComparing(AmpFundingMTEFProjection::getAmount)
            .thenComparing(AmpFundingMTEFProjection::getProjectionDate);

    @Override
    public Output getOutput() {
        Output out = new Output();
        out.setOutputs(new ArrayList<Output>());
        
        String orgName = this.ampDonorOrgId.getName();
        if (this.ampDonorOrgId != null 
                && this.ampDonorOrgId.getDeleted() != null && this.ampDonorOrgId.getDeleted()) {
            orgName += " (" + TranslatorWorker.translateText("deleted") + ")";
            out.setDeletedValues(true);
        }
        out.getOutputs().add(new Output(null, new String[]{"Organization"}, new Object[]{orgName}));

        if (this.typeOfAssistance != null) {
            out.getOutputs().add(new Output(null, new String[]{"Type of Assistance"}, new Object[]{this.typeOfAssistance.getValue()}));
        }
        if (this.financingInstrument != null) {
            out.getOutputs().add(new Output(null, new String[]{"Financing Instrument"}, new Object[]{this.financingInstrument.getValue()}));
        }
        if (this.conditions != null && !this.conditions.trim().equals("")) {
            out.getOutputs().add(new Output(null, new String[]{"Conditions"}, new Object[]{this.conditions}));
        }
        if (this.donorObjective != null && !this.donorObjective.trim().equals("")) {
            out.getOutputs().add(new Output(null, new String[]{"Donor Objective"}, new Object[]{this.donorObjective}));
        }
        if (this.active != null) {
            out.getOutputs().add(new Output(null, new String[]{"Active"}, new Object[]{this.active.toString()}));
        }
        if (this.delegatedCooperation != null) {
            out.getOutputs().add(new Output(null, new String[]{"Delegated Cooperation"}, new Object[]{this.delegatedCooperation.toString()}));
        }
        if (this.delegatedPartner != null) {
            out.getOutputs().add(new Output(null, new String[]{"Delegated Partner"}, new Object[]{this.delegatedPartner.toString()}));
        }
        if (this.modeOfPayment != null) {
            out.getOutputs().add(new Output(null, new String[]{"Mode of Payment"}, new Object[]{this.modeOfPayment.getValue()}));
        }
        if (this.concessionalityLevel != null) {
            out.getOutputs().add(new Output(null, new String[]{"Concessionality Level"}, new Object[]{this.concessionalityLevel.getValue()}));
        }
        if (this.fundingStatus != null) {
            out.getOutputs().add(new Output(null, new String[]{"Funding Status"}, new Object[]{this.fundingStatus.getValue()}));
        }
        if (this.financingId != null) {
            out.getOutputs().add(new Output(null, new String[]{"Financing Id"}, new Object[]{this.financingId}));
        }
        if (this.agreement != null) {
            out.getOutputs().add(new Output(null, new String[]{"Agreement"}, new Object[]{this.agreement.getValue()}));
        }
        boolean trnComm = false;
        boolean trnDisb = false;
        boolean trnExp = false;
        boolean trnDisbOrder = false;
        boolean trnMTEF = false;
        boolean trnEDD = false;
        boolean trnRoF = false;
        List<AmpFundingDetail> auxDetails = new ArrayList(this.fundingDetails);
        Collections.sort(auxDetails, fundingDetailsComparator);
        Iterator<AmpFundingDetail> iter = auxDetails.iterator();
        while (iter.hasNext()) {
            boolean error = false;
            AmpFundingDetail auxDetail = iter.next();
            String transactionType = "";
            String extraValues = "";
            Output auxOutDetail = null;
            switch (auxDetail.getTransactionType().intValue()) {
            case Constants.COMMITMENT: 
                transactionType = "Commitments";
                if (auxDetail.getPledgeid() != null) {
                    if (auxDetail.getPledgeid().getTitle() != null) {
                        extraValues = " - " + auxDetail.getPledgeid().getTitle().getValue();
                    }
                }
                if (!trnComm) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnComm = true;
                }
                break;
            
            case Constants.DISBURSEMENT: 
                transactionType = " Disbursements";
                if (auxDetail.getDisbOrderId() != null && auxDetail.getDisbOrderId().trim().length() > 0) extraValues += " - " + auxDetail.getDisbOrderId();
                if (auxDetail.getContract() != null) extraValues += " - " + auxDetail.getContract().getContractName();
                if (auxDetail.getPledgeid() != null) {
                    if (auxDetail.getPledgeid().getTitle() != null) {
                        extraValues = " - " + auxDetail.getPledgeid().getTitle().getValue();
                    }
                }
                if (!trnDisb) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnDisb = true;
                }
                break;
            
            case Constants.EXPENDITURE: 
                transactionType = " Expenditures";
                if (auxDetail.getExpCategory() != null && auxDetail.getExpCategory().trim().length() > 0) extraValues += " - " + auxDetail.getExpCategory();
                if (!trnExp) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnExp = true;
                }
                break;
            
            case Constants.DISBURSEMENT_ORDER: 
                transactionType = " Disbursement Orders";
                if (auxDetail.getDisbOrderId() != null && auxDetail.getDisbOrderId().trim().length() > 0) extraValues += " - " + auxDetail.getDisbOrderId();
                if (auxDetail.getContract() != null) extraValues += " - " + auxDetail.getContract().getContractName();
                if (auxDetail.getDisbursementOrderRejected() != null) {
                    if (auxDetail.getDisbursementOrderRejected()) extraValues += " - Rejected"; else extraValues += " - Not Rejected";
                }
                if (!trnDisbOrder) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnDisbOrder = true;
                }
                break;
            
            case Constants.ESTIMATED_DONOR_DISBURSEMENT: 
                transactionType = " Estimated Donor Disbursements";
                if (!trnEDD) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnEDD = true;
                }
                break;
            
            case Constants.RELEASE_OF_FUNDS: 
                transactionType = " Release of Funds";
                if (!trnRoF) {
                    out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{transactionType}, new Object[]{""}));
                    trnRoF = true;
                }
                break;
            
            default: 
                error = true;
                break;
            
            }
            if (!error) {
                String recipientInfo = "";
                if (auxDetail.getRecipientOrg() != null) recipientInfo = String.format(" to %s as %s", auxDetail.getRecipientOrg().getName(), auxDetail.getRecipientRole().getName());
                String adjustment = auxDetail.getAdjustmentType().getValue();
                auxOutDetail = out.getOutputs().get(out.getOutputs().size() - 1);
                auxOutDetail.getOutputs().add(new Output(null, new String[]{""}, new Object[]{adjustment, " - ", auxDetail.getTransactionAmount(), " ", auxDetail.getAmpCurrencyId(), " - ", auxDetail.getTransactionDate(), extraValues + recipientInfo}));
            }
        }
        Iterator<AmpFundingMTEFProjection> it2 = this.mtefProjections.iterator();
        while (it2.hasNext()) {
            AmpFundingMTEFProjection mtef = (AmpFundingMTEFProjection)it2.next();
            if (!trnMTEF) {
                out.getOutputs().add(new Output(new ArrayList<Output>(), new String[]{"MTEF Projection"}, new Object[]{""}));
                trnMTEF = true;
            }
            String adjustment = mtef.getProjection().getValue();
            Output auxOutDetail = out.getOutputs().get(out.getOutputs().size() - 1);
            auxOutDetail.getOutputs().add(new Output(null, new String[]{""}, new Object[]{adjustment, " - ", mtef.getAmount(), " ", mtef.getAmpCurrency(), " - ", mtef.getProjectionDate()}));
        }
        return out;
    }
    
    @Override
    public Object prepareMerge(AmpActivityVersion newActivity) throws CloneNotSupportedException {
        AmpFunding aux = (AmpFunding)clone();
        aux.ampActivityId = newActivity;
        aux.ampFundingId = null;
        if (aux.fundingDetails != null && aux.fundingDetails.size() > 0) {
            Set<AmpFundingDetail> auxSetFD = new HashSet<AmpFundingDetail>();
            Iterator<AmpFundingDetail> iF = aux.fundingDetails.iterator();
            while (iF.hasNext()) {
                AmpFundingDetail auxFD = iF.next();
                AmpFundingDetail newFD = (AmpFundingDetail)auxFD.clone();
                newFD.setAmpFundDetailId(null);
                newFD.setAmpFundingId(aux);
                auxSetFD.add(newFD);
            }
            aux.fundingDetails = auxSetFD;
        } else {
            aux.fundingDetails = null;
        }
        if (aux.mtefProjections != null && aux.mtefProjections.size() > 0) {
            Set<AmpFundingMTEFProjection> auxSetMTEF = new HashSet<AmpFundingMTEFProjection>();
            Iterator<AmpFundingMTEFProjection> iMTEF = aux.mtefProjections.iterator();
            while (iMTEF.hasNext()) {
                AmpFundingMTEFProjection auxMTEF = iMTEF.next();
                AmpFundingMTEFProjection newMTEF = (AmpFundingMTEFProjection)auxMTEF.clone();
                newMTEF.setAmpFundingMTEFProjectionId(null);
                newMTEF.setAmpFunding(aux);
                auxSetMTEF.add(newMTEF);
            }
            aux.mtefProjections = auxSetMTEF;
        } else {
            aux.mtefProjections = null;
        }
        return aux;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        if (groupVersionedFunding == null) groupVersionedFunding = System.currentTimeMillis();
        return super.clone();
    }
    
    /**
     * should the funding under this FundingItem be counted in an activity's totals
     * @return
     */
    public boolean isCountedInTotals() {
        return this.isDonorFunding() || this.isSscFunding();
    }
    
    protected boolean isDonorFunding() {
        return (this.getSourceRole() == null) || ((this.getSourceRole() != null) && this.getSourceRole().getRoleCode().equals(Constants.ROLE_CODE_DONOR));
    }
    
    protected boolean isSscFunding() {
        for (AmpFundingDetail afd : this.fundingDetails) if (afd.isSscTransaction()) return true;
        return false;
    }
    
    public Date getFundingClassificationDate() {
        return fundingClassificationDate;
    }
    
    public void setFundingClassificationDate(Date fundingClassificationDate) {
        this.fundingClassificationDate = fundingClassificationDate;
    }

    public Date getEffectiveFundingDate() {
        return effectiveFundingDate;
    }

    public void setEffectiveFundingDate(Date effectiveFundingDate) {
        this.effectiveFundingDate = effectiveFundingDate;
    }

    public Date getFundingClosingDate() {
        return fundingClosingDate;
    }

    public void setFundingClosingDate(Date fundingClosingDate) {
        this.fundingClosingDate = fundingClosingDate;
    }

    @java.lang.SuppressWarnings("all")
    public Long getAmpFundingId() {
        return this.ampFundingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpOrganisation getAmpDonorOrgId() {
        return this.ampDonorOrgId;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpActivityVersion getAmpActivityId() {
        return this.ampActivityId;
    }
    
    @java.lang.SuppressWarnings("all")
    public Long getCrsTransactionNo() {
        return this.crsTransactionNo;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getFinancingId() {
        return this.financingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getFundingTermsCode() {
        return this.fundingTermsCode;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getPlannedStartDate() {
        return this.plannedStartDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getPlannedCompletionDate() {
        return this.plannedCompletionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getActualStartDate() {
        return this.actualStartDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getActualCompletionDate() {
        return this.actualCompletionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getOriginalCompDate() {
        return this.originalCompDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getLastAuditDate() {
        return this.lastAuditDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getReportingDate() {
        return this.reportingDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getConditions() {
        return this.conditions;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getDonorObjective() {
        return this.donorObjective;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getLanguage() {
        return this.language;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getVersion() {
        return this.version;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getCalType() {
        return this.calType;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getComments() {
        return this.comments;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getSignatureDate() {
        return this.signatureDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Set<AmpFundingDetail> getFundingDetails() {
        return this.fundingDetails;
    }
    
    @java.lang.SuppressWarnings("all")
    public Set<AmpFundingMTEFProjection> getMtefProjections() {
        return this.mtefProjections;
    }
    
    @java.lang.SuppressWarnings("all")
    public Boolean getActive() {
        return this.active;
    }
    
    @java.lang.SuppressWarnings("all")
    public Boolean getDelegatedCooperation() {
        return this.delegatedCooperation;
    }
    
    @java.lang.SuppressWarnings("all")
    public Boolean getDelegatedPartner() {
        return this.delegatedPartner;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCategoryValue getTypeOfAssistance() {
        return this.typeOfAssistance;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCategoryValue getFinancingInstrument() {
        return this.financingInstrument;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCategoryValue getFundingStatus() {
        return this.fundingStatus;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCategoryValue getModeOfPayment() {
        return this.modeOfPayment;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getLoanTerms() {
        return this.loanTerms;
    }
    
    @java.lang.SuppressWarnings("all")
    public Long getGroupVersionedFunding() {
        return this.groupVersionedFunding;
    }
    
    @java.lang.SuppressWarnings("all")
    public Float getCapitalSpendingPercentage() {
        return this.capitalSpendingPercentage;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpAgreement getAgreement() {
        return this.agreement;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpRole getSourceRole() {
        return this.sourceRole;
    }
    
    @java.lang.SuppressWarnings("all")
    public Comparator getFundingDetailsComparator() {
        return this.fundingDetailsComparator;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpFundingId(final Long ampFundingId) {
        this.ampFundingId = ampFundingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpDonorOrgId(final AmpOrganisation ampDonorOrgId) {
        this.ampDonorOrgId = ampDonorOrgId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpActivityId(final AmpActivityVersion ampActivityId) {
        this.ampActivityId = ampActivityId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setCrsTransactionNo(final Long crsTransactionNo) {
        this.crsTransactionNo = crsTransactionNo;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFinancingId(final String financingId) {
        this.financingId = financingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFundingTermsCode(final String fundingTermsCode) {
        this.fundingTermsCode = fundingTermsCode;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setPlannedStartDate(final Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setPlannedCompletionDate(final Date plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setActualStartDate(final Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setActualCompletionDate(final Date actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setOriginalCompDate(final Date originalCompDate) {
        this.originalCompDate = originalCompDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setLastAuditDate(final Date lastAuditDate) {
        this.lastAuditDate = lastAuditDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setReportingDate(final Date reportingDate) {
        this.reportingDate = reportingDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setConditions(final String conditions) {
        this.conditions = conditions;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDonorObjective(final String donorObjective) {
        this.donorObjective = donorObjective;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setLanguage(final String language) {
        this.language = language;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setVersion(final String version) {
        this.version = version;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setCalType(final String calType) {
        this.calType = calType;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setComments(final String comments) {
        this.comments = comments;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setSignatureDate(final Date signatureDate) {
        this.signatureDate = signatureDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFundingDetails(final Set<AmpFundingDetail> fundingDetails) {
        this.fundingDetails = fundingDetails;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setMtefProjections(final Set<AmpFundingMTEFProjection> mtefProjections) {
        this.mtefProjections = mtefProjections;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setActive(final Boolean active) {
        this.active = active;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDelegatedCooperation(final Boolean delegatedCooperation) {
        this.delegatedCooperation = delegatedCooperation;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDelegatedPartner(final Boolean delegatedPartner) {
        this.delegatedPartner = delegatedPartner;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setTypeOfAssistance(final AmpCategoryValue typeOfAssistance) {
        this.typeOfAssistance = typeOfAssistance;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFinancingInstrument(final AmpCategoryValue financingInstrument) {
        this.financingInstrument = financingInstrument;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFundingStatus(final AmpCategoryValue fundingStatus) {
        this.fundingStatus = fundingStatus;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setModeOfPayment(final AmpCategoryValue modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setLoanTerms(final String loanTerms) {
        this.loanTerms = loanTerms;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setGroupVersionedFunding(final Long groupVersionedFunding) {
        this.groupVersionedFunding = groupVersionedFunding;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setCapitalSpendingPercentage(final Float capitalSpendingPercentage) {
        this.capitalSpendingPercentage = capitalSpendingPercentage;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAgreement(final AmpAgreement agreement) {
        this.agreement = agreement;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setSourceRole(final AmpRole sourceRole) {
        this.sourceRole = sourceRole;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFundingDetailsComparator(final Comparator fundingDetailsComparator) {
        this.fundingDetailsComparator = fundingDetailsComparator;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getRatificationDate() {
        return ratificationDate;
    }

    public void setRatificationDate(Date ratificationDate) {
        this.ratificationDate = ratificationDate;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Float interestRate) {
        this.interestRate = interestRate;
    }

    public Date getMaturity() {
        return maturity;
    }

    public void setMaturity(Date maturity) {
        this.maturity = maturity;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public void setConcessionalityLevel(AmpCategoryValue concessionalityLevel) {
        this.concessionalityLevel = concessionalityLevel;
    }
    
    public AmpCategoryValue getConcessionalityLevel() {
        return this.concessionalityLevel;
    }

    @Override
    public void touch() {
        if (ampActivityId != null) {
            ampActivityId.touch();
        }
    }
}
