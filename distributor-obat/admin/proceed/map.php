<?php
require_once('./config/conection.php');
$query = $con->prepare("SELECT * FROM track where kurir=? and tanggal=?");
$query -> bind_param('ss',$_GET['kurir'],$_GET['tgl']);
$query->execute();
$query->store_result();
$query->bind_result($id,$id_pesanan,$lat,$lng,$kurir,$tanggal);


?>
<div class="page-title">
              <div class="title_left">
                <h3>Track Kurir </h3>
              </div>
		</div>
<div class="clearfix"></div>
</br>
<div class="col-xs-12 col-sm-12 col-md-12">

<?php if($query->num_rows > 0) { ?>

<div class="map_container">
    <div id="map_canvas" class="map_canvas"></div>
</div>
<script>
function initialize() {
	
	var bounds = new google.maps.LatLngBounds();
	var center = null;
	var track = [ ];
	
  var map = new google.maps.Map(document.getElementById('map_canvas'), {
          zoom: 13,
          center: {lat: -7.257472, lng: 112.752088},
          mapTypeId: google.maps.MapTypeId.TERRAIN
        });

        var koordinat = [
		<?php
	while ($query->fetch()){ 
?>
		{lat: <?php echo $lat; ?>, lng: <?php echo $lng; ?>},
	<?php } ?>
        ];
		
		
		for(var i=0; i<koordinat.length;i++)
		{
			track.push(new google.maps.LatLng(koordinat[i].lat,koordinat[i].lng));
		}
		
		for(var i=0; i<track.length;i++)
		{
			bounds.extend(track[i]);
		}
		
        var flightPath = new google.maps.Polyline({
          path: track,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2,
		  map: map
        });
		
		var startMarker = new google.maps.Marker({
		position:flightPath.getPath().getAt(0), 
		map:map,
		icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
		title: 'Start'
		});
		var endMarker =  new google.maps.Marker({
		position:flightPath.getPath().getAt(flightPath.getPath().getLength()-1), 
		map:map,
		icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',
		title: 'End'
		});
		
		
		
        flightPath.setMap(map);
		center = bounds.getCenter();
		map.fitBounds(bounds);
		
		}

function loadScript() {
  var script = document.createElement('script');
  script.type = 'text/javascript';
  script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBRRdEAdM4flZoHR7ezIyvTmbwHDWSOu98&callback=initialize';
  document.body.appendChild(script);
}

window.onload = loadScript;
</script>
 
<?php } else {?>

<div class="alert alert-warning alert-dismissible fade in" role="alert">
                    <strong>Tidak ada data</strong> Cari tanggal lainnya.
                  </div>

<?php } ?>

					</div>