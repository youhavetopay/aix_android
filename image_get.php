<?php

    $db = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm"); //keep your db name
    
    $id = 4;
    
    $query= "SELECT image_path FROM USER_IMAGE WHERE ID=$id" ; 

    $row = mysqli_query($db, $query);

    while($data = mysqli_fetch_assoc($row)){
        
        echo $data['image_path'];

        $test = $data['image_path'];

    }

    echo "<img src=$test />";

    mysqli_close($db);

?>