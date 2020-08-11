// All javascript functions are located in this one file
// Please note that you can use multiple .js files in your own projects
// as long as you link them in your header.

// We use AJAX to do all of our API calls to link through our API and DB
// Cards are rendered dynamically for each of our tuples in or db table
// and we render modals dynamically to display our update and create forms.

// The initialize function is linked to our html body, using the onload attribute.
// This means that when the HTML body is loaded, the .initialize() function activates.
function initialize() {
    // Our get students function also does our rendering of all our cards, by calling the renderStudent() function.
    getStudents('/api/students');
    // This render Modal call create our button and modal for creating a student at page onload.
    renderModal('createStudent', 'modals');
    renderModal('updateStudent');
  }
  
  // AJAX call and rendering using rederStudent() inside
  function getStudents(url) {
    //make initial api call to get Student list
    var xhttpList = new XMLHttpRequest();
  
    // Read JSON - and put in storage
    xhttpList.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        renderStudent(this.responseText);
      }
    };
    xhttpList.open('GET', url, true);
    xhttpList.send();
    console.log('Student list stored');
  }
  
  // We need this single student AJAX call to get API data when we update the student
  function getOneStudent(id) {
    var url = '/api/students/' + id;
    //make initial api call to get Student list
    var xhttpList = new XMLHttpRequest();
    var student;
  
    // Read JSON - and put in storage
    xhttpList.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        sessionStorage.setItem('student', this.responseText);
      }
    };
    xhttpList.open('GET', url, false);
    xhttpList.send();
    console.log('Single student retrieved');
  
    return sessionStorage.getItem('student');
  }
  
  // This function renders all the students as we receive from our AJAX call
  function renderStudent(data) {
    // var students = document.getElementById('students');
  
    var json = JSON.parse(data);
  
    // Ajax returns an array of JSON objects - the index represents each individual JSON object from our AJAX call
    // We can the iterate over all of our students
    for (var index = 0; index < json.length; index++) {
      var cardHtml =
        // '<div class="card bg-primary"  style="width:400px">' +
        // '<img class="card-img-top" src= https://thumbs.dreamstime.com/z/happy-university-college-student-thumbs-up-15010463.jpg alt="Card image" style="width:100%">' +
        // '<div class="card-body">' +
        // '<h4 class="card-title">' +
        // json[1].firstName +
        // ' ' +
        // json[1].lastName +
        // '</h4>' +
        // '<p class="card-text">Description Here</p>' +
        // '<a href="#" class="btn btn-warning">DELETE</a>' +
        // '</div>' +
        // '</div>' +
        // '<div class="card bg-success"  style="width:400px">' +
        // '<img class="card-img-top" src= https://thumbs.dreamstime.com/z/young-doctor-surgeon-medical-student-putting-sterile-gloves-isolated-white-40261822.jpg alt="Card image" style="width:100%">' +
        // '<div class="card-body">' +
        // '<h4 class="card-title">' +
        // json[2].firstName +
        // ' ' +
        // json[2].lastName +
        // '</h4>' +
        // '<p class="card-text">Description Here</p>' +
        // '<a href="#" class="btn btn-warning">DELETE</a>' +
        // '</div>' +
        // '</div>' +
        // '<div class="card bg-info"  style="width:400px">' +
        // '<img class="card-img-top" src= https://thumbs.dreamstime.com/z/medical-student-young-doctor-17615432.jpg alt="Card image" style="width:100%">' +
        // '<div class="card-body">' +
        // '<h4 class="card-title">' +
        // json[3].firstName +
        // ' ' +
        // json[3].lastName +
        // '</h4>' +
        // '<p class="card-text">Description Here</p>' +
        // '<a href="#" class="btn btn-warning">DELETE</a>' +
        // '</div>' +
        // '</div>' +
        // '<div class="card bg-secondary"  style="width:400px">' +
        // '<img class="card-img-top" src= https://thumbs.dreamstime.com/z/young-attractive-medical-student-12667479.jpg alt="Card image" style="width:100%">' +
        // '<div class="card-body">' +
        // '<h4 class="card-title">' +
        // json[4].firstName +
        // ' ' +
        // json[4].lastName +
        // '</h4>' +
        // '<p class="card-text">Description Here</p>' +
        // '<a href="#" class="btn btn-warning">DELETE</a>' +
        // '</div>' +
        // '</div>' +
        '<div class="card bg-primary" style="width:400px" id="' +
        json[index].id +
        '">' +
        '<div class = "card-header"><h3>' +
        json[index].firstName +
        ' ' +
        json[index].lastName +
        '</h3>' +
        '<img class"card=img-top" src=' +
        json[index].imagePath +
        ' alt="Card image" style="width:100%">' +
        'div class="card-body' +
        '<h4 class="card-title">' +
        json[index].email +
        '</h4>' +
        '<p class="card-text">' +
        json[index.firstName] +
        ' ' +
        json[index.lastName] +
        ' is in the ' +
        json[index].department +
        ' department ' +
        '</p>' +
        '<div class="card-footer btn-group">' +
        '<button class="btn btn-danger" onclick="deletestudent(' +
        json[index].id +
        ')">DELETE</button' +
        '<button class="btn btn-warning">UPDATE</button>' +
        '/div' +
        '/div' +
        '/div' +
        '/div';
  
      // We create a card deck that will dictate our groupings of cards
      var cardDeck;
      if (index % 4 == 0) {
        cardDeck = document.createElement('div');
        cardDeck.classList.add('card-deck');
        cardDeck.id = 'deck' + index;
        document.getElementById('students').appendChild(cardDeck);
        // cardDeck.id = 'index';
        students.appendChild(cardDeck);
      }
    }
  
    cardDeck.insertAdjacentHTML('beforeend', cardHtml);
    // Once the student cards are created and rendered on our page, we can then find them and add on the update buttons with associated modals
    renderModal('updateStudent', json[index].id);
  
    //   document.getElementById('students').insertAdjacentHTML('beforeend', cardHtml);
  }
  
  // This function renders the buttons and modals for our Create and Update students, and calls the renderForm() function that conatains the form data in
  // a string version of HTML to be rendered.
  function renderModal(modalPurpose) {
    var modalHtml =
      '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#' +
      modalPurpose +
      '">Open modal</button>' +
      ' <div class="modal fade" id="' +
      modalPurpose +
      '"> ' +
      ' <div class="modal-dialog modal-xl"> ' +
      ' <div class="modal-content"> ' +
      '<div class="modal-header">' +
      '<h4 class="modal-title">Modal Heading</h4>' +
      '<button type="button" class="close" data-dismiss="modal">&times;</button>' +
      '</div>' +
      '<div class="modal-body">' +
      'Modal body..' +
      '</div>' +
      '<div class="modal-footer">' +
      '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>' +
      '</div>';
  
    document.getElementById('modals').insertAdjacentHTML('beforeend', modalHtml);
  }
  
  function createStudent() {
    var sendData = {
      firstName: document.getElementById('createStudentfirstName').value,
      lastName: document.getElementById('createStudentlastName').value,
      email: document.getElementById('createStudentemail').value,
      imagePath: document.getElementById('createStudentimagePath').value,
      department: document.getElementById('createStudentdepartment').value,
    };
    console.log(sendData);
  
    var ok = confirm('Ready to send?');
  
    if (ok == true) {
      var xhttp = new XMLHttpRequest();
      xhttp.open('POST', '/api/student', true);
      xhttp.setRequestHeader('Context-Type', 'application/json');
      xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
          console.log('Update Success');
          var display = document.getElementById('students');
          display.innerHTML = '';
          getStudents('/api/students');
          console.log('student created');
        }
      };
      xhttp.send(JSON.stringify(sendData));
    }
  }
  
  function deleteStudent(id) {
    var link = '/api/delete/student/' + id;
  
    var ok = confirm(
      "Are you sure you want to delete this student?\nPress 'ok' to continue, or 'cancel' to abort"
    );
  
    if (ok == true) {
      var xhttp = new XMLHttpRequest();
      xhttp.open('DELETE', link, true);
  
      xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
          var removeCard = document.getElementById(id);
  
          removeCard.parentNode.removeChild(removeCard);
          console.log('Student deleted');
        }
      };
      xhttp.send(null);
    }
  }
  
  function updateStudent() {
    console.log('entered the update function');
  }
  