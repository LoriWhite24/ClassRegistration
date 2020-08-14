/*********
AJAX call functions
Author: Tara Kelly
Provide logic for retrieving needed data from our API's
***********/

function validateLogin(){

    let email = txtEmail.value; //"student1@gmail.com"
    let password = txtPassword.value; //"password1"

    var url = `api/students/login/username/${email}/password/${password}`;
    var xhttpList = new XMLHttpRequest();
    
    var student;
    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            localStorage.setItem("student", this.responseText);
            console.log(this.responseText);
            //student = getStudentById(id);
            alert("Valid login");
            window.location.href = "./registration.html";

        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();

    // do I need to keep returning the student? or can I access it from anywhere in page?
    // can I return it here and pass to next webpage?
    // return sessionStorage.getItem("student");  
}

function getLoggedInStudent(){
    // you have to parse the student from local storage each time you get it
    let studentStr = localStorage.getItem("student");  
    if(studentStr == "" || studentStr == null){
        return "";
    }
    let student = JSON.parse(studentStr);

    return student;
}

function logOut(){

    // need to clear both student obj in local storage and courses/registrations in session storage
    localStorage.clear();
    sessionStorage.clear();
}

//AJAX call for student using app (for now assume they are logged in)
// for now this is for viewing how data looks in console
function getStudentById(id){

    var url = "/api/students/" + id;
    var xhttpList = new XMLHttpRequest();
    var student;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("student", this.responseText); // this code would depend on what this function is used for
            console.log(this.responseText)
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Student received");  

    return sessionStorage.getItem("student");

}


// AJAX calls for courses
function getCourses(url){

    // url to pass in for all courses: "/api/courses"
    // don't hardcode url in this function b/c will reuse it in other functions
    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("courses", this.responseText);
        }
    };

    xhttpList.open("GET", url, true);
    xhttpList.send();
    return sessionStorage.getItem("courses");
}

// may or may not need this function - this was for updating
function getCourseById(id){

    var url = "/api/courses/" + id;
    var xhttpList = new XMLHttpRequest();
    var course;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("course", this.responseText); // this code would depend on what this function is used for
            //console.log(this.responseText);
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Course received");  

    return sessionStorage.getItem("course");
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
// will I need this function to do withdraw?
function getRegistrationById(){

    var url = "/api/registration/" + id;
    var xhttpList = new XMLHttpRequest();
    var registration;

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            sessionStorage.setItem("registration", this.responseText); 
            //console.log(this.responseText);
        }
    };
    xhttpList.open("GET", url, false);
    xhttpList.send();
    console.log("Registration received");  

    return sessionStorage.getItem("registration");
}

function getRegistrationsForStudent(id){

    let url = "/api/registration/student/" + id;
    var xhttpList = new XMLHttpRequest();

    xhttpList.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
           console.log(this.responseText);
           sessionStorage.setItem("registrations", this.responseText);
        }
    };
    xhttpList.open("GET", url, true);
    xhttpList.send();
    console.log("Student's Registrations received");  
    return sessionStorage.getItem("registrations");
    
}

function addRegistration(courseId){

    // hard-coding student id again for now
    //let studentId = 1;

    // you have to parse the student from local storage each time you get it
    let student = getLoggedInStudent();

    let newRegistration = {
        "id": -1,
        "registrationDate": (new Date()).toJSON(),
        "hasWithdrawn": false,
        "studentId": student.id,
        "courseId": courseId
    }
   
    sendPostRegistration(newRegistration);
}

function sendPostRegistration(sendData){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){
        // if the POST request went through,
        // the student is alerted and the page is redirected to My Courses page w/ refreshed data
        if(this.readyState == 4 && this.status == 201){
            alert("Registration successful!");
            window.location.href = "./view-registrations.html";
        }else if(this.status == 409){
            alert("You have already registered for this course");
        }
    };

    xhttp.open("POST", "/api/add/registration", true);
    xhttp.setRequestHeader('Content-type', 'application/json');
    xhttp.send(JSON.stringify(sendData));
}

/******
 * withdraws a registration for a student
 * can also re-enroll a previously withdrawn registration
 * (param) : id of student, id of course, boolean (true = withdraw)
 ******/
