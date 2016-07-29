<?php
error_reporting(0);
$response = array();
$response2 = array();
$response3=array();
$response4=array();
$hasil=array();
$hasil2=array();
$hasil3=array();
$hasil4=array();

if( isset($_GET['user'] ) ) {
 //Getting values 
 sleep(1);
 $user = $_GET['user'];
 $status=$_GET['status'];
 
  //importing dbConnect.php script 
 require_once('conection.php');
 
 $query = $con->prepare("SELECT id_pesanan,nama_penerima,alamat FROM orderan WHERE kurir=? AND status=?");
 $query->bind_param('ss',$user,$status);
 
$query->execute();
$query->bind_result($id,$penerima,$alamat);


 
 while($query->fetch()){
		$response['id'] = $id;
		$response['nama']= $penerima;
		$response['alamat']=$alamat;
		array_push($hasil, $response);
	}
	$con->close();
	echo json_encode($hasil);
 } 
 else if(isset($_GET['id'])) {
	 $id=$_GET['id'];
	 require_once('conection.php');
	 $query2 = $con->prepare("SELECT nama_obat, jumlah FROM pesanan WHERE id_pesanan=?");
	 $query2->bind_param('s',$id);
	 $query2->execute();
	 $query2->bind_result($nama_obat,$jumlah);
	 
	 while($query2->fetch()){
		 $response2['nama_obat'] = $nama_obat;
		 $response2['jumlah'] = $jumlah;
		 array_push($hasil2, $response2);
	 }
	 $con->close();
	 echo json_encode($hasil2);
 }
 else if(isset($_GET['ids'])){
	 $id=$_GET['ids'];
	 require_once('conection.php');
	 $query3 = $con->prepare("SELECT geo_lat,geo_long FROM lokasi_pemesan WHERE id_pesanan=?");
	 $query3->bind_param('s',$id);
	 $query32 = $con->prepare("SELECT alamat FROM orderan WHERE id_pesanan=?");
	 $query32->bind_param('s',$id);	 
	 	 
	 $query3->execute();
	 $query3->bind_result($geo_lat,$geo_long);
	 
	 while($query3->fetch()){
		 $response3['geo_lat'] = $geo_lat;
		 $response3['geo_long'] = $geo_long;
	 }
	 $query3->close();
	 
	 $query32->execute();
	 $query32->bind_result($alamat);
	 while($query32->fetch()){
		 $response3['alamat'] = $alamat;
		 array_push($hasil3, $response3);
	 }
	 $query32->close();
	 
	 $con->close();
	 
	 echo json_encode($hasil3);
	 
 }
 else if(isset($_GET['proses'])){
 $status=$_GET['proses'];
 
  //importing dbConnect.php script 
 require_once('conection.php');
 
 $query4 = $con->prepare("SELECT id_pesanan,nama_penerima,alamat FROM orderan WHERE status=?");
 $query4->bind_param('s',$status);
 
$query4->execute();
$query4->bind_result($id,$penerima,$alamat);

while($query4->fetch()){
		$response4['id'] = $id;
		$response4['nama']= $penerima;
		$response4['alamat']=$alamat;
		array_push($hasil4, $response4);
	}
	$con->close();
	echo json_encode($hasil4);
 }
?>