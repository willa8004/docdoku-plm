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

package com.docdoku.core.services;

import com.docdoku.core.document.DocumentIterationKey;
import com.docdoku.core.meta.InstanceAttribute;
import com.docdoku.core.meta.InstanceAttributeTemplate;
import com.docdoku.core.product.*;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.util.List;


/**
 *
 * @author Florent Garin
 */
public interface IProductManagerLocal{
    
    List<PartUsageLink[]> findPartUsages(ConfigurationItemKey pKey, PartMasterKey pPartMKey) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException;
    PartUsageLink filterProductStructure(ConfigurationItemKey pKey, ConfigSpec configSpec, Integer partUsageLink, Integer depth) throws ConfigurationItemNotFoundException, WorkspaceNotFoundException, NotAllowedException, UserNotFoundException, UserNotActiveException, PartUsageLinkNotFoundException;
    ConfigurationItem createConfigurationItem(String pWorkspaceId, String pId, String pDescription, String pDesignItemNumber) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, ConfigurationItemAlreadyExistsException, CreationException, PartMasterNotFoundException;
    PartMaster createPartMaster(String pWorkspaceId, String pNumber, String pName, String pPartMasterDescription, boolean pStandardPart, String pWorkflowModelId, String pPartRevisionDescription, String templateId) throws NotAllowedException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException, WorkflowModelNotFoundException, PartMasterAlreadyExistsException, CreationException, PartMasterTemplateNotFoundException, FileAlreadyExistsException;
    PartRevision checkOutPart(PartRevisionKey pPartRPK) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, PartRevisionNotFoundException, NotAllowedException, FileAlreadyExistsException, CreationException;
    PartRevision undoCheckOutPart(PartRevisionKey pPartRPK) throws NotAllowedException, PartRevisionNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    PartRevision checkInPart(PartRevisionKey pPartRPK) throws PartRevisionNotFoundException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException;
    java.io.File saveNativeCADInPartIteration(PartIterationKey pPartIPK, String pName, long pSize) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;
    java.io.File saveGeometryInPartIteration(PartIterationKey pPartIPK, String pName, int quality, long pSize) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;
    java.io.File saveFileInPartIteration(PartIterationKey pPartIPK, String pName, long pSize) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;
    PartRevision updatePartIteration(PartIterationKey pKey, java.lang.String pIterationNote, PartIteration.Source source, java.util.List<PartUsageLink> pUsageLinks, java.util.List<InstanceAttribute> pAttributes, DocumentIterationKey[] pLinkKeys) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, PartRevisionNotFoundException, PartMasterNotFoundException;
    File getDataFile(String pFullName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, FileNotFoundException, NotAllowedException;
    List<ConfigurationItem> getConfigurationItems(String pWorkspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    List<Layer> getLayers(ConfigurationItemKey pKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    Layer getLayer(int pId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, LayerNotFoundException;
    Layer createLayer(ConfigurationItemKey pKey, String pName) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, ConfigurationItemNotFoundException;
    Layer updateLayer(ConfigurationItemKey pKey, int pId, String pName) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, ConfigurationItemNotFoundException, LayerNotFoundException, UserNotActiveException;
    Marker createMarker(int pLayerId, String pTitle, String pDescription, double pX, double pY, double pZ) throws LayerNotFoundException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException;
    void deleteMarker(int pLayerId, int pMarkerId) throws WorkspaceNotFoundException, UserNotActiveException, LayerNotFoundException, UserNotFoundException, AccessRightException, MarkerNotFoundException;
    List<PartMaster> findPartMasters(String pWorkspaceId, String pPartNumber, int pMaxResults) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException;
    PartRevision getPartRevision(PartRevisionKey pPartRPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException;
    PartMaster getPartMaster(PartMasterKey pPartMPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartMasterNotFoundException;
    List<PartUsageLink> getComponents(PartIterationKey pPartIPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartIterationNotFoundException, NotAllowedException;
    boolean partMasterExists(PartMasterKey partMasterKey)throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    void deleteConfigurationItem(ConfigurationItemKey configurationItemKey) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, UserNotActiveException, ConfigurationItemNotFoundException, LayerNotFoundException;
    void deleteLayer(String workspaceId, int layerId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, LayerNotFoundException;
    void removeCADFileFromPartIteration(PartIterationKey partIKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartIterationNotFoundException;

    PartMasterTemplate createPartMasterTemplate(String pWorkspaceId, String pId, String pPartType, String pMask, InstanceAttributeTemplate[] pAttributeTemplates, boolean idGenerated) throws WorkspaceNotFoundException, AccessRightException, PartMasterTemplateAlreadyExistsException, UserNotFoundException, NotAllowedException, CreationException;
    File saveFileInTemplate(PartMasterTemplateKey pPartMTemplateKey, String pName, long pSize) throws WorkspaceNotFoundException, NotAllowedException, PartMasterTemplateNotFoundException, FileAlreadyExistsException, UserNotFoundException, UserNotActiveException, CreationException ;
    String generateId(String pWorkspaceId, String pPartMTemplateId) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException, PartMasterTemplateNotFoundException;
    PartMasterTemplate[] getPartMasterTemplates(String pWorkspaceId) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException;
    PartMasterTemplate getPartMasterTemplate(PartMasterTemplateKey pKey) throws WorkspaceNotFoundException, PartMasterTemplateNotFoundException, UserNotFoundException, UserNotActiveException;
    PartMasterTemplate updatePartMasterTemplate(PartMasterTemplateKey pKey, String pPartType, String pMask, InstanceAttributeTemplate[] pAttributeTemplates, boolean idGenerated) throws WorkspaceNotFoundException, WorkspaceNotFoundException, AccessRightException, PartMasterTemplateNotFoundException, UserNotFoundException;
    void deletePartMasterTemplate(PartMasterTemplateKey pKey) throws WorkspaceNotFoundException, AccessRightException, PartMasterTemplateNotFoundException, UserNotFoundException;
    PartMasterTemplate removeFileFromTemplate(String pFullName) throws WorkspaceNotFoundException, PartMasterTemplateNotFoundException, AccessRightException, FileNotFoundException, UserNotFoundException, UserNotActiveException;

    List<PartMaster> getPartMasters(String pWorkspaceId, int start, int pMaxResults) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, UserNotActiveException;

    void deletePartMaster(PartMasterKey partMasterKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartMasterNotFoundException, EntityConstraintException;
    int getPartMastersCount(String pWorkspaceId) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, UserNotActiveException;

    Long getDiskUsageForPartsInWorkspace(String pWorkspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    Long getDiskUsageForPartTemplatesInWorkspace(String pWorkspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
}
