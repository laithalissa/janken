import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Start extends Component {

  constructor(props) {
    super(props);
    this.setPlayerId.bind(this);
  }

  setPlayerId = () => {
    fetch('http://localhost:8888/new-player', {method: 'POST'})
      .then((response) => response.json())
      .then((id) => {
        this.props.setPlayerId(id);
    })
  }

  render() {
    return (
      <div className="start-game">
        <button className="start-btn" onClick={this.setPlayerId}>Enter the fray!</button>
      </div>
    )
  }
}

Start.propTypes = {
  setPlayerId: PropTypes.func
};

export default Start;
