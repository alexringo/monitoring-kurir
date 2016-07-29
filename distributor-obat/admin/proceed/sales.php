<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT * FROM user where level=2");
 
$query->execute();
$query->bind_result($id,$use,$passe,$level);
?>

<div class="col-xs-12 col-sm-12 col-md-12">
<button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-user-plus"></i> Tambah Sales</button>

                  <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">

                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span>
                          </button>
                          <h4 class="modal-title" id="myModalLabel">Tambah Sales</h4>
                        </div>
                        <div class="modal-body">
                          <form class="form-horizontal form-label-left" action="config/manage-user.php?op=add-sales" method="post" >

                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="name" class="form-control col-md-7 col-xs-12" name="username" placeholder="username" required="required" type="text">
                        </div>
                      </div>
                      <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="password">Password <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input id="password" class="form-control col-md-7 col-xs-12" name="password" placeholder="password" required="required" type="password">
                        </div>
                      </div>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          <button type="submit" class="btn btn-success">Tambah</button>
                        </div>
</form>
                      </div>
                    </div>
                  </div>

</div>
<div class="clearfix"></div>
</br>

<div class="col-xs-12 col-sm-12 col-md-12">
<table id="datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th style="width: 80%">Daftar Sales</th>
                          <th style="width: 20%">#Edit</th>
                        </tr>
                      </thead>
                      <tbody>
					  						<?php
	while ($query->fetch()){ 
?>
                        <tr>
                          <td>
                            <a><?php echo $use; ?></a>
                          </td>
                          <td>
                            <a href="#" class="btn btn-primary btn-xs"><i class="fa fa-folder"></i> View </a>
                            <a href="<?php echo $use;?>" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>
                            <a href="#" onClick="if(confirm('Anda yakin HAPUS Sales ini? ')){document.location='./config/manage-user.php?op=del-sales&del-sales=<?=$use ?>'}" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> Delete </a>
                          </td>
                        </tr>
						<?php } 
						$query->close();
						$con->close();?>
                      </tbody>
                    </table>
					</div>