function withdrawOrReEnroll(courseId, studentId, value){

   
    let url = "api/update/registration/has_withdrawn";

    var sendData = {
        studentId: studentId,
        courseId: courseId,
        hasWithdrawn: value
    };
    console.log(sendData);


    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function(){
        // if the POST request went through,
        // the student is alerted and the page is redirected to My Courses page w/ refreshed data
        // TODO: the button displayed should change based on status
        if(this.readyState == 4 && this.status == 202){
            alert("Updated Registration successfully!");
            console.log(this.responseText);
            //TODO: reloading page not working - reloading cached version
            // better to grab the button element and update it
            //window.location.href = "./view-registrations.html";
            // this supposedly fixes cache issues, but not working
            window.location.reload(true);
        }
    };

    xhttp.open("PATCH", url, true);
    xhttp.setRequestHeader('Content-type', 'application/json');
   
    xhttp.send(JSON.stringify(sendData));

}

/***********
Render HTML functions
Author: Tara Kelly
Rendering portions of HTML with data from our database
***********/

function renderCourseTableRows(){
   
    let courseList = getCourses("/api/courses/");
    let jsonArray = JSON.parse(courseList);

    let rows = "";
    // the last cell in each table row will be a Register button
    // the id of the button will be "register-" + the id of the course, i.e. "register-1"
    // we can then pull the course id when someone clicks the Register button
    jsonArray.forEach(json => {
        rows +=
        `<tr>
            <td>${json.id}</td>
            <td>${json.department}</td>
            <td>${json.name}</td>
            <td>${json.noCredits}</td>
            <td><button class="btn btn-primary" id="register-${json.id}" onclick="addRegistration(${json.id})">Register</button></td>
        </tr>`;
    });
 
    document.getElementById("courseTable").innerHTML = rows;

}

/******
 * renders table rows of registered courses for a student
 * (param) : id of student 
 * uses student id to get list of registrations
 * parses list of registrations to get each course object
 * displays course object in a table row 
 ******/
function renderRegisteredCourses(){

    // resetting table 
    let table = document.getElementById("registeredCoursesTable");
    table.innerHTML = "";

    let student = getLoggedInStudent();
    // this prevents an error in console when accessing the My Courses page w/out being logged in
    // could be handled differently but OK for now
    if(student == ""){
        return;
    }
    let studentId = student.id;

    let registrations = getRegistrationsForStudent(studentId);
    if(registrations == null){
        return;
    }

    let jsonArray = JSON.parse(registrations);

    let rows = "";
    var btnDisplay;

    for(var index = 0; index < jsonArray.length; index++){

        let registration = jsonArray[index];
        
        // try using registration id next
        //let registrationId = registration.id;
        let course = getCourseById(registration.courseId);
        let json = JSON.parse(course);

        if(registration.hasWithdrawn == false){
            //btnName = "withdraw";
            btnDisplay = `<button class="btn btn-danger" id="withdraw-${json.id}" onclick="withdrawOrReEnroll(${json.id}, ${studentId}, true)">Withdraw</button>`;
        }else if(registration.hasWithdrawn == true){
            //btnName = "reenroll";
            btnDisplay = `<button class="btn btn-primary" id="reenroll-${json.id}" onclick="withdrawOrReEnroll(${json.id}, ${studentId}, false)">ReEnroll</button>`;
        }

        rows +=
        `<tr>
            <td>${json.id}</td>
            <td>${json.department}</td>
            <td>${json.name}</td>
            <td>${json.noCredits}</td>
            <td>
                ${btnDisplay}
            </td>
        </tr>`;
    }

    table.innerHTML = rows;

}

function drawButton(buttonName){
    if(buttonName == "withdraw"){
        return `<button class="btn btn-danger" id="withdraw-${json.id}" onclick="withdrawOrReEnroll(${json.id}, ${studentId}, true)">Withdraw</button>`;
    }else if(buttonName == "reenroll"){
        return `<button class="btn btn-primary" id="reenroll-${json.id}" onclick="withdrawOrReEnroll(${json.id}, ${studentId}, false)">ReEnroll</button>`;
    } 
}


