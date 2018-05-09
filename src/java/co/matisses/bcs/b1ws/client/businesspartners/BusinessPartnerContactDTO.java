package co.matisses.bcs.b1ws.client.businesspartners;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessPartnerContactDTO {

    private String name;
    private String firstName;
    private String middleName;
    private String lastName1;
    private String lastName2;
    private String address;
    private String Tel1;
    private String cellolar;
    private String eMailL;

    public BusinessPartnerContactDTO() {
    }

    public BusinessPartnerContactDTO(String name, String firstName, String middleName, String lastName1, String lastName2, String address, String Tel1, String cellolar, String eMailL) {
        this.name = name;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.address = address;
        this.Tel1 = Tel1;
        this.cellolar = cellolar;
        this.eMailL = eMailL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel1() {
        return Tel1;
    }

    public void setTel1(String Tel1) {
        this.Tel1 = Tel1;
    }

    public String getCellolar() {
        return cellolar;
    }

    public void setCellolar(String cellolar) {
        this.cellolar = cellolar;
    }

    public String geteMailL() {
        return eMailL;
    }

    public void seteMailL(String eMailL) {
        this.eMailL = eMailL;
    }

    @Override
    public String toString() {
        return "BusinessPartnerContactDTO{" + "name=" + name + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName1=" + lastName1 + ", lastName2=" + lastName2 + ", address=" + address + ", Tel1=" + Tel1 + ", cellolar=" + cellolar + ", eMailL=" + eMailL + '}';
    }
}