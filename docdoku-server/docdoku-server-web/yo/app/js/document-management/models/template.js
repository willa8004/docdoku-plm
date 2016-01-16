/*global _,$,define,App*/
define([
    'backbone',
    'common-objects/collections/file/attached_file_collection',
    'common-objects/utils/acl-checker'
], function (Backbone, AttachedFileCollection, ACLChecker) {
    'use strict';
    var Template = Backbone.Model.extend({
        initialize: function () {
            this.className = 'Template';
        },

        parse: function (response) {
            var filesMapping = _.map(response.attachedFiles, function (fullName) {
                return {
                    'fullName': fullName,
                    shortName: _.last(fullName.split('/')),
                    created: true
                };
            });
            response.attachedFiles = new AttachedFileCollection(filesMapping);
            return response;
        },

        defaults: {
            attachedFiles: []
        },

        toJSON: function () {
            return this.clone().set({
                attributeTemplates: _.reject(this.get('attributeTemplates'),
                    function (attribute) {
                        return attribute.name === '';
                    }
                )
            }, {silent: true}).attributes;
        },

        getId: function () {
            return this.get('id');
        },

        getAttachedFiles: function () {
            return this.get('attachedFiles');
        },
        getUploadBaseUrl: function () {
            return App.config.contextPath + '/api/files/' + this.get('workspaceId') + '/document-templates/' + this.get('id') + '/';
        },

        isAttributesLocked: function () {
            return this.get('attributesLocked');
        },
        hasACLForCurrentUser: function () {
            return this.getACLPermissionForCurrentUser() !== false;
        },

        isForbidden: function () {
            return this.getACLPermissionForCurrentUser() === 'FORBIDDEN';
        },

        isReadOnly: function () {
            return this.getACLPermissionForCurrentUser() === 'READ_ONLY';
        },

        isFullAccess: function () {
            return this.getACLPermissionForCurrentUser() === 'FULL_ACCESS';
        },

        getACLPermissionForCurrentUser: function () {
            return ACLChecker.getPermission(this.get('acl'));
        },
        updateACL: function (args) {
            $.ajax({
                type: 'PUT',
                url: this.url() + '/acl',
                data: JSON.stringify(args.acl),
                contentType: 'application/json; charset=utf-8',
                success: args.success,
                error: args.error
            });
        }

    });
    return Template;
});
