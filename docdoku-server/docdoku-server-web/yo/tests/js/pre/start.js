/*global casper*/

// This is the first file in the tests suite : use casper.start()
casper.options.viewportSize = {
    width: 1680,
    height: 1050
};
casper.options.waitTimeout = 10000;
casper.options.timeout = 60000;

casper.start();

casper.setFilter("page.confirm", function(msg) {
    this.log("Confirm box: "+msg,'info');
    return true;
});

casper.on('remote.message', function remoteMessage(message) {
    this.log(message,'info');
});

casper.test.begin('DocdokuPLM Tests suite',1, function docdokuPLMTestsSuite() {

    casper.thenOpen(homeUrl,function homePageLoaded(){
        this.test.assert(true,'Tests begins');
    });

    casper.run(function letsGo(){
        this.test.done();
    });
});