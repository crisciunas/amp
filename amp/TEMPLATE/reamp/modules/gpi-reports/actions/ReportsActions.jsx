import reportsApi from '../api/ReportsApi';

export function fetchReportSuccess(data){
    return {type: 'FETCH_REPORT_SUCCESS', data: data}
}

export function fetchRemarksSuccess(code, remarks){
    return {type: 'FETCH_REMARKS_SUCCESS', data:{code: code, remarks: remarks}}
}

export function clearRemarks(code){
    return {type: 'CLEAR_REMARKS', data: {code: code}}
}

export function fetchSupportingEvidenceSuccess(code, supportingEvidence) {
    return {type: 'FETCH_SUPPORTING_EVIDENCE_SUCCESS', data:{code: code, supportingEvidence: supportingEvidence}}
}

export function clearSupportingEvidence(code){
    return {type: 'CLEAR_SUPPORTING_EVIDENCE', data: {code: code}}
}


export function fetchRemarks(code, url) {
    return function(dispatch) {
        return reportsApi.fetchRemarks(url).then(response => {            
            dispatch(fetchRemarksSuccess(code, response));                                
        }).catch(error => {
            throw(error);
        });
    }; 
}


export function fetchReportData(requestData, reportCode) {
    return function(dispatch) {
        return reportsApi.fetchReportData(requestData, reportCode).then(response => {            
            dispatch(fetchReportSuccess({reportData: response, code: reportCode, requestData: requestData }));                                
        }).catch(error => {
            throw(error);
        });
    };  
}


export function fetchSupportingEvidence(code, requestData) {
    return function(dispatch) {
        return reportsApi.fetchSupportingEvidence(requestData).then(response => {            
            dispatch(fetchSupportingEvidenceSuccess(code, response));                                
        }).catch(error => {
            throw(error);
        });
    }; 
}
