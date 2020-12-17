import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { CSSTransitionGroup } from 'react-transition-group';

// Dont use react-plotly directly: https://github.com/plotly/react-plotly.js/issues/135#issuecomment-501398125
import Plotly from 'plotly.js';
import createPlotlyComponent from 'react-plotly.js/factory';
import {
  DIRECT_PROGRAM, INDIRECT_PROGRAMS, PROGRAMLVL1, AMOUNT, CODE, DIRECT, INDIRECT,
  TRANSITIONS, PROGRAMLVL2, AVAILABLE_COLORS, TRN_PREFIX
} from '../utils/constants';
import {
  addAlpha, getCustomColor, getGradient
} from '../utils/Utils';
import styles from './styles.css';

const Plot = createPlotlyComponent(Plotly);

class NestedDonutsProgramChart extends Component {
  constructor(props) {
    super(props);
    const { handleOuterChartClick } = this.props;
    this.extractOuterData = this.extractOuterData.bind(this);
    this.extractInnerData = this.extractInnerData.bind(this);
    this.handleOuterChartClick = handleOuterChartClick;
    this.calculateOpacity = this.calculateOpacity.bind(this);
  }

  /**
   * Create an array with each unique Direct Program Level 1 adding the amounts when it has multiple children.
   * @returns {[]}
   */
  extractOuterData(calculateLvl2) {
    const { selectedDirectProgram } = this.props;
    const ret = [];
    const { data } = this.props;
    if (data && data.length > 0) {
      const totalAmount = data.reduce((acc, cur) => (acc + cur[DIRECT_PROGRAM][AMOUNT]), 0);
      data.forEach(i => {
        const directProgram = (calculateLvl2 && this.isSubProgram(i, selectedDirectProgram))
          ? i[DIRECT_PROGRAM][PROGRAMLVL2]
          : i[DIRECT_PROGRAM][PROGRAMLVL1];
        if (ret.filter(d => d[CODE] === directProgram[CODE]).length === 0) {
          const item = {
            [CODE]: directProgram[CODE],
            name: directProgram.name,
            [AMOUNT]: data.filter(d2 => ((calculateLvl2 && this.isSubProgram(i, selectedDirectProgram))
              ? d2[DIRECT_PROGRAM][PROGRAMLVL2][CODE] === directProgram[CODE]
              : d2[DIRECT_PROGRAM][PROGRAMLVL1][CODE] === directProgram[CODE]))
              .reduce((accumulator, currentValue) => (
                accumulator + currentValue[DIRECT_PROGRAM][AMOUNT]
              ), 0),
            neverFade: this.isSubProgram(i, selectedDirectProgram)
          };
          item.percentageInTotal = (item[AMOUNT] / totalAmount) * 100;
          ret.push(item);
        }
      });
    }
    return ret;
  }

  isSubProgram(i, selectedDirectProgram) {
    if (selectedDirectProgram) {
      return i[DIRECT_PROGRAM][PROGRAMLVL1][CODE] === selectedDirectProgram[CODE];
    }
    return false;
  }

  extractInnerData(outerData) {
    const ret = [];
    const { data } = this.props;
    if (data && data.length > 0) {
      outerData.forEach((i, index) => {
        const innerSubGroup = [];
        const subProgramsData = data.filter(j => j[DIRECT_PROGRAM][PROGRAMLVL1][CODE] === i[CODE]);
        subProgramsData.forEach(j => {
          j[INDIRECT_PROGRAMS].forEach(k => {
            if (innerSubGroup.filter(l => l[CODE] === k[PROGRAMLVL1][CODE]).length === 0) {
              innerSubGroup.push({
                [CODE]: k[PROGRAMLVL1][CODE] + Array(index).fill(' ').join(''),
                originalAmount: k[AMOUNT],
                name: k[PROGRAMLVL1].name,
                directProgramCode: j[DIRECT_PROGRAM][PROGRAMLVL1][CODE]
              });
            } else {
              const program = innerSubGroup.find(l => l[CODE] === k[PROGRAMLVL1][CODE]);
              program.originalAmount += k[AMOUNT];
            }
          });
        });
        const totalForSubGroup = innerSubGroup.reduce((acc, cur) => (acc + cur.originalAmount), 0);
        innerSubGroup.forEach(j => {
          j.percentageInSubGroup = ((j.originalAmount * 100) / totalForSubGroup);
        });
        ret.push(innerSubGroup);
      });
    }
    return ret;
  }

