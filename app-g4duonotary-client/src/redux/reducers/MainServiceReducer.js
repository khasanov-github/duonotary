import * as types from "../types/MainServiceType";
import {createReducer} from "../../utills/StoreUtils";

const initState = {
    loading: false,
    active: false,
    online: false,
    mainServices: [],
    showModal: false,
    currentItem: null,
    showDeleteModal: false,
    showStatusModal: false,
    showOnlineModal: false,
    page: '',
    size: '',
    totalPages: '',
    totalElements: '',
    text:''
};

const reducers = {
    [types.START](state) {
        state.loading = true;
    },
    [types.REQ_GET_MAIN_SERVICE_LIST](state, data) {
        state.mainServices = data.payload.object;
    },
    [types.REQ_SUCCESS](state){
        state.loading = false;
        state.showDeleteModal = false;
        state.showStatusModal = false;
        state.showDefaultInputModal = false;
        state.showOnlineModal = false;
        state.showDynamicModal = false;
        state.showModal = false;
        state.currentItem = null;
    },
    [types.CHANGE_ACTIVE](state) {
        state.active = !state.active;
    },
    [types.CHANGE_ONLINE](state){
        state.online = !state.online
    },

    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
}

export default createReducer(initState, reducers);