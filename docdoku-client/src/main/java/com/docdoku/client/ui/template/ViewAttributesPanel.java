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

package com.docdoku.client.ui.template;

import com.docdoku.client.ui.common.GUIConstants;
import com.docdoku.core.meta.InstanceAttributeTemplate;
import com.docdoku.core.document.DocumentMasterTemplate;

import javax.swing.*;

import java.awt.*;

public class ViewAttributesPanel extends JPanel{
    
    private JScrollPane mAttributesScrollPane;
    private JList mAttributesList;
    private DefaultListModel mAttributesListModel;    
    
    public ViewAttributesPanel(DocumentMasterTemplate pEditedDocMTemplate) {
        mAttributesListModel = new DefaultListModel();
        mAttributesScrollPane = new JScrollPane();
        mAttributesList = new JList(mAttributesListModel);
        
        for(InstanceAttributeTemplate attr:pEditedDocMTemplate.getAttributeTemplates()) {
            mAttributesListModel.addElement(attr);
        }
        createLayout();
    }
    


    private void createLayout() {
        mAttributesScrollPane.getViewport().add(mAttributesList);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = GUIConstants.INSETS;
        constraints.gridwidth = 1;
        
        constraints.gridheight = 3;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(mAttributesScrollPane, constraints);

    }
    

}