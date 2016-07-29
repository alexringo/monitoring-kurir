<?php 
include("conection.php");
$response = array();
//if($_SERVER['REQUEST_METHOD']=='POST'){
	
$pesanan = $_POST['pesanan'];
$data_pemesan = $_POST['data_pemesan'];
if (get_magic_quotes_gpc()){
$pesanan = stripslashes($pesanan);
$data_pemesan=stripslashes($data_pemesan);
}

$data2 = json_decode($pesanan);
$data = json_decode($data_pemesan);
$query=$con->prepare("insert into orderan (nama_penerima,kurir,status,alamat,bukti) values (?,?,?,?,?)");
$query->bind_param('sssss',$nama,$kurir,$status,$alamat,$bukti);
$query2=$con->prepare("insert into lokasi_pemesan (id_pesanan,geo_lat,geo_long) values (?,?,?)");
$query2->bind_param('sss',$last_id,$geo_lat,$geo_lng);
$query3=$con->prepare("insert into pesanan (id_pesanan,nama_obat,jumlah) values (?,?,?)");
$query3->bind_param('sss',$last_id,$nama_obat,$jumlah);



for($i=0; $i<count($data) ; $i++) {
	$nama=$data[$i]->nama_instansi;
	$kurir="";
	$status="assign";
	$alamat=$data[$i]->alamat_pemesan;
	$bukti="";
	 $query->execute();
	 $last_id=$query->insert_id;
	 $geo_lat=$data[$i]->geo_lat;
	 $geo_lng=$data[$i]->geo_lng;
	 $query2->execute();
}
for($s=0; $s<count($data2) ; $s++) {
	$nama_obat=$data2[$s]->nama_obat;
	$jumlah=$data2[$s]->jumlah_obat;
	$query3->execute();
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

?>