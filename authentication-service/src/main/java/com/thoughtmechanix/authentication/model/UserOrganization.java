package com.thoughtmechanix.authentication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_orgs")
public class UserOrganization implements Serializable {
    @Column(name = "organization_id", nullable = false)
    String organizationId;

    @Id
    @Column(name = "user_name", nullable = false)
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }


}
