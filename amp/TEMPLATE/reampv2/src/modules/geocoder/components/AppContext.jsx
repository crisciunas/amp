import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import PropTypes from 'prop-types';
import fetchTranslations from '../../../utils/actions/fetchTranslations';
import defaultTrnPack from '../config/initialTranslations.json';
import {Loading} from '../../../utils/components/Loading';
import {loadActivities} from "../actions/activitiesAction";


export const TranslationContext = React.createContext({translations: defaultTrnPack});

/**
 * Component used to load everything we need before launching the APP
 * TODO check if we should abstract it to a Load Translations component to avoid copy ^
 */
class AppContext extends Component {
    static propTypes = {
        translationPending: PropTypes.bool,
        translations: PropTypes.object
    };

    componentDidMount() {
        this.props.fetchTranslations(defaultTrnPack);
        this.props.loadActivities();
    }

    render() {
        return this.props.translationPending
            ? (<Loading/>) :
            <TranslationContext.Provider value={{translations: this.props.translations, activities: this.props.activities}}>
                {this.props.children}
            </TranslationContext.Provider>;
    }
}

const mapStateToProps = state => {
    return {
        translationPending: state.translationsReducer.pending,
        translations: state.translationsReducer.translations,
        activities: state.activitiesReducer.activities
    };
};

const mapDispatchToProps = dispatch => bindActionCreators({
    fetchTranslations: fetchTranslations,
    loadActivities: loadActivities
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(AppContext);
