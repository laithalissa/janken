import React, { Component } from 'react';
import './App.css';
import Start from './Start.js';
import Form from './Form.js';
import Wait from './Wait.js';
import Results from './Results.js';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      playerId: null,
      roundId: null,
      results: null
    };
  }

  setPlayerId = (id) => {
    this.setState({playerId: id});
  }

  setRoundId = (id) => {
    this.setState({roundId: id});
  }

  setResults = (score) => {
    this.setState({results: score});
  }

  resetRound = () => {
    this.setState({
      roundId: null,
      results: null
    })
  }

  renderInterface = () => {
    if (this.state.roundId && this.state.results) {
      return <Results score={this.state.results} resetRound={this.resetRound}/>
    } else if (this.state.roundId && !this.state.results) {
      return <Wait roundId={this.state.roundId} playerId={this.state.playerId} setResults={this.setResults}/>
    } else if (this.state.playerId) {
      return <Form playerId={this.state.playerId} setRoundId={this.setRoundId}/>
    } else {
      return <Start setPlayerId={this.setPlayerId}/>
    }
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2 className="title">Janken</h2>
        </div>
        <div className="interface-container">
          {this.renderInterface()}
        </div>
      </div>
    );
  }
}

export default App;
