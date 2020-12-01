import React, { Component } from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { NDDTranslationContext } from './components/StartUp';
import MainDashboardContainer from './components/MainDashboardContainer';
import HeaderContainer from './components/HeaderContainer';
import { callReport } from './actions/callReports';
import { FUNDING_TYPE } from './utils/constants';
import loadDashboardSettings from './actions/loadDashboardSettings';

class NDDDashboardHome extends Component {
  constructor(props) {
    super(props);
    this.state = { filters: undefined, filtersWithModels: undefined, dashboardId: undefined };
  }

  componentDidMount() {
    const { loadDashboardSettings, callReport } = this.props;
    // eslint-disable-next-line react/destructuring-assignment,react/prop-types
    const { id } = this.props.match.params;
    this.setState({ dashboardId: id });
    // This is not a saved dashboard we can load the report without filters.
    if (!id) {
      loadDashboardSettings()
        .then(settings => callReport(settings.payload.find(i => i.id === FUNDING_TYPE).value.defaultId), null);
    } else {
      loadDashboardSettings();
    }
  }

  onApplyFilters = (data, dataWithModels) => {
    const { callReport } = this.props;
    this.setState({ filters: data, filtersWithModels: dataWithModels });
    callReport(null, data);
  }

  render() {
    const { filters, dashboardId } = this.state;
    const {
      error, ndd, nddLoadingPending, nddLoaded, dashboardSettings
    } = this.props;
    return (
      <Container fluid className="main-container">
        <Row>
          <HeaderContainer onApplyFilters={this.onApplyFilters} filters={filters} dashboardId={dashboardId} />
        </Row>
        <Row>
          <MainDashboardContainer
            filters={filters}
            ndd={ndd}
            nddLoaded={nddLoaded}
            nddLoadingPending={nddLoadingPending}
            dashboardSettings={dashboardSettings} />
        </Row>
      </Container>
    );
  }
}

const mapStateToProps = state => ({
  ndd: state.reportsReducer.ndd,
  error: state.reportsReducer.error,
  nddLoaded: state.reportsReducer.nddLoaded,
  nddLoadingPending: state.reportsReducer.nddLoadingPending,
  dashboardSettings: state.dashboardSettingsReducer.dashboardSettings,
});

const mapDispatchToProps = dispatch => bindActionCreators({
  callReport, loadDashboardSettings
}, dispatch);

NDDDashboardHome.propTypes = {
  callReport: PropTypes.func.isRequired,
  error: PropTypes.object,
  ndd: PropTypes.array.isRequired,
  nddLoadingPending: PropTypes.bool.isRequired,
  nddLoaded: PropTypes.bool.isRequired,
  dashboardSettings: PropTypes.object,
  loadDashboardSettings: PropTypes.func.isRequired
};

NDDDashboardHome.defaultProps = {
  dashboardSettings: undefined,
  error: undefined,
};

NDDDashboardHome.contextType = NDDTranslationContext;
export default connect(mapStateToProps, mapDispatchToProps)(NDDDashboardHome);
