var baseUrl = 'http://localhost:8080';

var loginBtn = document.getElementById('loginBtn')
loginBtn.onclick = login;
var passwordBox = document.getElementById('password');
passwordBox.onkeydown = enterLogin;

async function enterLogin(){
    if(event.keyCode === 13){
        login();
    }
}

async function login() {



    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    let url = baseUrl + '/users?';
    url += 'username=' + username + '&';
    url += 'password=' + password;

    let response = await fetch(url, {method:'PUT'});

    switch (response.status){
        case 200:
            //redirect to logged in page
            //console.log('login');
            window.location.replace("home.html");
            break;
        case 400:
            console.log('fail');
            let invalidBox = document.getElementById('invalid-box');
            invalidBox.innerHTML = '<div id="badLogin" class="col justify-content-center">Invalid login credentials</div>'
            break;
        default:
            console.log('error');
    }
}