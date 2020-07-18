<?php
$con = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");

if(mysqli_connect_errno($con)){
    echo "Failed to connect to MySQL: ".mysqli_connect_error();

}

mysqli_set_charset($con,"utf8");

$res = mysqli_query($con, "select * from USERS");
$user_result = array();

while($row = mysqli_fetch_array($res)){
    array_push($user_result,
    array('code'=>$row[0], 'pw'=>$row[1], 'name'=>$row[2]));
}

echo json_encode(array("user_result"=> $user_result));

mysqli_close($con);

?>