import * as api from "../../api/AdminAgentsApi";
import * as types from '../types/AgentActionTypes'


export const getAgents = (payload) => async (dispatch) => {
   await dispatch({
        api: api.getAgents,
        types: [types.REQUEST_AGENTS_START, types.REQUEST_AGENTS_SUCCESS, types.REQUEST_AGENTS_ERROR],
        data:payload
    })
}

export const getAgent = (payload) => async (dispatch) => {
    await dispatch({
        api: api.getAgentById,
        types: [types.AGENT_START, types.AGENT_SUCCESS, types.AGENT_ERROR],
        data:payload
    })
}

export const searchAgents = (payload) => async (dispatch) => {
    await dispatch({
        api: api.searchAgents,
        types: [types.SEARCH_AGENT_START, types.SEARCH_AGENT_SUCCESS, types.SEARCH_AGENT_ERROR],
        data:payload
    })
}

export const changeActive = (payload) => async (dispatch) => {
    await dispatch({
        api: api.changeAgentActive,
        types: [types.CHANGE_ACTIVE_AGENT_START, types.CHANGE_ACTIVE_AGENT_SUCCESS, types.CHANGE_ACTIVE_AGENT_ERROR],
        data:payload
    })
}

export const changeDocumentStatus = (payload) => (dispatch) => {
    dispatch({
        api: api.changeStatusDocument,
        types: ['', '', ''],
        data: payload
    }).then(res => {
        console.log(res)
        window.location.reload()
    })
}

export const getSchedule = (payload) => async (dispatch) => {
    await dispatch({
        api: api.getAgentSchedule,
        types: [types.AGENT_SCHEDULE_START, types.AGENT_SCHEDULE_SUCCESS, types.AGENT_SCHEDULE_ERROR],
        data:payload
    })
}

export const getHourOff = (payload) => async (dispatch) => {
    await dispatch({
        api: api.getHourOff,
        types: [types.START, types.SUCCESS, types.ERROR],
        data:payload
    })
}


export const editAgent = (payload) => async (dispatch) => {
    await dispatch({
        api: api.editAgent,
        types: ["", types.EDITED_SUCCESS, ""],
        data:payload
    })
}

export const getCurrentAgentLocationAction = (payload) => async (dispatch) => {
    await dispatch({
        types: 'updateState',
        payload: {agents:payload.arr}
    })
}





