<?php 
require_once('./config/conection.php');

$query = $con->prepare("SELECT * FROM orderan where id_pesanan=?");
$query2 = $con->prepare("SELECT * FROM kurir");
$query->bind_param('i',$_GET['assign-courier']);
$query->execute();
$query->bind_result($id,$penerima,$kurir,$status,$alamat,$bukti); 


?>
<div class="page-title">
              <div class="title_left">
                <h3>Tentukan Kurir</h3>
              </div>
		</div>
		<div class="clearfix" ></div>
		</br>
		
<div class="col-xs-12 col-sm-12 col-md-6">
<form class="form-horizontal form-label-left" action="config/manage-order.php?op=assign-courier" method="post" >
<?php
	while ($query->fetch()){ 
?>
<input type="hidden" name="id_order" value="<?php echo $id ?>" />
<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Penerima</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" class="form-control" readonly="readonly" placeholder="<?php echo $penerima ?>" value="<?php echo $penerima ?>">
                        </div>
                      </div>
	<?php }
		$query->close();
	?>
	
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Pilih Kurir</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select name="user_kurir" class="select2_single form-control" tabindex="-1">
                            <option></option>
							<?php 
$query2->execute();
$query2->bind_result($id_kurir,$user_kurir,$hp,$gcm);
	while ($query2->fetch()){ 

	?>
	<option value="<?php echo $user_kurir ?>"><?php echo $user_kurir ?></option>
	<?php } ?>
                          </select>
                        </div>
                      </div>
					  <div class="form-group">
					  <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
					  <button type="submit" class="btn btn-success">Simpan</button>
					  </div>
					  </div>
</form>

</div>