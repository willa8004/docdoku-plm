/*global casper,urls,documents,$*/

casper.test.begin('Document tag creation tests suite', 7, function documentTagCreationTestsSuite() {

    'use strict';

    casper.open('');

    /**
     * Open document management URL
     * */

    casper.then(function () {
        this.open(urls.documentManagement);
    });

    /**
     * Click on the tag button
     * */
    casper.then(function openTagCreationModal() {
        this.waitForSelector('.actions .tags', function buttonDisplayed() {
            this.click('.actions .tags');
        }, function fail() {
            this.capture('screenshot/documentTagCreation/openTagCreationModal-error.png');
            this.test.assert(false, 'Tag creation button can not be found');
        });
    });


    /**
     * Wait for the modal to be opened
     * */
    casper.then(function waitTagCreationModal() {
        this.waitForSelector('.modal.tag-management .newTag', function modalOpened() {

            this.test.assert(true, 'Tag creation modal opened');
        }, function fail() {
            this.capture('screenshot/documentTagCreation/waitTagCreationModal-error.png');
            this.test.assert(false, 'Tag creation modal can not be found');
        });
    });

    /**
     * Try to add a empty tag
     * */
    casper.then(function createEmptyTag() {
        this.waitFor(function checkHidden() {
            return this.evaluate(function() {
                return $('.modal.tag-management .newTag-button').is(':hidden');
            });
        },function success() {
            this.test.assert(true, 'add tag button hidden');
        }, function fail() {
            this.test.assert(false, 'add tab button not hidden');
        });
    });

    /**
     * Send key to input
     */
    casper.then(function sendKey() {
        this.sendKeys('.modal.tag-management .newTag', documents.tags.tag1,{reset:true});
        this.waitFor(function checkVisible() {
            return this.evaluate(function() {
                return $('.modal.tag-management .newTag-button').is(':visible');
            });
        }, function success() {
            this.test.assert(true, 'add tag button visible');
        }, function fail() {
            this.test.assert(false, 'add tab button not visible');
        });
    });

    /**
     * Try to add a tag
     * */
    casper.then(function createTags() {
        this.click('.modal.tag-management .newTag-button');
        this.waitForSelector('.modal.tag-management ul.existing-tags-list li',function tagAdded(){
            this.test.assertElementCount('.modal.tag-management ul.existing-tags-list li', 1, 'Should add a tag');
        },function fail(){
            this.capture('screenshot/documentTagCreation/createTags-error.png');
            this.test.assert(false, 'Cannot add a tag');
        });
    });

    /**
     * Assert the input has been reset
     * */
    casper.then(function checkInputReset() {
        this.test.assertField('.modal.tag-management .newTag', null, 'Input has been reset');
    });

    /**
     *
     * Try to add an other tag
     * */
    casper.then(function createAnOtherTags() {
        this.sendKeys('.modal.tag-management .newTag', documents.tags.tag2, {reset: true});
        this.click('.modal.tag-management .newTag-button');
        this.test.assertElementCount('.modal.tag-management ul.existing-tags-list li', 2, 'Should add an other tag');
    });

    /**
     *
     * Save the tags
     * */
    casper.then(function saveTags() {
        this.wait(100, function () {
            this.click('.modal.tag-management .modal-footer .btn-primary');
            this.waitWhileSelector('.modal.tag-management', function modalClosed() {
                this.test.assert(true, 'Tag modal has been closed');
            }, function fail() {
                this.capture('screenshot/documentTagCreation/saveTags-error.png');
                this.test.assert(false, 'Tag modal can not be closed');
            });
        });
    });

    casper.run(function allDone() {
        this.test.done();
    });
});
