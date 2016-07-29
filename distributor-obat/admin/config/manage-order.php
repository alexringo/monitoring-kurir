<?php
session_start();
require_once('conection.php');

$op = $_GET['op'];

if($op=="assign-courier"){
		$query = $con->prepare("UPDATE orderan SET kurir=?, status=? WHERE id_pesanan=?");
		$query->bind_param('sss',$_POST['user_kurir'],$status,$_POST['id_order']);
		$query2 = $con->prepare("SELECT gcm_token from kurir where user=?");
		$query2->bind_param('s',$_POST['user_kurir']);
		$query3 = $con->prepare("SELECT nama_penerima from orderan where id_pesanan=?");
		$query3->bind_param('s',$_POST['id_order']);
		//parameter
		$status="proses";
		
		$query->execute();
		$query->close();
		
		$query2->execute();
		$query2->bind_result($gcm);
		while ($query2->fetch()){ 
				$tokens = $gcm;
		}
		$query2->close();
		
		$query3->execute();
		$query3->bind_result($penerima);
		$query3->fetch();
		$query3->close();		
		
		$con->close();
		
		//Getting api key 
	$api_key = "AIzaSyBdC-VLWfkitu8yvlEIY8FMAFv7RGpK8xA";	
	
	//Getting registration token we have to make it as array 
	$reg_token = array($gcm);
	
	//Getting the message 
	$message = $penerima;
	
	//Creating a message array 
	$msg = array
	(
		'message' 	=> $message,
		'title'		=> 'Cour-Monitor',
		'subtitle'	=> 'Notification',
		'tickerText'	=> $message,
		'vibrate'	=> 1,
		'sound'		=> 1,
		'largeIcon'	=> 'large_icon',
		'smallIcon'	=> 'small_icon'
	);
	
	//Creating a new array fileds and adding the msg array and registration token array here 
	$fields = array
	(
		'registration_ids' 	=> $reg_token,
		'data'			=> $msg
	);
	
	//Adding the api key in one more array header 
	$headers = array
	(
		'Authorization: key=' . $api_key,
		'Content-Type: application/json'
	); 
	
	//Using curl to perform http request 
	$ch = curl_init();
	curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
	curl_setopt( $ch,CURLOPT_POST, true );
	curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
	curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
	curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
	curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
	
	//Getting the result 
	$result = curl_exec($ch );
	curl_close( $ch );
	
	//Decoding json from result 
	$res = json_decode($result);

	
	//Getting value from success 
	$flag = $res->success;
	
	//if success is 1 means message is sent 
	if($flag == 1){
		//Redirecting back to our form with a request success 
		header('Location: index.php?success');
	}else{
		//Redirecting back to our form with a request failure 
		header('Location: index.php?failure');
	}
		
		header("location:../index.php?menu=order&order=assign");
    
}else if($op=="del-kurir"){
    switch ($_GET['del-kurir']) {
                                case $_GET['del-kurir']:
                                     $query = $con->prepare("delete from user where user=?");
									$query->bind_param('s',$user);
		
									//parameter
									$user = $_GET['del-kurir'];
									
									$query->execute();
									$query->close();
									$con->close();
									header("location:../index.php?menu=manage&manage=kurir");
                                     break;
                             }
}
?>