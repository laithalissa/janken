(function($) {

  var Widget = function(config) {
    var self = this;
    self.$scoreTarget = config.$scoreTarget;
    self.$voteTarget = config.$voteTarget;

    self.playerId;

    self.register = function() {
    }

    self.renderScores = function(data) {
      var $table = $('<table>')
      var $headings = $('<tr>')
        .append($('<th>').text('Wins'))
        .append($('<th>').text('Losses'))
        .append($('<th>').text('Draws'))
        .append($('<th>').text('Score'))

      var $scores = $('<tr>')
        .append($('<td>').text(data.wins))
        .append($('<td>').text(data.losses))
        .append($('<td>').text('coming soon!'))
        .append($('<td>').text(data.score))

      self.$scoreTarget.append($table.append($headings).append($scores));
    }

    self.renderVoteForm = function() {
      var $select = $('<select>')
        .append($select.val('r').text('Rock'))
        .append($select.val('p').text('Paper'))
        .append($select.val('s').text('Scissors'))

      var $submit = $('<input>').attr('type', 'submit').attr('value', "Submit")

      $submit.submit(function(event) {
        // TODO
        event.preventDefault();
      });

      $('<form>').id('vote-form').append($select).append($submit)
    }

  };

  window.JankenWidget = Widget;
})(jQuery);

