var fs = require('fs');
var _ = require('underscore');
var BackboneDash = require('../backbone-dash');
var footerTemplate = _.template(fs.readFileSync(
  __dirname + '/../templates/footer.html', 'UTF-8'));


module.exports = BackboneDash.View.extend({

  className: 'row',

  initialize: function(options) {
    this.app = options.app;
  },

  render: function() {
    this.$el.html(footerTemplate());
    return this;
  }

});