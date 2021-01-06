import HttpClient from "../utills/HttpClient";
import {api} from './api';
import axios from 'axios';

export const getAgents = (data) => {
    return HttpClient.doGet(api.agentsForAdmin+"?page="+data.currentPage+"&size="+data.pageSize);
}

export const searchAgents = (data) => {
    return HttpClient.doGet(api.agentsSearch+"?search="+data.searchValue);
}

export const getAgentById=(data)=>{
    return HttpClient.doGet(api.agentById+data)
}

export const changeAgentActive=(data)=>{
    return HttpClient.doPut(api.changeAgentActive+data.id,data.stateIds)
}

export const changeStatusDocument=(data)=>{
    return HttpClient.doPut(api.changeStatusDocument+"?documentId="+data.documentId+"&statusEnum="+data.statusEnum+"&description="+data.description)
}

export const getAgentSchedule = (data) => {
    return HttpClient.doGet(api.agentSchedule+data);
}

export const getHourOff = (data) => {
    return HttpClient.doGet(api.getHourOff+data);
}

export const editAgent = (data) => {
    return HttpClient.doPut(api.editUser,data);
}

// export const getLocation = (data) => {
//
//     const linkStart="https://maps.googleapis.com/maps/api/geocode/json?latlng=";
//     const linkEnd="&sensor=false&key=AIzaSyBOfWcaw_Kz0ABY4JxXO9Hd7Nq3_pkIbmI";
//     const url=linkStart+data.a+","+data.b+linkEnd;
//     let sws=[]
//     axios.get(url)
//         .then(res => {
//             return sws = res;
//         })
//     console.log(sws)
//     return sws
//
// }