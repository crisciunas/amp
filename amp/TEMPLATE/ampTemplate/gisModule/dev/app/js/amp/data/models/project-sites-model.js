var Deferred = require('jquery').Deferred;
var _ = require('underscore');
var Backbone = require('backbone');
var Palette = require('../../colours/colour-palette');
var APIHelper = require('../../../libs/local/api-helper');


module.exports = Backbone.Model.extend({

  url: function() {
    return APIHelper.getAPIBase() + '/rest/gis/project-sites';
  },

  defaults: {
    title: 'Project Sites',
    value: '',
    helpText: '',
  },

  initialize: function() {
    this.palette = new Palette.FromSet();

    // private load state tracking
    this._dataLoaded = new Deferred();

    this.listenTo(this, 'change:selected', function(blah, show) {
      this.trigger(show ? 'show' : 'hide', this);
    });
  },

  load: function() {
    var self = this;

    // skip the work if we're already loaded
    if (this._dataLoaded.state() === 'pending') {
      this.fetch({type: 'POST'}).then(function() {
        self._dataLoaded.resolve();
        self.updatePaletteSet();  // in here so we don't re-do it later
      });
    }

    this._dataLoaded.then(function() {
      self.trigger('loaded processed', self);
    });

    return this._dataLoaded.promise();
  },

  updatePaletteSet: function() {
    // TODO...
  }

});
