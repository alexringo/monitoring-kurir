<?php

 error_reporting(0);
  include("conection.php");

  // array for JSON response
  $response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
    $user=$_POST['user'];
    $token=$_POST['token'];

    $query = $con->prepare("UPDATE kurir SET gcm_token=? where user=?");
 $query->bind_param('ss',$token,$user);
 
$query->execute();

    if($query->affected_rows>0){
         $response["success"] = 1;
         $response["message"] = "Updated Sucessfully.";
     }
    else{
        $response["success"] = 0;
        $response["message"] = "Failed To Update.";  
     }  
  // echoing JSON response
  echo json_encode($response);
  
}
?>