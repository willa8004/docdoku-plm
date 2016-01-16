/*global casper,urls,products*/

casper.test.begin('Private shared part tests suite', 3, function privateSharedPartTestsSuite() {

    'use strict';

    casper.open('');

    /**
     * Open part management URL
     * */

    casper.then(function () {
        this.open(urls.privatePartPermalink);
    });

    /**
     * We should be prompted for password
     */
    casper.then(function checkPasswordIsRequested() {
        this.waitForSelector('#shared-entity-password-form input[type=password]', function passwordRequested() {
            this.sendKeys('#shared-entity-password-form input[type=password]', products.part1.sharedPassword, {reset: true});
            this.click('#shared-entity-password-form .btn-primary');
            this.test.assert(true, 'We are prompted for password');
        }, function fail() {
            this.capture('screenshot/privateSharedPart/checkPasswordIsRequested-error.png');
            this.test.assert(false, 'Password field can not be found');
        });
    });

    /**
     * Check for part title
     */

    casper.then(function checkPartTitle() {
        this.waitForSelector('#page > h3', function titleDisplayed() {
            this.test.assertSelectorHasText('#page > h3', products.part1.number + '-A');
        }, function fail() {
            this.capture('screenshot/privateSharedPart/checkPartTitle-error.png');
            this.test.assert(false, 'Title can not be found');
        });
    });

    /**
     * Check for part iteration note
     */
    casper.then(function checkIterationNote() {
        this.click('.nav-tabs a[href="#tab-part-iteration"]');
        this.waitForSelector('#page > h3', function iterationNoteDisplayed() {
            this.test.assertSelectorHasText('#tab-part-iteration > table > tbody > tr:nth-child(2) > td', products.part1.iterationNote);
        }, function fail() {
            this.capture('screenshot/privateSharedPart/checkIterationNote-error.png');
            this.test.assert(false, 'Iteration note can not be found');
        });
    });

    casper.run(function allDone() {
        this.test.done();
    });
});
