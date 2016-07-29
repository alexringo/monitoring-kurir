<?php 
$response=array();
$hasil=array();
include("conection.php");

$q=$_GET['q'];
$o = "%$q%";

$query=$con->prepare("select nama_obat from daftar_obat where nama_obat like ?");
$query->bind_param('s',$o);
$query->execute();
$query->bind_result($nama_obat);

while($query->fetch()){
		 $response['nama_obat'] = $nama_obat;
		 array_push($hasil, $response);
	 }
	 $con->close();
	 echo json_encode($hasil);


?>