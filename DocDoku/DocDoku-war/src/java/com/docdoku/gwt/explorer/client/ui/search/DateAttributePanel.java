/*
 * DocDoku, Professional Open Source
 * Copyright 2006, 2007, 2008, 2009, 2010 DocDoku SARL
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

package com.docdoku.gwt.explorer.client.ui.search;

import com.docdoku.gwt.explorer.client.data.ShortDateFormater;
import com.docdoku.gwt.explorer.client.ui.widget.DocdokuDateBox;
import com.docdoku.gwt.explorer.shared.SearchQueryDTO;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DateBox;

public class DateAttributePanel extends AbstractAttributePanel {

    private static final int MAX_VISIBLE_LENGTH = 5;
//    private DateBox dateField;
    private DateBox fromDate;
    private DateBox toDate;

    public DateAttributePanel() {
        super(false);
//        dateField = new DateBox();
        fromDate = new DocdokuDateBox(DocdokuDateBox.RoundType.FLOOR);
        fromDate.setFormat(new ShortDateFormater());
        fromDate.getTextBox().setVisibleLength(MAX_VISIBLE_LENGTH);
        toDate = new DocdokuDateBox(DocdokuDateBox.RoundType.CEIL);
        toDate.setFormat(new ShortDateFormater());
        toDate.getTextBox().setVisibleLength(MAX_VISIBLE_LENGTH);
        add(new Label("≥"));
        add(fromDate);
        add(new Label("≤"));
        add(toDate);
    }

    @Override
    public SearchQueryDTO.AbstractAttributeQueryDTO getAttribute() {
        if (getNameValue().isEmpty()) {
            return null;
        }
        SearchQueryDTO.DateAttributeQueryDTO result = new SearchQueryDTO.DateAttributeQueryDTO(getNameValue(),fromDate.getValue(),toDate.getValue());
        return result;
    }
}
