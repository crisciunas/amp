import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';
import fetchTranslations from '../../../../utils/actions/fetchTranslations';
import defaultTrnPack from '../config/initialTranslations';
import { Loading } from '../../../../utils/components/Loading';

export const TranslationContext = React.createContext({ translations: defaultTrnPack });

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
    }

    render() {
      return this.props.translationPending
        ? (<Loading />)
        : (
          <TranslationContext.Provider value={{ translations: this.props.translations }}>
            {this.props.children}
          </TranslationContext.Provider>
        );
    }
}

const mapStateToProps = state => ({
  translationPending: state.translationsReducer.pending,
  translations: state.translationsReducer.translations
});

const mapDispatchToProps = dispatch => bindActionCreators({ fetchTranslations }, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(Startup);
