import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Form extends Component {

  handleSelect = (e) => {
    const move = e.currentTarget.value;
    e.preventDefault();
    fetch('http://localhost:8888/current')
      .then((response) => response.json())
      .then((roundId) => this.makeMove(roundId, move))
  }

  makeMove = (roundId, move) => {
    fetch('http://localhost:8888/make-move', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        timestamp: roundId,
        uuid: this.props.playerId,
        move: move
      })
    }).then((res) => this.props.setRoundId(roundId))
      .catch((error) => console.error(error))
  }

  render() {
    return (
      <div className="move-container">
        <p className="move-form-description">Choose your weapon!</p>
        <form className="move-form">
          <button className="move-btn rock" value="r" onClick={this.handleSelect}>👊</button>
          <button className="move-btn paper" value="p" onClick={this.handleSelect}>✋</button>
          <button className="move-btn scissors" value="s" onClick={this.handleSelect}>✌</button>
        </form>
      </div>
    )
  }
}

Form.propTypes = {
  playerId: PropTypes.string,
  setRoundId: PropTypes.func
};

export default Form;
