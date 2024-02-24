console.log("This is a script file");

const toggleSidebar=()=>{
    //sidebar is visible then close it
    //sidebar is closed the open it

    if ($(".sideBar").is(":visible")){
        //close it
        $(".sideBar").css("display","none");
        $(".main-content").css(",margin-left","0%");
    }else{
        //open it
        $(".sideBar").css("display","block");
        $(".main-content").css("margin-left","20%");
    }
}