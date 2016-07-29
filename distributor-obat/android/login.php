<?php
error_reporting(0);
$response = array();

if( isset($_POST['user'] ) && isset($_POST['password']) ) {
 //Getting values 
 $user = $_POST['user'];
 $password = $_POST['password'];
 
  //importing dbConnect.php script 
 require_once('conection.php');
 
 $query = $con->prepare("SELECT * FROM user WHERE BINARY user=? AND BINARY password=?");
 $query->bind_param('ss',$user,$password);
 
$query->execute();
$query->bind_result($id,$use,$passe,$level);

$check = $query->fetch();
 
 //if we got some result 
 if(isset($check)){
 //displaying success 
 if($level == 2){
	 echo "success";
 } else {
 $response["success"] = 1;
 $response["message"] = "sd";
  echo json_encode($response);}
 }else{
 //displaying failure
$response["success"] = 0;
        $response["message"] = "Failed To Update.";
 echo json_encode($response);		
 }
 //$con->close();

 }
?>