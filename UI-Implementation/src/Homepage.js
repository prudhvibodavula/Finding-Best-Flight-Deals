import './Apply.css';
import axios from 'axios';
import React from "react";
import Table from './Table';
import DatePicker from 'react-date-picker';
export class Homepage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [{}],
            _401K: '',
            source: '',
            destination: '',
            noOfTickets: '',
			travelDate: new Date(),
			cost: '',
			duration: '',
			airlinesName: ''		
			
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange = (e) => {
        console.log(e)
        console.log(e.target.value);
        const {name, value} = e.target;
        this.setState({[name]: value});
    };

    handledateChange = (e) => {
        this.setState({['travelDate']: e});
    };


    submitForm(e) {

        e.preventDefault();
        const deals = {
            _401K: this.state._401K,
            source: this.state.source,
            destination: this.state.destination,
            noOfTickets: this.state.noOfTickets,
			travelDate: this.state.travelDate,
			cost: this.state.cost,
			duration: this.state.duration,
			airlinesName: this.state.airlinesName,			
        };
        if (this.state.source) {

            axios({
                method: "post",
                url: "http://localhost:8080/v1/dealsfilter",
                data: deals,
                headers: {"Content-Type": "application/json"},
            }).then((response) => {
                console.log(response.data);
                this.setState({data: response.data});
                console.log(this.data);
            });

        }
        else {
            alert("Please enter the source");
            window.location.reload();
        }


    }


    render() {
        return (
             <div className="applypage">
                    <div className="applypage__content bgimage">
                        <div className="header">
                            Flight Deals Portal
                        </div>
                        <div className="applypage__text">

                            <form className="applypage__form">
                                <div className="subheader_separator subheader_main">DEALS SEARCH</div>
                                <div className="applypage__form_row">

                                    <label>Source</label>
                                    <input type="text" name="source" value={this.state.source} onChange={this.handleChange}></input>

                                    <label>Destination</label>
                                    <input type="text" name="destination" value={this.state.destination} onChange={this.handleChange}></input>
                                    
                                    <label>NumberofTickets</label>
                                    <input type="text" name="noOfTickets" value={this.state.noOfTickets} onChange={this.handleChange}></input>

                                    <label>JourneyDate</label>
                                    {/* <input type="text" name="travelDate" value={this.state.travelDate} onChange={this.handleChange}></input> */}
                                     <div>
                                     <DatePicker name="travelDate" onChange={this.handledateChange} value={this.state.travelDate} />
                                     </div>
                                </div>



                                <input type="submit" value="Get Deals" style={{width: "12rem"}} onClick={this.submitForm.bind(this)} />

                            </form>
                            <Table data={this.state.data} />
                        </div>
                    </div>
                </div>
        );

    }

}
export default Homepage;