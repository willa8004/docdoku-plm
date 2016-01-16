/*global casper,urls,documents*/

casper.test.begin('Private shared document tests suite', 3, function privateSharedDocumentTestsSuite() {

    'use strict';

    casper.open('');

    /**
     * Open document management URL
     * */

    casper.then(function () {
        this.open(urls.privateDocumentPermalink);
    });

    /**
     * We should be prompted for password
     */
    casper.then(function checkPasswordIsRequested() {
        this.waitForSelector('#shared-entity-password-form input[type=password]', function passwordRequested() {
            this.sendKeys('#shared-entity-password-form input[type=password]', documents.document1.sharedPassword, {reset: true});
            this.click('#shared-entity-password-form .btn-primary');
            this.test.assert(true, 'We are prompted for password');
        }, function fail() {
            this.capture('screenshot/privateSharedDocument/checkPasswordIsRequested-error.png');
            this.test.assert(false, 'Password field can not be found');
        });
    });

    /**
     * Check for document title
     */

    casper.then(function checkDocumentTitle() {
        this.waitForSelector('#page > h3', function titleDisplayed() {
            this.test.assertSelectorHasText('#page > h3', documents.document1.number + '-A');
        }, function fail() {
            this.capture('screenshot/privateSharedDocument/checkDocumentTitle-error.png');
            this.test.assert(false, 'Title can not be found');
        });
    });

    /**
     * Check for document iteration note
     */
    casper.then(function checkIterationNote() {
        this.click('.nav-tabs a[href="#tab-document-iteration"]');
        this.waitForSelector('#page > h3', function iterationNoteDisplayed() {
            this.test.assertSelectorHasText('#tab-document-iteration > table > tbody > tr:nth-child(2) > td', documents.document1.iterationNote);
        }, function fail() {
            this.capture('screenshot/privateSharedDocument/checkIterationNote-error.png');
            this.test.assert(false, 'Iteration note can not be found');
        });
    });

    casper.run(function allDone() {
        this.test.done();
    });
});
