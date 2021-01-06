import HttpClient from "../utills/HttpClient";
import {api} from './api'

export const getMainServices = async ()=>{
   return HttpClient.doGet(api.mainService+"list")
}
export const addMainService= (data) => {
    return HttpClient.doPost(api.mainService, data)
}
export const editMainService = (data) => {
    return HttpClient.doPost(api.mainService, data)
}

export const deleteMainService = (data) => {
    return HttpClient.doDelete(api.mainService +"remove"+"/"+data.id)
}