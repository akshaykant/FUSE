<?php

	include "../config.php";

	class Citizen{

		private $uid;

		public function setID($ID) {
			$this->uid = $ID;
		}

		public function generateOTP(){
			global $dbh;
			$five_digit_random_number = mt_rand(10000, 99999);
			$query = "update citizens set last_otp =:otp where uid =:uid ";
			try{
				$sth = $dbh -> prepare($query);
				$sth -> bindValue("otp",$five_digit_random_number);
				$sth -> bindValue("uid",$this->uid);
				$sth -> execute();
				return $five_digit_random_number;
			}catch(Exception $e){
				$err["error_msg"]= $e->getMessage();
				return $err;
			}
		}

		public function verifyOTP($entered_otp){
			$params["req_col"] = "last_otp";
			$stored_otp = $this->getCitizenData($params)["last_otp"];
			// print_r($stored_otp);
			if ($entered_otp == $stored_otp) {
				return "True";
			}else{
				return "False";
			}
		}

		public function getCitizenData($params){
			global $dbh;
			$query = "select ".$params["req_col"]." from citizens where uid = :uid";
			// echo $query;
			// echo $this->uid;
			try{
				$sth = $dbh -> prepare($query);
				$sth -> bindValue("uid",$this->uid);
				$sth -> execute();
				$result = $sth->fetch(PDO::FETCH_ASSOC);
				// print_r($result);
				return $result;
			}catch(Exception $e){
				$err["error_msg"]= $e->getMessage();
				return $err;
			}
		}
	}

?>