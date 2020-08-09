// AJAX calls for courses
function getCourses(url){

    // url to pass in for all courses: "/api/courses"
    // don't hardcode url in this function b/c will reuse it in other functions
    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            // TODO: hook up to webpage
            //renderCourseList(this.responseText);  // this is an example- depends on how Thien handles display
        }
    };
    xhttpList.open("GET", url, true);
    xhttpList.send();
    console.log("Courses received");  
}

// may or may not need this function - this was for updating
function getCourseById(id){

    var url = "/api/courses/" + id;
    var xhttpList = new XMLHttpRequest();
    var course;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            //sessionStorage.setItem("course", this.responseText); // this code would depend on what this function is used for
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Restaurant received");  

    return sessionStorage.getItem("restaurant");
}

function getCoursesByDepartment(dept){

    // TODO: hook this up to web page
    // this is an example of how to reset display of course List
    // var courseList = document.getElementById("courseList");
    // courseList.innerHTML = '';
    let url = "/api/courses/department" + dept;  
    getCourses(url);
}

function getCourseByName(name){
    
    //TODO: reset display or redirect to diff page
    let url = "/api/courses/name" + name;
    getCourses(url);
}

// AJAX calls for Registrations
function addRegistration(){

    // TODO: need to pull send data from interface - student id and course id, etc.
    // let sendData = ....
    sendPostRegistration(sendData);
}

function sendPostRegistration(sendData){

    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/api/add/registration", true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.onreadystatechange = function(){
        // if the POST request went through, all we need to do is update HTML components
        if(this.readyState == 4 && this.status == 200){
            var display = document.getElementById("registrations");
            display.innerHTML = '';
            getRegistrations("/api/registration");
            console.log("Registration created!");
        }
    };
    xhttp.send(JSON.stringify(sendData));
}