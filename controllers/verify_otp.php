<?php
	
	include "../modules/Citizen.php";
	$oCitizen = new Citizen();
	$received_data = file_get_contents("php://input");
	file_put_contents("check.txt", $received_data);
	$oCitizen->setID("12345");

	$is_verfied = $oCitizen->verifyOTP("74174");

	header("Content-type: application/json");

	if (!is_verfied) {
		$user_data["verify"] = "false";
	}else{
		$user_data = $oCitizen->getCitizenData(array('req_col' => "*" ));
	}
	
	echo(json_encode($user_data));
?>