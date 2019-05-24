var fs = require('fs');
var _ = require('underscore');
var chartUtils = require('../../../libs/local/chart-util');
var Backbone = require('backbone');
var Template = fs.readFileSync(__dirname + '/datasources-item-adm-clusters.html', 'utf8');

module.exports = Backbone.View.extend({
  tagName: 'tbody',

  template: _.template(Template),
  initialize: function(options) {
    this.app = options.app;

    _.bindAll(this, 'render');
  },

  render: function() {
	  var self = this;
	  $.when(self.app.data.generalSettings.loaded, self.collection.load()).then(function() {
			  //TODO: inefficient to constantly redraw (if already on page), put in temp obj first.
			  // then only append once.
              var isRtl = app.data.generalSettings.get('rtl-direction');
              var language = app.data.generalSettings.get('language');
              var region = app.data.generalSettings.get('region');
              var zeroSign = TranslationManager.convertNumbersToEasternArabicIfNeeded(isRtl, language, region, "0");

			  self.collection.each(function(project) {
				  // it joins on activity init, but for some reason it was being overridden...
				  // temp dirty force rejoin for now, otherwise use: getJoinedVersion
				  // dec 31st, 2014 tried getjoinedversion INSTEAD OF tempDirtyForceJoin, but still doesn't work
				  project.tempDirtyForceJoin().then(function() {

					  // Get actual or planned based on funding type setting
					  var fundingType = 'Actual';
					  var selected = self.app.data.settingsWidget.definitions.getSelectedOrDefaultFundingTypeId();
					  if (selected.toLowerCase().indexOf('planned') >= 0) {
						  fundingType = 'Planned';
					  }

                      var orgColumn, columnName1, columnName2;

                      if (selected.toLowerCase().indexOf('ssc') >= 0) {
                          columnName1 = 'Bilateral SSC Commitments';
                          columnName2 = 'Triangular SSC Commitments';
                          orgColumn = 'executingNames';
                      } else {
                          columnName1 = fundingType + ' Commitments';
                          columnName2 = fundingType + ' Disbursements';
                          orgColumn = 'donorNames';
                      }


					  // Format values.
					  var columnName1Value = project.attributes[columnName1] ? project.attributes[columnName1] : zeroSign;
					  var columnName2Value = project.attributes[columnName2] ? project.attributes[columnName2] : zeroSign;

					  var currencyCode = self.app.data.settingsWidget.definitions.getSelectedOrDefaultCurrencyId();

                      var activity = project.toJSON()
                      var orgColumnName = activity[orgColumn];

                      // put them on the page.
					  self.$el.append(self.template({
						  activity: activity,
                          orgColumnName: orgColumnName ? orgColumnName : '',
                          formattedColumnName1: [columnName1Value, ' ', currencyCode].join(''),
                          formattedColumnName2: [columnName2Value,' ', currencyCode].join('')
					  }));

			  });

		  });
	  });


	  return this;
  }

});
