<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT * FROM orderan where status='done'");
$query->execute();
$query->bind_result($id,$penerima,$kurir,$status,$alamat,$bukti);

?>
            <div class="page-title">
              <div class="title_left">
                <h3>Pesanan Selesai</h3>
              </div>
		</div>
<div class="clearfix"></div>
</br>
<div class="col-xs-12 col-sm-12 col-md-12">
<table id="datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>Pemesan</th>
						  <th style="width: 40%">Alamat</th>
                          <th style="width: 20%">Kurir</th>
						  <th style="width: 10%">Foto</th>
                        </tr>
                      </thead>
                      <tbody>
					  						<?php
	while ($query->fetch()){ 
?>
                        <tr>
                          <td>
                            <a><?php echo $penerima; ?></a>
                          </td>
						  <td>
                            <a><?php echo $alamat; ?></a>
                          </td>
                          <td>
							<a><?php echo $kurir; ?></a>
                          </td>
						  <td>
							<a href="<?php echo $bukti; ?>" target="_blank" class="btn btn-info btn-xs"><i class="fa fa-image"></i> Foto</a>
                          </td>
                        </tr>
						<?php } 
						$query->close();
						$con->close();
						?>
                      </tbody>
                    </table>
					</div>
					
					