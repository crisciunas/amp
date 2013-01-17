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

import org.digijava.module.categorymanager.dbentity.AmpCategoryValue;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.util.Output;

public class AmpFunding implements Serializable, Versionable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Long ampFundingId;
	private AmpOrganisation ampDonorOrgId;
	private AmpActivityVersion ampActivityId;
	private Long crsTransactionNo;
	private String financingId;
	private String fundingTermsCode;
	private Date plannedStartDate;
	private Date plannedCompletionDate;
	private Date actualStartDate;
	private Date actualCompletionDate;
	private Date originalCompDate;
	private Date lastAuditDate;
	private Date reportingDate;
	private String conditions;
	private String donorObjective;
	private String language;
	private String version;
	private String calType;
	private String comments;
	private Date signatureDate;
	private Set<AmpFundingDetail> fundingDetails;
	private Set<AmpFundingMTEFProjection> mtefProjections;
	// private AmpTermsAssist ampTermsAssistId ;
	
	/*
	 * tanzania adds funding amp-1707
	 */
	private Boolean active;
	private Boolean delegatedCooperation;
	private Boolean delegatedPartner;

	private ArrayList<Boolean> activeList;
	// private AmpModality modalityId;

	private AmpCategoryValue typeOfAssistance;
	private AmpCategoryValue financingInstrument;
	private AmpCategoryValue fundingStatus;
	private AmpCategoryValue modeOfPayment;
	private String loanTerms;
	private Long groupVersionedFunding;
    private Float capitalSpendingPercentage;
    private AmpAgreement agreement;
    
    
    
    public AmpAgreement getAgreement() {
		return agreement;
	}

	public void setAgreement(AmpAgreement agreement) {
		this.agreement = agreement;
	}

	public Float getCapitalSpendingPercentage() {
        return capitalSpendingPercentage;
    }

    public void setCapitalSpendingPercentage(Float capitalSpendingPercentage) {
        this.capitalSpendingPercentage = capitalSpendingPercentage;
    }
    // private Set survey;

	
	/**
	 * @return
	 */
	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	/**
	 * @return
	 */
	public Date getActualStartDate() {
		return actualStartDate;
	}

	/**
	 * @return
	 */
	public AmpOrganisation getAmpDonorOrgId() {
		return ampDonorOrgId;
	}

	/**
	 * @return
	 */
	public Long getAmpFundingId() {
		return ampFundingId;
	}

	/**
	 * @return
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * @return
	 */
	public Long getCrsTransactionNo() {
		return crsTransactionNo;
	}

	/**
	 * @return
	 */
	public String getFinancingId() {
		return financingId;
	}

	/**
	 * @return
	 */
	public Set<AmpFundingDetail> getFundingDetails() {
		return fundingDetails;
	}

	/**
	 * @return
	 */
	public String getFundingTermsCode() {
		return fundingTermsCode;
	}

	/**
	 * @return
	 */
	public Date getPlannedCompletionDate() {
		return plannedCompletionDate;
	}

	/**
	 * @return
	 */
	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	/**
	 * @return
	 */
	public Date getReportingDate() {
		return reportingDate;
	}

	/**
	 * @return
	 */
	public Date getSignatureDate() {
		return signatureDate;
	}

	/**
	 * @param date
	 */
	public void setActualCompletionDate(Date date) {
		actualCompletionDate = date;
	}

	/**
	 * @param date
	 */
	public void setActualStartDate(Date date) {
		actualStartDate = date;
	}

	/**
	 * @param long1
	 */
	public void setAmpDonorOrgId(AmpOrganisation ampDonorOrgId) {
		this.ampDonorOrgId = ampDonorOrgId;
	}

	/**
	 * @param long1
	 */
	public void setAmpFundingId(Long long1) {
		ampFundingId = long1;
	}

	/**
	 * @param string
	 */
	public void setConditions(String string) {
		conditions = string;
	}

	/**
	 * @param long1
	 */
	public void setCrsTransactionNo(Long long1) {
		crsTransactionNo = long1;
	}

	/**
	 * @param string
	 */
	public void setFinancingId(String string) {
		financingId = string;
	}

	/**
	 * @param set
	 */
	public void setFundingDetails(Set<AmpFundingDetail> set) {
		fundingDetails = set;
	}

	/**
	 * @param string
	 */
	public void setFundingTermsCode(String string) {
		fundingTermsCode = string;
	}

	/**
	 * @param date
	 */
	public void setPlannedCompletionDate(Date date) {
		plannedCompletionDate = date;
	}

	/**
	 * @param date
	 */
	public void setPlannedStartDate(Date date) {
		plannedStartDate = date;
	}

	/**
	 * @param date
	 */
	public void setReportingDate(Date date) {
		reportingDate = date;
	}

	/**
	 * @param date
	 */
	public void setSignatureDate(Date date) {
		signatureDate = date;
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	public String getVersion() {
		return version;
	}

	public void setLanguage(String string) {
		language = string;
	}

	public void setVersion(String string) {
		version = string;
	}

	public void setAmpActivityId(AmpActivityVersion a) {
		this.ampActivityId = a;
	}

	public AmpActivityVersion getAmpActivityId() {
		return ampActivityId;
	}

	/**
	 * @return
	 */
	public Date getOriginalCompDate() {
		return originalCompDate;
	}

	/**
	 * @param date
	 */
	public void setOriginalCompDate(Date date) {
		originalCompDate = date;
	}

	/**
	 * @return
	 */
	public Date getLastAuditDate() {
		return lastAuditDate;
	}

	/**
	 * @param date
	 */
	public void setLastAuditDate(Date date) {
		lastAuditDate = date;
	}

	/**
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param string
	 */
	public void setComments(String string) {
		comments = string;
	}

	/**
	 * @return
	 */
	public String getCalType() {
		return calType;
	}

	/**
	 * @param string
	 */
	public void setCalType(String string) {
		calType = string;
	}

	public AmpCategoryValue getFinancingInstrument() {
		return financingInstrument;
	}

	public void setFinancingInstrument(AmpCategoryValue financingInstrument) {
		this.financingInstrument = financingInstrument;
	}

	public AmpCategoryValue getTypeOfAssistance() {
		return typeOfAssistance;
	}

	public void setTypeOfAssistance(AmpCategoryValue typeOfAssistence) {
		this.typeOfAssistance = typeOfAssistence;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getDelegatedCooperation() {
		return delegatedCooperation;
	}

	public void setDelegatedCooperation(Boolean delegatedCooperation) {
		this.delegatedCooperation = delegatedCooperation;
	}

	public Boolean getDelegatedPartner() {
		return delegatedPartner;
	}

	public void setDelegatedPartner(Boolean delegatedPartner) {
		this.delegatedPartner = delegatedPartner;
	}

	public ArrayList<Boolean> getActiveList() {
		return activeList;
	}

	public void setActiveList(ArrayList<Boolean> activeList) {
		this.activeList = activeList;
	}

	public Set<AmpFundingMTEFProjection> getMtefProjections() {
		return mtefProjections;
	}

	public void setMtefProjections(Set<AmpFundingMTEFProjection> mtefProjections) {
		this.mtefProjections = mtefProjections;
	}

	/**
	 * @return the donorObjective
	 */
	public String getDonorObjective() {
		return this.donorObjective;
	}

	/**
	 * @param donorObjective
	 *            the donorObjective to set
	 */
	public void setDonorObjective(String donorObjective) {
		this.donorObjective = donorObjective;
	}

	/**
	 * @return the fundingStatus
	 */
	public AmpCategoryValue getFundingStatus() {
		return fundingStatus;
	}

	/**
	 * @param fundingStatus
	 *            the fundingStatus to set
	 */
	public void setFundingStatus(AmpCategoryValue fundingStatus) {
		this.fundingStatus = fundingStatus;
	}

	/**
	 * @return the modeOfPayment
	 */
	public AmpCategoryValue getModeOfPayment() {
		return modeOfPayment;
	}

	/**
	 * @param modeOfPayment
	 *            the modeOfPayment to set
	 */
	public void setModeOfPayment(AmpCategoryValue modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getLoanTerms() {
		return loanTerms;
	}

	public void setLoanTerms(String loanTerms) {
		this.loanTerms = loanTerms;
	}
	
	
	@Override
	public boolean equalsForVersioning(Object obj) {
		AmpFunding auxFunding = (AmpFunding) obj;
		if (this.groupVersionedFunding != null
				&& this.groupVersionedFunding.equals(auxFunding.getGroupVersionedFunding())) {
			return true;
		}
		return false;
	}

	@Override
	public Object getValue() {
		// Compare fields from AmpFunding.
		String ret = "";
		ret = ret + "-Type of Assistance:" + (this.typeOfAssistance != null ? this.typeOfAssistance.getEncodedValue() : "");
		ret = ret + "-Financing Instrument:" + (this.financingInstrument != null ? this.financingInstrument.getEncodedValue() : "");
		ret = ret + "-Conditions:" + (this.conditions == null ? "" : this.conditions.trim());
		ret = ret + "-Donor Objective:" + (this.donorObjective == null ? "" : this.donorObjective.trim());
		ret = ret + "-Active:" + this.active;
		ret = ret + "-Delegated Cooperation:" + this.delegatedCooperation;
		ret = ret + "-Delegated Partner:" + this.delegatedPartner;
		ret = ret + "-Mode Of Payment:" + (this.modeOfPayment != null ? this.modeOfPayment.getEncodedValue() : "");
		ret = ret + "-Funding Status:" + (this.fundingStatus != null ? this.fundingStatus.getEncodedValue() : "");
		ret = ret + "-Funding Status:" + (this.financingId != null ? this.financingId : "");
		
		// Compare fields from AmpFundingDetail.
		List<AmpFundingDetail> auxDetails = new ArrayList(this.fundingDetails);
		Collections.sort(auxDetails, fundingDetailsComparator);
		Iterator<AmpFundingDetail> iter = auxDetails.iterator();
		while (iter.hasNext()) {
			AmpFundingDetail auxDetail = iter.next();
			ret = ret + auxDetail.getTransactionType() + "-" + auxDetail.getTransactionAmount() + "-"
					+ auxDetail.getAmpCurrencyId() + "-" + auxDetail.getTransactionDate();
			if (auxDetail.getPledgeid() != null)
				ret += "-" + auxDetail.getPledgeid().getId();
			ret += "-" + auxDetail.getDisbOrderId();
			if (auxDetail.getContract() != null)
				ret += "-" + auxDetail.getContract().getId();
			ret += "-" + auxDetail.getExpCategory();
			ret += "-" + auxDetail.getDisbursementOrderRejected();
		}
		return ret;
	}

	// Compare by transaction type, then amount, then date.
	// (transient in order to be wicket friendly, no need to serialize this field)
	private transient Comparator fundingDetailsComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			AmpFundingDetail aux1 = (AmpFundingDetail) o1;
			AmpFundingDetail aux2 = (AmpFundingDetail) o2;
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

	@Override
	public Output getOutput() {
		Output out = new Output();
		out.setOutputs(new ArrayList<Output>());
		out.getOutputs().add(
				new Output(null, new String[] { "Organization:&nbsp;" }, new Object[] { this.ampDonorOrgId.getName() }));
		if (this.typeOfAssistance != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br />", "Type of Assistance:&nbsp;" },
							new Object[] { this.typeOfAssistance.getValue() }));
		}
		if (this.financingInstrument != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br />", "Financing Instrument:&nbsp;" },
							new Object[] { this.financingInstrument.getValue() }));
		}
		if (this.conditions != null && !this.conditions.trim().equals("")) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br/>", " Conditions:&nbsp;" }, new Object[] { this.conditions }));
		}
		if (this.donorObjective != null && !this.donorObjective.trim().equals("")) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br/>", "Donor Objective:&nbsp;" },
							new Object[] { this.donorObjective }));
		}
		if (this.active != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br/>", "Active:&nbsp;" }, new Object[] { this.active.toString() }));
		}
		if (this.delegatedCooperation != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br/>", "Delegated Cooperation:&nbsp;" }, new Object[] { this.delegatedCooperation.toString() }));
		}
		if (this.delegatedPartner != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br/>", "Delegated Partner:&nbsp;" }, new Object[] { this.delegatedPartner.toString() }));
		}
		if (this.modeOfPayment != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br />", "Mode of Payment:&nbsp;" },
							new Object[] { this.modeOfPayment.getValue() }));
		}
		if (this.fundingStatus != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br />", "Funding Status:&nbsp;" },
							new Object[] { this.fundingStatus.getValue() }));
		}
		if (this.financingId != null) {
			out.getOutputs().add(
					new Output(null, new String[] { "<br />", "Financing Id:&nbsp;" },
							new Object[] { this.financingId }));
		}

		boolean trnComm = false;
		boolean trnDisb = false;
		boolean trnExp = false;
		boolean trnDisbOrder = false;
		boolean trnMTEF = false;
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
				transactionType = "Commitments:&nbsp;";
				if (auxDetail.getPledgeid() != null)
					extraValues = " - " + auxDetail.getPledgeid().getTitle().getValue();
				if (!trnComm) {
					out.getOutputs().add(
							new Output(new ArrayList<Output>(), new String[] {"<br/>", transactionType }, new Object[] { "" }));
					trnComm = true;
				}
				break;
			case Constants.DISBURSEMENT:
				transactionType = " Disbursements:&nbsp;";
				
				if (auxDetail.getDisbOrderId() != null && auxDetail.getDisbOrderId().trim().length() > 0)
					extraValues += " - " + auxDetail.getDisbOrderId();
				if (auxDetail.getContract() != null)
					extraValues += " - " + auxDetail.getContract().getContractName();
				if (auxDetail.getPledgeid() != null)
					extraValues = " - " + auxDetail.getPledgeid().getTitle().getValue();
				
				if (!trnDisb) {
					out.getOutputs().add(
							new Output(new ArrayList<Output>(), new String[] {"<br/>", transactionType }, new Object[] { "" }));
					trnDisb = true;
				}
				break;
			case Constants.EXPENDITURE:
				transactionType = " Expenditures:&nbsp;";
				
				if (auxDetail.getExpCategory() != null && auxDetail.getExpCategory().trim().length() > 0)
					extraValues += " - " + auxDetail.getExpCategory();
				
				if (!trnExp) {
					out.getOutputs().add(
							new Output(new ArrayList<Output>(), new String[] {"<br/>", transactionType }, new Object[] { "" }));
					trnExp = true;
				}
				break;
			case Constants.DISBURSEMENT_ORDER:
				transactionType = " Disbursement Orders:&nbsp;";

				if (auxDetail.getDisbOrderId() != null && auxDetail.getDisbOrderId().trim().length() > 0)
					extraValues += " - " + auxDetail.getDisbOrderId();
				if (auxDetail.getContract() != null)
					extraValues += " - " + auxDetail.getContract().getContractName();
				if (auxDetail.getDisbursementOrderRejected())
					extraValues += " - " + "Rejected";
				else
					extraValues += " - " + "Not Rejected";

				if (!trnDisbOrder) {
					out.getOutputs().add(
							new Output(new ArrayList<Output>(), new String[] {"<br/>", transactionType }, new Object[] { "" }));
					trnDisbOrder = true;
				}
				break;
			
			default:
				error = true;
				break;
			}
			if (!error) {
				String adjustment = auxDetail.getAdjustmentType().getValue();
				auxOutDetail = out.getOutputs().get(out.getOutputs().size() - 1);
				auxOutDetail.getOutputs().add(
						new Output(null, new String[] { "" }, new Object[] { adjustment, " - ",
								auxDetail.getTransactionAmount(), " ", auxDetail.getAmpCurrencyId(), " - ",
								auxDetail.getTransactionDate() + extraValues }));
			}
		}
		
		Iterator<AmpFundingMTEFProjection> it2 = this.mtefProjections.iterator();
		while (it2.hasNext()) {
			AmpFundingMTEFProjection mtef = (AmpFundingMTEFProjection) it2
					.next();
			if (!trnMTEF) {
				out.getOutputs().add(
						new Output(new ArrayList<Output>(), new String[] {"<br/>", " MTEF Projection:&nbsp;" }, new Object[] { "" }));
				trnMTEF = true;
			}
			String adjustment = mtef.getProjected().getValue();
			Output auxOutDetail = out.getOutputs().get(out.getOutputs().size() - 1);
			auxOutDetail.getOutputs().add(
					new Output(null, new String[] { "" }, new Object[] { adjustment, " - ",
							mtef.getAmount(), " ", mtef.getAmpCurrency(), " - ",
							mtef.getProjectionDate() }));
		}
		return out;
	}

	public Long getGroupVersionedFunding() {
		return groupVersionedFunding;
	}

	public void setGroupVersionedFunding(Long previousVersionedFunding) {
		this.groupVersionedFunding = previousVersionedFunding;
	}
	
	@Override
	public Object prepareMerge(AmpActivityVersion newActivity) throws CloneNotSupportedException {
		AmpFunding aux = (AmpFunding) clone();
		aux.ampActivityId = newActivity;
		aux.ampFundingId = null;
		if (aux.fundingDetails != null && aux.fundingDetails.size() > 0) {
			Set<AmpFundingDetail> auxSetFD = new HashSet<AmpFundingDetail>();
			Iterator<AmpFundingDetail> iF = aux.fundingDetails.iterator();
			while (iF.hasNext()) {
				AmpFundingDetail auxFD = iF.next();
				AmpFundingDetail newFD = (AmpFundingDetail) auxFD.clone();
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
				AmpFundingMTEFProjection newMTEF = (AmpFundingMTEFProjection) auxMTEF.clone();
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
		if (groupVersionedFunding == null)
			groupVersionedFunding = System.currentTimeMillis();
		return super.clone();
	}
}
