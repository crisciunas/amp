//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 03:12:33 PM EEST 
//


package org.digijava.module.dataExchangeIATI.iatiSchema.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.digijava.module.dataExchangeIATI.iatiSchema.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DefaultTiedStatus_QNAME = new QName("", "default-tied-status");
    private final static QName _ImplementationLevel_QNAME = new QName("", "implementation-level");
    private final static QName _ActivityStatus_QNAME = new QName("", "activity-status");
    private final static QName _CollaborationType_QNAME = new QName("", "collaboration-type");
    private final static QName _DefaultAidType_QNAME = new QName("", "default-aid-type");
    private final static QName _Title_QNAME = new QName("", "title");
    private final static QName _DefaultFinanceType_QNAME = new QName("", "default-finance-type");
    private final static QName _DefaultFlowType_QNAME = new QName("", "default-flow-type");
    private final static QName _ContactInfoMailingAddress_QNAME = new QName("", "mailing-address");
    private final static QName _ContactInfoOrganisation_QNAME = new QName("", "organisation");
    private final static QName _ContactInfoTelephone_QNAME = new QName("", "telephone");
    private final static QName _ContactInfoPersonName_QNAME = new QName("", "person-name");
    private final static QName _ContactInfoEmail_QNAME = new QName("", "email");
    private final static QName _LocationAdministrative_QNAME = new QName("", "administrative");
    private final static QName _LocationCoordinates_QNAME = new QName("", "coordinates");
    private final static QName _LocationGazetteerEntry_QNAME = new QName("", "gazetteer-entry");
    private final static QName _LocationName_QNAME = new QName("", "name");
    private final static QName _LocationDescription_QNAME = new QName("", "description");
    private final static QName _LocationLocationType_QNAME = new QName("", "location-type");
    private final static QName _ConditionsCondition_QNAME = new QName("", "condition");
    private final static QName _ResultIndicatorActual_QNAME = new QName("", "actual");
    private final static QName _ResultIndicatorBaseline_QNAME = new QName("", "baseline");
    private final static QName _ResultIndicatorTarget_QNAME = new QName("", "target");
    private final static QName _BudgetPeriodStart_QNAME = new QName("", "period-start");
    private final static QName _BudgetPeriodEnd_QNAME = new QName("", "period-end");
    private final static QName _BudgetValue_QNAME = new QName("", "value");
    private final static QName _TransactionTiedStatus_QNAME = new QName("", "tied-status");
    private final static QName _TransactionFlowType_QNAME = new QName("", "flow-type");
    private final static QName _TransactionTransactionDate_QNAME = new QName("", "transaction-date");
    private final static QName _TransactionReceiverOrg_QNAME = new QName("", "receiver-org");
    private final static QName _TransactionProviderOrg_QNAME = new QName("", "provider-org");
    private final static QName _TransactionFinanceType_QNAME = new QName("", "finance-type");
    private final static QName _TransactionDisbursementChannel_QNAME = new QName("", "disbursement-channel");
    private final static QName _TransactionAidType_QNAME = new QName("", "aid-type");
    private final static QName _TransactionTransactionType_QNAME = new QName("", "transaction-type");
    private final static QName _ResultIndicator_QNAME = new QName("", "indicator");
    private final static QName _DocumentLinkCategory_QNAME = new QName("", "category");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.digijava.module.dataExchangeIATI.iatiSchema.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IndicatorOutcomeType }
     * 
     */
    public IndicatorOutcomeType createIndicatorOutcomeType() {
        return new IndicatorOutcomeType();
    }

    /**
     * Create an instance of {@link Transaction }
     * 
     */
    public Transaction createTransaction() {
        return new Transaction();
    }

    /**
     * Create an instance of {@link ContactInfo.MailingAddress }
     * 
     */
    public ContactInfo.MailingAddress createContactInfoMailingAddress() {
        return new ContactInfo.MailingAddress();
    }

    /**
     * Create an instance of {@link ContactInfo.Telephone }
     * 
     */
    public ContactInfo.Telephone createContactInfoTelephone() {
        return new ContactInfo.Telephone();
    }

    /**
     * Create an instance of {@link IatiActivities }
     * 
     */
    public IatiActivities createIatiActivities() {
        return new IatiActivities();
    }

    /**
     * Create an instance of {@link PlannedDisbursement }
     * 
     */
    public PlannedDisbursement createPlannedDisbursement() {
        return new PlannedDisbursement();
    }

    /**
     * Create an instance of {@link RefReqType }
     * 
     */
    public RefReqType createRefReqType() {
        return new RefReqType();
    }

    /**
     * Create an instance of {@link Conditions }
     * 
     */
    public Conditions createConditions() {
        return new Conditions();
    }

    /**
     * Create an instance of {@link LegacyData }
     * 
     */
    public LegacyData createLegacyData() {
        return new LegacyData();
    }

    /**
     * Create an instance of {@link Transaction.TransactionDate }
     * 
     */
    public Transaction.TransactionDate createTransactionTransactionDate() {
        return new Transaction.TransactionDate();
    }

    /**
     * Create an instance of {@link CurrencyType }
     * 
     */
    public CurrencyType createCurrencyType() {
        return new CurrencyType();
    }

    /**
     * Create an instance of {@link DateType }
     * 
     */
    public DateType createDateType() {
        return new DateType();
    }

    /**
     * Create an instance of {@link Location.Administrative }
     * 
     */
    public Location.Administrative createLocationAdministrative() {
        return new Location.Administrative();
    }

    /**
     * Create an instance of {@link Budget }
     * 
     */
    public Budget createBudget() {
        return new Budget();
    }

    /**
     * Create an instance of {@link Conditions.Condition }
     * 
     */
    public Conditions.Condition createConditionsCondition() {
        return new Conditions.Condition();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link RecipientCountry }
     * 
     */
    public RecipientCountry createRecipientCountry() {
        return new RecipientCountry();
    }

    /**
     * Create an instance of {@link PolicyMarker }
     * 
     */
    public PolicyMarker createPolicyMarker() {
        return new PolicyMarker();
    }

    /**
     * Create an instance of {@link DateReqType }
     * 
     */
    public DateReqType createDateReqType() {
        return new DateReqType();
    }

    /**
     * Create an instance of {@link PlannedDisbursement.PeriodStart }
     * 
     */
    public PlannedDisbursement.PeriodStart createPlannedDisbursementPeriodStart() {
        return new PlannedDisbursement.PeriodStart();
    }

    /**
     * Create an instance of {@link ContactInfo }
     * 
     */
    public ContactInfo createContactInfo() {
        return new ContactInfo();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link Result.Indicator }
     * 
     */
    public Result.Indicator createResultIndicator() {
        return new Result.Indicator();
    }

    /**
     * Create an instance of {@link Location.Coordinates }
     * 
     */
    public Location.Coordinates createLocationCoordinates() {
        return new Location.Coordinates();
    }

    /**
     * Create an instance of {@link Transaction.ProviderOrg }
     * 
     */
    public Transaction.ProviderOrg createTransactionProviderOrg() {
        return new Transaction.ProviderOrg();
    }

    /**
     * Create an instance of {@link RelatedActivity }
     * 
     */
    public RelatedActivity createRelatedActivity() {
        return new RelatedActivity();
    }

    /**
     * Create an instance of {@link CodeReqType }
     * 
     */
    public CodeReqType createCodeReqType() {
        return new CodeReqType();
    }

    /**
     * Create an instance of {@link ReportingOrg }
     * 
     */
    public ReportingOrg createReportingOrg() {
        return new ReportingOrg();
    }

    /**
     * Create an instance of {@link ActivityDate }
     * 
     */
    public ActivityDate createActivityDate() {
        return new ActivityDate();
    }

    /**
     * Create an instance of {@link Sector }
     * 
     */
    public Sector createSector() {
        return new Sector();
    }

    /**
     * Create an instance of {@link ParticipatingOrg }
     * 
     */
    public ParticipatingOrg createParticipatingOrg() {
        return new ParticipatingOrg();
    }

    /**
     * Create an instance of {@link CodeType }
     * 
     */
    public CodeType createCodeType() {
        return new CodeType();
    }

    /**
     * Create an instance of {@link IatiIdentifier }
     * 
     */
    public IatiIdentifier createIatiIdentifier() {
        return new IatiIdentifier();
    }

    /**
     * Create an instance of {@link ActivityWebsite }
     * 
     */
    public ActivityWebsite createActivityWebsite() {
        return new ActivityWebsite();
    }

    /**
     * Create an instance of {@link Transaction.ReceiverOrg }
     * 
     */
    public Transaction.ReceiverOrg createTransactionReceiverOrg() {
        return new Transaction.ReceiverOrg();
    }

    /**
     * Create an instance of {@link Description }
     * 
     */
    public Description createDescription() {
        return new Description();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link OtherIdentifier }
     * 
     */
    public OtherIdentifier createOtherIdentifier() {
        return new OtherIdentifier();
    }

    /**
     * Create an instance of {@link RefType }
     * 
     */
    public RefType createRefType() {
        return new RefType();
    }

    /**
     * Create an instance of {@link PlainType }
     * 
     */
    public PlainType createPlainType() {
        return new PlainType();
    }

    /**
     * Create an instance of {@link DocumentLink }
     * 
     */
    public DocumentLink createDocumentLink() {
        return new DocumentLink();
    }

    /**
     * Create an instance of {@link IatiActivity }
     * 
     */
    public IatiActivity createIatiActivity() {
        return new IatiActivity();
    }

    /**
     * Create an instance of {@link RecipientRegion }
     * 
     */
    public RecipientRegion createRecipientRegion() {
        return new RecipientRegion();
    }

    /**
     * Create an instance of {@link Location.GazetteerEntry }
     * 
     */
    public Location.GazetteerEntry createLocationGazetteerEntry() {
        return new Location.GazetteerEntry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "default-tied-status")
    public JAXBElement<CodeReqType> createDefaultTiedStatus(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_DefaultTiedStatus_QNAME, CodeReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "implementation-level")
    public JAXBElement<CodeType> createImplementationLevel(CodeType value) {
        return new JAXBElement<CodeType>(_ImplementationLevel_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "activity-status")
    public JAXBElement<CodeType> createActivityStatus(CodeType value) {
        return new JAXBElement<CodeType>(_ActivityStatus_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "collaboration-type")
    public JAXBElement<CodeReqType> createCollaborationType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_CollaborationType_QNAME, CodeReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "default-aid-type")
    public JAXBElement<CodeReqType> createDefaultAidType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_DefaultAidType_QNAME, CodeReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "title")
    public JAXBElement<TextType> createTitle(TextType value) {
        return new JAXBElement<TextType>(_Title_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "default-finance-type")
    public JAXBElement<CodeReqType> createDefaultFinanceType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_DefaultFinanceType_QNAME, CodeReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "default-flow-type")
    public JAXBElement<CodeReqType> createDefaultFlowType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_DefaultFlowType_QNAME, CodeReqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactInfo.MailingAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mailing-address", scope = ContactInfo.class)
    public JAXBElement<ContactInfo.MailingAddress> createContactInfoMailingAddress(ContactInfo.MailingAddress value) {
        return new JAXBElement<ContactInfo.MailingAddress>(_ContactInfoMailingAddress_QNAME, ContactInfo.MailingAddress.class, ContactInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlainType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "organisation", scope = ContactInfo.class)
    public JAXBElement<PlainType> createContactInfoOrganisation(PlainType value) {
        return new JAXBElement<PlainType>(_ContactInfoOrganisation_QNAME, PlainType.class, ContactInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactInfo.Telephone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "telephone", scope = ContactInfo.class)
    public JAXBElement<ContactInfo.Telephone> createContactInfoTelephone(ContactInfo.Telephone value) {
        return new JAXBElement<ContactInfo.Telephone>(_ContactInfoTelephone_QNAME, ContactInfo.Telephone.class, ContactInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlainType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "person-name", scope = ContactInfo.class)
    public JAXBElement<PlainType> createContactInfoPersonName(PlainType value) {
        return new JAXBElement<PlainType>(_ContactInfoPersonName_QNAME, PlainType.class, ContactInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlainType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "email", scope = ContactInfo.class)
    public JAXBElement<PlainType> createContactInfoEmail(PlainType value) {
        return new JAXBElement<PlainType>(_ContactInfoEmail_QNAME, PlainType.class, ContactInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location.Administrative }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "administrative", scope = Location.class)
    public JAXBElement<Location.Administrative> createLocationAdministrative(Location.Administrative value) {
        return new JAXBElement<Location.Administrative>(_LocationAdministrative_QNAME, Location.Administrative.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location.Coordinates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "coordinates", scope = Location.class)
    public JAXBElement<Location.Coordinates> createLocationCoordinates(Location.Coordinates value) {
        return new JAXBElement<Location.Coordinates>(_LocationCoordinates_QNAME, Location.Coordinates.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Location.GazetteerEntry }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "gazetteer-entry", scope = Location.class)
    public JAXBElement<Location.GazetteerEntry> createLocationGazetteerEntry(Location.GazetteerEntry value) {
        return new JAXBElement<Location.GazetteerEntry>(_LocationGazetteerEntry_QNAME, Location.GazetteerEntry.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name", scope = Location.class)
    public JAXBElement<TextType> createLocationName(TextType value) {
        return new JAXBElement<TextType>(_LocationName_QNAME, TextType.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "description", scope = Location.class)
    public JAXBElement<TextType> createLocationDescription(TextType value) {
        return new JAXBElement<TextType>(_LocationDescription_QNAME, TextType.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "location-type", scope = Location.class)
    public JAXBElement<CodeReqType> createLocationLocationType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_LocationLocationType_QNAME, CodeReqType.class, Location.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Conditions.Condition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "condition", scope = Conditions.class)
    public JAXBElement<Conditions.Condition> createConditionsCondition(Conditions.Condition value) {
        return new JAXBElement<Conditions.Condition>(_ConditionsCondition_QNAME, Conditions.Condition.class, Conditions.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndicatorOutcomeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "actual", scope = Result.Indicator.class)
    public JAXBElement<IndicatorOutcomeType> createResultIndicatorActual(IndicatorOutcomeType value) {
        return new JAXBElement<IndicatorOutcomeType>(_ResultIndicatorActual_QNAME, IndicatorOutcomeType.class, Result.Indicator.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndicatorOutcomeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "baseline", scope = Result.Indicator.class)
    public JAXBElement<IndicatorOutcomeType> createResultIndicatorBaseline(IndicatorOutcomeType value) {
        return new JAXBElement<IndicatorOutcomeType>(_ResultIndicatorBaseline_QNAME, IndicatorOutcomeType.class, Result.Indicator.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndicatorOutcomeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "target", scope = Result.Indicator.class)
    public JAXBElement<IndicatorOutcomeType> createResultIndicatorTarget(IndicatorOutcomeType value) {
        return new JAXBElement<IndicatorOutcomeType>(_ResultIndicatorTarget_QNAME, IndicatorOutcomeType.class, Result.Indicator.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "period-start", scope = Budget.class)
    public JAXBElement<DateType> createBudgetPeriodStart(DateType value) {
        return new JAXBElement<DateType>(_BudgetPeriodStart_QNAME, DateType.class, Budget.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "period-end", scope = Budget.class)
    public JAXBElement<DateType> createBudgetPeriodEnd(DateType value) {
        return new JAXBElement<DateType>(_BudgetPeriodEnd_QNAME, DateType.class, Budget.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "value", scope = Budget.class)
    public JAXBElement<CurrencyType> createBudgetValue(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_BudgetValue_QNAME, CurrencyType.class, Budget.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tied-status", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionTiedStatus(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionTiedStatus_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "flow-type", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionFlowType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionFlowType_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transaction.TransactionDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transaction-date", scope = Transaction.class)
    public JAXBElement<Transaction.TransactionDate> createTransactionTransactionDate(Transaction.TransactionDate value) {
        return new JAXBElement<Transaction.TransactionDate>(_TransactionTransactionDate_QNAME, Transaction.TransactionDate.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "description", scope = Transaction.class)
    public JAXBElement<TextType> createTransactionDescription(TextType value) {
        return new JAXBElement<TextType>(_LocationDescription_QNAME, TextType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transaction.ReceiverOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "receiver-org", scope = Transaction.class)
    public JAXBElement<Transaction.ReceiverOrg> createTransactionReceiverOrg(Transaction.ReceiverOrg value) {
        return new JAXBElement<Transaction.ReceiverOrg>(_TransactionReceiverOrg_QNAME, Transaction.ReceiverOrg.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transaction.ProviderOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "provider-org", scope = Transaction.class)
    public JAXBElement<Transaction.ProviderOrg> createTransactionProviderOrg(Transaction.ProviderOrg value) {
        return new JAXBElement<Transaction.ProviderOrg>(_TransactionProviderOrg_QNAME, Transaction.ProviderOrg.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "finance-type", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionFinanceType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionFinanceType_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "disbursement-channel", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionDisbursementChannel(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionDisbursementChannel_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "value", scope = Transaction.class)
    public JAXBElement<CurrencyType> createTransactionValue(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_BudgetValue_QNAME, CurrencyType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "aid-type", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionAidType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionAidType_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transaction-type", scope = Transaction.class)
    public JAXBElement<CodeReqType> createTransactionTransactionType(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_TransactionTransactionType_QNAME, CodeReqType.class, Transaction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Result.Indicator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "indicator", scope = Result.class)
    public JAXBElement<Result.Indicator> createResultIndicator(Result.Indicator value) {
        return new JAXBElement<Result.Indicator>(_ResultIndicator_QNAME, Result.Indicator.class, Result.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "category", scope = DocumentLink.class)
    public JAXBElement<CodeReqType> createDocumentLinkCategory(CodeReqType value) {
        return new JAXBElement<CodeReqType>(_DocumentLinkCategory_QNAME, CodeReqType.class, DocumentLink.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedDisbursement.PeriodStart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "period-start", scope = PlannedDisbursement.class)
    public JAXBElement<PlannedDisbursement.PeriodStart> createPlannedDisbursementPeriodStart(PlannedDisbursement.PeriodStart value) {
        return new JAXBElement<PlannedDisbursement.PeriodStart>(_BudgetPeriodStart_QNAME, PlannedDisbursement.PeriodStart.class, PlannedDisbursement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "period-end", scope = PlannedDisbursement.class)
    public JAXBElement<DateType> createPlannedDisbursementPeriodEnd(DateType value) {
        return new JAXBElement<DateType>(_BudgetPeriodEnd_QNAME, DateType.class, PlannedDisbursement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "value", scope = PlannedDisbursement.class)
    public JAXBElement<CurrencyType> createPlannedDisbursementValue(CurrencyType value) {
        return new JAXBElement<CurrencyType>(_BudgetValue_QNAME, CurrencyType.class, PlannedDisbursement.class, value);
    }

}
