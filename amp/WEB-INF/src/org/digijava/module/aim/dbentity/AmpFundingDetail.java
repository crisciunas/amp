// Generated by delombok at Mon Mar 24 00:10:06 EET 2014
package org.digijava.module.aim.dbentity;

import static org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants.RequiredValidation.ALWAYS;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.digijava.kernel.ampapi.endpoints.activity.ActivityEPConstants;
import org.digijava.kernel.ampapi.endpoints.activity.values.FundingePledgesValueProvider;
import org.digijava.kernel.ampapi.endpoints.activity.visibility.FMVisibility;
import org.digijava.kernel.ampapi.endpoints.common.CommonFieldsConstants;
import org.digijava.kernel.validators.activity.PledgeOrgValidator;
import org.digijava.module.aim.annotations.interchange.ActivityFieldsConstants;
import org.digijava.module.aim.annotations.interchange.Interchangeable;
import org.digijava.module.aim.annotations.interchange.InterchangeableBackReference;
import org.digijava.module.aim.annotations.interchange.InterchangeableId;
import org.digijava.module.aim.annotations.interchange.TimestampField;
import org.digijava.module.aim.annotations.interchange.PossibleValues;
import org.digijava.module.aim.util.FeaturesUtil;
import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.categorymanager.util.CategoryConstants;
import org.digijava.module.fundingpledges.dbentity.FundingPledges;

public class AmpFundingDetail implements Serializable, Cloneable, FundingInformationItem {

    public static class FundingDetailComparatorByTransactionDateAsc implements Comparator<AmpFundingDetail>, Serializable {

        @Override
        public int compare(AmpFundingDetail arg0, AmpFundingDetail arg1) {
            if (arg0.getTransactionDate() != null && arg1.getTransactionDate() != null){
                return arg0.getTransactionDate().compareTo(arg1.getTransactionDate());
            }else{
                if(arg0.getTransactionDate()==null){
                    return 1;
                }else{
                    return -1;
                }
            }
        }
    }

    public static class FundingDetailComparatorByTransactionDateDesc implements Comparator<AmpFundingDetail>, Serializable {

