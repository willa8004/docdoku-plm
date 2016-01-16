/*global define,App,$*/
define([
    'backbone',
    'mustache',
    'common-objects/views/workflow/lifecycle_task_signing',
    'common-objects/utils/date',
    'text!common-objects/templates/workflow/lifecycle_task.html'
], function (Backbone, Mustache, LifecycleTaskSigningView, date, template) {
	'use strict';
    var LifecycleTaskView = Backbone.View.extend({

        tagName: 'div',
        className: 'task well',

        events: {
            'click i.toggle-comment': 'toggleComment',
            'click i.approve-task': 'approveTaskButtonClicked',
            'click i.reject-task': 'rejectTaskButtonClicked',
            'submit .closure-comment-form': 'submitClosure',
            'click .closure-comment .cancel': 'cancelClosure'
        },

        initialize: function () {
            this.APPROVE_MODE = '1';
            this.REJECT_MODE = '2';
        },

        setTask: function (task) {
            this.task = task;

            if (this.task.closureDate) {
                this.task.formattedClosureDate = date.formatTimestamp(
                    App.config.i18n._DATE_FORMAT,
                    this.task.closureDate
                );
                this.task.ClosureStatus = (this.task.status.toLowerCase() === 'approved') ? App.config.i18n.TASK_APPROVED_ON : App.config.i18n.TASK_REJECT_ON;
            }

            this.task.isAcceptableOrRejectable = (
                this.task.worker.login === App.config.login &&
                this.task.status.toLowerCase() === 'in_progress'
                );

            return this;
        },

        setEntityType: function (entityType) {
            this.entityType = entityType;
            return this;
        },

        render: function () {
            this.$el.html(Mustache.render(template, {i18n: App.config.i18n, task: this.task}));
            this.$el.addClass(this.task.status.toLowerCase());
            this.$('.user-popover').userPopover(this.task.worker.login, this.task.title, 'left');
            this.bindDomElements();
            this.lifecycleTaskSigningView = new LifecycleTaskSigningView().render();
            this.$tasksigning.append(this.lifecycleTaskSigningView.$el);
            return this;
        },

        bindDomElements: function () {
            this.$comment = this.$('.task-comment');
            this.$closureComment = this.$('.closure-comment');
            this.$closureCommentTitle = this.$closureComment.find('h5');
            this.$closureTypeInput = this.$closureComment.find('input[name=closure-type]');
            this.$commentInput = this.$closureComment.find('input[name=closure-comment-input]');
            this.$tasksigning = this.$('.task-signing');
        },

        toggleComment: function () {
            this.$comment.toggleClass('toggled');
        },

        approveTaskButtonClicked: function () {
            this.$closureComment.addClass('toggled');
            this.$closureTypeInput.val(this.APPROVE_MODE);
            this.$closureCommentTitle.text(App.config.i18n.APPROVE_TASK);
        },

        rejectTaskButtonClicked: function () {
            this.$closureComment.addClass('toggled');
            this.$closureTypeInput.val(this.REJECT_MODE);
            this.$closureCommentTitle.text(App.config.i18n.REJECT_TASK);
        },

        submitClosure: function (e) {

            // documents / parts
            var entityType = this.entityType;

            var processUrl = App.config.contextPath +
	            '/api/workspaces/' +
                App.config.workspaceId +
                '/tasks/' + entityType + '/process?' +
                'activityWorkflowId=' + this.task.parentWorkflowId +
                '&index=' + this.task.index +
                '&activityStep=' + this.task.parentActivityStep;

            var closureComment = this.$commentInput.val();
            var closureType = this.$closureTypeInput.val();
            var signature = this.lifecycleTaskSigningView.signature;

            var data = JSON.stringify({
                comment: closureComment,
                signature: signature
            });

            if (closureType === this.APPROVE_MODE) {

                $.ajax({
                    context: this,
                    type: 'POST',
                    url: processUrl + '&action=approve',
                    data: data,
                    contentType: 'application/json;charset=UTF-8',
                    success: function () {
                        this.task.closureDate = Date.now();
                        this.task.closureComment = closureComment;
                        this.task.signature = signature;
                        this.task.status = 'approved';
                        this.refreshTask();
                    },
                    error: function (error) {
                        Backbone.Events.trigger('task:errorDisplay', this.task, error);
                    }
                });

            } else if (closureType === this.REJECT_MODE) {

                $.ajax({
                    context: this,
                    type: 'POST',
                    url: processUrl + '&action=reject',
                    data: data,
                    contentType: 'application/json;charset=UTF-8',
                    success: function () {
                        this.task.closureDate = Date.now();
                        this.task.closureComment = closureComment;
                        this.task.signature = signature;
                        this.task.status = 'rejected';
                        this.refreshTask();
                    },
                    error: function (error) {
                        Backbone.Events.trigger('task:errorDisplay', this.task, error);
                    }
                });

            }

            e.stopPropagation();
            e.preventDefault();
            return false;
        },

        cancelClosure: function (e) {
            this.$closureComment.removeClass('toggled');
            e.stopPropagation();
            e.preventDefault();
            return false;
        },

        refreshTask: function () {
            this.trigger('task:change');
            // this.setTask(this.task);
            //  this.render();
        }

    });
    return LifecycleTaskView;

});
