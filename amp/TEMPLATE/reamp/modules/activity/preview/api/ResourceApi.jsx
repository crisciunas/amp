import { RESOURCES_POSSIBLE_VALUES_API, RESOURCES_ENABLED_FIELDS_API, RESOURCES_API }
from '../common/ReampConstants.jsx';
import {fetchJson, postJson} from 'amp/tools/index';

export default class ResourceApi {
    static getResourcesEnabledFields() {
        const url = RESOURCES_ENABLED_FIELDS_API;
        return ResourceApi._fetchData(url);
    }

    static fetchResources(resourceUUID) {
        const url = RESOURCES_API;
        return ResourceApi._postData(url, resourceUUID);
    }

    static fetchPossibleValues(fields) {
        const url = RESOURCES_POSSIBLE_VALUES_API;
        return ResourceApi._postData(url, fields);
    }

    static extractErrors(errors, obj) {
        var errorMessages = [];
        if (errors) {
            errors = Array.isArray(errors) ? errors : [errors];
            errors.forEach(function (error) {
                for (var key in error) {
                    let messageKey = 'amp.preview:server-errors-' + key;
                    let message = {messageKey: messageKey};
                    if (obj && obj.id) {
                        message.id = obj.id;
                    }

                    if (obj && obj.cid) {
                        message.cid = obj.cid;
                    }

                    errorMessages.push(message);
                }
            });
        }

        return errorMessages;
    }

    static _postData(url, body) {
        return postJson(url, body).then((result) => result.json())
            .then((data) => {
            debugger;
                if (data.error) {
                    throw ResourceApi.extractErrors(data.error);
                } else {
                    return data;
                }
            });
    }

    static _fetchData(url) {
        return new Promise((resolve, reject) => {
            return fetchJson(url).then((result) => {
                resolve(result)
            }).catch((error) => {
                return reject(error);
            });
        });
    }
}
