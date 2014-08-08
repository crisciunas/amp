var fs = require('fs');
var _ = require('underscore');
var Backbone = require('backbone');
var Template = fs.readFileSync(__dirname + '/legend-item-template.html', 'utf8');


module.exports = Backbone.View.extend({

  template: _.template(Template),

  render: function() {
    var self = this;
    this.$el.html(this.template(_.extend({
      status: 'loading'
    }, this.model.toJSON())));

    this.model.indicator.load().then(function() {
      self.$el.html(self.template(_.extend({}, self.model.toJSON(), {
        status: 'loaded',
        colourBuckets: self.model.palette.colours,
        unit: self.model.indicator.get('data').unit
      })));
    });

    return this;
  }

});
