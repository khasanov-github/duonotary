import * as types from "../types/ServiceType";
import {createReducer} from "../../utills/StoreUtils";

const initState = {
    loading: false,
    active: false,
    services: [],
    text: '',
};

const reducers = {
    [types.START_SERVICE](state) {
        state.loading = true;
        state.text = '';
    },

    [types.REQ_SERVICE_LIST](state, data) {
        state.services = data.payload.object;
        // console.log(data, "DATA")
    },

    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
}

export default createReducer(initState, reducers);