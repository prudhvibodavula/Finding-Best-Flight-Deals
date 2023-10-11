import React from "react";
import './Apply.css';

export class Table extends React.Component {
  constructor(props) {
    super(props);
    this.renderTableRows = this.renderTableRows.bind(this);
  }



  renderTableRows = () => {
    return this.props.data.map(user => {
      return (
        <tr >
          <td>{user.source}</td>
          <td>{user.destination}</td>
          <td>{user.airlinesName}</td>
		  <td>{user.travelDate}</td>
		  <td>{user.duration}</td>
		  <td>{user.price}</td>
        </tr>
      );
    });
  };


  render() {
    return (
      <div className="app-container">
        <table>
          <thead>
            <tr>
              <th>Source</th>
              <th>Destination</th>              
			  <th>Airlines</th>
			  <th>Journey</th>
			  <th>Duration</th>
			  <th>Cost</th>
            </tr>
          </thead>
          <tbody>
            {this.renderTableRows()}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Table;