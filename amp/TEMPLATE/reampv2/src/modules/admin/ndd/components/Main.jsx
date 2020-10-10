import React, {Component} from 'react';
import './css/style.css';
import {TranslationContext} from './Startup';
import * as Constants from "../constants/Constants";
import ProgramSelectGroup from "./ProgramSelectGroup";
import {getNDD, getNDDError, getNDDPending} from "../reducers/startupReducer";
import {bindActionCreators} from "redux";
import fetchNDD from "../actions/fetchNDD";
import {connect} from "react-redux";
import {SRC_PROGRAM, VALUE} from "../constants/Constants";
import ProgramSelect from "./ProgramSelect";

class Main extends Component {

    constructor(props) {
        super(props);
        this.shouldComponentRender = this.shouldComponentRender.bind(this);
    }

    componentDidMount() {
        const {fetchNDD} = this.props;
        fetchNDD();
    }

    shouldComponentRender() {
        return !this.props.pending;
    }

    render() {
        const {ndd} = this.props;
        const {translations} = this.context;

        if (!this.shouldComponentRender() || ndd.length === 0) {
            return <div>loading...</div>
        } else {
            return (<div>
                <div className='col-md-12'>
                    <div>
                        <h2>{translations[Constants.TRN_PREFIX + 'title']}</h2>
                        <h4>{translations[Constants.TRN_PREFIX + 'src-program-lvl-1']}: {ndd[SRC_PROGRAM][VALUE]}</h4>
                    </div>
                    <div>
                        <ProgramSelectGroup ndd={ndd}/>
                    </div>
                </div>
            </div>);
        }
    }
}

Main.contextType = TranslationContext;

const mapStateToProps = state => ({
    error: getNDDError(state.startupReducer),
    ndd: getNDD(state.startupReducer),
    pending: getNDDPending(state.startupReducer),
    translations: state.translationsReducer.translations
});
const mapDispatchToProps = dispatch => bindActionCreators({fetchNDD: fetchNDD}, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(Main);
