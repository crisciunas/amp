import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import PropTypes from 'prop-types';
import {NDDContext} from './Startup';
import './css/style.css';
import * as Constants from "../constants/Constants";

class Header extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {translations, onAddRow, onSaveAll} = this.props;
        return (<div>
            <div className="panel panel-default">
                <div className="panel-body custom-panel">
                    <span className="glyphicon glyphicon-plus clickable" onClick={onAddRow}/>
                    <span onClick={onAddRow}
                          className="add-new-text clickable">{translations[Constants.TRN_PREFIX + 'add-new']}</span>
                    <span className="insert-data-text">{translations['amp.gpi-data:insert-data']}</span>
                    <span> / </span> <span className="glyphicon glyphicon-ok-circle success-color"> </span> <span
                    className="click-save-text">{translations['click-save']}</span>
                    <span> / </span>
                    <span className="required-fields">{translations['required-fields']}</span>
                    <span className="float-right button-wrapper">
                        <button type="button" onClick={onSaveAll}
                                className="btn btn-success">{translations[Constants.TRN_PREFIX + 'button-save-all-edits']}</button>
                    </span>
                </div>
            </div>
        </div>);
    }
}

Header.contextType = NDDContext;

Header.propTypes = {
    onAddRow: PropTypes.func.isRequired,
    onSaveAll: PropTypes.func.isRequired
}

const mapStateToProps = state => ({
    translations: state.translationsReducer.translations
});
const mapDispatchToProps = dispatch => bindActionCreators({}, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(Header);