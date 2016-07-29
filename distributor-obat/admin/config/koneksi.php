<?php
session_start();
$con=new mysqli("localhost","root","","cour_monitor");

$user = $_POST['username'];
$pass = $_POST["password"];
$password= md5($pass);
$lev = "0";
$op = $_GET['op'];

if($op=="in"){
		$query = $con->prepare("SELECT * FROM user WHERE BINARY user=? AND BINARY password=? AND level=?");
		$query->bind_param('ssi',$user,$password,$lev);
 
		$query->execute();
		$query->bind_result($id,$use,$passe,$level);

		$check = $query->fetch();
		
		if(isset($check)){
			$_SESSION['user']= $use;
			header("location:../");
		}else{
		//displaying failure
		die("<script language=\"JavaScript\">alert('Username or Password was incorrect!');window.location='../login.php'</script>");
		}
		$con->close();
    
}else if($op=="out"){
    unset($_SESSION['user']);
    unset($_SESSION['level']);
	session_unset();
	session_destroy();
    header("location:../login.php");
}
?>