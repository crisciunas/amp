import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';
import fetchTranslations from '../../../utils/actions/fetchTranslations';
import { loadActivities } from '../actions/callReports';
import loadAmpSettings from '../actions/loadAmpSettings';
import defaultTrnPack from '../config/initialTranslations';
import { Loading } from '../../../utils/components/Loading';

export const SSCTranslationContext = React.createContext({ translations: defaultTrnPack });

/**
 * Component used to load everything we need before launching the APP
 * TODO check if we should abstract it to a Load Translations component to avoid copy ^
 */
class Startup extends Component {
    static propTypes = {
      translationPending: PropTypes.bool,
      translations: PropTypes.object
    };

    componentDidMount() {
      this.props.fetchTranslations(defaultTrnPack);
      this.props.loadAmpSettings();
      this.props.loadActivities();
    }

    render() {
      if (this.props.translationPending) {
        return (<Loading />);
      } else {
        const { translations } = this.props;
        document.title = translations['amp.ssc.dashboard:page-title'];
        return (
          <SSCTranslationContext.Provider value={{ translations }}>
            {this.props.children}
          </SSCTranslationContext.Provider>
        );
      }
    }
}

const mapStateToProps = state => ({
  translationPending: state.translationsReducer.pending,
  translations: state.translationsReducer.translations
});

const mapDispatchToProps = dispatch => bindActionCreators({
  fetchTranslations,
  loadAmpSettings,
  loadActivities
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(Startup);
