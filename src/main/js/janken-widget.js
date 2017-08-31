(function($) {

  var Widget = function(config) {
    var self = this;
    self.$scoreTarget = config.$scoreTarget;
    self.$voteTarget = config.$voteTarget;

    self.playerId;

    self.register = function() {
      $.ajax({
        method: 'PUT',
        url: '/newplayer',
        success: function(data, textStatus, request) {
          self.playerId = data.playerId
        }
      })
    }

    self.makeMove = function(move) {
      $.ajax({
        method: 'POST',
        url: '/makemove',
        data: {playerId: self.playerId, move: move},
        success: function(data, textStatus, request) {
          self.getScores();
        }
      })
    }

    self.getScores = function() {
      $.ajax({
        url: '/score',
        data: {playerId: self.playerId},
        success: function(data, textStatus, request) {
          if (data.score) {
            self.renderScores(data);
          } else {
            setTimeout(doPoll, 1000);
            self.getScores()
          }
        }
      })
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

      $table.append($headings).append($scores);
      self.$scoreTarget.html($table.html());
    }

    self.renderVoteForm = function() {
      var $select = $('<select>')
      $select
        .append($select.val('r').text('Rock'))
        .append($select.val('p').text('Paper'))
        .append($select.val('s').text('Scissors'))

      var $submit = $('<input>').attr('type', 'submit').attr('value', "Submit")

      $submit.submit(function(event) {
        self.makeMove($select.val())
        event.preventDefault()
      });

      self.$voteTarget.html(
        $('<form>').id('vote-form').append($select).append($submit).html()
      );
    }

    self.renderVoteForm()
  };

  window.JankenWidget = Widget;
})(jQuery);

