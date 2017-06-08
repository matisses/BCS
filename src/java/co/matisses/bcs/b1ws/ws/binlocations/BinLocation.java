
package co.matisses.bcs.b1ws.ws.binlocations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="AbsEntry" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Warehouse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sublevel1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sublevel2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sublevel3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sublevel4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BinCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Inactive" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="tNO"/>
 *               &lt;enumeration value="tYES"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlternativeSortCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BarCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Attribute10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestrictedItemType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="briNone"/>
 *               &lt;enumeration value="briSpecificItem"/>
 *               &lt;enumeration value="briSingleItemOnly"/>
 *               &lt;enumeration value="briSpecificItemGroup"/>
 *               &lt;enumeration value="briSpecificItemGroupOnly"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SpecificItem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecificItemGroup" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="BatchRestrictions" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="brbNoRestrictions"/>
 *               &lt;enumeration value="brbSingleBatch"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RestrictedTransType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="brtNoRestrictions"/>
 *               &lt;enumeration value="brtAllTrans"/>
 *               &lt;enumeration value="brtInboundTrans"/>
 *               &lt;enumeration value="brtOutboundTrans"/>
 *               &lt;enumeration value="brtAllExceptInventoryTrans"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RestrictionReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateRestrictionChanged" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="MinimumQty" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaximumQty" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="IsSystemBin" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="tNO"/>
 *               &lt;enumeration value="tYES"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ReceivingBinLocation" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="tNO"/>
 *               &lt;enumeration value="tYES"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ExcludeAutoAllocOnIssue" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="tNO"/>
 *               &lt;enumeration value="tYES"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MaximumWeight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaximumWeight1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaximumWeightUnit" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="MaximumWeightUnit1" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "BinLocation")
public class BinLocation {

