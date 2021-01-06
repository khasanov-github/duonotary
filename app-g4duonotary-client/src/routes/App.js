import React from "react";
import {Provider} from "react-redux";
import {Route, Switch} from "react-router-dom";
import NotFound from "../pages/NotFound";
import PublicRoute from "../utills/PublicRoute";
import HomePage from "../pages/HomePage";
import store from "../redux";
import AdminMain from "../pages/adminPages/AdminMain";
import AgentMain from "../pages/agentPages/AgentMain";
import CustomerMain from "../pages/customerPages/CustomerMain";
import PrivateRoute from "../utills/PrivateRoute";
import AdminCalendar from "../pages/adminPages/AdminCalendar";
import Admins from "../pages/adminPages/Admins";
import AdminAgents from "../pages/adminPages/AdminAgents";
import AdminMainService from "../pages/adminPages/AdminMainService";
import AdminService from "../pages/adminPages/AdminService";
import AdminServicePrice from "../pages/adminPages/AdminServicePrice";
import AdminAdditionalService from "../pages/adminPages/AdminAdditionalService";
import AdminAdditionalServicePrice from "../pages/adminPages/AdminAdditionalServicePrice";
import AdminPricing from "../pages/adminPages/AdminPricing";
import AdminMainServiceWorkTime from "../pages/adminPages/AdminMainServiceWorkTime";
import AdminOrder from "../pages/adminPages/AdminOrder";
import AdminZipCode from "../pages/adminPages/AdminZipCode";
import AdminDiscount from "../pages/adminPages/AdminDiscount";
import AdminCustomer from "../pages/adminPages/AdminCustomer";
import AdminBlog from "../pages/adminPages/AdminBlog";
import AdminCountry from "../pages/adminPages/AdminCountry";
import AdminAgent from "../pages/adminPages/AdminAgent";


function App() {
  return (
      <Provider store={store}>
        <Switch>

          <PublicRoute exact path="/" component={HomePage} />


          <PrivateRoute exact path="/admin" component={AdminMain} />
          <PrivateRoute exact path="/admin/calendar" component={AdminCalendar} />
          <PrivateRoute exact path="/admin/admins" component={Admins} />
          <PrivateRoute exact path="/admin/agent" component={AdminAgents} />
          <PrivateRoute exact path="/admin/agent/:id" component={AdminAgent} />
          <PrivateRoute exact path="/admin/mainService" component={AdminMainService} />
          <PrivateRoute exact path="/admin/service" component={AdminService} />
          <PrivateRoute exact path="/admin/servicePrice" component={AdminServicePrice} />
          <PrivateRoute exact path="/admin/additionalService" component={AdminAdditionalService} />
          <PrivateRoute exact path="/admin/additionalServicePrice" component={AdminAdditionalServicePrice} />
          <PrivateRoute exact path="/admin/pricing" component={AdminPricing} />
          <PrivateRoute exact path="/admin/mainServiceWorkTime" component={AdminMainServiceWorkTime} />
          <PrivateRoute exact path="/admin/order" component={AdminOrder} />
          <PrivateRoute exact path="/admin/zipCode" component={AdminZipCode} />
          <PrivateRoute exact path="/admin/discount" component={AdminDiscount} />
          <PrivateRoute exact path="/admin/customer" component={AdminCustomer} />
          <PrivateRoute exact path="/admin/blog" component={AdminBlog} />
          <PrivateRoute exact path="/admin/country" component={AdminCountry} />



          <PrivateRoute exact path="/agent" component={AgentMain} />



          <PrivateRoute exact path="/customer" component={CustomerMain} />



          <Route exact component={NotFound}/>
        </Switch>
      </Provider>
  );
}

export default App;
