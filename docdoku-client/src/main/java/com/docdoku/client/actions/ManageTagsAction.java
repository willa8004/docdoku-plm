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

package com.docdoku.client.actions;

import com.docdoku.client.ui.tag.ManageTagsDialog;
import com.docdoku.core.document.DocumentMaster;
import com.docdoku.client.ui.ExplorerFrame;

import javax.swing.*;

import java.awt.event.*;

import com.docdoku.client.localization.I18N;

public class ManageTagsAction extends ClientAbstractAction {
    
    public ManageTagsAction(ExplorerFrame pOwner) {
        super(I18N.BUNDLE.getString("ManageTags_title"), "/com/docdoku/client/resources/icons/note.png", pOwner);
        putValue(Action.SHORT_DESCRIPTION, I18N.BUNDLE.getString("ManageTags_short_desc"));
        putValue(Action.LONG_DESCRIPTION, I18N.BUNDLE.getString("ManageTags_long_desc"));
        putValue(Action.MNEMONIC_KEY, new Integer(I18N.getCharBundle("ManageTags_mnemonic_key")));
    }

    @Override
    public void actionPerformed(ActionEvent pAE) {
        DocumentMaster docM = mOwner.getSelectedDocM();
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pAE) {
                ManageTagsDialog source = (ManageTagsDialog) pAE.getSource();
                DocumentMaster docM = source.getDocM();
                String[] tags = source.getTags();
                MainController controller = MainController.getInstance();
                try{
                    controller.saveTags(docM, tags);
                }catch (Exception pEx) {
                    String message = pEx.getMessage()==null?I18N.BUNDLE
                            .getString("Error_unknown"):pEx.getMessage();
                    JOptionPane.showMessageDialog(null,
                            message, I18N.BUNDLE
                            .getString("Error_title"),
                            JOptionPane.ERROR_MESSAGE);
                }
                ExplorerFrame.unselectElementInAllFrame();
            }
        };
        new ManageTagsDialog(mOwner, docM, action);
    }
}
