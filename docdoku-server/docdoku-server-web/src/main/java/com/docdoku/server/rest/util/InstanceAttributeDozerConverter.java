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

package com.docdoku.server.rest.util;

import com.docdoku.core.common.BinaryResource;
import com.docdoku.core.meta.InstanceAttribute;
import com.docdoku.core.meta.InstanceBooleanAttribute;
import com.docdoku.core.meta.InstanceDateAttribute;
import com.docdoku.core.meta.InstanceNumberAttribute;
import com.docdoku.core.meta.InstanceTextAttribute;
import com.docdoku.core.meta.InstanceURLAttribute;
import com.docdoku.server.rest.dto.InstanceAttributeDTO;
import org.dozer.DozerConverter;

import java.util.Date;

/**
 * @author Florent Garin
 */
public class InstanceAttributeDozerConverter extends DozerConverter<InstanceAttribute, InstanceAttributeDTO> {

    public InstanceAttributeDozerConverter() {
        super(InstanceAttribute.class, InstanceAttributeDTO.class);
    }


    @Override
    public InstanceAttributeDTO convertTo(InstanceAttribute source, InstanceAttributeDTO bdestination) {
        InstanceAttributeDTO.Type type;
        String value = "";
        if (source instanceof InstanceBooleanAttribute) {
            type = InstanceAttributeDTO.Type.BOOLEAN;
            value=source.getValue()+"";
        } else if (source instanceof InstanceTextAttribute) {
            type = InstanceAttributeDTO.Type.TEXT;
            value=source.getValue()+"";
        } else if (source instanceof InstanceNumberAttribute) {
            type = InstanceAttributeDTO.Type.NUMBER;
            value=source.getValue()+"";
        } else if (source instanceof InstanceDateAttribute) {
            type = InstanceAttributeDTO.Type.DATE;
            if(((InstanceDateAttribute)source).getDateValue() != null)
                value=((InstanceDateAttribute)source).getDateValue().getTime()+"";
        } else if (source instanceof InstanceURLAttribute) {
            type = InstanceAttributeDTO.Type.URL;
            value=source.getValue()+"";
        } else {
            throw new IllegalArgumentException("Instance attribute not supported");
        }
        
        return new InstanceAttributeDTO(source.getName(), type, value);
    }

    @Override
    public InstanceAttribute convertFrom(InstanceAttributeDTO source, InstanceAttribute destination) {
        switch(source.getType()){
            case BOOLEAN:
                return new InstanceBooleanAttribute(source.getName(), Boolean.parseBoolean(source.getValue()));              
            case DATE:
                return new InstanceDateAttribute(source.getName(), new Date(Long.parseLong(source.getValue())));                
            case NUMBER:
                return new InstanceNumberAttribute(source.getName(), Float.parseFloat(source.getValue()));              
            case TEXT:
                return new InstanceTextAttribute(source.getName(), source.getValue());               
            case URL:
                return new InstanceURLAttribute(source.getName(), source.getValue());
                      
        }
        throw new IllegalArgumentException("Instance attribute not supported");
    }

}
