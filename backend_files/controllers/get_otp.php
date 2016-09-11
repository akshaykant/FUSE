<?php

	require '../twilio-php-master/Twilio/autoload.php';
	include "../modules/Citizen.php";

	// Use the REST API Client to make requests to the Twilio REST API
	use Twilio\Rest\Client;
	$received_data = $_GET;
	file_put_contents("check.txt", $received_data);
	// Your Account SID and Auth Token from twilio.com/console
	$sid = 'ACbf3b4c740e56b12a8f8c26bd1ecb9c71';
	$token = '2d491e1876e879d05c27b14a0f0e166c';
	$client = new Client($sid, $token);


	$oCitizen = new Citizen();
	$oCitizen->setID("12345");
	$otp = $oCitizen->generateOTP();
	$mobile_number = $oCitizen->getCitizenData(array('req_col' => "phone" ));
	// $number = $mobile_number["phone"];
	// echo $otp;
	// header("Content-type: application/json");
	// echo(json_encode($mobile_number));
	// exit;
	// print_r($mobile_number);


	//Use the client to do fun stuff like send text messages!
	$client->messages->create(
	    // the number you'd like to send the message to
	    // $mobile_number["phone"],
	    "+919557777321",
	    array(
	        // A Twilio phone number you purchased at twilio.com/console
	        'from' => '+12053199479',
	        // the body of the text message you'd like to send
	        'body' => "Unique Identification Authority Of India. Your OTP is ".$otp
	    )
	);

?>