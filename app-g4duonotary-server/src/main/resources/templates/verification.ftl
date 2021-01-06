<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Java Techie Mail</title>
    <style type="text/css">

        .button{
            color: #fff!important; font-size: 25px;
            width: 70px;
            border-radius: 5px;
            text-decoration: none;
            padding: 7px 20px 7px 10px;
            margin: 10px;
            display: block;
        }
        .row{
            background-color:#fffbe0;
            border-radius:3px;
        }
        .bottom-side{
            padding:1rem;
            width:  100%;
            margin-top: 3rem;
            background-color: #9d9cd2;
        }

    </style>
</head>

<body>
<div style="font-size: 20px; margin: 0 auto; width: 70%; padding: 2em;">
    <div style=" width: 100%; background-color: #16a; padding-top: 1em; padding-bottom: 1em">
        <div style="width: 50%; margin: 0 auto;">
            <img src="https://usvirtualnotary.com/wp-content/uploads/2020/07/logo-top-v2-2-1.png" style="width:45%;height: 50%;" alt="virtual notary logo">
        </div>
    </div>
    <div class="row">
        <div style="width:100%;">
            <div>
                <p style="text-align: center; font-size: 3rem; color: #4b4b4b; font-family: 'Verdana' ">
                    Email Confirmation
                </p>
                <p style=" padding-bottom: 2em; font-size: 20px; color:#4b4b4b;">
                    hello dear ${fullName}!
                </p>

   <p style="font-size: 1rem!important;"><#if changeEmail == false>Thank you for registering for our duo notary website! we are looking forward to seeing you there and sharing our inbound marketing content with you</#if>
<#if changeEmail == true><bold>PLEASE NOTE</bold>: You're  receiving this email to change your email on duonotary website, if this is not you, press cancel button to stop this operation</#if>
   </p>
    <p> Please confirm to verify from the system.</p>
</div>
           <div style="margin: 0 auto; width: 30%">
               <a class="button" style="background-color: green;" target="_blank" href="http://localhost/api/auth/verifyEmail${'?emailCode=' + emailCode + '&isAccept=1&changeEmail='}${changeEmail?c}">Accept</a>
               <a class="button" style="background-color: red" target="_blank" href="http://localhost/api/auth/verifyEmail${'?emailCode=' + emailCode + '&isAccept=0&changeEmail='}${changeEmail?c}">Reject</a>

           </div>

        </div>
    </div>
</div>
<div class="bottom-side">

<div>
</div>

</div>
</body>
</html>