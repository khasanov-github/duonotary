import { createBrowserHistory } from "history";
// import {browserHistory} from 'react-router'
import { applyMiddleware, createStore, compose } from "redux";
import thunkMiddleware from "redux-thunk";
import apiMiddleware from "./ApiMiddleware";
import { routerMiddleware } from "react-router-redux";
import { rootReducer } from "./reducers/RootReducer";

const history = createBrowserHistory();
const routeMiddleware = routerMiddleware(history);

const middleWares = [thunkMiddleware, apiMiddleware, routeMiddleware].filter(
  Boolean
);

const store = createStore(
  rootReducer,
  compose(
    applyMiddleware(...middleWares)
  )
);

export default store;
