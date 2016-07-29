<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$id= $_POST['id'];
		$image = $_POST['image'];
		$status="done";
		
		require_once('conection.php');
		
		$path = "bukti/$id.png";
		
		$actualpath = "http://192.168.42.99/cour-monitor/android/$path";
		
		$query = $con->prepare("UPDATE orderan SET bukti=? , status=? where id_pesanan=?");
		$query->bind_param('sss', $actualpath,$status,$id);
		
		if($query->execute()){
			file_put_contents($path,base64_decode($image));
			echo "Successfully Uploaded";
		}
		
		$con->close();
	}else{
		echo "Error";
	}