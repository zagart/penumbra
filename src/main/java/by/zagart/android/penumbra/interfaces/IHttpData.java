package by.zagart.android.penumbra.interfaces;

import android.support.annotation.StringDef;

/**
 * Interface defines data which is necessary for executing
 * HTTP-requests and handling HTTP-responses.
 *
 * @author zagart
 */

interface IHttpData {

    @StringDef({
            Header.ACTION,
            Header.LOGIN,
            Header.TOKEN,
            Header.PASSWORD,
            Header.CONTENT_TYPE,
            Header.CRITERIA,
    })
    @interface Header {

        String ACTION = "action";
        String LOGIN = "login";
        String TOKEN = "token";
        String PASSWORD = "password";
        String CONTENT_TYPE = "Content-Type";
        String CRITERIA = "criteria";
    }

    @StringDef(ContentType.APPLICATION_JSON)
    @interface ContentType {

        String APPLICATION_JSON = "application/json; charset=UTF-8";
    }

    @StringDef({
            Actions.AUTHENTICATE,
            Actions.REGISTER
    })
    @interface Actions {

        String AUTHENTICATE = "authenticate";
        String REGISTER = "register";
    }
}
