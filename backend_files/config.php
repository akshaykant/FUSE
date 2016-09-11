<?php

/*
	This file contains all the globals constants and configrations. Include this file in all other codes and modules.
*/


	//credentials for local mysql server
	define('DB_HOST','localhost');
	define('DB_USER','root');
	define('DB_PASSWORD','root');
	define('DB_DATABASE','aadhar');


	//connecting string to mySQL using PDO
	$dbh = new PDO('mysql:unix_socket=/Applications/MAMP/tmp/mysql/mysql.sock;mysql:host='.DB_HOST.';dbname='.DB_DATABASE, DB_USER,DB_PASSWORD,array( PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, PDO::ATTR_PERSISTENT => false,PDO::MYSQL_ATTR_INIT_COMMAND => 'set names utf8mb4'));


	//global error_directory
	$error_dir = "/error_logs/";


	//function to write the captured error into files
	function write_error_to_file($error,$file_name){
		global $error_dir;
		$file_path = $error_dir.$file_name;
		file_put_contents($file_path, $error,FILE_APPEND);
	}


?>