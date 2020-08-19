import React, {Component} from 'react';
import {TranslationContext} from '../../AppContext';
import GeocoderHeader from "./GeocoderHeader";
import ActivityTable from "../table/ActivityTable";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import GeocodingTable from "../table/GeocodingTable";
import {SECTORS_CHART} from "../../../../sscdashboard/utils/constants";

const GeocodingNotAvailable = ({user, workspace}) => <h4>Geocoding process not available. User {user} is owner of the process in '{workspace}' workspace</h4>;

const GeocodingRunning = ({message}) => <div><h4>{message}</h4></div>

const ProjectList = ({title}) => <h3>{title}</h3>;

class GeocoderPanel extends Component {

    state = {
        selectedActivities: []
    };

    onSelectActivity = (isSelected, activityId) => {
        this.setState(previousState => {
            let selectedActivities = Array.from(this.state.selectedActivities);
            if (isSelected) {
                selectedActivities.push(activityId);
            } else {
                selectedActivities = selectedActivities.filter(id => id !== activityId);
            }
            return {selectedActivities};
        });
    }

    onSelectAllActivities = (isSelected, rows) => {
        let selectedActivities = Array.from(this.state.selectedActivities);
        let activityIds = rows.map(r => r.id);

        if (isSelected) {
            activityIds.forEach(activityId => selectedActivities.push(activityId));
        } else {
            selectedActivities = selectedActivities.filter(id => !activityIds.includes(id));
        }

        this.setState({selectedActivities});
    }

    render() {
        let {translations} = this.context;

        let isGeocodingNotAvailable = this.props.geocoding.status === 'NOT_AVAILABLE';
        let isGeocodingAvailable = this.props.geocoding.status === 'AVAILABLE';
        let isGeocodingRunning = this.props.geocoding.status === 'RUNNING';
        let isGeocodingCompleted = this.props.geocoding.status === 'COMPLETED';

        let title = translations['amp.geocoder:projectList'];

        if (isGeocodingAvailable) {
            title = title +  ' - ' + translations['amp.geocoder:geocodedSelection'];
        }

        return (
            <div>
            {isGeocodingNotAvailable && <GeocodingNotAvailable user={this.props.geocoding.creator} workspace={this.props.geocoding.workspace}/>}
            {(isGeocodingCompleted || isGeocodingAvailable) &&
                <div>
                    <ProjectList title={title}/>
                    <div className='panel panel-default'>
                        <GeocoderHeader selectedActivities={this.state.selectedActivities} />
                        {isGeocodingAvailable && <GeocodingTable/>}
                        {isGeocodingCompleted && <ActivityTable onSelectActivity={this.onSelectActivity.bind(this)}
                                                                onSelectAllActivities={this.onSelectAllActivities.bind(this)}
                                                                selectedActivities={this.state.selectedActivities}/>}
                    </div>
                </div>
            }
            {isGeocodingRunning && <GeocodingRunning message={translations['amp.geocoder:running']}/>}
            </div>
        );
    }
}

GeocoderPanel.contextType = TranslationContext;

const mapStateToProps = state => {
    return {
        geocoding: state.geocodingReducer,
    };
};

const mapDispatchToProps = dispatch => bindActionCreators({}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(GeocoderPanel);