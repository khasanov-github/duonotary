import *  as types from "../types/AgentActionTypes"
import {createReducer} from "../../utills/StoreUtils";

const initState = {
    loading: false,
    currentAgent: null,
    agents: [],
    searchedAgents: [],
    pageSize: 10,
    totalElements: '',
    currentPage: 0,
    showStatusModal: false,
    statusEnums: ["PENDING", "RECEIVED", "REJECTED"],
    showChangeStatusModal: false,
    currentItem: null,
    schedule: null,
    hourOff:null,
    locationName:'',
    showModal:false


}
const reducers = {
    [types.REQUEST_AGENTS_START](state) {
        state.loading = true
    },
    [types.REQUEST_AGENTS_SUCCESS](state, payload) {
        console.log(payload,"newPPPPPPPPPPPPPPPPPPP")
        state.agents = payload.payload.object;
        state.totalElements = payload.payload.totalElements
    },
    [types.REQUEST_AGENTS_ERROR](state) {
    },

    [types.SEARCH_AGENT_SUCCESS](state, payload) {
        console.log(payload, "search")
        state.searchedAgents = payload.payload.object;
    },

    [types.AGENT_START](state) {
        state.loading = true
    },
    [types.AGENT_SUCCESS](state, payload) {
        state.currentAgent = payload.payload;
    },
    [types.AGENT_ERROR](state) {
    },
    [types.CHANGE_ACTIVE_AGENT_SUCCESS](state) {
        state.loading = false
    },
    [types.AGENT_SCHEDULE_SUCCESS](state,payload){
        state.schedule=payload.payload.object
    },
    [types.SUCCESS](state,payload){
        state.hourOff=payload.payload.object
    },



    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
};
export default createReducer(initState, reducers);