import {fetchJson, postJson} from 'amp/tools/index';

export default class ApiHelper {
    static extractErrors(errors, obj) {
        const errorMessages = [];
        if (errors) {
            errors = Array.isArray(errors) ? errors : [errors];
            errors.forEach((error) => {
                for (const key in error) {
                    const messageKey = 'amp.preview:server-errors-' + key;
                    const message = {messageKey};
                    if (obj && obj.id) {
                        message.id = obj.id;
                    }

                    errorMessages.push(message);
                }
            });
        }
        return errorMessages;
    }

    static _fetchData(url) {
        return new Promise((resolve, reject) => {
            return fetchJson(url).then((result) => {
                if (result.error) {
                    throw ApiHelper.extractErrors(result.error);
                } else {
                    resolve(result)
                }
            }).catch((error) => {
                return reject(error);
            });
        });
    }

    static _postData(url, body) {
        return postJson(url, body).then((result) => result.json())
            .then((data) => {
                if (data.error) {
                    throw ApiHelper.extractErrors(data.error);
                } else {
                    return data;
                }
            });
    }
}
