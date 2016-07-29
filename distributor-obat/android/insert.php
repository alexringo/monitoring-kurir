<?php 
include("conection.php");
$response = array();
if($_SERVER['REQUEST_METHOD']=='POST'){
	
$geolocation = $_POST['geolocation'];
if (get_magic_quotes_gpc()){
$geolocation = stripslashes($geolocation);
}
$data = json_decode($geolocation);
$query=$con->prepare("insert into track (id,id_pesanan,lat,lng,kurir,tanggal) values (?,?,?,?,?,?)");
$query->bind_param('ssssss',$id,$idps,$lat,$lng,$kurir,$tgl);
for($i=0; $i<count($data) ; $i++) {
	$id="";
	$idps=$data[$i]->id_pesanan;
	$lat=$data[$i]->lat;
	$lng=$data[$i]->lng;
	$kurir=$data[$i]->kurir;
	$tgl=$data[$i]->tgl;
	 $query->execute();
}
 if($query->affected_rows>0){
         $response["success"] = 1;
         $response["message"] = "DB Synced Sucessfully.";
     }
    else{
        $response["success"] = 0;
        $response["message"] = "Failed To Update.";  
     }  	
	echo json_encode($response);
	}
?>