  innerDataToChartValues(data, outerData) {
    const ret = [];
    if (data) {
      data.forEach(i => {
        i.forEach(j => {
          const parentPercentage = outerData.find(k => k[CODE] === j.directProgramCode).percentageInTotal;
          ret.push({
            [CODE]: j[CODE],
            name: j.name,
            [AMOUNT]: (j.percentageInSubGroup / 100) * parentPercentage
          });
        });
      });
    }
    return ret;
  }

  calculateOpacity(colors, data) {
    const { selectedDirectProgram } = this.props;
    let newColors = [];
    if (selectedDirectProgram) {
      colors.forEach((c, i) => {
        const index = data.findIndex(i => i[CODE] === selectedDirectProgram[CODE]);
        if (i !== index && data[i].neverFade !== true) {
          c = addAlpha(c, 0.15);
          newColors.push(c);
        } else {
          newColors.push(c);
        }
      });
    } else {
      newColors = colors;
    }
    return newColors;
  }

  render() {
    const { selectedDirectProgram, translations } = this.props;
    const outerData = this.extractOuterData(false);
    const outerDataLvl2 = selectedDirectProgram ? this.extractOuterData(true) : this.extractOuterData(false);
    const innerData = this.extractInnerData(outerData);
    const innerDataForChart = this.innerDataToChartValues(innerData, outerData);
    const innerColors = this.calculateOpacity(innerDataForChart.map(i => getCustomColor(i, INDIRECT_PROGRAMS)),
      innerDataForChart);
    const outerColors = this.calculateOpacity(outerDataLvl2
      .map(o => getCustomColor(o, o.neverFade ? `${PROGRAMLVL1}_${selectedDirectProgram.code}` : PROGRAMLVL1)),
    outerDataLvl2);
    const transition = {
      duration: 2000,
      easing: 'cubic-in-out'
    };
    const annotations = outerData.length === 0 ? [
      {
        text: translations[`${TRN_PREFIX}no-data`],
        xref: 'paper',
        yref: 'paper',
        showarrow: false,
        font: {
          size: 28
        }
      }
    ] : [];
    // Note: Remove prop 'key' if you want to disable the fade effect after clicking the outer ring.
    return (
      <CSSTransitionGroup
        /* key={selectedDirectProgram} */
        transitionName="solar-chart"
        transitionAppear
        transitionLeave
        transitionEnter
        transitionEnterTimeout={TRANSITIONS}
        transitionLeaveTimeout={TRANSITIONS}
        transitionAppearTimeout={TRANSITIONS}>
        <Plot
          key="solarChart"
          data={
            [{
              values: innerDataForChart.map(i => i[AMOUNT]),
              labels: innerDataForChart.map(i => i[CODE]),
              text: INDIRECT,
              domain: {
                x: [0.15, 0.85],
                y: [0.15, 0.85]
              },
              textposition: 'inside',
              direction: 'clockwise',
              name: INDIRECT,
              hoverinfo: 'label',
              hole: 0.5,
              type: 'pie',
              sort: false,
              textinfo: 'label',
              marker: {
                colors: innerColors,
                line: {
                  color: 'white',
                  width: 2
                }
              },
              textfont: {
                color: 'white'
              }
            }, {
              values: outerDataLvl2.map(i => i[AMOUNT]),
              labels: outerDataLvl2.map(i => i[CODE]),
              name: DIRECT,
              hoverinfo: 'skip',
              textposition: 'inside',
              hole: 0.7,
              type: 'pie',
              sort: false,
              direction: 'clockwise',
              textinfo: 'label',
              marker: {
                colors: outerColors,
                line: {
                  color: 'white',
                  width: 2
                }
              },
              textfont: {
                color: 'white'
              }
            }]
          }
          layout={{
            autosize: false,
            paper_bgcolor: 'rgba(0,0,0,0)',
            width: 400,
            height: 400,
            title: '',
            showlegend: false,
            transition,
            margin: {
              l: 55,
              r: 0,
              b: 10,
              t: 20,
              pad: 4
            },
            annotations
          }}
          config={{ displaylogo: false }}
          onClick={event => this.handleOuterChartClick(event, outerData)}
        />
      </CSSTransitionGroup>
    );
  }
}

NestedDonutsProgramChart.propTypes = {
  data: PropTypes.array.isRequired,
  handleOuterChartClick: PropTypes.func.isRequired,
  selectedDirectProgram: PropTypes.object.isRequired,
  translations: PropTypes.array.isRequired
};

const mapStateToProps = state => ({
  translations: state.translationsReducer.translations
});

const mapDispatchToProps = dispatch => bindActionCreators({}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(NestedDonutsProgramChart);
