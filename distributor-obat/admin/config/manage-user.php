<?php
session_start();
require_once('conection.php');

$op = $_GET['op'];

if($op=="add-kurir"){
		$query = $con->prepare("INSERT INTO user (id,user,password,level) VALUES (?,?,?,?)");
		$query2 = $con->prepare("INSERT INTO kurir (id,user,no_hp,gcm_token) VALUES (?,?,?,?)");
		$query->bind_param('ssss',$id,$user,$password,$lvl);
		$query2->bind_param('ssss',$ids,$_POST['username'],$_POST['no_hp'],$gcm);
		
		//parameter
		$id="";
		$user = $_POST['username'];
		$password = md5($_POST["password"]);
		$lvl="1";
		$ids="";
		$gcm="";
		
		$query->execute();
		$query->close();
		$query2->execute();
		$query2->close();
		$con->close();
		header("location:../index.php?menu=manage&manage=kurir");
    
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
}else if($op=="add-sales"){
    $query = $con->prepare("INSERT INTO user (id,user,password,level) VALUES (?,?,?,?)");
		$query->bind_param('ssss',$id,$user,$password,$lvl);
		
		//parameter
		$id="";
		$user = $_POST['username'];
		$password = md5($_POST["password"]);
		$lvl="2";
		
		$query->execute();
		$query->close();
		$con->close();
		header("location:../index.php?menu=manage&manage=sales");
}else if($op=="del-sales"){
    switch ($_GET['del-sales']) {
                                case $_GET['del-sales']:
                                     $query = $con->prepare("delete from user where user=?");
									$query->bind_param('s',$user);
		
									//parameter
									$user = $_GET['del-sales'];
									
									$query->execute();
									$query->close();
									$con->close();
									header("location:../index.php?menu=manage&manage=sales");
                                     break;
                             }
}
?>