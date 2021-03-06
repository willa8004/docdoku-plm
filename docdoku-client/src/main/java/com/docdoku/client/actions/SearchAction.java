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

import com.docdoku.client.data.MainModel;
import com.docdoku.client.localization.I18N;
import com.docdoku.client.ui.ExplorerFrame;
import com.docdoku.client.ui.search.SearchDialog;
import com.docdoku.client.ui.search.SearchResultDialog;
import com.docdoku.core.document.DocumentMaster;
import com.docdoku.core.document.SearchQuery;
import com.docdoku.core.common.User;
import com.docdoku.core.common.Version;

import java.awt.Cursor;
import javax.swing.*;

import java.awt.Event;
import java.awt.event.*;
import java.util.Date;

public class SearchAction extends ClientAbstractAction {
    
    public SearchAction(ExplorerFrame pOwner) {
        super(I18N.BUNDLE.getString("SearchMenu_title"), "/com/docdoku/client/resources/icons/find.png", pOwner);
        putValue(Action.SHORT_DESCRIPTION, I18N.BUNDLE.getString("SearchMenu_short_desc"));
        putValue(Action.LONG_DESCRIPTION,
                I18N.BUNDLE.getString("SearchMenu_long_desc"));
        putValue(Action.MNEMONIC_KEY, new Integer(I18N.getCharBundle("SearchMenu_mnemonic_key")));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('F', Event.CTRL_MASK));
    }
    
    @Override
    public void actionPerformed(ActionEvent pAE) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pAE) {
                SearchDialog source = (SearchDialog) pAE.getSource();
                try{                   
                    source.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));          
                    String id = source.getId();
                    String title = source.getDocMTitle();
                    String type = source.getDocumentType();
                    Version version = source.getVersion();
                    User author = source.getAuthor();
                    String[] tags=source.getTags();
                    SearchQuery.AbstractAttributeQuery[] attrs = source.getInstanceAttributes();
                    String content = source.getContent();
                    Date creationDateFrom = source.getCreationDateFrom();
                    Date creationDateTo = source.getCreationDateTo();
                    ActionListener resultAction = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent pAE) {
                            SearchResultDialog source = (SearchResultDialog) pAE.getSource();
                            DocumentMaster docM = source.getSelectedDocMs()[0];
                            mOwner.showDocM(docM);
                        }
                    };         
                    DocumentMaster[] docMs = MainModel.getInstance().searchDocMs(id, title, version, author, type, creationDateFrom, creationDateTo, attrs, tags, content);
                    new SearchResultDialog(source, docMs, resultAction, false);
                }catch (Exception pEx) {
                    String message = pEx.getMessage()==null?I18N.BUNDLE
                            .getString("Error_unknown"):pEx.getMessage();
                    JOptionPane.showMessageDialog(null,
                            message, I18N.BUNDLE
                            .getString("Error_title"),
                            JOptionPane.ERROR_MESSAGE);
                }finally{
                    source.setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        new SearchDialog(mOwner, action);
    }
    
}
