import React from 'react';
import { printChartPrinter } from '../../../../utils/PrintUtils';
import { SSCTranslationContext } from '../../../StartUp';

class PrintCountryCharts extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true
        };
        this.handleMessage = this.handleMessage.bind(this);
    }

    printIframe(id) {
        if (!this.state.isLoading) {
            const {translations} = this.context;
            const {countriesForExport} = this.props;
            const iframe = document.frames
                ? document.frames[id]
                : document.getElementById(id);
            const iframeWindow = iframe.contentWindow || iframe;
            this.cleanPrintNodes(iframe);
            iframe.focus();
            printChartPrinter(translations['amp.ssc.dashboard:Sector-Analysis'], 'countries-charts', 'print-friendly-dummy-container', iframe,countriesForExport);
            iframeWindow.print();
            return false;
        }
    }
    cleanPrintNodes(iframe) {
        const node = iframe.contentDocument.getElementById('print-friendly-dummy-container');
        while (node.firstChild) {
            node.removeChild(node.firstChild);
        }
    }

    componentWillMount() {
        window.addEventListener('message', this.handleMessage);
    }

    componentWillUnmount() {
        window.removeEventListener('message', this.handleMessage);
    }

    handleMessage(event) {
        if (event.data.action === 'chart-loaded') {
            this.setState({
                isLoading: false
            });
        }
    }

    render() {
        return (
            <>
                <iframe
                    id="countryChart"
                    src="/#/ssc/print"
                    style={{display: 'none'}}
                    title="Country chart"
                />
                <li className="print" onClick={() => this.printIframe('countryChart')}>print</li>
                <li onClick={this.printIframe2.bind(this)}>do print print</li>
            </>
        );
    }
}

export default PrintCountryCharts;
PrintCountryCharts.contextType = SSCTranslationContext;
