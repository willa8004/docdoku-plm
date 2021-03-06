/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU Affero General Public License for more details.  
 *  
 * You should have received a copy of the GNU Affero General Public License  
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.  
 */

package com.docdoku.core.common;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Table;

/**
 * The context in which documents, workflow models, document templates and all
 * the other objects reside.  
 * 
 * @author Florent Garin
 * @version 1.0, 02/06/08
 * @since   V1.0
 */
@Table(name="WORKSPACE")
@javax.persistence.Entity
public class Workspace implements Serializable, Cloneable {

    @Column(length=50)
    @javax.persistence.Id
    private String id="";
    
    @javax.persistence.ManyToOne(optional=false, fetch=FetchType.EAGER)
    private Account admin;
    
    private String description;    
    
    private boolean folderLocked;
    
    private VaultType vaultType;
    
    public enum VaultType {DEMO,SMALL,LARGE,UNLIMITED}


    public Workspace(String pId, Account pAdmin, String pDescription, VaultType pVaultType, boolean pFolderLocked) {
        id = pId;
        admin = pAdmin;
        description = pDescription;
        vaultType = pVaultType;
        folderLocked=pFolderLocked;
    }
    
    public Workspace(String pId) {
        id = pId;
    }

    public Workspace() {
    }
    
    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public void setAdmin(Account pAdmin) {
        admin = pAdmin;
    }

    public Account getAdmin() {
        return admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String pId){
        id=pId;
    }
    
    public String getDescription() {
        return description;
    }

    public VaultType getVaultType() {
        return vaultType;
    }

    public void setVaultType(VaultType pVaultType) {
        vaultType = pVaultType;
    }

    public boolean isFolderLocked() {
        return folderLocked;
    }

    public void setFolderLocked(boolean folderLocked) {
        this.folderLocked = folderLocked;
    }

    
    @Override
    public String toString() {
        return id;
    }
    
    @Override
    public boolean equals(Object pObj) {
        if (this == pObj) {
            return true;
        }
        if (!(pObj instanceof Workspace))
            return false;
        Workspace workspace = (Workspace) pObj;
        return workspace.id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    

    @Override
    public Workspace clone() {
        Workspace clone = null;
        try {
            clone = (Workspace) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return clone;
    }
}
