/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2015 DocDoku SARL
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

import com.docdoku.core.change.ModificationNotification;
import com.docdoku.core.common.BinaryResource;
import com.docdoku.core.common.User;
import com.docdoku.core.configuration.*;
import com.docdoku.core.document.DocumentIterationLink;
import com.docdoku.core.document.DocumentRevisionKey;
import com.docdoku.core.exceptions.*;
import com.docdoku.core.meta.InstanceAttribute;
import com.docdoku.core.meta.InstanceAttributeDescriptor;
import com.docdoku.core.meta.InstanceAttributeTemplate;
import com.docdoku.core.product.*;
import com.docdoku.core.query.PartSearchQuery;
import com.docdoku.core.query.Query;
import com.docdoku.core.query.QueryResultRow;
import com.docdoku.core.security.ACLUserEntry;
import com.docdoku.core.security.ACLUserGroupEntry;
import com.docdoku.core.sharing.SharedEntityKey;
import com.docdoku.core.sharing.SharedPart;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Florent Garin
 */
public interface IProductManagerLocal {

    public List<PartLink[]> findPartUsages(ConfigurationItemKey pKey, PSFilter filter, String search) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException, ConfigurationItemNotFoundException;

    ConfigurationItem createConfigurationItem(String pWorkspaceId, String pId, String pDescription, String pDesignItemNumber) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, ConfigurationItemAlreadyExistsException, CreationException, PartMasterNotFoundException;

    PartMaster createPartMaster(String pWorkspaceId, String pNumber, String pName, boolean pStandardPart, String pWorkflowModelId, String pPartRevisionDescription, String templateId, Map<String, String> roleMappings, ACLUserEntry[] userEntries, ACLUserGroupEntry[] userGroupEntries) throws NotAllowedException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException, WorkflowModelNotFoundException, PartMasterAlreadyExistsException, CreationException, PartMasterTemplateNotFoundException, FileAlreadyExistsException, RoleNotFoundException;

    PartRevision checkOutPart(PartRevisionKey pPartRPK) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, PartRevisionNotFoundException, NotAllowedException, FileAlreadyExistsException, CreationException;

    PartRevision undoCheckOutPart(PartRevisionKey pPartRPK) throws NotAllowedException, PartRevisionNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, AccessRightException;

    PartRevision checkInPart(PartRevisionKey pPartRPK) throws PartRevisionNotFoundException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, ESServerException, UserNotActiveException, EntityConstraintException, PartMasterNotFoundException;

    BinaryResource saveNativeCADInPartIteration(PartIterationKey pPartIPK, String pName, long pSize) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;

    BinaryResource saveGeometryInPartIteration(PartIterationKey pPartIPK, String pName, int quality, long pSize, double[] box) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;

    PartRevision updatePartIteration(PartIterationKey pKey, java.lang.String pIterationNote, PartIteration.Source source, java.util.List<PartUsageLink> pUsageLinks, java.util.List<InstanceAttribute> pAttributes, java.util.List<InstanceAttributeTemplate> pAttributeTemplates, DocumentRevisionKey[] pLinkKeys, String[] documentLinkComments, String[] lovNames) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, PartRevisionNotFoundException, PartMasterNotFoundException, EntityConstraintException, UserNotActiveException, ListOfValuesNotFoundException, PartUsageLinkNotFoundException;

    BinaryResource saveFileInPartIteration(PartIterationKey pPartIPK, String pName, String subType, long pSize) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, PartRevisionNotFoundException, FileAlreadyExistsException, CreationException;

