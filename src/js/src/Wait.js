import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Wait extends Component {
  constructor() {
    super();
    this.state = {}
  }

  componentDidMount() {
    this.setState({ intervalId: setInterval(this.poll, 1000) })
  }

  componentWillUnmount() {
    clearInterval(this.state.intervalId);
  }

  poll = () => {
    fetch('http://localhost:8888/score/' + this.props.roundId + '/' + this.props.playerId)
      .then((response) => response.json())
      .then((score) => {
        if (score.wins !== undefined) {
          this.props.setResults(score)
        }
      })
  }

  render() {
    return (
      <div className="wait-container">
        <p>Waiting...</p>
      </div>
    )
  }
}

Wait.propTypes = {
  roundId: PropTypes.string,
  playerId: PropTypes.string,
  setResults: PropTypes.func
}

export default Wait;
