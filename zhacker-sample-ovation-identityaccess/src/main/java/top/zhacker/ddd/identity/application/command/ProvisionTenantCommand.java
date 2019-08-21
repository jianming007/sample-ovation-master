//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package top.zhacker.ddd.identity.application.command;

/**
 * 提供租户命令
 */
public class ProvisionTenantCommand {

    private String tenantName;
    private String tenantDescription;
    /** 管理员名称*/
    private String administorFirstName;
    /** 管理员姓*/
    private String administorLastName;
    private String emailAddress;
    /** 主要电话*/
    private String primaryTelephone;
    /** 二手电话*/
    private String secondaryTelephone;
    /** 地址街道地址*/
    private String addressStreetAddress;
    /** 地址城市*/
    private String addressCity;
    /** 地址州*/
    private String addressStateProvince;
    /** 地址邮政编码*/
    private String addressPostalCode;
    /** 地址国家代码*/
    private String addressCountryCode;

    public ProvisionTenantCommand(String tenantName, String tenantDescription, String administorFirstName,
            String administorLastName, String emailAddress, String primaryTelephone, String secondaryTelephone,
            String addressStreetAddress, String addressCity, String addressStateProvince, String addressPostalCode,
            String addressCountryCode) {

        super();

        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;
        this.administorFirstName = administorFirstName;
        this.administorLastName = administorLastName;
        this.emailAddress = emailAddress;
        this.primaryTelephone = primaryTelephone;
        this.secondaryTelephone = secondaryTelephone;
        this.addressStreetAddress = addressStreetAddress;
        this.addressCity = addressCity;
        this.addressStateProvince = addressStateProvince;
        this.addressPostalCode = addressPostalCode;
        this.addressCountryCode = addressCountryCode;
    }

    public ProvisionTenantCommand() {
        super();
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return this.tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public String getAdministorFirstName() {
        return this.administorFirstName;
    }

    public void setAdministorFirstName(String administorFirstName) {
        this.administorFirstName = administorFirstName;
    }

    public String getAdministorLastName() {
        return this.administorLastName;
    }

    public void setAdministorLastName(String administorLastName) {
        this.administorLastName = administorLastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPrimaryTelephone() {
        return this.primaryTelephone;
    }

    public void setPrimaryTelephone(String primaryTelephone) {
        this.primaryTelephone = primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return this.secondaryTelephone;
    }

    public void setSecondaryTelephone(String secondaryTelephone) {
        this.secondaryTelephone = secondaryTelephone;
    }

    public String getAddressStreetAddress() {
        return this.addressStreetAddress;
    }

    public void setAddressStreetAddress(String addressStreetAddress) {
        this.addressStreetAddress = addressStreetAddress;
    }

    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStateProvince() {
        return this.addressStateProvince;
    }

    public void setAddressStateProvince(String addressStateProvince) {
        this.addressStateProvince = addressStateProvince;
    }

    public String getAddressPostalCode() {
        return this.addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountryCode() {
        return this.addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
    }
}
