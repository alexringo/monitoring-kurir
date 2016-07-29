<?php
session_start();

//cek apakah user sudah login
if(!isset($_SESSION['user'])){
	header( "location:login.php");
}
?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Distributor Obat | </title>

    <!-- Bootstrap -->
    <link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- jVectorMap -->
    <link href="css/maps/jquery-jvectormap-2.0.3.css" rel="stylesheet"/>
    <!-- Select2 -->
    <link href="../vendors/select2/dist/css/select2.min.css" rel="stylesheet">
	<!-- FullCalendar -->
    <link href="../vendors/fullcalendar/dist/fullcalendar.min.css" rel="stylesheet">
    <link href="../vendors/fullcalendar/dist/fullcalendar.print.css" rel="stylesheet" media="print">

    <!-- Custom Theme Style -->
    <link href="css/custom.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a class="site_title"><i class="fa fa-location-arrow"></i> <span>Distributor Obat</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2><?php echo $_SESSION['user']; ?></h2>
              </div>
            </div>
            <!-- /menu profile quick info -->
			
			<div class="clearfix"></div>
            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-users"></i> Manage User <span class="fa fa-chevron-down"></span></a>
				  <ul class="nav child_menu">
                      <li><a href="index.php?menu=manage&manage=kurir">Manage Courier</a>
                      </li>
                      <li><a href="index.php?menu=manage&manage=sales">Manage Sales</a>
                      </li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-truck"></i> Manage Order <span class="fa fa-chevron-down"></span></a>
				  <ul class="nav child_menu">
                      <li><a href="index.php?menu=order&order=assign"><i class="fa fa-bell"></i> Assign Cour</a>
                      </li>
                      <li><a href="index.php?menu=order&order=on-progress"><i class="fa fa-spinner fa-pulse fa-3x fa-fw margin-bottom"></i> On-progress</a></li>
                      <li><a href="index.php?menu=order&order=done"><i class="fa fa-check"></i> Done</a>
                      </li>
                    </ul>
                  </li>
                  <li><a href="index.php?menu=track&track=courier"><i class="fa fa-location-arrow"></i> Track Kurir </a>
                  </li>
                </ul>
              </div>
            </div>
            <!-- /sidebar menu -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">

          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="images/img.jpg" alt=""><?php echo $_SESSION['user']; ?>
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="javascript:;">  Profile</a>
                    </li>
                    <li>
                      <a href="javascript:;">
                        <span class="badge bg-red pull-right">50%</span>
                        <span>Settings</span>
                      </a>
                    </li>
                    <li>
                      <a href="javascript:;">Help</a>
                    </li>
                    <li><a href="./config/koneksi.php?op=out"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                    </li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>

        </div>
        <!-- /top navigation -->


        <!-- page content -->
        <div class="right_col" role="main">

          

          <div class="row">	 
				<?php
				if(isset($_GET['menu'])){
                      
                     switch ($_GET['menu']) {
                                case "map":
                                     include_once './proceed/map.php';
                                     break;
                             }
					  
					  if($_GET['menu'] == "manage"){
                             switch ($_GET['manage']) {
                                case "kurir":
                                     include_once './proceed/kurir.php';
                                     break;
								case "sales":
                                     include_once './proceed/sales.php';
                                     break;
                             }
						}
						else if($_GET['menu'] == "order"){
                             switch ($_GET['order']) {
                                case "assign":
                                     include_once './proceed/assign.php';
                                     break;
								case "on-progress":
                                     include_once './proceed/on-progress.php';
                                     break;
								case "done":
                                     include_once './proceed/done.php';
                                     break;
                             }
						}
						else if($_GET['menu'] == "assign-courier"){
                             switch ($_GET['assign-courier']) {
                                case $_GET['assign-courier']:
                                     include_once './proceed/assign-courier.php';
                                     break;
                             }
						}
						else if($_GET['menu'] == "track"){
                             switch ($_GET['track']) {
								 case "courier":
                                     include_once './proceed/track-courier.php';
                                     break;
                                case $_GET['track']:
                                     include_once './proceed/track.php';
                                     break;
                             }
						}
						} else {
								echo '<div class="col-xs-12 col-sm-12 col-md-12">
									<div class="jumbotron">
										<h1><i class="fa fa-location-arrow"></i> Distributor Obat</h1>
										<p>Halaman administrasi aplikasi Distributor Obat. </br>
										Silahkan pilih menu disamping untuk memulai.</p>
										</div>
									</div>';
									  }
		  ?>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="../vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="../vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->
    <script src="../vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- gauge.js -->
    <script src="../vendors/bernii/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="../vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="../vendors/iCheck/icheck.min.js"></script>
    <!-- Skycons -->
    <script src="../vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="../vendors/Flot/jquery.flot.js"></script>
    <script src="../vendors/Flot/jquery.flot.pie.js"></script>
    <script src="../vendors/Flot/jquery.flot.time.js"></script>
    <script src="../vendors/Flot/jquery.flot.stack.js"></script>
    <script src="../vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="js/flot/jquery.flot.orderBars.js"></script>
    <script src="js/flot/date.js"></script>
    <script src="js/flot/jquery.flot.spline.js"></script>
    <script src="js/flot/curvedLines.js"></script>
    <!-- jVectorMap -->
    <script src="js/maps/jquery-jvectormap-2.0.3.min.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="js/moment/moment.min.js"></script>
    <script src="js/datepicker/daterangepicker.js"></script>
	
	 <!-- Datatables -->
    <script src="../vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
	
    <script src="../vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
    <script src="../vendors/google-code-prettify/src/prettify.js"></script>
    <!-- jQuery Tags Input -->
    <script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
    <!-- Switchery -->
    <script src="../vendors/switchery/dist/switchery.min.js"></script>
    <!-- Select2 -->
    <script src="../vendors/select2/dist/js/select2.full.min.js"></script>
    <!-- jQuery autocomplete -->
    <script src="../vendors/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
	<!-- FullCalendar -->
    <script src="../vendors/fullcalendar/dist/fullcalendar.min.js"></script>
	
	

    <!-- Custom Theme Scripts -->
    <script src="js/custom.js"></script>

    <script>
      $(document).ready(function() {
		  <!-- Datatables -->
        $('#datatable').dataTable({
			"pageLength": 5
		});
		<!-- /Datatables -->
		
		<!-- Select2 -->
		$(".select2_single").select2({
          placeholder: "Pilih",
          allowClear: true
        });
		<!-- /Select2 -->
	});
    </script>
	
	<!-- FullCalendar -->
    <script>
      $(window).load(function() {
        var date = new Date(),
            d = date.getDate(),
            m = date.getMonth(),
            y = date.getFullYear(),
            started,
            categoryClass;

        var calendar = $('#calendar').fullCalendar({
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month'
          }, 
		  contentHeight: "auto" ,
    dayClick: function(date, jsEvent, view, resourceObj) {
        
            window.open("./index.php?menu=map&kurir=<?=$_GET['track']?>&tgl="+date.format(),"_self");
	}
   
        });
      });
    </script>
    
    
 

  </body>
</html>