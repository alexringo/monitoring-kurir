<?php
			define('HOST','localhost');
			define('USER','root');
			define('PASS','');
			define('DB','cour_monitor');
 
 $con = new mysqli(HOST,USER,PASS,DB);
 $conect = mysqli_connect(HOST,USER,PASS,DB) or die('unable to connect to db');
?>