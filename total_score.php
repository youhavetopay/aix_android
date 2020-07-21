<?php
    $conn = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");
    mysqli_query($conn, 'SET NAMES utf8');

    if(mysqli_connect_errno($con)){
        echo "Failed to connect to MySQL: ".mysqli_connect_error();
    
    }

    $USER_ID = $_POST["name"];
    
    
    $res = "SELECT SUM(STEMP_SCORE) FROM STEMP WHERE STEMP_CODE IN (SELECT STEMP_CODE FROM USERS2_GET_STEMP WHERE USER_ID= '".$USER_ID."' )";

    $row = mysqli_query($conn, $res);

    while($data = mysqli_fetch_assoc($row)){
        $d = $data;

    }

    echo json_encode($d);

    mysqli_close($conn);
    
?>
