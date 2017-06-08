package co.matisses.bcs.b1ws.ws.login;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "databaseServer",
    "databaseName",
    "databaseType",
    "companyUsername",
    "companyPassword",
    "language",
    "licenseServer"
})
@XmlRootElement(name = "Login", namespace = "LoginService")
public class Login {

    @XmlElement(name = "DatabaseServer", namespace = "LoginService")
    protected String databaseServer;
    @XmlElement(name = "DatabaseName", namespace = "LoginService")
    protected String databaseName;
    @XmlElement(name = "DatabaseType", namespace = "LoginService")
    protected String databaseType;
    @XmlElement(name = "CompanyUsername", namespace = "LoginService")
    protected String companyUsername;
    @XmlElement(name = "CompanyPassword", namespace = "LoginService")
    protected String companyPassword;
    @XmlElement(name = "Language", namespace = "LoginService")
    protected String language;
    @XmlElement(name = "LicenseServer", namespace = "LoginService")
    protected String licenseServer;

    public String getDatabaseServer() {
        return databaseServer;
    }

    public void setDatabaseServer(String value) {
        this.databaseServer = value;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String value) {
        this.databaseName = value;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String value) {
        this.databaseType = value;
    }

    public String getCompanyUsername() {
        return companyUsername;
    }

    public void setCompanyUsername(String value) {
        this.companyUsername = value;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String value) {
        this.companyPassword = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }

    public String getLicenseServer() {
        return licenseServer;
    }

    public void setLicenseServer(String value) {
        this.licenseServer = value;
    }

}
