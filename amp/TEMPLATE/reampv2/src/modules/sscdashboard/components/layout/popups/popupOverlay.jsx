import React, { Component } from 'react';
import './popups.css';

class PopupOverlay extends Component {
  render() {
    if (!this.props.show) {
      return null;
    }
    return (
      <div className="country-popup-wrapper">
        <div className="container-fluid">
          {this.props.children}
        </div>
      </div>
    );
  }
}

export default PopupOverlay;
