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

package com.docdoku.server.rest.dto;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class PathListDTO implements Serializable {
    private String configSpec;
    private String[] paths;

    public PathListDTO() {
    }

    public PathListDTO(String configSpec, String[] paths) {
        this.configSpec = configSpec;
        this.paths = paths;
    }

    public String getConfigSpec() {
        return configSpec;
    }
    public void setConfigSpec(String configSpec) {
        this.configSpec = configSpec;
    }

    public String[] getPaths() {
        return paths;
    }
    public void setPaths(String[] paths) {
        this.paths = paths;
    }
}
