<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT * FROM track where kurir=?");
$query -> bind_param('s',$_GET['track']);
$query->execute();
$query->bind_result($id,$id_pesanan,$lat,$long,$kurir,$tanggal);

?>

<div class="page-title">
              <div class="title_left">
                <h3>Track Kurir <small>berdasarkan tanggal</small></h3>
              </div>
		</div>
		<div class="clearfix" ></div>
		</br>
		
<div class="col-xs-12 col-sm-12 col-md-12">

                    <div id='calendar' ></div>


</div>