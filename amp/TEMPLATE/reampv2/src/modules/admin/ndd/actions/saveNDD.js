import { saveNDDSuccess, saveNDDPending, saveNDDError } from './saveAction';
import { LAYOUT_EP } from '../constants/Constants';

function saveNDD(src, dst, mappings, urlSavePrograms, urlSaveConfig) {
  return dispatch => {
    dispatch(saveNDDPending());
    fetch(LAYOUT_EP).then(layoutRes => layoutRes.json()).then(data => {
      if (data.logged) {
        fetch(urlSavePrograms, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            'src-program': src.id,
            'dst-program': dst.id
          })
        }).then(res => {
          processResponse(res);
          return fetch(urlSaveConfig, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(mappings)
          })
            .then(response => {
              processResponse(response);
              dispatch(saveNDDSuccess(response));
              return response;
            })
            .catch(error => {
              dispatch(saveNDDError(error));
            });
        })
          .catch(error => {
            dispatch(saveNDDError(error));
          });
      } else {
        window.location.replace('/login.do');
        dispatch(saveNDDError({ error: 'not logged' }));
      }
    });
  };
}

function processResponse(response) {
  if (response.status === 500 || response.error) {
    if (response.error) {
      throw (response.error);
    } else {
      throw (response.statusText);
    }
  }
}

export default saveNDD;
