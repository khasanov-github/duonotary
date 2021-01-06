import React, { useState, useEffect} from "react";
import axios from 'axios'
import {
    CardElement,
    useStripe,
    useElements
} from "@stripe/react-stripe-js";
export default function CheckoutForm() {
    const [succeeded, setSucceeded] = useState(false);
    const [error, setError] = useState(null);
    const [processing, setProcessing] = useState('');
    const [disabled, setDisabled] = useState(true);
    const [clientSecret, setClientSecret] = useState('');
    const stripe = useStripe();
    const [stopEffect,setStopEffect]=useState(false);
    const elements = useElements();
    useEffect(() => {
        if (!stopEffect){
            axios.get(
                'http://localhost/api/stripe/test'
            ).then(
                res=>{
                    setStopEffect(true);
                    console.log(res,"==========");
                    if (res.data.success){
                        setClientSecret(res.data.object)
                    }
                }
            )

        }

    }, []);
    const cardStyle = {
        style: {
            base: {
                color: "#32325d",
                fontFamily: 'Arial, sans-serif',
                fontSmoothing: "antialiased",
                fontSize: "16px",
                "::placeholder": {
                    color: "#32325d"
                }
            },
            invalid: {
                color: "#fa755a",
                iconColor: "#fa755a"
            }
        }
    };
    const handleChange = async (event) => {
        // Listen for changes in the CardElement
        // and display any errors as the customer types their card details
        setDisabled(event.empty);
        setError(event.error ? event.error.message : "");
    };
    const handleSubmit = async ev => {
        ev.preventDefault();
        setProcessing(true);
        const payload = await stripe.confirmCardPayment(clientSecret, {
            payment_method: {
                card: elements.getElement(CardElement)
            }
        });
        if (payload.error) {
            setError(`Payment failed ${payload.error.message}`);
            setProcessing(false);
        } else {
            axios.get(
                // 'http://localhost/api/stripe/'+{orderId}
            )
            setError(null);
            setProcessing(false);
            setSucceeded(true);

        }
    };
    return ( <form id="payment-form" onSubmit={handleSubmit}>
        <CardElement id="card-element" options={cardStyle} onChange={handleChange} />
        <button
            disabled={processing || disabled || succeeded}
            id="submit"
        >
        <span id="button-text">
          {processing ? (
              <div className="spinner" id="spinner"></div>
          ) : (
              "Pay"
          )}
        </span>
        </button>
        {/* Show any error that happens when processing the payment */}
        {error && (
            <div className="card-error" role="alert">
                {error}
            </div>
        )}
        {/* Show a success message upon completion */}
        <p className={succeeded ? "result-message" : "result-message hidden"}>
            Payment succeeded, see the result in your
            <a
                href={`https://dashboard.stripe.com/test/payments`}
                >
                {" "}
                    Stripe dashboard.
                    </a> Refresh the page to pay again.
                    </p>
                    </form>

    );}

