<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT * FROM orderan where status='assign'");
$query->execute();
$query->bind_result($id,$penerima,$kurir,$status,$alamat,$bukti);

?>
            <div class="page-title">
              <div class="title_left">
                <h3>Pesanan Baru <small>assign courir</small></h3>
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
                          <th style="width: 20%">#Edit</th>
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
							<a href="./index.php?menu=assign-courier&assign-courier=<?=$id ?>" class="btn btn-info btn-xs"><i class="fa fa-user"></i> Assign Courier </a>
                            <a href="#" onClick="if(confirm('Anda yakin HAPUS Pesanan ini? ')){document.location='./config/manage-order.php?op=del-order&del-order=<?=$id ?>'}" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Delete </a>
                          </td>
                        </tr>
						<?php } 
						$query->close();
						$con->close();
						?>
                      </tbody>
                    </table>
					</div>
					
					