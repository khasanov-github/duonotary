import * as types from '../types/AuthTypes'
import jwt from "jwt-decode";
import {TOKEN} from "../../utills/constants";
import {loginUser,me} from "../../api/AuthApi";

export const login = (payload) => async (dispatch) => {
    try {
        const res = await dispatch({
            api: loginUser,
            types: [types.START, "",""],
            data: payload.v,
        });
        console.log(res,"RES")

        if (res.success) {
            let parsedToken = jwt(res.payload.token);
            console.log(parsedToken,"PARSED TOKEN")
            localStorage.setItem(
                TOKEN,
                res.payload.tokenType + " " + res.payload.token
            );
            pushHisPage(parsedToken.roles, payload.history,dispatch);

        }
        return true;
    } catch (err) {
        // if (err.response) toast.error(err.response.data.message);

        return false;
    }
};

export const logout = () => (dispatch) => {
    localStorage.removeItem(TOKEN)

};

export const userMe = () => async (dispatch, getState) => {
    const {
        auth: { currentUser },
    } = getState();
    if (currentUser || !localStorage.getItem(TOKEN)) {
        return;
    }
    try {
        const response = await dispatch({
            api: me,
            types: [
                types.START,
                types.AUTH_GET_USER_FROM_TOKEN_SUCCESS,
                types.AUTH_GET_USER_FROM_TOKEN_ERROR,
            ],
        });
        if (response.success) {
            dispatch({
                type: types.AUTH_GET_USER_FROM_TOKEN_SUCCESS,
                payload: response.payload,
            });
            setStateRole(response.payload.roles, dispatch);
        } else {
            // pushHome();
        }
    } catch (e) {
        // pushHome();
    }
};

const setStateRole = (roles, dispatch) => {
    roles.forEach((item) => {
        if (item.roleName === "ROLE_SUPER_ADMIN") {
            dispatch({
                type: "updateState",
                payload: {
                    superAdmin: true,
                    admin: false,
                    agent: false,
                    customer: false
                },
            });
        } else if (item.roleName === "ROLE_ADMIN") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: true,
                    agent: false,
                    customer: false } });
        } else if (item.roleName === "ROLE_AGENT") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: false,
                    agent: true,
                    customer: false} });
        } else if (item.roleName === "ROLE_USER") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: false,
                    agent: false,
                    customer: true } });
        }
    });
};

const pushHisPage = (roles, history,dispatch) => {
    console.log(roles,"ROLES")
    console.log(history,"ROLES")
    const { push } = history;
    roles.forEach(({name}) => {
        if (name === "ROLE_SUPER_ADMIN") {
            dispatch({
                type: "updateState",
                payload: {
                    superAdmin: true,
                    admin: false,
                    agent: false,
                    customer: false
                },
            });
            push("/admin");
        } else if (name ==="ROLE_ADMIN") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: true,
                    agent: false,
                    customer: false } });
            push("/admin");
        } else if (name === "ROLE_AGENT") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: false,
                    agent: true,
                    customer: false} });
            push("/agent");
        } else if (name ==="ROLE_USER") {
            dispatch({ type: "updateState", payload: {  superAdmin: false,
                    admin: false,
                    agent: false,
                    customer: true } });
            push("/client");
        }
        else {
            alert()
        }
    });
};
