package by.zagart.android.penumbra.interfaces;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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
    @Retention(RetentionPolicy.SOURCE)
    @interface Header {

        String ACTION = "action";
        String LOGIN = "login";
        String TOKEN = "token";
        String PASSWORD = "password";
        String CONTENT_TYPE = "Content-Type";
        String CRITERIA = "criteria";
    }

    @StringDef(ContentType.APPLICATION_JSON)
    @Retention(RetentionPolicy.SOURCE)
    @interface ContentType {

        String APPLICATION_JSON = "application/json; charset=UTF-8";
    }

    @StringDef({
            Actions.AUTHENTICATE,
            Actions.REGISTER
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface Actions {

        String AUTHENTICATE = "authenticate";
        String REGISTER = "register";
    }
}
