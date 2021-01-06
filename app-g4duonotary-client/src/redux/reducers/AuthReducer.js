
import {TOKEN} from "../../utills/constants";
import {createReducer} from "../../utills/StoreUtils";
import * as authActionTypes from '../types/AuthTypes'


const initState = {
    loading: false,
    superAdmin:false,
    admin:false,
    agent:false,
    customer:false,
    currentUser:'',
    isOpen:false
};

const reducers = {
    [authActionTypes.START](state, action) {
        state.loading = true
    },
    [authActionTypes.SIGN_IN_SUCCESS](state, payload) {
        console.log(payload,"SUCCESS")
        // localStorage.setItem()
    },
    [authActionTypes.SIGN_IN_ERROR](state, payload) {
        console.log(payload,"ERROR")
    },
    [authActionTypes.AUTH_GET_USER_FROM_TOKEN_SUCCESS](state, payload) {
        console.log(payload,"SUCCESS")
        // localStorage.setItem()
    },
    [authActionTypes.AUTH_GET_USER_FROM_TOKEN_ERROR](state, payload) {
        console.log(payload,"ERROR")
    },
    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
};

export default createReducer(initState, reducers);