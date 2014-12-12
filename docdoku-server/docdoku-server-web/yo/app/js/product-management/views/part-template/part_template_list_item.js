/*global define*/
define([
    'backbone',
    'mustache',
    'models/part_template',
    'text!templates/part-template/part_template_list_item.html',
    'views/part-template/part_template_edit_view'
], function (Backbone, Mustache, PartTemplate, template, PartTemplateEditView) {
    'use strict';
    var PartTemplateListItemView = Backbone.View.extend({

        events: {
            'click input[type=checkbox]': 'selectionChanged',
            'click td.reference': 'toPartTemplateEditModal'
        },

        tagName: 'tr',

        initialize: function () {
            _.bindAll(this);
            this._isChecked = false;
            this.listenTo(this.model, 'change', this.render);
        },

        render: function () {
            this.$el.html(Mustache.render(template, this.model));
            this.$checkbox = this.$('input[type=checkbox]');
            if (this.isChecked()) {
                this.check();
                this.trigger('selectionChanged', this);
            }
            this.bindUserPopover();
            this.trigger('rendered', this);
            return this;
        },

        selectionChanged: function () {
            this._isChecked = this.$checkbox.prop('checked');
            this.trigger('selectionChanged', this);
        },

        isChecked: function () {
            return this._isChecked;
        },

        check: function () {
            this.$checkbox.prop('checked', true);
            this._isChecked = true;
        },

        unCheck: function () {
            this.$checkbox.prop('checked', false);
            this._isChecked = false;
        },

        bindUserPopover: function () {
            this.$('.author-popover').userPopover(this.model.getAuthorLogin(), this.model.getId(), 'left');
        },

        toPartTemplateEditModal: function () {
            var that = this;
            var partTemplateEditView = new PartTemplateEditView({model: that.model}).render();
            partTemplateEditView.show();
        }

    });

    return PartTemplateListItemView;

});