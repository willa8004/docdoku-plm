/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDoku.
 *
 * DocDoku is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDoku is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU General Public License for more details.  
 *  
 * You should have received a copy of the GNU General Public License  
 * along with DocDoku.  If not, see <http://www.gnu.org/licenses/>.  
 */

package com.docdoku.core.services;

import java.text.MessageFormat;
import java.util.Locale;

/**
 *
 * @author Florent Garin
 */
public class DocumentMasterTemplateNotFoundException extends ApplicationException {


    private String mDocMTemplateId;

    
    public DocumentMasterTemplateNotFoundException(String pMessage) {
        super(pMessage);
    }
    
    
    public DocumentMasterTemplateNotFoundException(Locale pLocale, String pDocMTemplateID) {
        this(pLocale, pDocMTemplateID, null);
    }

    public DocumentMasterTemplateNotFoundException(Locale pLocale, String pDocMTemplateId, Throwable pCause) {
        super(pLocale, pCause);
        mDocMTemplateId=pDocMTemplateId;
    }
    
    public String getLocalizedMessage() {
        String message = getBundleDefaultMessage();
        return MessageFormat.format(message,mDocMTemplateId);     
    }
}
