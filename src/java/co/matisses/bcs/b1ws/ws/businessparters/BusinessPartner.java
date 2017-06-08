package co.matisses.bcs.b1ws.ws.businessparters;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "BusinessPartner")
public class BusinessPartner {

    @XmlElement(name = "CardCode")
    protected String cardCode;
    @XmlElement(name = "CardName")
    protected String cardName;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "GroupCode")
    protected Long groupCode;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "ZipCode")
    protected String zipCode;
    @XmlElement(name = "MailAddress")
    protected String mailAddress;
    @XmlElement(name = "MailZipCode")
    protected String mailZipCode;
    @XmlElement(name = "Phone1")
    protected String phone1;
    @XmlElement(name = "Phone2")
    protected String phone2;
    @XmlElement(name = "Fax")
    protected String fax;
    @XmlElement(name = "ContactPerson")
    protected String contactPerson;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "PayTermsGrpCode")
    protected Long payTermsGrpCode;
    @XmlElement(name = "CreditLimit")
    protected Double creditLimit;
    @XmlElement(name = "MaxCommitment")
    protected Double maxCommitment;
    @XmlElement(name = "DiscountPercent")
    protected Double discountPercent;
    @XmlElement(name = "VatLiable")
    protected String vatLiable;
    @XmlElement(name = "FederalTaxID")
    protected String federalTaxID;
    @XmlElement(name = "DeductibleAtSource")
    protected String deductibleAtSource;
    @XmlElement(name = "DeductionPercent")
    protected Double deductionPercent;
    @XmlElement(name = "DeductionValidUntil")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar deductionValidUntil;
    @XmlElement(name = "PriceListNum")
    protected Long priceListNum;
    @XmlElement(name = "IntrestRatePercent")
    protected Double intrestRatePercent;
    @XmlElement(name = "CommissionPercent")
    protected Double commissionPercent;
    @XmlElement(name = "CommissionGroupCode")
    protected Long commissionGroupCode;
    @XmlElement(name = "FreeText")
    protected String freeText;
    @XmlElement(name = "SalesPersonCode")
    protected Long salesPersonCode;
    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "RateDiffAccount")
    protected String rateDiffAccount;
    @XmlElement(name = "Cellular")
    protected String cellular;
    @XmlElement(name = "AvarageLate")
    protected Long avarageLate;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "County")
    protected String county;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "MailCity")
    protected String mailCity;
    @XmlElement(name = "MailCounty")
    protected String mailCounty;
    @XmlElement(name = "MailCountry")
    protected String mailCountry;
    @XmlElement(name = "EmailAddress")
    protected String emailAddress;
    @XmlElement(name = "Picture")
    protected String picture;
    @XmlElement(name = "DefaultAccount")
    protected String defaultAccount;
    @XmlElement(name = "DefaultBranch")
    protected String defaultBranch;
    @XmlElement(name = "DefaultBankCode")
    protected String defaultBankCode;
    @XmlElement(name = "AdditionalID")
    protected String additionalID;
    @XmlElement(name = "Pager")
    protected String pager;
    @XmlElement(name = "FatherCard")
    protected String fatherCard;
    @XmlElement(name = "CardForeignName")
    protected String cardForeignName;
    @XmlElement(name = "FatherType")
    protected String fatherType;
    @XmlElement(name = "DeductionOffice")
    protected String deductionOffice;
    @XmlElement(name = "ExportCode")
    protected String exportCode;
    @XmlElement(name = "MinIntrest")
    protected Double minIntrest;
    @XmlElement(name = "CurrentAccountBalance")
    protected Double currentAccountBalance;
    @XmlElement(name = "OpenDeliveryNotesBalance")
    protected Double openDeliveryNotesBalance;
    @XmlElement(name = "OpenOrdersBalance")
    protected Double openOrdersBalance;
    @XmlElement(name = "VatGroup")
    protected String vatGroup;
    @XmlElement(name = "ShippingType")
    protected Long shippingType;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "Indicator")
    protected String indicator;
    @XmlElement(name = "IBAN")
    protected String iban;
    @XmlElement(name = "CreditCardCode")
    protected Long creditCardCode;
    @XmlElement(name = "CreditCardNum")
    protected String creditCardNum;
    @XmlElement(name = "CreditCardExpiration")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creditCardExpiration;
    @XmlElement(name = "DebitorAccount")
    protected String debitorAccount;
    @XmlElement(name = "OpenOpportunities")
    protected Long openOpportunities;
    @XmlElement(name = "Valid")
    protected String valid;
    @XmlElement(name = "ValidFrom")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validFrom;
    @XmlElement(name = "ValidTo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar validTo;
    @XmlElement(name = "ValidRemarks")
    protected String validRemarks;
    @XmlElement(name = "Frozen")
    protected String frozen;
    @XmlElement(name = "FrozenFrom")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar frozenFrom;
    @XmlElement(name = "FrozenTo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar frozenTo;
    @XmlElement(name = "FrozenRemarks")
    protected String frozenRemarks;
    @XmlElement(name = "Block")
    protected String block;
    @XmlElement(name = "BillToState")
    protected String billToState;
    @XmlElement(name = "ExemptNum")
    protected String exemptNum;
    @XmlElement(name = "Priority")
    protected Long priority;
    @XmlElement(name = "FormCode1099")
    protected Long formCode1099;
    @XmlElement(name = "Box1099")
    protected String box1099;
    @XmlElement(name = "PeymentMethodCode")
    protected String peymentMethodCode;
    @XmlElement(name = "BackOrder")
    protected String backOrder;
    @XmlElement(name = "PartialDelivery")
    protected String partialDelivery;
    @XmlElement(name = "BlockDunning")
    protected String blockDunning;
    @XmlElement(name = "BankCountry")
    protected String bankCountry;
    @XmlElement(name = "HouseBank")
    protected String houseBank;
    @XmlElement(name = "HouseBankCountry")
    protected String houseBankCountry;
    @XmlElement(name = "HouseBankAccount")
    protected String houseBankAccount;
    @XmlElement(name = "ShipToDefault")
    protected String shipToDefault;
    @XmlElement(name = "DunningLevel")
    protected Long dunningLevel;
    @XmlElement(name = "DunningDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dunningDate;
    @XmlElement(name = "CollectionAuthorization")
    protected String collectionAuthorization;
    @XmlElement(name = "DME")
    protected String dme;
    @XmlElement(name = "InstructionKey")
    protected String instructionKey;
    @XmlElement(name = "SinglePayment")
    protected String singlePayment;
    @XmlElement(name = "ISRBillerID")
    protected String isrBillerID;
    @XmlElement(name = "PaymentBlock")
    protected String paymentBlock;
    @XmlElement(name = "ReferenceDetails")
    protected String referenceDetails;
    @XmlElement(name = "HouseBankBranch")
    protected String houseBankBranch;
    @XmlElement(name = "OwnerIDNumber")
    protected String ownerIDNumber;
    @XmlElement(name = "PaymentBlockDescription")
    protected Long paymentBlockDescription;
    @XmlElement(name = "TaxExemptionLetterNum")
    protected String taxExemptionLetterNum;
    @XmlElement(name = "MaxAmountOfExemption")
    protected Double maxAmountOfExemption;
    @XmlElement(name = "ExemptionValidityDateFrom")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar exemptionValidityDateFrom;
    @XmlElement(name = "ExemptionValidityDateTo")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar exemptionValidityDateTo;
    @XmlElement(name = "LinkedBusinessPartner")
    protected String linkedBusinessPartner;
    @XmlElement(name = "LastMultiReconciliationNum")
    protected Long lastMultiReconciliationNum;
    @XmlElement(name = "DeferredTax")
    protected String deferredTax;
    @XmlElement(name = "Equalization")
    protected String equalization;
    @XmlElement(name = "SubjectToWithholdingTax")
    protected String subjectToWithholdingTax;
    @XmlElement(name = "CertificateNumber")
    protected String certificateNumber;
    @XmlElement(name = "ExpirationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "NationalInsuranceNum")
    protected String nationalInsuranceNum;
    @XmlElement(name = "AccrualCriteria")
    protected String accrualCriteria;
    @XmlElement(name = "WTCode")
    protected String wtCode;
    @XmlElement(name = "BillToBuildingFloorRoom")
    protected String billToBuildingFloorRoom;
    @XmlElement(name = "DownPaymentClearAct")
    protected String downPaymentClearAct;
    @XmlElement(name = "ChannelBP")
    protected String channelBP;
    @XmlElement(name = "DefaultTechnician")
    protected Long defaultTechnician;
    @XmlElement(name = "BilltoDefault")
    protected String billtoDefault;
    @XmlElement(name = "CustomerBillofExchangDisc")
    protected String customerBillofExchangDisc;
    @XmlElement(name = "Territory")
    protected Long territory;
    @XmlElement(name = "ShipToBuildingFloorRoom")
    protected String shipToBuildingFloorRoom;
    @XmlElement(name = "CustomerBillofExchangPres")
    protected String customerBillofExchangPres;
    @XmlElement(name = "ProjectCode")
    protected String projectCode;
    @XmlElement(name = "VatGroupLatinAmerica")
    protected String vatGroupLatinAmerica;
    @XmlElement(name = "DunningTerm")
    protected String dunningTerm;
    @XmlElement(name = "Website")
    protected String website;
    @XmlElement(name = "OtherReceivablePayable")
    protected String otherReceivablePayable;
    @XmlElement(name = "BillofExchangeonCollection")
    protected String billofExchangeonCollection;
    @XmlElement(name = "CompanyPrivate")
    protected String companyPrivate;
    @XmlElement(name = "LanguageCode")
    protected Long languageCode;
    @XmlElement(name = "UnpaidBillofExchange")
    protected String unpaidBillofExchange;
    @XmlElement(name = "WithholdingTaxDeductionGroup")
    protected Long withholdingTaxDeductionGroup;
    @XmlElement(name = "ClosingDateProcedureNumber")
    protected Long closingDateProcedureNumber;
    @XmlElement(name = "Profession")
    protected String profession;
    @XmlElement(name = "BankChargesAllocationCode")
    protected String bankChargesAllocationCode;
    @XmlElement(name = "TaxRoundingRule")
    protected String taxRoundingRule;
    @XmlElement(name = "Properties1")
    protected String properties1;
    @XmlElement(name = "Properties2")
    protected String properties2;
    @XmlElement(name = "Properties3")
    protected String properties3;
    @XmlElement(name = "Properties4")
    protected String properties4;
    @XmlElement(name = "Properties5")
    protected String properties5;
    @XmlElement(name = "Properties6")
    protected String properties6;
    @XmlElement(name = "Properties7")
    protected String properties7;
    @XmlElement(name = "Properties8")
    protected String properties8;
    @XmlElement(name = "Properties9")
    protected String properties9;
    @XmlElement(name = "Properties10")
    protected String properties10;
    @XmlElement(name = "Properties11")
    protected String properties11;
    @XmlElement(name = "Properties12")
    protected String properties12;
    @XmlElement(name = "Properties13")
    protected String properties13;
    @XmlElement(name = "Properties14")
    protected String properties14;
    @XmlElement(name = "Properties15")
    protected String properties15;
    @XmlElement(name = "Properties16")
    protected String properties16;
    @XmlElement(name = "Properties17")
    protected String properties17;
    @XmlElement(name = "Properties18")
    protected String properties18;
    @XmlElement(name = "Properties19")
    protected String properties19;
    @XmlElement(name = "Properties20")
    protected String properties20;
    @XmlElement(name = "Properties21")
    protected String properties21;
    @XmlElement(name = "Properties22")
    protected String properties22;
    @XmlElement(name = "Properties23")
    protected String properties23;
    @XmlElement(name = "Properties24")
    protected String properties24;
    @XmlElement(name = "Properties25")
    protected String properties25;
    @XmlElement(name = "Properties26")
    protected String properties26;
    @XmlElement(name = "Properties27")
    protected String properties27;
    @XmlElement(name = "Properties28")
    protected String properties28;
    @XmlElement(name = "Properties29")
    protected String properties29;
    @XmlElement(name = "Properties30")
    protected String properties30;
    @XmlElement(name = "Properties31")
    protected String properties31;
    @XmlElement(name = "Properties32")
    protected String properties32;
    @XmlElement(name = "Properties33")
    protected String properties33;
    @XmlElement(name = "Properties34")
    protected String properties34;
    @XmlElement(name = "Properties35")
    protected String properties35;
    @XmlElement(name = "Properties36")
    protected String properties36;
    @XmlElement(name = "Properties37")
    protected String properties37;
    @XmlElement(name = "Properties38")
    protected String properties38;
    @XmlElement(name = "Properties39")
    protected String properties39;
    @XmlElement(name = "Properties40")
    protected String properties40;
    @XmlElement(name = "Properties41")
    protected String properties41;
    @XmlElement(name = "Properties42")
    protected String properties42;
    @XmlElement(name = "Properties43")
    protected String properties43;
    @XmlElement(name = "Properties44")
    protected String properties44;
    @XmlElement(name = "Properties45")
    protected String properties45;
    @XmlElement(name = "Properties46")
    protected String properties46;
    @XmlElement(name = "Properties47")
    protected String properties47;
    @XmlElement(name = "Properties48")
    protected String properties48;
    @XmlElement(name = "Properties49")
    protected String properties49;
    @XmlElement(name = "Properties50")
    protected String properties50;
    @XmlElement(name = "Properties51")
    protected String properties51;
    @XmlElement(name = "Properties52")
    protected String properties52;
    @XmlElement(name = "Properties53")
    protected String properties53;
    @XmlElement(name = "Properties54")
    protected String properties54;
    @XmlElement(name = "Properties55")
    protected String properties55;
    @XmlElement(name = "Properties56")
    protected String properties56;
    @XmlElement(name = "Properties57")
    protected String properties57;
    @XmlElement(name = "Properties58")
    protected String properties58;
    @XmlElement(name = "Properties59")
    protected String properties59;
    @XmlElement(name = "Properties60")
    protected String properties60;
    @XmlElement(name = "Properties61")
    protected String properties61;
    @XmlElement(name = "Properties62")
    protected String properties62;
    @XmlElement(name = "Properties63")
    protected String properties63;
    @XmlElement(name = "Properties64")
    protected String properties64;
    @XmlElement(name = "CompanyRegistrationNumber")
    protected String companyRegistrationNumber;
    @XmlElement(name = "VerificationNumber")
    protected String verificationNumber;
    @XmlElement(name = "DiscountBaseObject")
    protected String discountBaseObject;
    @XmlElement(name = "DiscountRelations")
    protected String discountRelations;
    @XmlElement(name = "TypeReport")
    protected String typeReport;
    @XmlElement(name = "ThresholdOverlook")
    protected String thresholdOverlook;
    @XmlElement(name = "SurchargeOverlook")
    protected String surchargeOverlook;
    @XmlElement(name = "DownPaymentInterimAccount")
    protected String downPaymentInterimAccount;
    @XmlElement(name = "OperationCode347")
    protected String operationCode347;
    @XmlElement(name = "InsuranceOperation347")
    protected String insuranceOperation347;
    @XmlElement(name = "HierarchicalDeduction")
    protected String hierarchicalDeduction;
    @XmlElement(name = "ShaamGroup")
    protected String shaamGroup;
    @XmlElement(name = "WithholdingTaxCertified")
    protected String withholdingTaxCertified;
    @XmlElement(name = "BookkeepingCertified")
    protected String bookkeepingCertified;
    @XmlElement(name = "PlanningGroup")
    protected String planningGroup;
    @XmlElement(name = "Affiliate")
    protected String affiliate;
    @XmlElement(name = "Industry")
    protected Long industry;
    @XmlElement(name = "VatIDNum")
    protected String vatIDNum;
    @XmlElement(name = "DatevAccount")
    protected Long datevAccount;
    @XmlElement(name = "DatevFirstDataEntry")
    protected String datevFirstDataEntry;
    @XmlElement(name = "GTSRegNo")
    protected String gtsRegNo;
    @XmlElement(name = "GTSBankAccountNo")
    protected String gtsBankAccountNo;
    @XmlElement(name = "GTSBillingAddrTel")
    protected String gtsBillingAddrTel;
    @XmlElement(name = "ETaxWebSite")
    protected Long eTaxWebSite;
    @XmlElement(name = "HouseBankIBAN")
    protected String houseBankIBAN;
    @XmlElement(name = "VATRegistrationNumber")
    protected String vatRegistrationNumber;
    @XmlElement(name = "RepresentativeName")
    protected String representativeName;
    @XmlElement(name = "IndustryType")
    protected String industryType;
    @XmlElement(name = "BusinessType")
    protected String businessType;
    @XmlElement(name = "Series")
    protected Long series;
    @XmlElement(name = "AutomaticPosting")
    protected String automaticPosting;
    @XmlElement(name = "InterestAccount")
    protected String interestAccount;
    @XmlElement(name = "FeeAccount")
    protected String feeAccount;
    @XmlElement(name = "CampaignNumber")
    protected Long campaignNumber;
    @XmlElement(name = "AliasName")
    protected String aliasName;
    @XmlElement(name = "DefaultBlanketAgreementNumber")
    protected Long defaultBlanketAgreementNumber;
    @XmlElement(name = "EffectiveDiscount")
    protected String effectiveDiscount;
    @XmlElement(name = "NoDiscounts")
    protected String noDiscounts;
    @XmlElement(name = "GlobalLocationNumber")
    protected String globalLocationNumber;
    @XmlElement(name = "EDISenderID")
    protected String ediSenderID;
    @XmlElement(name = "EDIRecipientID")
    protected String ediRecipientID;
    @XmlElement(name = "ResidenNumber")
    protected String residenNumber;
    @XmlElement(name = "RelationshipCode")
    protected String relationshipCode;
    @XmlElement(name = "RelationshipDateFrom")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar relationshipDateFrom;
    @XmlElement(name = "RelationshipDateTill")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar relationshipDateTill;
    @XmlElement(name = "UnifiedFederalTaxID")
    protected String unifiedFederalTaxID;
    @XmlElement(name = "AttachmentEntry")
    protected Long attachmentEntry;
    @XmlElement(name = "TypeOfOperation")
    protected String typeOfOperation;
    @XmlElement(name = "EndorsableChecksFromBP")
    protected String endorsableChecksFromBP;
    @XmlElement(name = "AcceptsEndorsedChecks")
    protected String acceptsEndorsedChecks;
    @XmlElement(name = "U_EsAutorret")
    protected String uEsAutorret;
    @XmlElement(name = "U_BPCO_RTC")
    protected String ubpcortc;
    @XmlElement(name = "U_BPCO_TDC")
    protected String ubpcotdc;
    @XmlElement(name = "U_BPCO_CS")
    protected String ubpcocs;
    @XmlElement(name = "U_BPCO_City")
    protected String ubpcoCity;
    @XmlElement(name = "U_BPCO_TP")
    protected String ubpcotp;
    @XmlElement(name = "U_BPCO_Nombre")
    protected String ubpcoNombre;
    @XmlElement(name = "U_BPCO_1Apellido")
    protected String ubpco1Apellido;
    @XmlElement(name = "U_BPCO_2Apellido")
    protected String ubpco2Apellido;
    @XmlElement(name = "U_BPCO_BPExt")
    protected String ubpcobpExt;
    @XmlElement(name = "U_BPCO_TBPE")
    protected String ubpcotbpe;
    @XmlElement(name = "U_BPCO_Address")
    protected String ubpcoAddress;
    @XmlElement(name = "U_Manejo")
    protected String uManejo;
    @XmlElement(name = "U_BD_Erst")
    protected String ubdErst;
    @XmlElement(name = "U_BD_Datev")
    protected String ubdDatev;
    @XmlElement(name = "U_saldoCL")
    protected Long uSaldoCL;
    @XmlElement(name = "U_FechaNacimiento")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar uFechaNacimiento;
    @XmlElement(name = "U_Sexo")
    protected String uSexo;
    @XmlElement(name = "U_OK1_AC_ECO")
    protected String uok1ACECO;
    @XmlElement(name = "BPAddresses")
    protected BusinessPartner.BPAddresses bpAddresses;
    @XmlElement(name = "ContactEmployees")
    protected BusinessPartner.ContactEmployees contactEmployees;
    @XmlElement(name = "BPAccountReceivablePaybleCollection")
    protected BusinessPartner.BPAccountReceivablePaybleCollection bpAccountReceivablePaybleCollection;
    @XmlElement(name = "BPPaymentMethods")
    protected BusinessPartner.BPPaymentMethods bpPaymentMethods;
    @XmlElement(name = "BPWithholdingTaxCollection")
    protected BusinessPartner.BPWithholdingTaxCollection bpWithholdingTaxCollection;
    @XmlElement(name = "BPPaymentDates")
    protected BusinessPartner.BPPaymentDates bpPaymentDates;
    @XmlElement(name = "BPBranchAssignment")
    protected BusinessPartner.BPBranchAssignment bpBranchAssignment;
    @XmlElement(name = "BPFiscalTaxIDCollection")
    protected BusinessPartner.BPFiscalTaxIDCollection bpFiscalTaxIDCollection;
    @XmlElement(name = "DiscountGroups")
    protected BusinessPartner.DiscountGroups discountGroups;
    @XmlElement(name = "BPIntrastatExtension")
    protected BusinessPartner.BPIntrastatExtension bpIntrastatExtension;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String value) {
        this.cardCode = value;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String value) {
        this.cardName = value;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String value) {
        this.cardType = value;
    }

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long value) {
        this.groupCode = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String value) {
        this.zipCode = value;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String value) {
        this.mailAddress = value;
    }

    public String getMailZipCode() {
        return mailZipCode;
    }

    public void setMailZipCode(String value) {
        this.mailZipCode = value;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String value) {
        this.phone1 = value;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String value) {
        this.phone2 = value;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String value) {
        this.fax = value;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String value) {
        this.notes = value;
    }

    public Long getPayTermsGrpCode() {
        return payTermsGrpCode;
    }

    public void setPayTermsGrpCode(Long value) {
        this.payTermsGrpCode = value;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double value) {
        this.creditLimit = value;
    }

    public Double getMaxCommitment() {
        return maxCommitment;
    }

    public void setMaxCommitment(Double value) {
        this.maxCommitment = value;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double value) {
        this.discountPercent = value;
    }

    public String getVatLiable() {
        return vatLiable;
    }

    public void setVatLiable(String value) {
        this.vatLiable = value;
    }

    public String getFederalTaxID() {
        return federalTaxID;
    }

    public void setFederalTaxID(String value) {
        this.federalTaxID = value;
    }

    public String getDeductibleAtSource() {
        return deductibleAtSource;
    }

    public void setDeductibleAtSource(String value) {
        this.deductibleAtSource = value;
    }

    public Double getDeductionPercent() {
        return deductionPercent;
    }

    public void setDeductionPercent(Double value) {
        this.deductionPercent = value;
    }

    public XMLGregorianCalendar getDeductionValidUntil() {
        return deductionValidUntil;
    }

    public void setDeductionValidUntil(XMLGregorianCalendar value) {
        this.deductionValidUntil = value;
    }

    public Long getPriceListNum() {
        return priceListNum;
    }

    public void setPriceListNum(Long value) {
        this.priceListNum = value;
    }

    public Double getIntrestRatePercent() {
        return intrestRatePercent;
    }

    public void setIntrestRatePercent(Double value) {
        this.intrestRatePercent = value;
    }

    public Double getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Double value) {
        this.commissionPercent = value;
    }

    public Long getCommissionGroupCode() {
        return commissionGroupCode;
    }

    public void setCommissionGroupCode(Long value) {
        this.commissionGroupCode = value;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String value) {
        this.freeText = value;
    }

    public Long getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(Long value) {
        this.salesPersonCode = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }

    public String getRateDiffAccount() {
        return rateDiffAccount;
    }

    public void setRateDiffAccount(String value) {
        this.rateDiffAccount = value;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String value) {
        this.cellular = value;
    }

    public Long getAvarageLate() {
        return avarageLate;
    }

    public void setAvarageLate(Long value) {
        this.avarageLate = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String value) {
        this.county = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getMailCity() {
        return mailCity;
    }

    public void setMailCity(String value) {
        this.mailCity = value;
    }

    public String getMailCounty() {
        return mailCounty;
    }

    public void setMailCounty(String value) {
        this.mailCounty = value;
    }

    public String getMailCountry() {
        return mailCountry;
    }

    public void setMailCountry(String value) {
        this.mailCountry = value;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String value) {
        this.picture = value;
    }

    public String getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(String value) {
        this.defaultAccount = value;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String value) {
        this.defaultBranch = value;
    }

    public String getDefaultBankCode() {
        return defaultBankCode;
    }

    public void setDefaultBankCode(String value) {
        this.defaultBankCode = value;
    }

    public String getAdditionalID() {
        return additionalID;
    }

    public void setAdditionalID(String value) {
        this.additionalID = value;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String value) {
        this.pager = value;
    }

    public String getFatherCard() {
        return fatherCard;
    }

    public void setFatherCard(String value) {
        this.fatherCard = value;
    }

    public String getCardForeignName() {
        return cardForeignName;
    }

    public void setCardForeignName(String value) {
        this.cardForeignName = value;
    }

    public String getFatherType() {
        return fatherType;
    }

    public void setFatherType(String value) {
        this.fatherType = value;
    }

    public String getDeductionOffice() {
        return deductionOffice;
    }

    public void setDeductionOffice(String value) {
        this.deductionOffice = value;
    }

    public String getExportCode() {
        return exportCode;
    }

    public void setExportCode(String value) {
        this.exportCode = value;
    }

    public Double getMinIntrest() {
        return minIntrest;
    }

    public void setMinIntrest(Double value) {
        this.minIntrest = value;
    }

    public Double getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public void setCurrentAccountBalance(Double value) {
        this.currentAccountBalance = value;
    }

    public Double getOpenDeliveryNotesBalance() {
        return openDeliveryNotesBalance;
    }

    public void setOpenDeliveryNotesBalance(Double value) {
        this.openDeliveryNotesBalance = value;
    }

    public Double getOpenOrdersBalance() {
        return openOrdersBalance;
    }

    public void setOpenOrdersBalance(Double value) {
        this.openOrdersBalance = value;
    }

    public String getVatGroup() {
        return vatGroup;
    }

    public void setVatGroup(String value) {
        this.vatGroup = value;
    }

    public Long getShippingType() {
        return shippingType;
    }

    public void setShippingType(Long value) {
        this.shippingType = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String value) {
        this.indicator = value;
    }

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String value) {
        this.iban = value;
    }

    public Long getCreditCardCode() {
        return creditCardCode;
    }

    public void setCreditCardCode(Long value) {
        this.creditCardCode = value;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String value) {
        this.creditCardNum = value;
    }

    public XMLGregorianCalendar getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(XMLGregorianCalendar value) {
        this.creditCardExpiration = value;
    }

    public String getDebitorAccount() {
        return debitorAccount;
    }

    public void setDebitorAccount(String value) {
        this.debitorAccount = value;
    }

    public Long getOpenOpportunities() {
        return openOpportunities;
    }

    public void setOpenOpportunities(Long value) {
        this.openOpportunities = value;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String value) {
        this.valid = value;
    }

    public XMLGregorianCalendar getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(XMLGregorianCalendar value) {
        this.validFrom = value;
    }

    public XMLGregorianCalendar getValidTo() {
        return validTo;
    }

    public void setValidTo(XMLGregorianCalendar value) {
        this.validTo = value;
    }

    public String getValidRemarks() {
        return validRemarks;
    }

    public void setValidRemarks(String value) {
        this.validRemarks = value;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String value) {
        this.frozen = value;
    }

    public XMLGregorianCalendar getFrozenFrom() {
        return frozenFrom;
    }

    public void setFrozenFrom(XMLGregorianCalendar value) {
        this.frozenFrom = value;
    }

    public XMLGregorianCalendar getFrozenTo() {
        return frozenTo;
    }

    public void setFrozenTo(XMLGregorianCalendar value) {
        this.frozenTo = value;
    }

    public String getFrozenRemarks() {
        return frozenRemarks;
    }

    public void setFrozenRemarks(String value) {
        this.frozenRemarks = value;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String value) {
        this.block = value;
    }

    public String getBillToState() {
        return billToState;
    }

    public void setBillToState(String value) {
        this.billToState = value;
    }

    public String getExemptNum() {
        return exemptNum;
    }

    public void setExemptNum(String value) {
        this.exemptNum = value;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long value) {
        this.priority = value;
    }

    public Long getFormCode1099() {
        return formCode1099;
    }

    public void setFormCode1099(Long value) {
        this.formCode1099 = value;
    }

    public String getBox1099() {
        return box1099;
    }

    public void setBox1099(String value) {
        this.box1099 = value;
    }

    public String getPeymentMethodCode() {
        return peymentMethodCode;
    }

    public void setPeymentMethodCode(String value) {
        this.peymentMethodCode = value;
    }

    public String getBackOrder() {
        return backOrder;
    }

    public void setBackOrder(String value) {
        this.backOrder = value;
    }

    public String getPartialDelivery() {
        return partialDelivery;
    }

    public void setPartialDelivery(String value) {
        this.partialDelivery = value;
    }

    public String getBlockDunning() {
        return blockDunning;
    }

    public void setBlockDunning(String value) {
        this.blockDunning = value;
    }

    public String getBankCountry() {
        return bankCountry;
    }

    public void setBankCountry(String value) {
        this.bankCountry = value;
    }

    public String getHouseBank() {
        return houseBank;
    }

    public void setHouseBank(String value) {
        this.houseBank = value;
    }

    public String getHouseBankCountry() {
        return houseBankCountry;
    }

    public void setHouseBankCountry(String value) {
        this.houseBankCountry = value;
    }

    public String getHouseBankAccount() {
        return houseBankAccount;
    }

    public void setHouseBankAccount(String value) {
        this.houseBankAccount = value;
    }

    public String getShipToDefault() {
        return shipToDefault;
    }

    public void setShipToDefault(String value) {
        this.shipToDefault = value;
    }

    public Long getDunningLevel() {
        return dunningLevel;
    }

    public void setDunningLevel(Long value) {
        this.dunningLevel = value;
    }

    public XMLGregorianCalendar getDunningDate() {
        return dunningDate;
    }

    public void setDunningDate(XMLGregorianCalendar value) {
        this.dunningDate = value;
    }

    public String getCollectionAuthorization() {
        return collectionAuthorization;
    }

    public void setCollectionAuthorization(String value) {
        this.collectionAuthorization = value;
    }

    public String getDME() {
        return dme;
    }

    public void setDME(String value) {
        this.dme = value;
    }

    public String getInstructionKey() {
        return instructionKey;
    }

    public void setInstructionKey(String value) {
        this.instructionKey = value;
    }

    public String getSinglePayment() {
        return singlePayment;
    }

    public void setSinglePayment(String value) {
        this.singlePayment = value;
    }

    public String getISRBillerID() {
        return isrBillerID;
    }

    public void setISRBillerID(String value) {
        this.isrBillerID = value;
    }

    public String getPaymentBlock() {
        return paymentBlock;
    }

    public void setPaymentBlock(String value) {
        this.paymentBlock = value;
    }

    public String getReferenceDetails() {
        return referenceDetails;
    }

    public void setReferenceDetails(String value) {
        this.referenceDetails = value;
    }

    public String getHouseBankBranch() {
        return houseBankBranch;
    }

    public void setHouseBankBranch(String value) {
        this.houseBankBranch = value;
    }

    public String getOwnerIDNumber() {
        return ownerIDNumber;
    }

    public void setOwnerIDNumber(String value) {
        this.ownerIDNumber = value;
    }

    public Long getPaymentBlockDescription() {
        return paymentBlockDescription;
    }

    public void setPaymentBlockDescription(Long value) {
        this.paymentBlockDescription = value;
    }

    public String getTaxExemptionLetterNum() {
        return taxExemptionLetterNum;
    }

    public void setTaxExemptionLetterNum(String value) {
        this.taxExemptionLetterNum = value;
    }

    public Double getMaxAmountOfExemption() {
        return maxAmountOfExemption;
    }

    public void setMaxAmountOfExemption(Double value) {
        this.maxAmountOfExemption = value;
    }

    public XMLGregorianCalendar getExemptionValidityDateFrom() {
        return exemptionValidityDateFrom;
    }

    public void setExemptionValidityDateFrom(XMLGregorianCalendar value) {
        this.exemptionValidityDateFrom = value;
    }

    public XMLGregorianCalendar getExemptionValidityDateTo() {
        return exemptionValidityDateTo;
    }

    public void setExemptionValidityDateTo(XMLGregorianCalendar value) {
        this.exemptionValidityDateTo = value;
    }

    public String getLinkedBusinessPartner() {
        return linkedBusinessPartner;
    }

    public void setLinkedBusinessPartner(String value) {
        this.linkedBusinessPartner = value;
    }

    public Long getLastMultiReconciliationNum() {
        return lastMultiReconciliationNum;
    }

    public void setLastMultiReconciliationNum(Long value) {
        this.lastMultiReconciliationNum = value;
    }

    public String getDeferredTax() {
        return deferredTax;
    }

    public void setDeferredTax(String value) {
        this.deferredTax = value;
    }

    public String getEqualization() {
        return equalization;
    }

    public void setEqualization(String value) {
        this.equalization = value;
    }

    public String getSubjectToWithholdingTax() {
        return subjectToWithholdingTax;
    }

    public void setSubjectToWithholdingTax(String value) {
        this.subjectToWithholdingTax = value;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String value) {
        this.certificateNumber = value;
    }

    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    public String getNationalInsuranceNum() {
        return nationalInsuranceNum;
    }

    public void setNationalInsuranceNum(String value) {
        this.nationalInsuranceNum = value;
    }

    public String getAccrualCriteria() {
        return accrualCriteria;
    }

    public void setAccrualCriteria(String value) {
        this.accrualCriteria = value;
    }

    public String getWTCode() {
        return wtCode;
    }

    public void setWTCode(String value) {
        this.wtCode = value;
    }

    public String getBillToBuildingFloorRoom() {
        return billToBuildingFloorRoom;
    }

    public void setBillToBuildingFloorRoom(String value) {
        this.billToBuildingFloorRoom = value;
    }

    public String getDownPaymentClearAct() {
        return downPaymentClearAct;
    }

    public void setDownPaymentClearAct(String value) {
        this.downPaymentClearAct = value;
    }

    public String getChannelBP() {
        return channelBP;
    }

    public void setChannelBP(String value) {
        this.channelBP = value;
    }

    public Long getDefaultTechnician() {
        return defaultTechnician;
    }

    public void setDefaultTechnician(Long value) {
        this.defaultTechnician = value;
    }

    public String getBilltoDefault() {
        return billtoDefault;
    }

    public void setBilltoDefault(String value) {
        this.billtoDefault = value;
    }

    public String getCustomerBillofExchangDisc() {
        return customerBillofExchangDisc;
    }

    public void setCustomerBillofExchangDisc(String value) {
        this.customerBillofExchangDisc = value;
    }

    public Long getTerritory() {
        return territory;
    }

    public void setTerritory(Long value) {
        this.territory = value;
    }

    public String getShipToBuildingFloorRoom() {
        return shipToBuildingFloorRoom;
    }

    public void setShipToBuildingFloorRoom(String value) {
        this.shipToBuildingFloorRoom = value;
    }

    public String getCustomerBillofExchangPres() {
        return customerBillofExchangPres;
    }

    public void setCustomerBillofExchangPres(String value) {
        this.customerBillofExchangPres = value;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    public String getVatGroupLatinAmerica() {
        return vatGroupLatinAmerica;
    }

    public void setVatGroupLatinAmerica(String value) {
        this.vatGroupLatinAmerica = value;
    }

    public String getDunningTerm() {
        return dunningTerm;
    }

    public void setDunningTerm(String value) {
        this.dunningTerm = value;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String value) {
        this.website = value;
    }

    public String getOtherReceivablePayable() {
        return otherReceivablePayable;
    }

    public void setOtherReceivablePayable(String value) {
        this.otherReceivablePayable = value;
    }

    public String getBillofExchangeonCollection() {
        return billofExchangeonCollection;
    }

    public void setBillofExchangeonCollection(String value) {
        this.billofExchangeonCollection = value;
    }

    public String getCompanyPrivate() {
        return companyPrivate;
    }

    public void setCompanyPrivate(String value) {
        this.companyPrivate = value;
    }

    public Long getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(Long value) {
        this.languageCode = value;
    }

    public String getUnpaidBillofExchange() {
        return unpaidBillofExchange;
    }

    public void setUnpaidBillofExchange(String value) {
        this.unpaidBillofExchange = value;
    }

    public Long getWithholdingTaxDeductionGroup() {
        return withholdingTaxDeductionGroup;
    }

    public void setWithholdingTaxDeductionGroup(Long value) {
        this.withholdingTaxDeductionGroup = value;
    }

    public Long getClosingDateProcedureNumber() {
        return closingDateProcedureNumber;
    }

    public void setClosingDateProcedureNumber(Long value) {
        this.closingDateProcedureNumber = value;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String value) {
        this.profession = value;
    }

    public String getBankChargesAllocationCode() {
        return bankChargesAllocationCode;
    }

    public void setBankChargesAllocationCode(String value) {
        this.bankChargesAllocationCode = value;
    }

    public String getTaxRoundingRule() {
        return taxRoundingRule;
    }

    public void setTaxRoundingRule(String value) {
        this.taxRoundingRule = value;
    }

    public String getProperties1() {
        return properties1;
    }

    public void setProperties1(String value) {
        this.properties1 = value;
    }

    public String getProperties2() {
        return properties2;
    }

    public void setProperties2(String value) {
        this.properties2 = value;
    }

    public String getProperties3() {
        return properties3;
    }

    public void setProperties3(String value) {
        this.properties3 = value;
    }

    public String getProperties4() {
        return properties4;
    }

    public void setProperties4(String value) {
        this.properties4 = value;
    }

    public String getProperties5() {
        return properties5;
    }

    public void setProperties5(String value) {
        this.properties5 = value;
    }

    public String getProperties6() {
        return properties6;
    }

    public void setProperties6(String value) {
        this.properties6 = value;
    }

    public String getProperties7() {
        return properties7;
    }

    public void setProperties7(String value) {
        this.properties7 = value;
    }

    public String getProperties8() {
        return properties8;
    }

    public void setProperties8(String value) {
        this.properties8 = value;
    }

    public String getProperties9() {
        return properties9;
    }

    public void setProperties9(String value) {
        this.properties9 = value;
    }

    public String getProperties10() {
        return properties10;
    }

    public void setProperties10(String value) {
        this.properties10 = value;
    }

    public String getProperties11() {
        return properties11;
    }

    public void setProperties11(String value) {
        this.properties11 = value;
    }

    public String getProperties12() {
        return properties12;
    }

    public void setProperties12(String value) {
        this.properties12 = value;
    }

    public String getProperties13() {
        return properties13;
    }

    public void setProperties13(String value) {
        this.properties13 = value;
    }

    public String getProperties14() {
        return properties14;
    }

    public void setProperties14(String value) {
        this.properties14 = value;
    }

    public String getProperties15() {
        return properties15;
    }

    public void setProperties15(String value) {
        this.properties15 = value;
    }

    public String getProperties16() {
        return properties16;
    }

    public void setProperties16(String value) {
        this.properties16 = value;
    }

    public String getProperties17() {
        return properties17;
    }

    public void setProperties17(String value) {
        this.properties17 = value;
    }

    public String getProperties18() {
        return properties18;
    }

    public void setProperties18(String value) {
        this.properties18 = value;
    }

    public String getProperties19() {
        return properties19;
    }

    public void setProperties19(String value) {
        this.properties19 = value;
    }

    public String getProperties20() {
        return properties20;
    }

    public void setProperties20(String value) {
        this.properties20 = value;
    }

    public String getProperties21() {
        return properties21;
    }

    public void setProperties21(String value) {
        this.properties21 = value;
    }

    public String getProperties22() {
        return properties22;
    }

    public void setProperties22(String value) {
        this.properties22 = value;
    }

    public String getProperties23() {
        return properties23;
    }

    public void setProperties23(String value) {
        this.properties23 = value;
    }

    public String getProperties24() {
        return properties24;
    }

    public void setProperties24(String value) {
        this.properties24 = value;
    }

    public String getProperties25() {
        return properties25;
    }

    public void setProperties25(String value) {
        this.properties25 = value;
    }

    public String getProperties26() {
        return properties26;
    }

    public void setProperties26(String value) {
        this.properties26 = value;
    }

    public String getProperties27() {
        return properties27;
    }

    public void setProperties27(String value) {
        this.properties27 = value;
    }

    public String getProperties28() {
        return properties28;
    }

    public void setProperties28(String value) {
        this.properties28 = value;
    }

    public String getProperties29() {
        return properties29;
    }

    public void setProperties29(String value) {
        this.properties29 = value;
    }

    public String getProperties30() {
        return properties30;
    }

    public void setProperties30(String value) {
        this.properties30 = value;
    }

    public String getProperties31() {
        return properties31;
    }

    public void setProperties31(String value) {
        this.properties31 = value;
    }

    public String getProperties32() {
        return properties32;
    }

    public void setProperties32(String value) {
        this.properties32 = value;
    }

    public String getProperties33() {
        return properties33;
    }

    public void setProperties33(String value) {
        this.properties33 = value;
    }

    public String getProperties34() {
        return properties34;
    }

    public void setProperties34(String value) {
        this.properties34 = value;
    }

    public String getProperties35() {
        return properties35;
    }

    public void setProperties35(String value) {
        this.properties35 = value;
    }

    public String getProperties36() {
        return properties36;
    }

    public void setProperties36(String value) {
        this.properties36 = value;
    }

    public String getProperties37() {
        return properties37;
    }

    public void setProperties37(String value) {
        this.properties37 = value;
    }

    public String getProperties38() {
        return properties38;
    }

    public void setProperties38(String value) {
        this.properties38 = value;
    }

    public String getProperties39() {
        return properties39;
    }

    public void setProperties39(String value) {
        this.properties39 = value;
    }

    public String getProperties40() {
        return properties40;
    }

    public void setProperties40(String value) {
        this.properties40 = value;
    }

    public String getProperties41() {
        return properties41;
    }

    public void setProperties41(String value) {
        this.properties41 = value;
    }

    public String getProperties42() {
        return properties42;
    }

    public void setProperties42(String value) {
        this.properties42 = value;
    }

    public String getProperties43() {
        return properties43;
    }

    public void setProperties43(String value) {
        this.properties43 = value;
    }

    public String getProperties44() {
        return properties44;
    }

    public void setProperties44(String value) {
        this.properties44 = value;
    }

    public String getProperties45() {
        return properties45;
    }

    public void setProperties45(String value) {
        this.properties45 = value;
    }

    public String getProperties46() {
        return properties46;
    }

    public void setProperties46(String value) {
        this.properties46 = value;
    }

    public String getProperties47() {
        return properties47;
    }

    public void setProperties47(String value) {
        this.properties47 = value;
    }

    public String getProperties48() {
        return properties48;
    }

    public void setProperties48(String value) {
        this.properties48 = value;
    }

    public String getProperties49() {
        return properties49;
    }

    public void setProperties49(String value) {
        this.properties49 = value;
    }

    public String getProperties50() {
        return properties50;
    }

    public void setProperties50(String value) {
        this.properties50 = value;
    }

    public String getProperties51() {
        return properties51;
    }

    public void setProperties51(String value) {
        this.properties51 = value;
    }

    public String getProperties52() {
        return properties52;
    }

    public void setProperties52(String value) {
        this.properties52 = value;
    }

    public String getProperties53() {
        return properties53;
    }

    public void setProperties53(String value) {
        this.properties53 = value;
    }

    public String getProperties54() {
        return properties54;
    }

    public void setProperties54(String value) {
        this.properties54 = value;
    }

    public String getProperties55() {
        return properties55;
    }

    public void setProperties55(String value) {
        this.properties55 = value;
    }

    public String getProperties56() {
        return properties56;
    }

    public void setProperties56(String value) {
        this.properties56 = value;
    }

    public String getProperties57() {
        return properties57;
    }

    public void setProperties57(String value) {
        this.properties57 = value;
    }

    public String getProperties58() {
        return properties58;
    }

    public void setProperties58(String value) {
        this.properties58 = value;
    }

    public String getProperties59() {
        return properties59;
    }

    public void setProperties59(String value) {
        this.properties59 = value;
    }

    public String getProperties60() {
        return properties60;
    }

    public void setProperties60(String value) {
        this.properties60 = value;
    }

    public String getProperties61() {
        return properties61;
    }

    public void setProperties61(String value) {
        this.properties61 = value;
    }

    public String getProperties62() {
        return properties62;
    }

    public void setProperties62(String value) {
        this.properties62 = value;
    }

    public String getProperties63() {
        return properties63;
    }

    public void setProperties63(String value) {
        this.properties63 = value;
    }

    public String getProperties64() {
        return properties64;
    }

    public void setProperties64(String value) {
        this.properties64 = value;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String value) {
        this.companyRegistrationNumber = value;
    }

    public String getVerificationNumber() {
        return verificationNumber;
    }

    public void setVerificationNumber(String value) {
        this.verificationNumber = value;
    }

    public String getDiscountBaseObject() {
        return discountBaseObject;
    }

    public void setDiscountBaseObject(String value) {
        this.discountBaseObject = value;
    }

    public String getDiscountRelations() {
        return discountRelations;
    }

    public void setDiscountRelations(String value) {
        this.discountRelations = value;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String value) {
        this.typeReport = value;
    }

    public String getThresholdOverlook() {
        return thresholdOverlook;
    }

    public void setThresholdOverlook(String value) {
        this.thresholdOverlook = value;
    }

    public String getSurchargeOverlook() {
        return surchargeOverlook;
    }

    public void setSurchargeOverlook(String value) {
        this.surchargeOverlook = value;
    }

    public String getDownPaymentInterimAccount() {
        return downPaymentInterimAccount;
    }

    public void setDownPaymentInterimAccount(String value) {
        this.downPaymentInterimAccount = value;
    }

    public String getOperationCode347() {
        return operationCode347;
    }

    public void setOperationCode347(String value) {
        this.operationCode347 = value;
    }

    public String getInsuranceOperation347() {
        return insuranceOperation347;
    }

    public void setInsuranceOperation347(String value) {
        this.insuranceOperation347 = value;
    }

    public String getHierarchicalDeduction() {
        return hierarchicalDeduction;
    }

    public void setHierarchicalDeduction(String value) {
        this.hierarchicalDeduction = value;
    }

    public String getShaamGroup() {
        return shaamGroup;
    }

    public void setShaamGroup(String value) {
        this.shaamGroup = value;
    }

    public String getWithholdingTaxCertified() {
        return withholdingTaxCertified;
    }

    public void setWithholdingTaxCertified(String value) {
        this.withholdingTaxCertified = value;
    }

    public String getBookkeepingCertified() {
        return bookkeepingCertified;
    }

    public void setBookkeepingCertified(String value) {
        this.bookkeepingCertified = value;
    }

    public String getPlanningGroup() {
        return planningGroup;
    }

    public void setPlanningGroup(String value) {
        this.planningGroup = value;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String value) {
        this.affiliate = value;
    }

    public Long getIndustry() {
        return industry;
    }

    public void setIndustry(Long value) {
        this.industry = value;
    }

    public String getVatIDNum() {
        return vatIDNum;
    }

    public void setVatIDNum(String value) {
        this.vatIDNum = value;
    }

    public Long getDatevAccount() {
        return datevAccount;
    }

    public void setDatevAccount(Long value) {
        this.datevAccount = value;
    }

    public String getDatevFirstDataEntry() {
        return datevFirstDataEntry;
    }

    public void setDatevFirstDataEntry(String value) {
        this.datevFirstDataEntry = value;
    }

    public String getGTSRegNo() {
        return gtsRegNo;
    }

    public void setGTSRegNo(String value) {
        this.gtsRegNo = value;
    }

    public String getGTSBankAccountNo() {
        return gtsBankAccountNo;
    }

    public void setGTSBankAccountNo(String value) {
        this.gtsBankAccountNo = value;
    }

    public String getGTSBillingAddrTel() {
        return gtsBillingAddrTel;
    }

    public void setGTSBillingAddrTel(String value) {
        this.gtsBillingAddrTel = value;
    }

    public Long getETaxWebSite() {
        return eTaxWebSite;
    }

    public void setETaxWebSite(Long value) {
        this.eTaxWebSite = value;
    }

    public String getHouseBankIBAN() {
        return houseBankIBAN;
    }

    public void setHouseBankIBAN(String value) {
        this.houseBankIBAN = value;
    }

    public String getVATRegistrationNumber() {
        return vatRegistrationNumber;
    }

    public void setVATRegistrationNumber(String value) {
        this.vatRegistrationNumber = value;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String value) {
        this.representativeName = value;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String value) {
        this.industryType = value;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String value) {
        this.businessType = value;
    }

    public Long getSeries() {
        return series;
    }

    public void setSeries(Long value) {
        this.series = value;
    }

    public String getAutomaticPosting() {
        return automaticPosting;
    }

    public void setAutomaticPosting(String value) {
        this.automaticPosting = value;
    }

    public String getInterestAccount() {
        return interestAccount;
    }

    public void setInterestAccount(String value) {
        this.interestAccount = value;
    }

    public String getFeeAccount() {
        return feeAccount;
    }

    public void setFeeAccount(String value) {
        this.feeAccount = value;
    }

    public Long getCampaignNumber() {
        return campaignNumber;
    }

    public void setCampaignNumber(Long value) {
        this.campaignNumber = value;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String value) {
        this.aliasName = value;
    }

    public Long getDefaultBlanketAgreementNumber() {
        return defaultBlanketAgreementNumber;
    }

    public void setDefaultBlanketAgreementNumber(Long value) {
        this.defaultBlanketAgreementNumber = value;
    }

    public String getEffectiveDiscount() {
        return effectiveDiscount;
    }

    public void setEffectiveDiscount(String value) {
        this.effectiveDiscount = value;
    }

    public String getNoDiscounts() {
        return noDiscounts;
    }

    public void setNoDiscounts(String value) {
        this.noDiscounts = value;
    }

    public String getGlobalLocationNumber() {
        return globalLocationNumber;
    }

    public void setGlobalLocationNumber(String value) {
        this.globalLocationNumber = value;
    }

    public String getEDISenderID() {
        return ediSenderID;
    }

    public void setEDISenderID(String value) {
        this.ediSenderID = value;
    }

    public String getEDIRecipientID() {
        return ediRecipientID;
    }

    public void setEDIRecipientID(String value) {
        this.ediRecipientID = value;
    }

    public String getResidenNumber() {
        return residenNumber;
    }

    public void setResidenNumber(String value) {
        this.residenNumber = value;
    }

    public String getRelationshipCode() {
        return relationshipCode;
    }

    public void setRelationshipCode(String value) {
        this.relationshipCode = value;
    }

    public XMLGregorianCalendar getRelationshipDateFrom() {
        return relationshipDateFrom;
    }

    public void setRelationshipDateFrom(XMLGregorianCalendar value) {
        this.relationshipDateFrom = value;
    }

    public XMLGregorianCalendar getRelationshipDateTill() {
        return relationshipDateTill;
    }

    public void setRelationshipDateTill(XMLGregorianCalendar value) {
        this.relationshipDateTill = value;
    }

    public String getUnifiedFederalTaxID() {
        return unifiedFederalTaxID;
    }

    public void setUnifiedFederalTaxID(String value) {
        this.unifiedFederalTaxID = value;
    }

    public Long getAttachmentEntry() {
        return attachmentEntry;
    }

    public void setAttachmentEntry(Long value) {
        this.attachmentEntry = value;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(String value) {
        this.typeOfOperation = value;
    }

    public String getEndorsableChecksFromBP() {
        return endorsableChecksFromBP;
    }

    public void setEndorsableChecksFromBP(String value) {
        this.endorsableChecksFromBP = value;
    }

    public String getAcceptsEndorsedChecks() {
        return acceptsEndorsedChecks;
    }

    public void setAcceptsEndorsedChecks(String value) {
        this.acceptsEndorsedChecks = value;
    }

    public String getUEsAutorret() {
        return uEsAutorret;
    }

    public void setUEsAutorret(String value) {
        this.uEsAutorret = value;
    }

    public String getUBPCORTC() {
        return ubpcortc;
    }

    public void setUBPCORTC(String value) {
        this.ubpcortc = value;
    }

    public String getUBPCOTDC() {
        return ubpcotdc;
    }

    public void setUBPCOTDC(String value) {
        this.ubpcotdc = value;
    }

    public String getUBPCOCS() {
        return ubpcocs;
    }

    public void setUBPCOCS(String value) {
        this.ubpcocs = value;
    }

    public String getUBPCOCity() {
        return ubpcoCity;
    }

    public void setUBPCOCity(String value) {
        this.ubpcoCity = value;
    }

    public String getUBPCOTP() {
        return ubpcotp;
    }

    public void setUBPCOTP(String value) {
        this.ubpcotp = value;
    }

    public String getUBPCONombre() {
        return ubpcoNombre;
    }

    public void setUBPCONombre(String value) {
        this.ubpcoNombre = value;
    }

    public String getUBPCO1Apellido() {
        return ubpco1Apellido;
    }

    public void setUBPCO1Apellido(String value) {
        this.ubpco1Apellido = value;
    }

    public String getUBPCO2Apellido() {
        return ubpco2Apellido;
    }

    public void setUBPCO2Apellido(String value) {
        this.ubpco2Apellido = value;
    }

    public String getUBPCOBPExt() {
        return ubpcobpExt;
    }

    public void setUBPCOBPExt(String value) {
        this.ubpcobpExt = value;
    }

    public String getUBPCOTBPE() {
        return ubpcotbpe;
    }

    public void setUBPCOTBPE(String value) {
        this.ubpcotbpe = value;
    }

    public String getUBPCOAddress() {
        return ubpcoAddress;
    }

    public void setUBPCOAddress(String value) {
        this.ubpcoAddress = value;
    }

    public String getUManejo() {
        return uManejo;
    }

    public void setUManejo(String value) {
        this.uManejo = value;
    }

    public String getUBDErst() {
        return ubdErst;
    }

    public void setUBDErst(String value) {
        this.ubdErst = value;
    }

    public String getUBDDatev() {
        return ubdDatev;
    }

    public void setUBDDatev(String value) {
        this.ubdDatev = value;
    }

    public Long getUSaldoCL() {
        return uSaldoCL;
    }

    public void setUSaldoCL(Long value) {
        this.uSaldoCL = value;
    }

    public XMLGregorianCalendar getUFechaNacimiento() {
        return uFechaNacimiento;
    }

    public void setUFechaNacimiento(XMLGregorianCalendar value) {
        this.uFechaNacimiento = value;
    }

    public String getUSexo() {
        return uSexo;
    }

    public void setUSexo(String value) {
        this.uSexo = value;
    }

    public String getUOK1ACECO() {
        return uok1ACECO;
    }

    public void setUOK1ACECO(String value) {
        this.uok1ACECO = value;
    }

    public BusinessPartner.BPAddresses getBPAddresses() {
        return bpAddresses;
    }

    public void setBPAddresses(BusinessPartner.BPAddresses value) {
        this.bpAddresses = value;
    }

    public BusinessPartner.ContactEmployees getContactEmployees() {
        return contactEmployees;
    }

    public void setContactEmployees(BusinessPartner.ContactEmployees value) {
        this.contactEmployees = value;
    }

    public BusinessPartner.BPAccountReceivablePaybleCollection getBPAccountReceivablePaybleCollection() {
        return bpAccountReceivablePaybleCollection;
    }

    public void setBPAccountReceivablePaybleCollection(BusinessPartner.BPAccountReceivablePaybleCollection value) {
        this.bpAccountReceivablePaybleCollection = value;
    }

    public BusinessPartner.BPPaymentMethods getBPPaymentMethods() {
        return bpPaymentMethods;
    }

    public void setBPPaymentMethods(BusinessPartner.BPPaymentMethods value) {
        this.bpPaymentMethods = value;
    }

    public BusinessPartner.BPWithholdingTaxCollection getBPWithholdingTaxCollection() {
        return bpWithholdingTaxCollection;
    }

    public void setBPWithholdingTaxCollection(BusinessPartner.BPWithholdingTaxCollection value) {
        this.bpWithholdingTaxCollection = value;
    }

    public BusinessPartner.BPPaymentDates getBPPaymentDates() {
        return bpPaymentDates;
    }

    public void setBPPaymentDates(BusinessPartner.BPPaymentDates value) {
        this.bpPaymentDates = value;
    }

    public BusinessPartner.BPBranchAssignment getBPBranchAssignment() {
        return bpBranchAssignment;
    }

    public void setBPBranchAssignment(BusinessPartner.BPBranchAssignment value) {
        this.bpBranchAssignment = value;
    }

    public BusinessPartner.BPFiscalTaxIDCollection getBPFiscalTaxIDCollection() {
        return bpFiscalTaxIDCollection;
    }

    public void setBPFiscalTaxIDCollection(BusinessPartner.BPFiscalTaxIDCollection value) {
        this.bpFiscalTaxIDCollection = value;
    }

    public BusinessPartner.DiscountGroups getDiscountGroups() {
        return discountGroups;
    }

    public void setDiscountGroups(BusinessPartner.DiscountGroups value) {
        this.discountGroups = value;
    }

    public BusinessPartner.BPIntrastatExtension getBPIntrastatExtension() {
        return bpIntrastatExtension;
    }

    public void setBPIntrastatExtension(BusinessPartner.BPIntrastatExtension value) {
        this.bpIntrastatExtension = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpAccountReceivablePayble"
    })
    public static class BPAccountReceivablePaybleCollection {

        @XmlElement(name = "BPAccountReceivablePayble")
        protected List<BusinessPartner.BPAccountReceivablePaybleCollection.BPAccountReceivablePayble> bpAccountReceivablePayble;

        public List<BusinessPartner.BPAccountReceivablePaybleCollection.BPAccountReceivablePayble> getBPAccountReceivablePayble() {
            if (bpAccountReceivablePayble == null) {
                bpAccountReceivablePayble = new ArrayList<>();
            }
            return this.bpAccountReceivablePayble;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPAccountReceivablePayble {

            @XmlElement(name = "AccountType")
            protected String accountType;
            @XmlElement(name = "AccountCode")
            protected String accountCode;
            @XmlElement(name = "BPCode")
            protected String bpCode;

            public String getAccountType() {
                return accountType;
            }

            public void setAccountType(String value) {
                this.accountType = value;
            }

            public String getAccountCode() {
                return accountCode;
            }

            public void setAccountCode(String value) {
                this.accountCode = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpAddress"
    })
    public static class BPAddresses {

        @XmlElement(name = "BPAddress")
        protected List<BusinessPartner.BPAddresses.BPAddress> bpAddress;

        public List<BusinessPartner.BPAddresses.BPAddress> getBPAddress() {
            if (bpAddress == null) {
                bpAddress = new ArrayList<>();
            }
            return this.bpAddress;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPAddress {

            @XmlElement(name = "AddressName")
            protected String addressName;
            @XmlElement(name = "Street")
            protected String street;
            @XmlElement(name = "Block")
            protected String block;
            @XmlElement(name = "ZipCode")
            protected String zipCode;
            @XmlElement(name = "City")
            protected String city;
            @XmlElement(name = "County")
            protected String county;
            @XmlElement(name = "Country")
            protected String country;
            @XmlElement(name = "State")
            protected String state;
            @XmlElement(name = "FederalTaxID")
            protected String federalTaxID;
            @XmlElement(name = "TaxCode")
            protected String taxCode;
            @XmlElement(name = "BuildingFloorRoom")
            protected String buildingFloorRoom;
            @XmlElement(name = "AddressType")
            protected String addressType;
            @XmlElement(name = "AddressName2")
            protected String addressName2;
            @XmlElement(name = "AddressName3")
            protected String addressName3;
            @XmlElement(name = "TypeOfAddress")
            protected String typeOfAddress;
            @XmlElement(name = "StreetNo")
            protected String streetNo;
            @XmlElement(name = "BPCode")
            protected String bpCode;
            @XmlElement(name = "RowNum")
            protected Long rowNum;
            @XmlElement(name = "GlobalLocationNumber")
            protected String globalLocationNumber;
            @XmlElement(name = "Nationality")
            protected String nationality;
            @XmlElement(name = "U_Municipio")
            protected String uMunicipio;

            public String getAddressName() {
                return addressName;
            }

            public void setAddressName(String value) {
                this.addressName = value;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String value) {
                this.street = value;
            }

            public String getBlock() {
                return block;
            }

            public void setBlock(String value) {
                this.block = value;
            }

            public String getZipCode() {
                return zipCode;
            }

            public void setZipCode(String value) {
                this.zipCode = value;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String value) {
                this.city = value;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String value) {
                this.county = value;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String value) {
                this.country = value;
            }

            public String getState() {
                return state;
            }

            public void setState(String value) {
                this.state = value;
            }

            public String getFederalTaxID() {
                return federalTaxID;
            }

            public void setFederalTaxID(String value) {
                this.federalTaxID = value;
            }

            public String getTaxCode() {
                return taxCode;
            }

            public void setTaxCode(String value) {
                this.taxCode = value;
            }

            public String getBuildingFloorRoom() {
                return buildingFloorRoom;
            }

            public void setBuildingFloorRoom(String value) {
                this.buildingFloorRoom = value;
            }

            public String getAddressType() {
                return addressType;
            }

            public void setAddressType(String value) {
                this.addressType = value;
            }

            public String getAddressName2() {
                return addressName2;
            }

            public void setAddressName2(String value) {
                this.addressName2 = value;
            }

            public String getAddressName3() {
                return addressName3;
            }

            public void setAddressName3(String value) {
                this.addressName3 = value;
            }

            public String getTypeOfAddress() {
                return typeOfAddress;
            }

            public void setTypeOfAddress(String value) {
                this.typeOfAddress = value;
            }

            public String getStreetNo() {
                return streetNo;
            }

            public void setStreetNo(String value) {
                this.streetNo = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }

            public Long getRowNum() {
                return rowNum;
            }

            public void setRowNum(Long value) {
                this.rowNum = value;
            }

            public String getGlobalLocationNumber() {
                return globalLocationNumber;
            }

            public void setGlobalLocationNumber(String value) {
                this.globalLocationNumber = value;
            }

            public String getNationality() {
                return nationality;
            }

            public void setNationality(String value) {
                this.nationality = value;
            }

            public String getUMunicipio() {
                return uMunicipio;
            }

            public void setUMunicipio(String value) {
                this.uMunicipio = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpBranchAssignmentItem"
    })
    public static class BPBranchAssignment {

        @XmlElement(name = "BPBranchAssignmentItem")
        protected List<BusinessPartner.BPBranchAssignment.BPBranchAssignmentItem> bpBranchAssignmentItem;

        public List<BusinessPartner.BPBranchAssignment.BPBranchAssignmentItem> getBPBranchAssignmentItem() {
            if (bpBranchAssignmentItem == null) {
                bpBranchAssignmentItem = new ArrayList<>();
            }
            return this.bpBranchAssignmentItem;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPBranchAssignmentItem {

            @XmlElement(name = "BPCode")
            protected String bpCode;
            @XmlElement(name = "BPLID")
            protected Long bplid;

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }

            public Long getBPLID() {
                return bplid;
            }

            public void setBPLID(Long value) {
                this.bplid = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpFiscalTaxID"
    })
    public static class BPFiscalTaxIDCollection {

        @XmlElement(name = "BPFiscalTaxID")
        protected List<BusinessPartner.BPFiscalTaxIDCollection.BPFiscalTaxID> bpFiscalTaxID;

        public List<BusinessPartner.BPFiscalTaxIDCollection.BPFiscalTaxID> getBPFiscalTaxID() {
            if (bpFiscalTaxID == null) {
                bpFiscalTaxID = new ArrayList<>();
            }
            return this.bpFiscalTaxID;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPFiscalTaxID {

            @XmlElement(name = "Address")
            protected String address;
            @XmlElement(name = "CNAECode")
            protected Long cnaeCode;
            @XmlElement(name = "TaxId0")
            protected String taxId0;
            @XmlElement(name = "TaxId1")
            protected String taxId1;
            @XmlElement(name = "TaxId2")
            protected String taxId2;
            @XmlElement(name = "TaxId3")
            protected String taxId3;
            @XmlElement(name = "TaxId4")
            protected String taxId4;
            @XmlElement(name = "TaxId5")
            protected String taxId5;
            @XmlElement(name = "TaxId6")
            protected String taxId6;
            @XmlElement(name = "TaxId7")
            protected String taxId7;
            @XmlElement(name = "TaxId8")
            protected String taxId8;
            @XmlElement(name = "TaxId9")
            protected String taxId9;
            @XmlElement(name = "TaxId10")
            protected String taxId10;
            @XmlElement(name = "TaxId11")
            protected String taxId11;
            @XmlElement(name = "BPCode")
            protected String bpCode;
            @XmlElement(name = "AddrType")
            protected String addrType;
            @XmlElement(name = "TaxId12")
            protected String taxId12;
            @XmlElement(name = "TaxId13")
            protected String taxId13;

            public String getAddress() {
                return address;
            }

            public void setAddress(String value) {
                this.address = value;
            }

            public Long getCNAECode() {
                return cnaeCode;
            }

            public void setCNAECode(Long value) {
                this.cnaeCode = value;
            }

            public String getTaxId0() {
                return taxId0;
            }

            public void setTaxId0(String value) {
                this.taxId0 = value;
            }

            public String getTaxId1() {
                return taxId1;
            }

            public void setTaxId1(String value) {
                this.taxId1 = value;
            }

            public String getTaxId2() {
                return taxId2;
            }

            public void setTaxId2(String value) {
                this.taxId2 = value;
            }

            public String getTaxId3() {
                return taxId3;
            }

            public void setTaxId3(String value) {
                this.taxId3 = value;
            }

            public String getTaxId4() {
                return taxId4;
            }

            public void setTaxId4(String value) {
                this.taxId4 = value;
            }

            public String getTaxId5() {
                return taxId5;
            }

            public void setTaxId5(String value) {
                this.taxId5 = value;
            }

            public String getTaxId6() {
                return taxId6;
            }

            public void setTaxId6(String value) {
                this.taxId6 = value;
            }

            public String getTaxId7() {
                return taxId7;
            }

            public void setTaxId7(String value) {
                this.taxId7 = value;
            }

            public String getTaxId8() {
                return taxId8;
            }

            public void setTaxId8(String value) {
                this.taxId8 = value;
            }

            public String getTaxId9() {
                return taxId9;
            }

            public void setTaxId9(String value) {
                this.taxId9 = value;
            }

            public String getTaxId10() {
                return taxId10;
            }

            public void setTaxId10(String value) {
                this.taxId10 = value;
            }

            public String getTaxId11() {
                return taxId11;
            }

            public void setTaxId11(String value) {
                this.taxId11 = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }

            public String getAddrType() {
                return addrType;
            }

            public void setAddrType(String value) {
                this.addrType = value;
            }

            public String getTaxId12() {
                return taxId12;
            }

            public void setTaxId12(String value) {
                this.taxId12 = value;
            }

            public String getTaxId13() {
                return taxId13;
            }

            public void setTaxId13(String value) {
                this.taxId13 = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {})
    public static class BPIntrastatExtension {

        @XmlElement(name = "CardCode")
        protected String cardCode;
        @XmlElement(name = "TransportMode")
        protected Long transportMode;
        @XmlElement(name = "Incoterms")
        protected Long incoterms;
        @XmlElement(name = "NatureOfTransactions")
        protected Long natureOfTransactions;
        @XmlElement(name = "StatisticalProcedure")
        protected Long statisticalProcedure;
        @XmlElement(name = "CustomsProcedure")
        protected Long customsProcedure;
        @XmlElement(name = "PortOfEntryOrExit")
        protected Long portOfEntryOrExit;
        @XmlElement(name = "DomesticOrForeignID")
        protected String domesticOrForeignID;
        @XmlElement(name = "IntrastatRelevant")
        protected String intrastatRelevant;

        public String getCardCode() {
            return cardCode;
        }

        public void setCardCode(String value) {
            this.cardCode = value;
        }

        public Long getTransportMode() {
            return transportMode;
        }

        public void setTransportMode(Long value) {
            this.transportMode = value;
        }

        public Long getIncoterms() {
            return incoterms;
        }

        public void setIncoterms(Long value) {
            this.incoterms = value;
        }

        public Long getNatureOfTransactions() {
            return natureOfTransactions;
        }

        public void setNatureOfTransactions(Long value) {
            this.natureOfTransactions = value;
        }

        public Long getStatisticalProcedure() {
            return statisticalProcedure;
        }

        public void setStatisticalProcedure(Long value) {
            this.statisticalProcedure = value;
        }

        public Long getCustomsProcedure() {
            return customsProcedure;
        }

        public void setCustomsProcedure(Long value) {
            this.customsProcedure = value;
        }

        public Long getPortOfEntryOrExit() {
            return portOfEntryOrExit;
        }

        public void setPortOfEntryOrExit(Long value) {
            this.portOfEntryOrExit = value;
        }

        public String getDomesticOrForeignID() {
            return domesticOrForeignID;
        }

        public void setDomesticOrForeignID(String value) {
            this.domesticOrForeignID = value;
        }

        public String getIntrastatRelevant() {
            return intrastatRelevant;
        }

        public void setIntrastatRelevant(String value) {
            this.intrastatRelevant = value;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpPaymentDate"
    })
    public static class BPPaymentDates {

        @XmlElement(name = "BPPaymentDate")
        protected List<BusinessPartner.BPPaymentDates.BPPaymentDate> bpPaymentDate;

        public List<BusinessPartner.BPPaymentDates.BPPaymentDate> getBPPaymentDate() {
            if (bpPaymentDate == null) {
                bpPaymentDate = new ArrayList<>();
            }
            return this.bpPaymentDate;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPPaymentDate {

            @XmlElement(name = "PaymentDate")
            protected String paymentDate;
            @XmlElement(name = "BPCode")
            protected String bpCode;

            public String getPaymentDate() {
                return paymentDate;
            }

            public void setPaymentDate(String value) {
                this.paymentDate = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpPaymentMethod"
    })
    public static class BPPaymentMethods {

        @XmlElement(name = "BPPaymentMethod")
        protected List<BusinessPartner.BPPaymentMethods.BPPaymentMethod> bpPaymentMethod;

        public List<BusinessPartner.BPPaymentMethods.BPPaymentMethod> getBPPaymentMethod() {
            if (bpPaymentMethod == null) {
                bpPaymentMethod = new ArrayList<>();
            }
            return this.bpPaymentMethod;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPPaymentMethod {

            @XmlElement(name = "PaymentMethodCode")
            protected String paymentMethodCode;
            @XmlElement(name = "RowNumber")
            protected Long rowNumber;
            @XmlElement(name = "BPCode")
            protected String bpCode;

            public String getPaymentMethodCode() {
                return paymentMethodCode;
            }

            public void setPaymentMethodCode(String value) {
                this.paymentMethodCode = value;
            }

            public Long getRowNumber() {
                return rowNumber;
            }

            public void setRowNumber(Long value) {
                this.rowNumber = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bpWithholdingTax"
    })
    public static class BPWithholdingTaxCollection {

        @XmlElement(name = "BPWithholdingTax")
        protected List<BusinessPartner.BPWithholdingTaxCollection.BPWithholdingTax> bpWithholdingTax;

        public List<BusinessPartner.BPWithholdingTaxCollection.BPWithholdingTax> getBPWithholdingTax() {
            if (bpWithholdingTax == null) {
                bpWithholdingTax = new ArrayList<>();
            }
            return this.bpWithholdingTax;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class BPWithholdingTax {

            @XmlElement(name = "WTCode")
            protected String wtCode;
            @XmlElement(name = "BPCode")
            protected String bpCode;

            public String getWTCode() {
                return wtCode;
            }

            public void setWTCode(String value) {
                this.wtCode = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "contactEmployee"
    })
    public static class ContactEmployees {

        @XmlElement(name = "ContactEmployee")
        protected List<BusinessPartner.ContactEmployees.ContactEmployee> contactEmployee;

        public List<BusinessPartner.ContactEmployees.ContactEmployee> getContactEmployee() {
            if (contactEmployee == null) {
                contactEmployee = new ArrayList<>();
            }
            return this.contactEmployee;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ContactEmployee {

            @XmlElement(name = "CardCode")
            protected String cardCode;
            @XmlElement(name = "Name")
            protected String name;
            @XmlElement(name = "Position")
            protected String position;
            @XmlElement(name = "Address")
            protected String address;
            @XmlElement(name = "Phone1")
            protected String phone1;
            @XmlElement(name = "Phone2")
            protected String phone2;
            @XmlElement(name = "MobilePhone")
            protected String mobilePhone;
            @XmlElement(name = "Fax")
            protected String fax;
            @XmlElement(name = "E_Mail")
            protected String eMail;
            @XmlElement(name = "Pager")
            protected String pager;
            @XmlElement(name = "Remarks1")
            protected String remarks1;
            @XmlElement(name = "Remarks2")
            protected String remarks2;
            @XmlElement(name = "Password")
            protected String password;
            @XmlElement(name = "InternalCode")
            protected Long internalCode;
            @XmlElement(name = "PlaceOfBirth")
            protected String placeOfBirth;
            @XmlElement(name = "DateOfBirth")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar dateOfBirth;
            @XmlElement(name = "Gender")
            protected String gender;
            @XmlElement(name = "Profession")
            protected String profession;
            @XmlElement(name = "Title")
            protected String title;
            @XmlElement(name = "CityOfBirth")
            protected String cityOfBirth;
            @XmlElement(name = "Active")
            protected String active;
            @XmlElement(name = "FirstName")
            protected String firstName;
            @XmlElement(name = "MiddleName")
            protected String middleName;
            @XmlElement(name = "LastName")
            protected String lastName;
            @XmlElement(name = "EmailGroupCode")
            protected String emailGroupCode;

            public String getCardCode() {
                return cardCode;
            }

            public void setCardCode(String value) {
                this.cardCode = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String value) {
                this.position = value;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String value) {
                this.address = value;
            }

            public String getPhone1() {
                return phone1;
            }

            public void setPhone1(String value) {
                this.phone1 = value;
            }

            public String getPhone2() {
                return phone2;
            }

            public void setPhone2(String value) {
                this.phone2 = value;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String value) {
                this.mobilePhone = value;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String value) {
                this.fax = value;
            }

            public String getEMail() {
                return eMail;
            }

            public void setEMail(String value) {
                this.eMail = value;
            }

            public String getPager() {
                return pager;
            }

            public void setPager(String value) {
                this.pager = value;
            }

            public String getRemarks1() {
                return remarks1;
            }

            public void setRemarks1(String value) {
                this.remarks1 = value;
            }

            public String getRemarks2() {
                return remarks2;
            }

            public void setRemarks2(String value) {
                this.remarks2 = value;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String value) {
                this.password = value;
            }

            public Long getInternalCode() {
                return internalCode;
            }

            public void setInternalCode(Long value) {
                this.internalCode = value;
            }

            public String getPlaceOfBirth() {
                return placeOfBirth;
            }

            public void setPlaceOfBirth(String value) {
                this.placeOfBirth = value;
            }

            public XMLGregorianCalendar getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(XMLGregorianCalendar value) {
                this.dateOfBirth = value;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String value) {
                this.gender = value;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String value) {
                this.profession = value;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String value) {
                this.title = value;
            }

            public String getCityOfBirth() {
                return cityOfBirth;
            }

            public void setCityOfBirth(String value) {
                this.cityOfBirth = value;
            }

            public String getActive() {
                return active;
            }

            public void setActive(String value) {
                this.active = value;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String value) {
                this.firstName = value;
            }

            public String getMiddleName() {
                return middleName;
            }

            public void setMiddleName(String value) {
                this.middleName = value;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String value) {
                this.lastName = value;
            }

            public String getEmailGroupCode() {
                return emailGroupCode;
            }

            public void setEmailGroupCode(String value) {
                this.emailGroupCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "discountGroup"
    })
    public static class DiscountGroups {

        @XmlElement(name = "DiscountGroup")
        protected List<BusinessPartner.DiscountGroups.DiscountGroup> discountGroup;

        public List<BusinessPartner.DiscountGroups.DiscountGroup> getDiscountGroup() {
            if (discountGroup == null) {
                discountGroup = new ArrayList<>();
            }
            return this.discountGroup;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class DiscountGroup {

            @XmlElement(name = "ObjectEntry")
            protected String objectEntry;
            @XmlElement(name = "DiscountPercentage")
            protected Double discountPercentage;
            @XmlElement(name = "BPCode")
            protected String bpCode;
            @XmlElement(name = "BaseObjectType")
            protected String baseObjectType;

            public String getObjectEntry() {
                return objectEntry;
            }

            public void setObjectEntry(String value) {
                this.objectEntry = value;
            }

            public Double getDiscountPercentage() {
                return discountPercentage;
            }

            public void setDiscountPercentage(Double value) {
                this.discountPercentage = value;
            }

            public String getBPCode() {
                return bpCode;
            }

            public void setBPCode(String value) {
                this.bpCode = value;
            }

            public String getBaseObjectType() {
                return baseObjectType;
            }

            public void setBaseObjectType(String value) {
                this.baseObjectType = value;
            }
        }
    }
}
