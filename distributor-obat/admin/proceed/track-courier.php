<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT user, no_hp FROM kurir");
$query->execute();
$query->bind_result($kurir,$no_hp);

?>
            <div class="page-title">
              <div class="title_left">
                <h3>Track Kurir </h3>
              </div>
		</div>
<div class="clearfix"></div>
</br>
<div class="col-xs-12 col-sm-12 col-md-12">
<table id="datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>Kurir</th>
						  <th style="width: 40%">No HP</th>
                          <th style="width: 20%">#</th>
                        </tr>
                      </thead>
                      <tbody>
					  						<?php
	while ($query->fetch()){ 
?>
                        <tr>
                          <td>
                            <a><?php echo $kurir; ?></a>
                          </td>
						  <td>
                            <a><?php echo $no_hp; ?></a>
                          </td>
                          <td>
							<a href="./index.php?menu=track&track=<?=$kurir ?>" class="btn btn-info btn-xs"><i class="fa fa-location-arrow"></i> Track </a>
                          </td>
                        </tr>
						<?php } 
						$query->close();
						$con->close();
						?>
                      </tbody>
                    </table>
					</div>
					
					