    @XmlElement(name = "AbsEntry")
    protected Long absEntry;
    @XmlElement(name = "Warehouse")
    protected String warehouse;
    @XmlElement(name = "Sublevel1")
    protected String sublevel1;
    @XmlElement(name = "Sublevel2")
    protected String sublevel2;
    @XmlElement(name = "Sublevel3")
    protected String sublevel3;
    @XmlElement(name = "Sublevel4")
    protected String sublevel4;
    @XmlElement(name = "BinCode")
    protected String binCode;
    @XmlElement(name = "Inactive")
    protected String inactive;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "AlternativeSortCode")
    protected String alternativeSortCode;
    @XmlElement(name = "BarCode")
    protected String barCode;
    @XmlElement(name = "Attribute1")
    protected String attribute1;
    @XmlElement(name = "Attribute2")
    protected String attribute2;
    @XmlElement(name = "Attribute3")
    protected String attribute3;
    @XmlElement(name = "Attribute4")
    protected String attribute4;
    @XmlElement(name = "Attribute5")
    protected String attribute5;
    @XmlElement(name = "Attribute6")
    protected String attribute6;
    @XmlElement(name = "Attribute7")
    protected String attribute7;
    @XmlElement(name = "Attribute8")
    protected String attribute8;
    @XmlElement(name = "Attribute9")
    protected String attribute9;
    @XmlElement(name = "Attribute10")
    protected String attribute10;
    @XmlElement(name = "RestrictedItemType")
    protected String restrictedItemType;
    @XmlElement(name = "SpecificItem")
    protected String specificItem;
    @XmlElement(name = "SpecificItemGroup")
    protected Long specificItemGroup;
    @XmlElement(name = "BatchRestrictions")
    protected String batchRestrictions;
    @XmlElement(name = "RestrictedTransType")
    protected String restrictedTransType;
    @XmlElement(name = "RestrictionReason")
    protected String restrictionReason;
    @XmlElement(name = "DateRestrictionChanged")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateRestrictionChanged;
    @XmlElement(name = "MinimumQty")
    protected Double minimumQty;
    @XmlElement(name = "MaximumQty")
    protected Double maximumQty;
    @XmlElement(name = "IsSystemBin")
    protected String isSystemBin;
    @XmlElement(name = "ReceivingBinLocation")
    protected String receivingBinLocation;
    @XmlElement(name = "ExcludeAutoAllocOnIssue")
    protected String excludeAutoAllocOnIssue;
    @XmlElement(name = "MaximumWeight")
    protected Double maximumWeight;
    @XmlElement(name = "MaximumWeight1")
    protected Double maximumWeight1;
    @XmlElement(name = "MaximumWeightUnit")
    protected Long maximumWeightUnit;
    @XmlElement(name = "MaximumWeightUnit1")
    protected Long maximumWeightUnit1;

    /**
     * Obtiene el valor de la propiedad absEntry.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAbsEntry() {
        return absEntry;
    }

    /**
     * Define el valor de la propiedad absEntry.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAbsEntry(Long value) {
        this.absEntry = value;
    }

    /**
     * Obtiene el valor de la propiedad warehouse.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * Define el valor de la propiedad warehouse.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarehouse(String value) {
        this.warehouse = value;
    }

    /**
     * Obtiene el valor de la propiedad sublevel1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSublevel1() {
        return sublevel1;
    }

    /**
     * Define el valor de la propiedad sublevel1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSublevel1(String value) {
        this.sublevel1 = value;
    }

    /**
     * Obtiene el valor de la propiedad sublevel2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSublevel2() {
        return sublevel2;
    }

    /**
     * Define el valor de la propiedad sublevel2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSublevel2(String value) {
        this.sublevel2 = value;
    }

    /**
     * Obtiene el valor de la propiedad sublevel3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSublevel3() {
        return sublevel3;
    }

    /**
     * Define el valor de la propiedad sublevel3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSublevel3(String value) {
        this.sublevel3 = value;
    }

    /**
     * Obtiene el valor de la propiedad sublevel4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSublevel4() {
        return sublevel4;
    }

    /**
     * Define el valor de la propiedad sublevel4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSublevel4(String value) {
        this.sublevel4 = value;
    }

    /**
     * Obtiene el valor de la propiedad binCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinCode() {
        return binCode;
    }

    /**
     * Define el valor de la propiedad binCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinCode(String value) {
        this.binCode = value;
    }

    /**
     * Obtiene el valor de la propiedad inactive.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInactive() {
        return inactive;
    }

    /**
     * Define el valor de la propiedad inactive.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInactive(String value) {
        this.inactive = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad alternativeSortCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternativeSortCode() {
        return alternativeSortCode;
    }

    /**
     * Define el valor de la propiedad alternativeSortCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternativeSortCode(String value) {
        this.alternativeSortCode = value;
    }

    /**
     * Obtiene el valor de la propiedad barCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * Define el valor de la propiedad barCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarCode(String value) {
        this.barCode = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute1() {
        return attribute1;
    }

    /**
     * Define el valor de la propiedad attribute1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute1(String value) {
        this.attribute1 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute2() {
        return attribute2;
    }

    /**
     * Define el valor de la propiedad attribute2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute2(String value) {
        this.attribute2 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute3() {
        return attribute3;
    }

    /**
     * Define el valor de la propiedad attribute3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute3(String value) {
        this.attribute3 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute4() {
        return attribute4;
    }

    /**
     * Define el valor de la propiedad attribute4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute4(String value) {
        this.attribute4 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute5() {
        return attribute5;
    }

    /**
     * Define el valor de la propiedad attribute5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute5(String value) {
        this.attribute5 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute6.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute6() {
        return attribute6;
    }

    /**
     * Define el valor de la propiedad attribute6.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute6(String value) {
        this.attribute6 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute7.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute7() {
        return attribute7;
    }

    /**
     * Define el valor de la propiedad attribute7.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute7(String value) {
        this.attribute7 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute8.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute8() {
        return attribute8;
    }

    /**
     * Define el valor de la propiedad attribute8.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute8(String value) {
        this.attribute8 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute9.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute9() {
        return attribute9;
    }

    /**
     * Define el valor de la propiedad attribute9.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute9(String value) {
        this.attribute9 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute10.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute10() {
        return attribute10;
    }

    /**
     * Define el valor de la propiedad attribute10.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute10(String value) {
        this.attribute10 = value;
    }

    /**
     * Obtiene el valor de la propiedad restrictedItemType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestrictedItemType() {
        return restrictedItemType;
    }

    /**
     * Define el valor de la propiedad restrictedItemType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestrictedItemType(String value) {
        this.restrictedItemType = value;
    }

    /**
     * Obtiene el valor de la propiedad specificItem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecificItem() {
        return specificItem;
    }

    /**
     * Define el valor de la propiedad specificItem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecificItem(String value) {
        this.specificItem = value;
    }

    /**
     * Obtiene el valor de la propiedad specificItemGroup.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSpecificItemGroup() {
        return specificItemGroup;
    }

    /**
     * Define el valor de la propiedad specificItemGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSpecificItemGroup(Long value) {
        this.specificItemGroup = value;
    }

    /**
     * Obtiene el valor de la propiedad batchRestrictions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchRestrictions() {
        return batchRestrictions;
    }

    /**
     * Define el valor de la propiedad batchRestrictions.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchRestrictions(String value) {
        this.batchRestrictions = value;
    }

    /**
     * Obtiene el valor de la propiedad restrictedTransType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestrictedTransType() {
        return restrictedTransType;
    }

    /**
     * Define el valor de la propiedad restrictedTransType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestrictedTransType(String value) {
        this.restrictedTransType = value;
    }

    /**
     * Obtiene el valor de la propiedad restrictionReason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestrictionReason() {
        return restrictionReason;
    }

    /**
     * Define el valor de la propiedad restrictionReason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestrictionReason(String value) {
        this.restrictionReason = value;
    }

    /**
     * Obtiene el valor de la propiedad dateRestrictionChanged.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateRestrictionChanged() {
        return dateRestrictionChanged;
    }

    /**
     * Define el valor de la propiedad dateRestrictionChanged.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateRestrictionChanged(XMLGregorianCalendar value) {
        this.dateRestrictionChanged = value;
    }

    /**
     * Obtiene el valor de la propiedad minimumQty.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinimumQty() {
        return minimumQty;
    }

    /**
     * Define el valor de la propiedad minimumQty.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinimumQty(Double value) {
        this.minimumQty = value;
    }

    /**
     * Obtiene el valor de la propiedad maximumQty.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumQty() {
        return maximumQty;
    }

    /**
     * Define el valor de la propiedad maximumQty.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumQty(Double value) {
        this.maximumQty = value;
    }

    /**
     * Obtiene el valor de la propiedad isSystemBin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSystemBin() {
        return isSystemBin;
    }

    /**
     * Define el valor de la propiedad isSystemBin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSystemBin(String value) {
        this.isSystemBin = value;
    }

    /**
     * Obtiene el valor de la propiedad receivingBinLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingBinLocation() {
        return receivingBinLocation;
    }

    /**
     * Define el valor de la propiedad receivingBinLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingBinLocation(String value) {
        this.receivingBinLocation = value;
    }

    /**
     * Obtiene el valor de la propiedad excludeAutoAllocOnIssue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExcludeAutoAllocOnIssue() {
        return excludeAutoAllocOnIssue;
    }

    /**
     * Define el valor de la propiedad excludeAutoAllocOnIssue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExcludeAutoAllocOnIssue(String value) {
        this.excludeAutoAllocOnIssue = value;
    }

    /**
     * Obtiene el valor de la propiedad maximumWeight.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumWeight() {
        return maximumWeight;
    }

    /**
     * Define el valor de la propiedad maximumWeight.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumWeight(Double value) {
        this.maximumWeight = value;
    }

    /**
     * Obtiene el valor de la propiedad maximumWeight1.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumWeight1() {
        return maximumWeight1;
    }

    /**
     * Define el valor de la propiedad maximumWeight1.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumWeight1(Double value) {
        this.maximumWeight1 = value;
    }

    /**
     * Obtiene el valor de la propiedad maximumWeightUnit.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaximumWeightUnit() {
        return maximumWeightUnit;
    }

    /**
     * Define el valor de la propiedad maximumWeightUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaximumWeightUnit(Long value) {
        this.maximumWeightUnit = value;
    }

    /**
     * Obtiene el valor de la propiedad maximumWeightUnit1.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaximumWeightUnit1() {
        return maximumWeightUnit1;
    }

    /**
     * Define el valor de la propiedad maximumWeightUnit1.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaximumWeightUnit1(Long value) {
        this.maximumWeightUnit1 = value;
    }

}