    void removeFileInPartIteration(PartIterationKey pPartIPK, String pSubType, String pName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartIterationNotFoundException, FileNotFoundException;

    /**
     * Returns the BinaryResource object given his Id. WARNING: You have to check access right before use it.
     *
     * @param fullName Id of the <a href="BinaryResource.html">BinaryResource</a> of which the
     *                 data file will be returned
     * @return The binary resource, a BinaryResource instance, that now needs to be created
     * @throws UserNotFoundException
     * @throws UserNotActiveException
     * @throws WorkspaceNotFoundException
     * @throws FileNotFoundException
     * @throws NotAllowedException
     */
    BinaryResource getBinaryResource(String fullName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, FileNotFoundException, NotAllowedException, AccessRightException;

    BinaryResource getTemplateBinaryResource(String pFullName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, FileNotFoundException;

    List<ConfigurationItem> getConfigurationItems(String pWorkspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    ConfigurationItem getConfigurationItem(ConfigurationItemKey ciKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException;

    List<Layer> getLayers(ConfigurationItemKey pKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    Layer getLayer(int pId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, LayerNotFoundException;

    Layer createLayer(ConfigurationItemKey pKey, String pName, String color) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, ConfigurationItemNotFoundException;

    Layer updateLayer(ConfigurationItemKey pKey, int pId, String pName, String color) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, ConfigurationItemNotFoundException, LayerNotFoundException, UserNotActiveException;

    Marker createMarker(int pLayerId, String pTitle, String pDescription, double pX, double pY, double pZ) throws LayerNotFoundException, UserNotFoundException, WorkspaceNotFoundException, AccessRightException;

    void deleteMarker(int pLayerId, int pMarkerId) throws WorkspaceNotFoundException, UserNotActiveException, LayerNotFoundException, UserNotFoundException, AccessRightException, MarkerNotFoundException;

    List<PartMaster> findPartMasters(String pWorkspaceId, String pPartNumber, String pPartName, int pMaxResults) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException;

    PartRevision getPartRevision(PartRevisionKey pPartRPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, AccessRightException;

    PartIteration getPartIteration(PartIterationKey pPartIPK) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, PartIterationNotFoundException;

    PartMaster getPartMaster(PartMasterKey pPartMPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartMasterNotFoundException;

    List<PartUsageLink> getComponents(PartIterationKey pPartIPK) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartIterationNotFoundException, NotAllowedException;

    boolean partMasterExists(PartMasterKey partMasterKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    void deleteConfigurationItem(ConfigurationItemKey configurationItemKey) throws UserNotFoundException, WorkspaceNotFoundException, AccessRightException, NotAllowedException, UserNotActiveException, ConfigurationItemNotFoundException, LayerNotFoundException, EntityConstraintException;

    void deleteLayer(String workspaceId, int layerId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, LayerNotFoundException, AccessRightException;

    BinaryResource renameFileInPartIteration(String pSubType, String fullName, String newName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, FileNotFoundException, FileAlreadyExistsException, CreationException, StorageException;

    PartMasterTemplate createPartMasterTemplate(String pWorkspaceId, String pId, String pPartType, String pWorkflowModelId, String pMask, InstanceAttributeTemplate[] pAttributeTemplates, String[] lovNames, InstanceAttributeTemplate[] pAttributeInstanceTemplates, String[] instanceLovNames, boolean idGenerated, boolean attributesLocked) throws WorkspaceNotFoundException, AccessRightException, PartMasterTemplateAlreadyExistsException, UserNotFoundException, NotAllowedException, CreationException, WorkflowModelNotFoundException, ListOfValuesNotFoundException;

    BinaryResource saveFileInTemplate(PartMasterTemplateKey pPartMTemplateKey, String pName, long pSize) throws WorkspaceNotFoundException, NotAllowedException, PartMasterTemplateNotFoundException, FileAlreadyExistsException, UserNotFoundException, UserNotActiveException, CreationException, AccessRightException;

    String generateId(String pWorkspaceId, String pPartMTemplateId) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException, PartMasterTemplateNotFoundException;

    PartMasterTemplate[] getPartMasterTemplates(String pWorkspaceId) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException;

    PartMasterTemplate getPartMasterTemplate(PartMasterTemplateKey pKey) throws WorkspaceNotFoundException, PartMasterTemplateNotFoundException, UserNotFoundException, UserNotActiveException;

    PartMasterTemplate updatePartMasterTemplate(PartMasterTemplateKey pKey, String pPartType, String pWorkflowModelId, String pMask, InstanceAttributeTemplate[] pAttributeTemplates, String[] lovNames, InstanceAttributeTemplate[] pAttributeInstanceTemplates, String[] instanceLovNames, boolean idGenerated, boolean attributesLocked) throws WorkspaceNotFoundException, AccessRightException, PartMasterTemplateNotFoundException, UserNotFoundException, WorkflowModelNotFoundException, UserNotActiveException, ListOfValuesNotFoundException, NotAllowedException;

    void deletePartMasterTemplate(PartMasterTemplateKey pKey) throws WorkspaceNotFoundException, AccessRightException, PartMasterTemplateNotFoundException, UserNotFoundException, UserNotActiveException;

    PartMasterTemplate removeFileFromTemplate(String pFullName) throws WorkspaceNotFoundException, PartMasterTemplateNotFoundException, AccessRightException, FileNotFoundException, UserNotFoundException, UserNotActiveException;

    BinaryResource renameFileInTemplate(String fileFullName, String newName) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, FileNotFoundException, UserNotActiveException, FileAlreadyExistsException, CreationException, StorageException, NotAllowedException;

    List<PartMaster> getPartMasters(String pWorkspaceId, int start, int pMaxResults) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, UserNotActiveException;

    int getTotalNumberOfParts(String pWorkspaceId) throws AccessRightException, WorkspaceNotFoundException, AccountNotFoundException, UserNotFoundException, UserNotActiveException;

    long getDiskUsageForPartsInWorkspace(String pWorkspaceId) throws WorkspaceNotFoundException, AccessRightException, AccountNotFoundException;

    long getDiskUsageForPartTemplatesInWorkspace(String pWorkspaceId) throws WorkspaceNotFoundException, AccessRightException, AccountNotFoundException;

    PartRevision[] getCheckedOutPartRevisions(String pWorkspaceId) throws WorkspaceNotFoundException, AccessRightException, AccountNotFoundException, UserNotFoundException, UserNotActiveException;

    PartRevision[] getAllCheckedOutPartRevisions(String pWorkspaceId) throws WorkspaceNotFoundException, AccessRightException, AccountNotFoundException;

    SharedPart createSharedPart(PartRevisionKey pPartRevisionKey, String pPassword, Date pExpireDate) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, PartRevisionNotFoundException, UserNotActiveException;

    void deleteSharedPart(SharedEntityKey pSharedEntityKey) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, SharedEntityNotFoundException;

    void updatePartRevisionACL(String workspaceId, PartRevisionKey revisionKey, Map<String, String> userEntries, Map<String, String> groupEntries) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, AccessRightException, DocumentRevisionNotFoundException;

    List<PartRevision> getPartRevisions(String pWorkspaceId, int start, int pMaxResults) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, UserNotActiveException;

    int getPartsInWorkspaceCount(String pWorkspaceId) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException;

    void deletePartRevision(PartRevisionKey partRevisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, EntityConstraintException, ESServerException;

    int getNumberOfIteration(PartRevisionKey partRevisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException;

    PartRevision createPartRevision(PartRevisionKey revisionKey, String pDescription, String pWorkflowModelId, ACLUserEntry[] pUserEntries, ACLUserGroupEntry[] pUserGroupEntries, Map<String, String> roleMappings) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, PartRevisionNotFoundException, NotAllowedException, FileAlreadyExistsException, CreationException, RoleNotFoundException, WorkflowModelNotFoundException, PartRevisionAlreadyExistsException;

    void removeACLFromPartRevision(PartRevisionKey revisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, AccessRightException;

    List<PartRevision> searchPartRevisions(PartSearchQuery partSearchQuery) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ESServerException;

    List<ProductBaseline> findBaselinesWherePartRevisionHasIterations(PartRevisionKey partRevisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException;


    PartMaster findPartMasterByCADFileName(String workspaceId, String cadFileName) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    PartRevision[] getPartRevisionsWithReferenceOrName(String pWorkspaceId, String reference, int maxResults) throws WorkspaceNotFoundException, UserNotFoundException, UserNotActiveException;

    PartRevision releasePartRevision(PartRevisionKey pRevisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, AccessRightException, NotAllowedException;

    PartRevision markPartRevisionAsObsolete(PartRevisionKey pRevisionKey) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, NotAllowedException;

    PartRevision getLastReleasePartRevision(ConfigurationItemKey ciKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, AccessRightException, PartRevisionNotFoundException;

    public User checkPartRevisionReadAccess(PartRevisionKey partRevisionKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, AccessRightException;

    boolean canAccess(PartRevisionKey partRKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException;

    boolean canAccess(PartIterationKey partRKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartRevisionNotFoundException, PartIterationNotFoundException;

    /**
     * Check if a user can access to a PartRevision
     * [WARN] Don't check if the user exist and if he can access to the part workspace
     *
     * @param user     The specific user
     * @param partRKey The key of the specif part
     * @return TRUE if he can access, False otherwise
     */
    boolean canUserAccess(User user, PartRevisionKey partRKey) throws PartRevisionNotFoundException;

    /**
     * Check if a user can access to a PartIteration
     * [WARN] Don't check if the user exist and if he can access to the part iteration workspace
     *
     * @param user     The specific user
     * @param partIKey The key of the specif part iteration
     * @return TRUE if he can access, False otherwise
     */
    boolean canUserAccess(User user, PartIterationKey partIKey) throws PartRevisionNotFoundException, PartIterationNotFoundException;

    Conversion getConversion(PartIterationKey partIterationKey) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, PartIterationNotFoundException;

    Conversion createConversion(PartIterationKey partIterationKey) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, PartIterationNotFoundException, CreationException;

    void removeConversion(PartIterationKey partIterationKey) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, PartIterationNotFoundException;

    void endConversion(PartIterationKey partIterationKey, boolean succeed) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException, PartIterationNotFoundException;

    void updateACLForPartMasterTemplate(String workspaceId, String templateId, Map<String, String> userEntries, Map<String, String> groupEntries) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, AccessRightException, PartMasterTemplateNotFoundException;

    void removeACLFromPartMasterTemplate(String workspaceId, String templateId) throws PartMasterNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartMasterTemplateNotFoundException, AccessRightException;


    PartRevision saveTags(PartRevisionKey revisionKey, String[] strings) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    PartRevision removeTag(PartRevisionKey partRevisionKey, String tagName) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    PartRevision[] findPartRevisionsByTag(String workspaceId, String tagId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    List<ModificationNotification> getModificationNotifications(PartIterationKey pPartIPK) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    void createModificationNotifications(PartIteration modifiedPartIteration) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    void removeModificationNotificationsOnIteration(PartIterationKey pPartIPK);

    void removeModificationNotificationsOnRevision(PartRevisionKey pPartRPK);

    List<PartIteration> getUsedByAsComponent(PartRevisionKey pPartRPK) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    List<PartIteration> getUsedByAsSubstitute(PartRevisionKey pPartRPK) throws UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartRevisionNotFoundException, AccessRightException;

    void checkCyclicAssemblyForPartIteration(PartIteration partIteration) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException;

    Component filterProductStructure(ConfigurationItemKey ciKey, PSFilter filter, List<PartLink> path, Integer depth) throws ConfigurationItemNotFoundException, WorkspaceNotFoundException, NotAllowedException, UserNotFoundException, UserNotActiveException, PartUsageLinkNotFoundException, AccessRightException, PartMasterNotFoundException, EntityConstraintException;

    Component filterProductStructureOnLinkType(ConfigurationItemKey ciKey, PSFilter filter, String configSpecType, String path, String linkType) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PartUsageLinkNotFoundException, ProductInstanceMasterNotFoundException, BaselineNotFoundException;

    PartLink getRootPartUsageLink(ConfigurationItemKey pKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException;

    PSFilter getLatestCheckedInPSFilter(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    void updateModificationNotification(String pWorkspaceId, int pModificationNotificationId, String pAcknowledgementComment) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, PartRevisionNotFoundException;

    List<PartLink> decodePath(ConfigurationItemKey ciKey, String path) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartUsageLinkNotFoundException, ConfigurationItemNotFoundException;

    List<PartRevision> searchPartRevisions(String workspaceId, Query query) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    List<Query> getQueries(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    Query getQuery(String workspaceId, int queryId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    void createQuery(String workspaceId, Query query) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, QueryAlreadyExistsException, CreationException;

    void deleteQuery(String workspaceId, int queryId) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException;

    List<InstanceAttributeDescriptor> getPartIterationsInstanceAttributesInWorkspace(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    List<InstanceAttributeDescriptor> getPathDataInstanceAttributesInWorkspace(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    List<PartIteration> getInversePartsLink(DocumentRevisionKey docKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, DocumentRevisionNotFoundException, PartIterationNotFoundException, PartRevisionNotFoundException;

    Set<ProductInstanceMaster> getInverseProductInstancesLink(DocumentRevisionKey docKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, DocumentRevisionNotFoundException, PartIterationNotFoundException, PartRevisionNotFoundException;

    Set<PathDataMaster> getInversePathDataLink(DocumentRevisionKey docKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, DocumentRevisionNotFoundException, PartIterationNotFoundException, PartRevisionNotFoundException;

    List<QueryResultRow> filterProductBreakdownStructure(String workspaceId, Query query) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException, ProductInstanceMasterNotFoundException, ConfigurationItemNotFoundException, NotAllowedException, PartMasterNotFoundException, EntityConstraintException;

    Query loadQuery(String workspaceId, int queryId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    Map<String, Set<BinaryResource>> getBinariesInTree(Integer baselineId, String workspaceId, ConfigurationItemKey configurationItemKey, PSFilter psFilter, boolean exportNativeCADFiles, boolean exportDocumentLinks) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException;

    ProductBaseline loadProductBaselineForProductInstanceMaster(ConfigurationItemKey ciKey, String serialNumber) throws ProductInstanceMasterNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    List<BinaryResource> getBinaryResourceFromBaseline(int baselineId);

    void deletePathToPathLink(String workspaceId, String configurationItemId, int pathToPathLinkId) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PathToPathLinkNotFoundException;

    PathToPathLink createPathToPathLink(String workspaceId, String configurationItemId, String type, String sourcePath, String targetPath, String description) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PathToPathLinkAlreadyExistsException, CreationException, PathToPathCyclicException, PartUsageLinkNotFoundException, UserNotActiveException, NotAllowedException;

    PathToPathLink updatePathToPathLink(String workspaceId, String configurationItemId, int pathToPathLinkId, String description) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PathToPathLinkAlreadyExistsException, CreationException, PathToPathCyclicException, PartUsageLinkNotFoundException, UserNotActiveException, NotAllowedException, PathToPathLinkNotFoundException;

    List<String> getPathToPathLinkTypes(String workspaceId, String configurationItemId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException;

    List<PathToPathLink> getPathToPathLinkFromSourceAndTarget(String workspaceId, String configurationItemId, String sourcePath, String targetPath) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException;

    ProductInstanceMaster findProductByPathMaster(String workspaceId, PathDataMaster pathDataMaster) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    public PartMaster getPartMasterFromPath(String workspaceId, String configurationItemId, String partPath) throws ConfigurationItemNotFoundException, UserNotFoundException, WorkspaceNotFoundException, UserNotActiveException, PartUsageLinkNotFoundException;

    boolean hasModificationNotification(ConfigurationItemKey key) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException;

    List<DocumentIterationLink> getDocumentLinksAsDocumentIterations(String workspaceId, String configurationItemId, String configSpec, PartIterationKey partIterationKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException, PartIterationNotFoundException, ProductInstanceMasterNotFoundException;

    PartIteration findPartIterationByBinaryResource(BinaryResource binaryResource) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;

    CascadeResult cascadeCheckout(ConfigurationItemKey configurationItemKey, String path) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException, PartUsageLinkNotFoundException, ConfigurationItemNotFoundException;

    CascadeResult cascadeCheckin(ConfigurationItemKey configurationItemKey, String path) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException, PartUsageLinkNotFoundException, ConfigurationItemNotFoundException;

    CascadeResult cascadeUndocheckout(ConfigurationItemKey configurationItemKey, String path) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException, PartUsageLinkNotFoundException, ConfigurationItemNotFoundException;

}

