<?php
	$array1=array();
	
	$test1['alamat']="vsvs";
	$test1['nama']="RS";
	echo $test1;
	array_push($array1,$test1);
	echo json_encode($array1);

?>