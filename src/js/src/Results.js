import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Results extends Component {
  render() {
    return (
      <div className="results-container">
        <p className="result-description">
          You <span className="desc-green">vanquished</span> {this.props.score.wins} foes!
        </p><br/>
        <p className="result-description">
          But you were <span className="desc-red">defeated</span> by {this.props.score.losses} bastards!
        </p><br/>
        <p className="result-description">
          Overall you earned {this.props.score.score} points!
        </p><br/>
        <button className="reset-round-btn" onClick={this.props.resetRound}>Play another round</button>
      </div>
    )
  }
}

Results.propTypes = {
  score: PropTypes.object,
  resetRound: PropTypes.func
}

export default Results;