        @Override
        public int compare(AmpFundingDetail arg0, AmpFundingDetail arg1) {
            if (arg0.getTransactionDate() != null && arg1.getTransactionDate() != null){
                return arg1.getTransactionDate().compareTo(arg0.getTransactionDate());
            }else{
                if(arg0.getTransactionDate()==null){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
    }

    public static class FundingDetailComparatorByFundingItemIdAsc implements Comparator<AmpFundingDetail>, Serializable {

        @Override
        public int compare(AmpFundingDetail arg0, AmpFundingDetail arg1) {
            if (arg0.getAmpFundDetailId() != null && arg1.getAmpFundDetailId() != null){
                return arg0.getAmpFundDetailId().compareTo(arg1.getAmpFundDetailId());
            }else{
                if(arg0.getAmpFundDetailId()==null){
                    return 1;
                }else{
                    return -1;
                }
            }
        }
    }

    public static class FundingDetailComparatorByFundingItemIdDesc implements Comparator<AmpFundingDetail>, Serializable {

        @Override
        public int compare(AmpFundingDetail arg0, AmpFundingDetail arg1) {
            if (arg0.getAmpFundDetailId() != null && arg1.getAmpFundDetailId() != null){
                return arg1.getAmpFundDetailId().compareTo(arg0.getAmpFundDetailId());
            }else{
                if(arg0.getAmpFundDetailId()==null){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
    }

    //IATI-check: not to be ignored!
    public static class FundingDetailComparator implements Comparator<AmpFundingDetail>, Serializable {

        /**
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final AmpFundingDetail arg0, final AmpFundingDetail arg1) {

            if (arg0.getTransactionDate() != null && arg0.getTransactionAmount() != null
                    && arg0.getAdjustmentType() != null) {
                if (arg0.getTransactionDate() != null && arg1.getTransactionDate() != null
                        && arg0.getTransactionDate().compareTo(arg1.getTransactionDate()) != 0) {
                    return arg0.getTransactionDate().compareTo(arg1.getTransactionDate());
                }
                if (arg0.getTransactionDate() != null && arg1.getTransactionDate() == null) {
                    return -1;
                }
                if (arg0.getTransactionDate() == null && arg1.getTransactionDate() != null) {
                    return 1;
                }
            }
            if (arg0.getReportingDate() != null && arg1.getReportingDate() != null) {
                return arg0.getReportingDate().compareTo(arg1.getReportingDate());
            }
            if (arg0.getAmpFundDetailId() != null && arg1.getAmpFundDetailId() != null) {
                return arg0.getAmpFundDetailId().compareTo(arg1.getAmpFundDetailId());
            }
            if (arg0.getAmpFundDetailId() != null && arg1.getAmpFundDetailId() == null) {
                return -1;
            }
            if (arg0.getAmpFundDetailId() == null && arg1.getAmpFundDetailId() != null) {
                return 1;
            }
            return arg0.hashCode() - arg1.hashCode();
        }
    }

    @InterchangeableId
    @Interchangeable(fieldTitle = "Transaction ID")
    private Long ampFundDetailId;

    private Integer fiscalYear;
    private Integer fiscalQuarter;
    
    /**
     * values of transactionType
     * public static final int COMMITMENT = 0 ;
     * public static final int DISBURSEMENT = 1 ;
     * public static final int EXPENDITURE = 2 ;
     * public static final int DISBURSEMENT_ORDER = 4 ;
     * public static final int MTEFPROJECTION = 3 ;
     * public static final int ARREAR = 10;
     */

    private Integer transactionType;

    @Interchangeable(fieldTitle = "Adjustment Type", importable = true, pickIdOnly = true,
            discriminatorOption = CategoryConstants.ADJUSTMENT_TYPE_KEY, required = ALWAYS)
    private AmpCategoryValue adjustmentType;

    @Interchangeable(fieldTitle = "Expenditure Class", importable = true, pickIdOnly = true,
            fmPath = FMVisibility.PARENT_FM + "/Expenditure Class", required = ALWAYS,
            requiredFmPath = FMVisibility.PARENT_FM + "/Required Validator for Expenditure Class",
            discriminatorOption = CategoryConstants.EXPENDITURE_CLASS_KEY)
    private AmpCategoryValue expenditureClass;
    
    
    public AmpCategoryValue getExpenditureClass() {
        return expenditureClass;
    }

    public void setExpenditureClass(final AmpCategoryValue expenditureClass) {
        this.expenditureClass = expenditureClass;
    }

    @Interchangeable(fieldTitle = "Transaction Date", importable = true, required = ALWAYS)
    private Date transactionDate;
    private Date transactionDate2;
    
    @Interchangeable(fieldTitle = "Reporting Date", importable = true)
    @TimestampField
    private Date reportingDate;
    
    @Interchangeable(fieldTitle = "Updated Date")
    @TimestampField
    private Date updatedDate;

    @Interchangeable(fieldTitle = "Transaction Amount", importable = true, required = ALWAYS)
    private Double transactionAmount;
    private String language;
    private String version;
    private String calType;
    private String orgRoleCode; // defunct
    @Interchangeable(fieldTitle = "Currency", importable = true, pickIdOnly = true, required = ALWAYS,
            commonPV = CommonFieldsConstants.COMMON_CURRENCY)
    private AmpCurrency ampCurrencyId;
    private AmpOrganisation reportingOrgId;
    @InterchangeableBackReference
    private AmpFunding ampFundingId;
    
    @Interchangeable(fieldTitle = "Fixed Exchange Rate", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Fixed exchange rate")
    private Double fixedExchangeRate;
    
    private AmpCurrency fixedRateBaseCurrency;
    
    @Interchangeable(fieldTitle = "Rejected", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Rejected")
    private Boolean disbursementOrderRejected;
    
    @Interchangeable(fieldTitle = ActivityFieldsConstants.Funding.Details.PLEDGE, importable = true, pickIdOnly = true,
            fmPath = FMVisibility.PARENT_FM + "/Pledges",
            dependencies = {PledgeOrgValidator.FUNDING_ORGANIZATION_VALID_PRESENT_KEY})
    @PossibleValues(FundingePledgesValueProvider.class)
    private FundingPledges pledgeid;
    
    @Interchangeable(fieldTitle = "Capital Spending Percentage", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Capital Spending Percentage")
    private Float capitalSpendingPercentage;
    
    @Interchangeable(fieldTitle = "Recipient Organization", importable = true, pickIdOnly = true,
            fmPath = FMVisibility.PARENT_FM + ActivityEPConstants.RECIPIENT_ORG_FM_PATH,
            commonPV = CommonFieldsConstants.COMMON_ORGANIZATION)
    private AmpOrganisation recipientOrg;
    
    @Interchangeable(fieldTitle = "Recipient Role", importable = true, pickIdOnly = true,
            fmPath = FMVisibility.PARENT_FM + ActivityEPConstants.RECIPIENT_ROLE_FM_PATH,
            commonPV = CommonFieldsConstants.COMMON_ROLE)
    private AmpRole recipientRole;
    
    @Interchangeable(fieldTitle = "Expenditure Classification", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Expenditure Classification")
    private String expCategory;
    
    @Interchangeable(fieldTitle = "Disbursement Order ID", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Disbursement Order Id")
    private String disbOrderId;
    
    private IPAContract contract;
    private boolean iatiAdded = false; //nonpersistant
    private Long checkSum;
    
    @Interchangeable(fieldTitle = "Disaster Response", importable = true,
            fmPath = FMVisibility.PARENT_FM + "/Disaster Response", required = ALWAYS,
            requiredFmPath = FMVisibility.PARENT_FM + "/Required Validator for Disaster Response")
    private Boolean disasterResponse;
    
    public boolean isIatiAdded() {
        return iatiAdded;
    }

    public void setIatiAdded(boolean iatiAdded) {
        this.iatiAdded = iatiAdded;
    }

    public AmpFundingDetail() {
    }
    
    public AmpFundingDetail(Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent, Double fixedExchangeRate) {
        this(null, transactionType, adjustmentType, transactionAmount, transactionDate, ampCurrencyId, percent, fixedExchangeRate);
    }
    
    public AmpFundingDetail(Long ampFundDetailId, Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent, Double fixedExchangeRate) {
        this(ampFundDetailId, transactionType, adjustmentType, transactionDate, ampCurrencyId, fixedExchangeRate);
        if (percent == null) {
            percent = 0.0F;
        }
        this.transactionAmount = transactionAmount * percent / 100;
    }
    
    public AmpFundingDetail(Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent, Float percent2, Double fixedExchangeRate) {
        this(null, transactionType, adjustmentType, transactionAmount, transactionDate, ampCurrencyId, percent, percent2, fixedExchangeRate);
    }
    
    public AmpFundingDetail(Long ampFundDetailId, Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent1, Float percent2, Double fixedExchangeRate) {
        this(ampFundDetailId, transactionType, adjustmentType, transactionDate, ampCurrencyId, fixedExchangeRate);
        if ((percent1 == null || percent1 == 0) && (percent2 == null || percent2 == 0)) {
            this.transactionAmount = transactionAmount;
        } else {
            //Check if the the percentage is null before dividing. If it's null, the calculation cannot be done, so return 0
            if (percent1 != null && percent2 != null) if (percent1.compareTo(0.0F) == 0 || percent2.compareTo(0.0F) == 0) this.transactionAmount = 0.0; else this.transactionAmount = (transactionAmount * percent1 / 100) * percent2 / 100; else this.transactionAmount = 0.0;
        }
    }
    // used in dashborads when there is a filter by location, sector and program
    public AmpFundingDetail(Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent1, Float percent2, Float percent3, Double fixedExchangeRate) {
        this(null, transactionType, adjustmentType, transactionAmount, transactionDate, ampCurrencyId, percent1, percent2, percent3, fixedExchangeRate);
    }
    
    public AmpFundingDetail(Long ampFundDetailId, Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Float percent1, Float percent2, Float percent3, Double fixedExchangeRate) {
        this(ampFundDetailId, transactionType, adjustmentType, transactionDate, ampCurrencyId, fixedExchangeRate);
        if ((percent1 == null || percent1 == 0) && (percent2 == null || percent2 == 0) && (percent3 == null || percent3 == 0)) {
            this.transactionAmount = transactionAmount;
        } else {
            //Check if the the percentage is null before dividing. If it's null, the calculation cannot be done, so return 0
            if (percent1 != null && percent2 != null && percent3 != null) if (percent1.compareTo(0.0F) == 0 || percent2.compareTo(0.0F) == 0 || percent3.compareTo(0.0F) == 0) this.transactionAmount = 0.0; else this.transactionAmount = ((transactionAmount * percent1 / 100) * percent2 / 100) * percent3 / 100; else this.transactionAmount = 0.0;
        }
    }
    // used in org profile 
    public AmpFundingDetail(Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Double fixedExchangeRate) {
        this(null, transactionType, adjustmentType, transactionAmount, transactionDate, ampCurrencyId, fixedExchangeRate);
    }
    
    public AmpFundingDetail(Long ampFundDetailId, Integer transactionType, AmpCategoryValue adjustmentType, Double transactionAmount, Date transactionDate, AmpCurrency ampCurrencyId, Double fixedExchangeRate) {
        this(ampFundDetailId, transactionType, adjustmentType, transactionDate, ampCurrencyId, fixedExchangeRate);
        this.transactionAmount = transactionAmount;
    }
    
    public AmpFundingDetail(Long ampFundDetailId, Integer transactionType, AmpCategoryValue adjustmentType, Date transactionDate, AmpCurrency ampCurrencyId, Double fixedExchangeRate) {
        this.ampFundDetailId = ampFundDetailId;
        this.transactionType = transactionType;
        this.adjustmentType = adjustmentType;
        this.transactionDate = transactionDate;
        this.ampCurrencyId = ampCurrencyId;
        this.fixedExchangeRate = fixedExchangeRate;
    }
    
    public Long getDbId(){
        return this.getAmpFundDetailId();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
    
    public Double getAbsoluteTransactionAmount() {
        return transactionAmount;
    }
    
    @Override
    public String toString() {
        String currency = this.getAmpCurrencyId() == null ? "NOCUR" : this.getAmpCurrencyId().getCurrencyCode();
        String recipient = this.getRecipientOrg() == null ? "NOORG" : this.getRecipientOrg().getName();
        String trTypeName = "NOTRTYPE";
        switch (getTransactionType().intValue()) {
        case 0: 
            trTypeName = "Commitment";
            break;
        
        case 1: 
            trTypeName = "Disbursement";
            break;
        
        case 2: 
            trTypeName = "Expenditure";
            break;
        
        case 3: 
            trTypeName = "MTEF Projection";
            break;
        
        default: 
            trTypeName = String.format("trType %d", getTransactionType());
            break;
        
        }
        String transText = (this.getAdjustmentType() == null ? "NOADJUST" : this.getAdjustmentType().getLabel()) + " " + trTypeName;
        return String.format("%s %s %s to %s", transText, this.getAbsoluteTransactionAmount(), currency, recipient);
    }
    
    public boolean isSscTransaction() {
        if (this.getAdjustmentType() == null) return false;
        if (this.getAdjustmentType().getAmpCategoryClass() == null) return false;
        return this.getAdjustmentType().getAmpCategoryClass().getKeyName().equals(CategoryConstants.SSC_ADJUSTMENT_TYPE_KEY);
    }
    
    @java.lang.SuppressWarnings("all")
    public Long getAmpFundDetailId() {
        return this.ampFundDetailId;
    }
    
    @java.lang.SuppressWarnings("all")
    public Integer getFiscalYear() {
        return this.fiscalYear;
    }
    
    @java.lang.SuppressWarnings("all")
    public Integer getFiscalQuarter() {
        return this.fiscalQuarter;
    }
    
    /**
     * values of transactionType
     * public static final int COMMITMENT = 0 ;
     * public static final int DISBURSEMENT = 1 ;
     * public static final int EXPENDITURE = 2 ;
     * public static final int DISBURSEMENT_ORDER = 4 ;
     * public static final int MTEFPROJECTION = 3 ;
     */
    @java.lang.SuppressWarnings("all")
    public Integer getTransactionType() {
        return this.transactionType;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCategoryValue getAdjustmentType() {
        return this.adjustmentType;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getTransactionDate2() {
        return this.transactionDate2;
    }
    
    @java.lang.SuppressWarnings("all")
    public Date getReportingDate() {
        return this.reportingDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public Double getTransactionAmount() {
        return transactionAmount;
    }

    @java.lang.SuppressWarnings("all")
    public Double getDisplayedTransactionAmount() {
        return FeaturesUtil.applyThousandsForVisibility(getTransactionAmount());
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
    public String getOrgRoleCode() {
        return this.orgRoleCode;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCurrency getAmpCurrencyId() {
        return this.ampCurrencyId;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpOrganisation getReportingOrgId() {
        return this.reportingOrgId;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpFunding getAmpFundingId() {
        return this.ampFundingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public Double getFixedExchangeRate() {
        return this.fixedExchangeRate;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpCurrency getFixedRateBaseCurrency() {
        return this.fixedRateBaseCurrency;
    }
    
    @java.lang.SuppressWarnings("all")
    public Boolean getDisbursementOrderRejected() {
        return this.disbursementOrderRejected;
    }
    
    @java.lang.SuppressWarnings("all")
    public FundingPledges getPledgeid() {
        return this.pledgeid;
    }
    
    @java.lang.SuppressWarnings("all")
    public Float getCapitalSpendingPercentage() {
        return this.capitalSpendingPercentage;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpOrganisation getRecipientOrg() {
        return this.recipientOrg;
    }
    
    @java.lang.SuppressWarnings("all")
    public AmpRole getRecipientRole() {
        return this.recipientRole;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getExpCategory() {
        return this.expCategory;
    }
    
    @java.lang.SuppressWarnings("all")
    public String getDisbOrderId() {
        return this.disbOrderId;
    }
    
    @java.lang.SuppressWarnings("all")
    public IPAContract getContract() {
        return this.contract;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpFundDetailId(final Long ampFundDetailId) {
        this.ampFundDetailId = ampFundDetailId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFiscalYear(final Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFiscalQuarter(final Integer fiscalQuarter) {
        this.fiscalQuarter = fiscalQuarter;
    }
    
    /**
     * values of transactionType
     * public static final int COMMITMENT = 0 ;
     * public static final int DISBURSEMENT = 1 ;
     * public static final int EXPENDITURE = 2 ;
     * public static final int DISBURSEMENT_ORDER = 4 ;
     * public static final int MTEFPROJECTION = 3 ;
     */
    @java.lang.SuppressWarnings("all")
    public void setTransactionType(final Integer transactionType) {
        this.transactionType = transactionType;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAdjustmentType(final AmpCategoryValue adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setTransactionDate(final Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setTransactionDate2(final Date transactionDate2) {
        this.transactionDate2 = transactionDate2;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setReportingDate(final Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    @java.lang.SuppressWarnings("all")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @java.lang.SuppressWarnings("all")
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransactionAmount(final Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDisplayedTransactionAmount(final Double displayedTransactionAmount) {
        setTransactionAmount(FeaturesUtil.applyThousandsForEntry(displayedTransactionAmount));
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
    public void setOrgRoleCode(final String orgRoleCode) {
        this.orgRoleCode = orgRoleCode;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpCurrencyId(final AmpCurrency ampCurrencyId) {
        this.ampCurrencyId = ampCurrencyId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setReportingOrgId(final AmpOrganisation reportingOrgId) {
        this.reportingOrgId = reportingOrgId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setAmpFundingId(final AmpFunding ampFundingId) {
        this.ampFundingId = ampFundingId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFixedExchangeRate(final Double fixedExchangeRate) {
        this.fixedExchangeRate = fixedExchangeRate;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setFixedRateBaseCurrency(final AmpCurrency fixedRateBaseCurrency) {
        this.fixedRateBaseCurrency = fixedRateBaseCurrency;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDisbursementOrderRejected(final Boolean disbursementOrderRejected) {
        this.disbursementOrderRejected = disbursementOrderRejected;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setPledgeid(final FundingPledges pledgeid) {
        this.pledgeid = pledgeid;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setCapitalSpendingPercentage(final Float capitalSpendingPercentage) {
        this.capitalSpendingPercentage = capitalSpendingPercentage;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setRecipientOrg(final AmpOrganisation recipientOrg) {
        this.recipientOrg = recipientOrg;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setRecipientRole(final AmpRole recipientRole) {
        this.recipientRole = recipientRole;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setExpCategory(final String expCategory) {
        this.expCategory = expCategory;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setDisbOrderId(final String disbOrderId) {
        this.disbOrderId = disbOrderId;
    }
    
    @java.lang.SuppressWarnings("all")
    public void setContract(final IPAContract contract) {
        this.contract = contract;
    }

    public Boolean getDisasterResponse() {
        return disasterResponse;
    }

    public void setDisasterResponse(Boolean disasterResponse) {
        this.disasterResponse = disasterResponse;
    }

    public Long getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(Long checkSum) {
        this.checkSum = checkSum;
    }
    
}
