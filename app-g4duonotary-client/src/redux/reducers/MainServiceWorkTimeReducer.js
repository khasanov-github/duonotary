import * as types from "../types/MainServiceWorkTimeType";
import {createReducer} from "../../utills/StoreUtils";

const initState = {
    loading: false,
    active: false,
    online: false,
    mainServiceWorkTimes: [],
    stateOptions: [],
    countyOptions: [],
    zipCodeOptions: [],
    showModal: false,
    currentItem: null,
    currentMainService: null,
    showDeleteModal: false,
    showStatusModal: false,
    showOnlineModal: false,
    page: '',
    size: '',
    totalPages: '',
    totalElements: '',
    text: '',
    all: false,
    selectZipCodes: null,
    selectCounties: null,
    selectStates: null,
    minMaxPercent:[]
};

const reducers = {
    [types.START_MAIN_SERVICE_WORK_TIME](state) {
        state.loading = true;
        state.text = '';
    },

    [types.REQ_GET_MAIN_SERVICE_WORK_TIME_LIST](state, data) {
        // console.log("Data",data)
        state.mainServiceWorkTimes = data.payload.object.object;
    },

    [types.REQ_SUCCESS_MAIN_SERVICE_WORK_TIME](state) {
        state.loading = false;
        state.showDeleteModal = false;
        state.showStatusModal = false;
        state.showDefaultInputModal = false;
        state.showOnlineModal = false;
        state.showDynamicModal = false;
        state.showModal = false;
        state.currentItem = null;
        state.currentMainService = null;
        state.selectCounties = null;
        state.selectZipCodes = null;
        state.selectStates = null;
    },

    // [types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_STATE_LIST_SUCCESS](state, payload) {
    //     state.countyOptions.push(...payload.payload.map(item => {
    //         return {value: item.id, label: item.name}
    //     }));
    // },

    // [types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_COUNTY_LIST_SUCCESS](state, payload) {
    //     state.zipCodeOptions.push(...payload.payload.map(item => {
    //         return {value: item.id, label: item.name}
    //     }));
    // },

    [types.CHANGE_ACTIVE](state) {
        state.active = !state.active;
    },
    [types.CHANGE_ONLINE](state) {
        state.online = !state.online
    },

    [types.REQ_ERROR](state) {
        state.selectCounties = null;
        state.selectionState = null;
        state.selectZipCodes = null;
    },

    [types.GET_MIN_MAX_PERCENT](state,data){
        console.log(data,"MinMaxPercent")
        state.minMaxPercent = data.payload.object;
    },

    [types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_STATE_LIST_SUCCESS](state,payload){
        // console.log('States',payload.payload.object)
        // state.stateOptions = payload.payload.object;
        state.stateOptions.push(...payload.payload.object.map(item => {
            return {value: item.id, label: item.name}
        }));
    },

    [types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_COUNTY_LIST_SUCCESS](state,payload){
        // console.log('County',payload)
        state.countyOptions.push(...payload.payload.map(item => {
            return {value: item.id, label: item.name}
        }));
    },

    [types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_ZIP_CODE_LIST_SUCCESS](state,payload){
        // console.log('ZipCode',payload)
        state.zipCodeOptions.push(...payload.payload.map(item => {
            return {value: item.id, label: item.code}
        }));
    },

    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
}

export default createReducer(initState, reducers);