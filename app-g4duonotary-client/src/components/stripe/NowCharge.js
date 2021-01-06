import React, {Component} from 'react';
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import CheckoutForm from "./CheckoutForm";
import "./Stripe.css";



class NowCharge extends Component {
    render() {
        const promise = loadStripe("pk_test_51HiumVL3EZks8ZyyLnUo0xbgKpQ98aULRPbb51XwK6jC2Bu3MCFJnDGkiL5jXte1zZii9oGi27KYiiuP1daT2DGW00AKHtv4QL");

        return (
            <div className="App">
                <Elements stripe={promise}>
                 <CheckoutForm/>
                </Elements>
            </div>
    )
        ;
    }
}

NowCharge.propTypes = {};

export default NowCharge;