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
package com.docdoku.core.product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elisabel Généreux
 */
public class PartLinkList {

    private List<PartLink> path = new ArrayList<>();

    public PartLinkList(List<PartLink> path) {
        this.path = path;
    }

    public List<PartLink> getPath() {
        return path;
    }

    public void setPath(List<PartLink> path) {
        this.path = path;
    }

    public int size() {
        return this.path.size();
    }

    public boolean isEmpty() {
        return this.path.isEmpty();
    }

    public PartLink[] toArray() {
        return path.toArray(new PartLink[this.size()]);
    }

}
