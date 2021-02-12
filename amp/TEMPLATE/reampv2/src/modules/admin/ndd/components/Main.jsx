import React, { Component } from 'react';
import './css/style.css';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { NDDContext } from './Startup';
import {
  getNDD,
  getNDDError,
  getNDDPending,
  getPrograms,
  getProgramsPending
} from '../reducers/startupReducer';
import fetchNDD from '../actions/fetchNDD';
import fetchPrograms from '../actions/fetchAvailablePrograms';
import fetchLayout from '../actions/fetchLayout';
import FormPrograms from './FormPrograms';
import BlockUI from './common/BlockUI';

class Main extends Component {
  constructor(props) {
    super(props);
    this.shouldComponentRender = this.shouldComponentRender.bind(this);
  }

  componentDidMount() {
    const {
      fetchNDD, fetchPrograms, api, fetchLayout
    } = this.props;
    fetchLayout().then((layout) => {
      if (layout && layout.logged && layout.administratorMode === true) {
        fetchNDD(api.mappingConfig);
        fetchPrograms(api.programs);
      } else {
        window.location.replace('/login.do');
      }
    });
  }

  shouldComponentRender() {
    const { pendingNDD, pendingPrograms } = this.props;
    return !pendingNDD && !pendingPrograms;
  }

  render() {
    const {
      ndd, programs, api, trnPrefix, isIndirect, indirectProgramUpdatePending
    } = this.props;
    const { translations } = this.context;
    if (!this.shouldComponentRender() || ndd.length === 0) {
      return <div className="loading">{translations[`${trnPrefix}loading`]}</div>;
    } else {
      return (
        <div className="ndd-container">
          <NDDContext.Provider value={{
            ndd, translations, programs, api, trnPrefix, isIndirect
          }}>
            <div className="col-md-12">
              <div>
                <FormPrograms />
              </div>
            </div>
            <BlockUI blocking={indirectProgramUpdatePending} />
          </NDDContext.Provider>
        </div>
      );
    }
  }
}

Main.contextType = NDDContext;

const mapStateToProps = state => ({
  error: getNDDError(state.startupReducer),
  ndd: getNDD(state.startupReducer),
  programs: getPrograms(state.startupReducer),
  pendingNDD: getNDDPending(state.startupReducer),
  pendingPrograms: getProgramsPending(state.startupReducer),
  translations: state.translationsReducer.translations,
  indirectProgramUpdatePending: state.updateActivitiesReducer.indirectProgramUpdatePending
});
const mapDispatchToProps = dispatch => bindActionCreators({
  fetchNDD,
  fetchPrograms,
  fetchLayout
}, dispatch);
export default connect(mapStateToProps, mapDispatchToProps)(Main);
