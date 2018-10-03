import React, { Component } from 'react';
import TopIcon from './fields/TopIcon';
import * as AC from '../utils/ActivityConstants';

require('../styles/ActivityView.css');

/**
 *    
 */
export default class IconGroup extends Component {
    render() {
        const { activity, translations, settings, activityInfo }  = this.props; 

        //Edit && Validate
        let editMsg = activityInfo && activityInfo[AC.INFO_VALIDATE] ? translations['amp.activity-preview:validate'] : translations['amp.activity-preview:edit'];
        let editIcon = activityInfo && activityInfo[AC.INFO_VALIDATE] ? '/TEMPLATE/reamp/modules/activity-preview/styles/images/AMP_validate.svg' : '/TEMPLATE/reamp/modules/activity-preview/styles/images/AMP_edit.svg';
        let editOrValidate = activityInfo && !activityInfo[AC.INFO_EDIT] ? '' : 
            (
                <TopIcon key={'editIcon'} link={'/wicket/onepager/activity/' + activity[AC.INTERNAL_ID].value}
                    label={editMsg}
                    img={editIcon}
                    target={'_self'} />
            );

        //Export to word
        let word = settings && settings[AC.HIDE_EXPORT] ? '' : 
            (
                <TopIcon key={'wordIcon'} link={'/aim/viewActivityPreview.do~activityId=' + activity[AC.INTERNAL_ID].value + '~exportActivityToWord=true'}
                    label={translations['amp.activity-preview:exportWord']}
                    img={"/TEMPLATE/reamp/modules/activity-preview/styles/images/AMP_word.svg"}
                    target={'_blank'}
                />
            );
        return (
            <div>
                {editOrValidate}
                <TopIcon key={'pdfIcon'} link={'/aim/exportActToPDF.do?activityid=' + activity[AC.INTERNAL_ID].value}
                    label={translations['amp.activity-preview:exportPDF']}
                    img={"/TEMPLATE/reamp/modules/activity-preview/styles/images/AMP_pdf.svg"} 
                    target={'_blank'}
                />
                {word}
                <TopIcon key={'printIcon'} link={'/showPrinterFriendlyPage.do?edit=true&activityid=' + activity[AC.INTERNAL_ID].value}
                    label={translations['amp.activity-preview:print']}
                    img={"/TEMPLATE/reamp/modules/activity-preview/styles/images/AMP_print.svg"} 
                    target={'_blank'}
                />
            </div>
        );
      }
}