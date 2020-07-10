import React, { Component } from 'react';
import HorizontalFilters from '../filters/HorizontalFilters';
import './map.css';
import MapHome from "../../map/MapHome";
import PopupOverlay from "../popups/popupOverlay";
import CountryPopupOverlay from "../popups/CountryPopupOverlay";
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { loadActivitiesDetails } from '../../../actions/callReports';
import SimplePopup from '../popups/homepopup/SimplePopup';
import { SSCTranslationContext } from '../../StartUp';

class MapContainer extends Component {
    render() {
        const {countries} = this.props.filters.countries;
        const {translations} = this.context;
        return (
            <div className="col-md-10 col-md-offset-2 map-wrapper">
                <HorizontalFilters selectedFilters={this.props.selectedFilters}
                                   filtersRestrictions={this.props.filtersRestrictions}
                                   handleSelectedFiltersChange={this.props.handleSelectedFiltersChange}
                                   chartSelected={this.props.chartSelected}

                />
                <MapHome filteredProjects={this.props.filteredProjects} countries={countries}/>
                {/*TODO refactor country popup in next story*/}
                <PopupOverlay show={this.props.showEmptyProjects}>
                    <SimplePopup message={translations['amp.ssc.dashboard:no-date']}
                                 onClose={this.props.onNoProjectsModalClose}/>
                </PopupOverlay>
                <CountryPopupOverlay show={this.props.showLargeCountryPopin} projects={this.props.filteredProjects}
                                     closeLargeCountryPopin={this.props.closeLargeCountryPopin}
                />


            </div>
        );
    }


}

const mapStateToProps = state => {
    return {
        filters: {
            sectors: {
                sectors: state.filtersReducer.sectors,
                sectorsLoaded: state.filtersReducer.sectorsLoaded
            },
            countries: {
                countries: state.filtersReducer.countries,
                countriesLoaded: state.filtersReducer.countriesLoaded
            }
        },
        projects: {
            activities: state.reportsReducer.activities,
            activitiesDetails: state.reportsReducer.activitiesDetails,
            activitiesLoaded: state.reportsReducer.activitiesLoaded,
        }
    };
};

const mapDispatchToProps = dispatch => bindActionCreators({
    loadActivitiesDetails: loadActivitiesDetails,
}, dispatch);

MapContainer.contextType = SSCTranslationContext;
export default connect(mapStateToProps, mapDispatchToProps)(MapContainer);

