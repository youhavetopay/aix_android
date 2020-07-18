<?php
header("Content-Type: text/html;charset=UTF-8");
$conn = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");

if(mysqli_connect_errno($conn)){
    echo "Failed to connect to MySQL: ".mysqli_connect_error();

}
$user_id = $_POST["name"];
$user_pw = $_POST["pw"];



mysqli_set_charset($conn,"utf8");

$res = mysqli_query($conn, "SELECT * FROM USERS WHERE USWER_CODE ='".$user_id."' AND USER_PW ='".$user_pw."'") or die(mysqli_error($conn));
$user_result = array();

while($row = mysqli_fetch_array($res)){
    array_push($user_result,
    array('USWER_CODE'=>$row[0], 'USER_PW'=>$row[1], 'USER_NAME'=>$row[2]));
}

if(count($user_result) == 0){
    echo "-1";
}
else{
    echo json_encode(array("user_result"=> $user_result));
}

mysqli_close($conn);

?>
