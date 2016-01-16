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

import com.docdoku.core.configuration.BaselinedPart;
import com.docdoku.core.configuration.PSFilter;
import com.docdoku.core.configuration.ProductBaseline;
import com.docdoku.core.configuration.ProductConfiguration;
import com.docdoku.core.exceptions.*;
import com.docdoku.core.product.ConfigurationItemKey;
import com.docdoku.core.product.PartIterationKey;
import com.docdoku.core.product.PartRevision;
import com.docdoku.core.product.PathToPathLink;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Morgan Guimard
 */
@WebService
public interface IProductBaselineManagerWS {

    ProductBaseline createBaseline(ConfigurationItemKey configurationItemKey, String name, ProductBaseline.BaselineType type, String description, List<PartIterationKey> partIterationKeys, List<String> substituteLinks, List<String> optionalUsageLinks) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PartRevisionNotReleasedException, PartIterationNotFoundException, UserNotActiveException, NotAllowedException, EntityConstraintException, PartMasterNotFoundException, CreationException, BaselineNotFoundException, PathToPathLinkAlreadyExistsException;
    List<ProductBaseline> getAllBaselines(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    List<ProductBaseline> getBaselines(ConfigurationItemKey configurationItemKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    void deleteBaseline(String pWorkspaceId, int baselineId) throws UserNotFoundException, AccessRightException, WorkspaceNotFoundException, BaselineNotFoundException, UserNotActiveException, EntityConstraintException;
    ProductBaseline getBaseline(int baselineId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException;
    ProductBaseline getBaselineById(int baselineId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    List<BaselinedPart> getBaselinedPartWithReference(int baselineId, String q, int maxResults) throws BaselineNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
   // List<PathChoice> getBaselineCreationPathChoices(ConfigurationItemKey ciKey, ProductBaseline.BaselineType type) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, PartMasterNotFoundException, NotAllowedException, EntityConstraintException;
    //List<PartIteration> getBaselineCreationVersionsChoices(ConfigurationItemKey ciKey) throws ConfigurationItemNotFoundException, UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, PartMasterNotFoundException, NotAllowedException, EntityConstraintException;

    //ProductConfiguration createProductConfiguration(ConfigurationItemKey ciKey, String name, String description, List<String> substituteLinks, List<String> optionalUsageLinks, Map<String,ACL.Permission> userEntries, Map<String,ACL.Permission> groupEntries) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException, CreationException, AccessRightException;
    List<ProductConfiguration> getAllProductConfigurations(String workspaceId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException;
    List<ProductConfiguration> getAllProductConfigurationsByConfigurationItemId(ConfigurationItemKey ciKey) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ConfigurationItemNotFoundException;
    ProductConfiguration getProductConfiguration(ConfigurationItemKey ciKey, int productConfigurationId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ProductConfigurationNotFoundException, AccessRightException;
    ProductConfiguration updateProductConfiguration(ConfigurationItemKey ciKey, int productConfigurationId, String name, String description,List<String> substituteLinks, List<String> optionalUsageLinks) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ProductConfigurationNotFoundException, AccessRightException;
    void deleteProductConfiguration(ConfigurationItemKey ciKey, int productConfigurationId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ProductConfigurationNotFoundException, AccessRightException;
    //void updateACLForConfiguration(ConfigurationItemKey ciKey, int productConfigurationId, Map<String,String> userEntries, Map<String,String> groupEntries) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ProductConfigurationNotFoundException, AccessRightException;
    void removeACLFromConfiguration(ConfigurationItemKey ciKey, int productConfigurationId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, ProductConfigurationNotFoundException, AccessRightException;
    List<PartRevision> getObsoletePartRevisionsInBaseline(String workspaceId, int baselineId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException;
    List<PathToPathLink> getPathToPathLinkFromSourceAndTarget(String workspaceId, String configurationItemId, int baselineId, String sourcePath, String targetPath) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException;
    List<String> getPathToPathLinkTypes(String workspaceId, String configurationItemId, int baselineId) throws UserNotFoundException, UserNotActiveException, WorkspaceNotFoundException, BaselineNotFoundException;
}