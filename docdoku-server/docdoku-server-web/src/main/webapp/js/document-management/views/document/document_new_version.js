define([
    "i18n!localization/nls/document-management-strings",
    "text!templates/document/document_new_version.html",
    "views/document/document_workflow_list"
], function (
    i18n,
    template,
    DocumentWorkflowListView
    ) {
    var DocumentsNewVersionView = Backbone.View.extend({

        id: "new-version-modal",
        className: "modal hide fade",

        events: {
            "click #create-new-version-btn":    "createNewVersionAction",
            "click #cancel-new-version-btn":    "closeModalAction",
            "click a.close":                    "closeModalAction"
        },

        initialize: function() {

        },

        render: function() {

            this.template = Mustache.render(template, {i18n: i18n, document: this.model.attributes});

            this.$el.html(this.template);

            this.$el.modal("show");

            this.bindDomElements();

            this.workflowsView = new DocumentWorkflowListView();
            this.newVersionWorkflowDiv.html(this.workflowsView.el);
            this.workflowsView.collection.fetch();

            return this;
        },

        bindDomElements: function() {
            this.newVersionWorkflowDiv = this.$("#new-version-workflow");
            this.inputNewVersionTitle = this.$("#new-version-title");
            this.textAreaNewVersionDescription = this.$("#new-version-description");
        },

        createNewVersionAction: function() {
            this.model.createNewVersion(this.inputNewVersionTitle.val(), this.textAreaNewVersionDescription.val(), this.workflowsView.selected());
            this.closeModalAction();
        },

        closeModalAction: function() {
            this.$el.modal("hide");
            this.remove();
        }

    });

    return DocumentsNewVersionView;

});
