<?php
    $conn = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");
    mysqli_query($conn, 'SET NAMES utf8');

    $USWER_CODE = $_POST["name"];
    $USER_PW = $_POST["pw"];
    
    $statement = mysqli_prepare($conn, "SELECT * FROM USERS2 WHERE USER_ID = ? AND USER_PW = ?");
    mysqli_stmt_bind_param($statement, "ss", $USWER_CODE, $USER_PW);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $USWER_CODE, $USER_PW, $USER_NAME);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["USWER_CODE"] = $USWER_CODE;
        $response["USER_PW"] = $USER_PW;
        $response["USER_NAME"] = $USER_NAME;
    }

    echo json_encode($response);

    
?>
