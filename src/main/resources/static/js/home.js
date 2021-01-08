var baseUrl = 'http://localhost:8080';
var user = null;
var mainContentDiv;
var claimsDiv;
var newClaimDiv;
var claims;
var events;

var rfcList;

checkLogin().then(showClaims);

async function checkLogin() {
    let url = baseUrl + '/users';
    let response = await fetch(url);
    if (response.status === 200) {
        user = await response.json();
        mainContentDiv = document.getElementById('mainContent');
        //console.log(user);
    } else if (response.status === 400) {
        console.log('no logged in user');
    }
}

async function logout(){
    user = null;
    claims = null;
    events = null;
    fetch(baseUrl + '/users', {method: 'DELETE'});
    window.location.replace(baseUrl);
}

//setup the html for showing claims
async function showClaims(){
    mainContentDiv.innerHTML = `<div id="newClaimDiv">
                                </div>

                                <br>

                                <div class="container">
                                    <div class="row">
                                        <div class="col justify-content-center">
                                            <h1>Active Claims</h1>
                                        </div>
                                    </div>
                                </div>

                                <br>

                                <div id="claimsDiv" class="container">

                                    <div class="container">
                                        <div class="row">
                                            <div class="col justify-content-center">
                                                <h2>Urgent Claims</h2>
                                            </div>
                                        </div>
                                    </div>

                                    <br>

                                    <div id="urgentClaimsDiv" class="container"></div>

                                    <br>

                                    <div class="container">
                                        <div class="row">
                                            <div class="col justify-content-center">
                                                <h2>Future Claims</h2>
                                            </div>
                                        </div>
                                    </div>

                                    <br>

                                    <div id="futureClaimsDiv" class="container"></div>

                                    <br>

                                    <div class="container">
                                        <div class="row">
                                            <div class="col justify-content-center">
                                                <h2>Archived Claims</h2>
                                            </div>
                                        </div>
                                    </div>

                                    <br>

                                    <div id="archivedClaimsDiv" class="container"></div>
                                    
                                </div>
                                
                                <div id="commentsDiv">
                                </div>`;
    getClaimsList();
}

async function getClaimsList() {
    if (user.role.id == 4) {
        //Employee
        //allow for new claims to be made
        newClaimDiv = document.getElementById("newClaimDiv");

        //show all claims made
        //fetch all claims by this user
        let url = baseUrl + '/claims/person/' + user.id;
        let response = await fetch(url);

        //update the html
        if (response.status === 200) {
            claims = await response.json();
            updateClaims();
        } else if (response.status === 400){
            console.log("failed");
        }
    } else if (user.role.id <= 3) {
        //Direct Supervisor
        //show all claims made that have been approved by no one
        
        //fetch all claims that have not been approved by the direct supervisor
        let url = baseUrl + '/claims/';

        if(user.role.id == 3){
            url += 'ds';
        } else if(user.role.id == 2){
            url += 'dh';
        } else if(user.role.id == 1){
            url += 'bc';
        }

        let response = await fetch(url);

        //update the html
        if (response.status === 200) {
            claims = await response.json();
            updateClaims();
        } else if (response.status === 400){
            console.log("failed");
        }
    }
}

async function updateClaims() {
    currentClaimID = null;
    claimsDiv = document.getElementById('claimsDiv');
    //claimsDiv.innerHTML = '';
    //clear comments div
    //document.getElementById('commentsDiv').innerHTML = '';
    let urgentClaimsDiv = document.getElementById('urgentClaimsDiv');
    urgentClaimsDiv.innerHTML = '';
    let futureClaimsDiv = document.getElementById('futureClaimsDiv');
    futureClaimsDiv.innerHTML = '';
    let archivedClaimsDiv = document.getElementById('archivedClaimsDiv');
    archivedClaimsDiv.innerHTML = '';

    newClaimDiv = document.getElementById('newClaimDiv');
    newClaimDiv.innerHTML = '';
    if(user.role.id == 4){
        let newClaimHTML = `<div id="makeAClaim" class="container" onclick="makeNewClaim()">
                                <div class="row">
                                    <div class="col justify-content-center">
                                        <h2 class="titleText">Make a claim</h2>
                                    </div>
                                </div>
                            </div>`;
        newClaimDiv.innerHTML = newClaimHTML;
    }    

    for (let i in claims) {
        //console.log(claims[i]);


        let claim = claims[i];
        let date = claim.eventDate;
        let claimHTML = `<div id="claim${claim.id}" class="container justify-content-center claim" onclick="viewClaimDetails(${i})">
            <div class="row claimTitleDate">
                <div class="col" >
                    <h2>${claim.title}</h2>
                </div>
                <div class="col" >
                    <h4 class="claimDate">${date.dayOfWeek} ${date.monthValue}/${date.dayOfMonth}/${date.year}</h4>
                </div>
            </div>

            <div class="row">
                <div class="col-1 statusCol" >
                    <h4>Status:</h4>
                </div>
                <div class="col-6 statusCol" >
                    <h4>${claim.approvalStage.name}</h4>
                </div>
            </div>`;
        //console.log();
        if(claim.approvalStage.id == 7 || claim.approvalStage.id == 0){
            archivedClaimsDiv.innerHTML += claimHTML;
        } else if(claim.isUrgent){
            urgentClaimsDiv.innerHTML += claimHTML;
        } else {
            futureClaimsDiv.innerHTML += claimHTML;
        }
    }
}

async function viewClaimDetails(index) {
    //console.log(id);
    let claim = claims[index];
    let date = claim.eventDate;

    let claimHTML = `<div id="claim${claim.id}" class="container justify-content-center claim">
                        <div class="row claimTitleDate">
                            <div class="col" >
                                <h2>${claim.title}</h2>
                            </div>
                            <div class="col" >
                                <h4 class="claimDate">${date.dayOfWeek} ${date.monthValue}/${date.dayOfMonth}/${date.year}</h4>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-1 statusCol">
                                <h4>Status:</h4>
                            </div>
                            <div class="col-10 statusCol">
                                <h4>${claim.approvalStage.name}</h4>
                             </div>
                             <div class=col-1 statusCol">
                                <h4>$${claim.price}</h4>
                             </div>
                        </div>

                        <div class="row">
                            <div id="description" class="col justify-content-center">
                                <h6>${claim.description}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Justification: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.justification}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Location: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.eventLocation}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Passing percentage: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.grading.passingPercentage}%</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Passing letter: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.grading.passingLetter}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Type: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.event.eventType}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Percentage covered: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.event.percentageCovered}</h6>
                            </div>
                        </div>

                        <div id="amountCoveredRow" class="row">
                            <div class="col justify-content-left">
                                <h6>Estimated amount covered: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6 id="estiamtedAmountText">$${claim.estimatedAmount}</h6>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col justify-content-left">
                                <h6>Hours missed: </h6>
                            </div>
                            <div class="col justify-content-center">
                                <h6>${claim.hoursMissed}</h6>
                            </div>
                        </div>

                    `;//</div>
    if(claim.approvalStage.id == 0){
        let deinalReasonHTML = `<div class="row">
                                    <div class="col justify-content-left">
                                        <h6>Denial reason: </h6>
                                    </div>
                                    <div class="col justify-content-center">
                                        <h6>${claim.denialReason}</h6>
                                    </div>
                                </div>`;
    }

    //list all the uploaded files
    let response = await fetch(baseUrl + '/attachment/' + claim.id);
    if(response.status === 200){
        let fileNameArray = await response.json();

        let fileNamesHeader = `<div class="row">
                                    <div class="col justify-content-center">
                                        <h3 class="titleText">Attached Files</h3>
                                    </div>
                                </div>`;
        claimHTML += fileNamesHeader;

        for(let i in fileNameArray){
            let fileURL = baseUrl + '/attachment/' + claim.id + '/download/' + fileNameArray[i];
            let fileNameHTML = `<div class="row">
                                    <div class="col">
                                        <a download="${fileNameArray[i]}" href="${fileURL}">${fileNameArray[i]}</a>
                                    </div>
                                </div>`;
            claimHTML += fileNameHTML;
        }
    } else if(response.status === 204){
        //do nothing
    } else {
        alert('Could not load uploaded files');
    }

    if(user.role.id == 4){
        attachmentDiv = `<div id="attachmentDiv" class="row">
                            <div class="col justify-content-center">
                                <iframe name="dummyframe" id="dummyframe" style="display: none;"></iframe>

                                <form action="attachment/${claim.id}" method="post" enctype="multipart/form-data" target="dummyframe">
                                    <div class="col justify-content-left">
                                        <h6>Attach file:<h6>
                                    </div>
                                    <div class="col justify-content-left">
                                        <input id="fileInput" name="files" type="file" multiple>
                                    </div>
                                    <div class="col justify-content-left">
                                        <button id="uploadButton" type="button" onclick="uploadFiles(${index})">Upload</button>
                                    </div>
                                </form>
                            </div>
                        </div>`;
        claimHTML += attachmentDiv;

        if(claim.approvalStage.id != 0){
            let cancelHTML = `<div class="row">
                                    <div class="col">
                                        <button id="cancelButton" onclick="cancelClaim(${claim.id})">Cancel Claim</button>
                                    </div>
                                </div>`;
            claimHTML += cancelHTML;
        }

        if(claim.approvalStage.id == 4 && claim.passingApproval == null){
            let submitForPassingHTML = `<div class="row">
                                            <div class="col justify-content-center">
                                                <button type="button" onclick="acceptClaim(${index})">Submit passing grade</button>
                                            </div>
                                        </div>`;
            claimHTML += submitForPassingHTML;
        }
        claimHTML += `</div>`;
    } else if(user.role.id <= 3){
        //console.log(claim.dsa);
        //console.log(user);
        if(user.role.id <= 2 && claim.dsa != null){
            //console.log('add dsa');
            claimHTML += `<div class="row">
                                <div class="col">
                                    <h6>Direct Supervisor approval:</h6>
                                </div>
                                <div class="col">
                                    <h6>${claim.dsa.firstName} ${claim.dsa.lastName}</h6>
                                </div>
                            </div>`
        }
        if(user.role.id <= 1 && claim.dsa != null && claim.dha != null){
            claimHTML += `<div class="row">
                                <div class="col">
                                    <h6>Department Head approval:</h6>
                                </div>
                                <div class="col">
                                    <h6>${claim.dha.firstName} ${claim.dha.lastName}</h6>
                                </div>
                            </div>`
        }
        claimHTML += `
                        <div class="row">
                            <div class="col justify-content-left">
                                <button type="button" onclick="acceptClaim(${index})">Accept</button>
                            </div>

                            <div class="col justify-content-left">
                                <button type="button" onclick="denyClaim(${index})">Deny</button>
                            </div>

                            <div class="col justify-content-left">
                                <input id="denialReason" type="text" placeholder="Denial reason" class="goodInput"/>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col justify-content-center">
                                <textarea id="rfcQuestion" class="goodInput"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col justify-content-center">
                                <button id="submitRFC" type="button" onclick="makeCommentRequest(${index})">Submit comment request</button> 
                            </div>
                        </div>
                    </div>`;
    }

    claimsDiv.innerHTML = claimHTML;

    if(user.role.id == 1){
        let pcr = document.getElementById('amountCoveredRow');
        pcr.innerHTML = `<div class="col justify-content-left">
                            <h6>Estimated amount covered: </h6>
                        </div>
                        <div id="amountCoveredArea" claimID="${claim.id}" class="col justify-content-center">
                            <h6 id="estiamtedAmountText">$${claim.estimatedAmount}</h6>
                        </div>
                        <div class="col justify-content-right">
                            <button id="updateCRA" type="button">Edit</button>
                        </div>
                        `;
        let pcrButton = document.getElementById('updateCRA');
        pcrButton.addEventListener('click', editCRA);
    }

    //add comments
    let commentsDiv = document.getElementById('commentsDiv');
    commentsDiv.innerHTML += `<div class="container">
                        <div class="row">
                            <div class="col justify-content-center">
                                <h1>Comments</h1>
                            </div>
                        </div>
                    </div>`;
    let rfcs = await getRFCs(claim.id);
    rfcList = rfcs;
    for(let i in rfcs){
        let comment;
        rfcToAnswer = rfcs[i];
        if(rfcs[i].answer != null){
            comment = `<div class="container justify-contents-center claim">
                            <div class="row">
                                <div class="col">
                                    <h6>Commenter: ${rfcs[i].commenter.firstName} ${rfcs[i].commenter.lastName}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h6>Comment:</h6>
                                </div>
                                <div class="col">
                                    <h6>${rfcs[i].description}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h6>Answer:</h6>
                                </div>
                                <div id="answer${rfcs[i].id}" class="col">
                                    <h6>${rfcs[i].answer}</h6>
                                </div>
                            </div>
                        </div>`;
        } else if(user.role.id < 4){
            comment = `<div class="container justify-contents-center claim">
                            <div class="row">
                                <div class="col">
                                    <h6>Commenter: ${rfcs[i].commenter.firstName} ${rfcs[i].commenter.lastName}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h6>Comment:</h6>
                                </div>
                                <div class="col">
                                    <h6>${rfcs[i].description}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h6>Answer:</h6>
                                </div>
                                <div id="answer${rfcs[i].id}" class="col">
                                    <h6>       </h6>
                                </div>
                            </div>
                        </div>`;
        } else if(user.role.id == 4) {
            comment = `<div class="container justify-contents-center claim">
                            <div class="row">
                                <div class="col">
                                    <h6>Commenter: ${rfcs[i].commenter.firstName} ${rfcs[i].commenter.lastName}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <h6>Comment:</h6>
                                </div>
                                <div class="col">
                                    <h6>${rfcs[i].description}</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div id="answer${rfcs[i].id}" class="col">
                                    <textarea id="rfc${rfcs[i].id}"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <button button="button" onclick="answerRFC(${i})">Submit answer</button>
                                </div>
                            </div>
                        </div>`;
        }
        commentsDiv.innerHTML += comment;
    }
}

async function acceptClaim(index){
    //console.log(claims[index]);
    let url = baseUrl + '/claims/accept/' +  claims[index].id;
    //console.log(url);
    let response = await fetch(url, {method: 'POST', body: JSON.stringify(claims[index])});

    if(response.status === 200){
        showClaims();
    } else {
        showClaims();
        alert('something went wrong');
    }
}

async function denyClaim(index){
    let denialReason = document.getElementById('denialReason');
    if(denialReason.value == ''){
        denialReason.classList.add('failInput');
    } else {
        let response = await fetch(baseUrl + '/claims/deny/' + claims[index].id, {method: 'DELETE', body: denialReason.value});
        if(response.status === 200){
            showClaims();
        } else {
            alert('Something went wrong');
        }
    }
}

async function makeNewClaim(){
    
    mainContentDiv.innerHTML = `<div class="container justify-content-center claim">
                                    <div class="row">
                                        <div class="col">
                                            <h2 class="titleText">New Claim</h2>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col">
                                            <h4 class="titleText">There is a yearly rembersment limit of $1000. So far this year you have claimed $${user.amountClaimed}</h4>
                                        </div>
                                    </div>

                                    <form id="newClaimForm">
                                        <h6>Name of the event: </h6> <input id="eventName" type="text" class="goodInput">
                                        <h6>Event Type:</h6><select id="eventOptions" class="goodInput"></select>
                                        <h6>Event Date:</h6><input id="eventDate" type="date" class="goodInput">
                                        <h6>Event Time:</h6><input id="eventTime" type="time" class="goodInput">
                                        <h6>Event Location:</h6><input id="eventLocation" type="text" class="goodInput">
                                        <h6>Passing percentage:<h6><input id="passingPercentage" type="number" class="goodInput" value="80">
                                        <h6>Passing letter:</h6>
                                        <select id="passingLetter" class="goodInput">
                                            <option>N/A</option>
                                            <option>A</option>
                                            <option>B</option>
                                            <option>C</option>
                                            <option>D</option>
                                        </select>
                                        <h6>Does this event require a presentation?<h6><input id="hasPresentation" type="checkbox" class="goodInput">
                                        <h6>Please privide a breif description of the event.</h6><textarea id="description" class="goodInput"></textarea>
                                        <h6>Justification:</h6><input id="justification" type="text" class="goodInput">
                                        <h6>Hours missed:</h6><input id="hoursMissed" type="number" class="goodInput">
                                        <h6>Cost: $</h6><input id="price" type="number" class="goodInput">
                                        <h6 id="estimatedRembersment">Estimated rembersment: $0</h6>
                                        <button id="submitButton" type="button" onclick="submitNewClaim()">Submit</button>
                                    </form>
                                </div>`;

    //populate the event types
    let response = await fetch(baseUrl+'/events');
    if(response.status === 200){
        events = await response.json();
        let eventSelect = document.getElementById("eventOptions");
        for(let i in events){
            let option = document.createElement('option');
            option.innerHTML = events[i].eventType + ':' + events[i].percentageCovered + '%';
            eventSelect.appendChild(option);
        }
    } else {
        showClaims();
        alert('Something went wrong');
    }

    let dateInput = document.getElementById('eventDate');
    let today = new Date();
    dateInput.setAttribute('min', today);

    //set event listener for live estimation updates
    let priceHTML = document.getElementById('price');
    priceHTML.addEventListener('keyup',calcEstimatedRembersment);
}

async function submitNewClaim(){
    let event = events[document.getElementById('eventOptions').selectedIndex];

    let priceInput = document.getElementById('price');
    percentageCovered = event.percentageCovered;

    let price = priceInput.value;
    let currentTotal = user.amountClaimed;
    let estimate = price * percentageCovered / 100.0;

    if(estimate + currentTotal > 1000){
        estimate = 1000-currentTotal;
    }

    let claim = {
        title : document.getElementById('eventName').value,
        event : event.id,
        hasPresentation : document.getElementById('hasPresentation').checked,
        passingPercentage : document.getElementById('passingPercentage').value,
        passingLetter : document.getElementById('passingLetter').value,
        eventDate : document.getElementById('eventDate').value,
        eventTime : document.getElementById('eventTime').value,
        eventLocation : document.getElementById('eventLocation').value,
        description : document.getElementById('description').value,
        price : document.getElementById('price').value,
        justification : document.getElementById('justification').value,
        hoursMissed : document.getElementById('hoursMissed').value,
        estimatedRembersment : '' + estimate
    };

    let form = document.getElementById('newClaimForm');
    let inputs = form.getElementsByTagName('input');

    for(let i = 0; i < inputs.length; i++){
        let input = inputs[i];
        input.classList.remove('failInput');
    }
    document.getElementById('description').classList.remove('failInput');

    let fail = false;
    if(claim.title == ''){
        fail = 'eventName';
    } else if(claim.eventDate == '') {
        fail = 'eventDate';
    } else if(claim.eventTime == '') {
        fail = 'eventTime';
    } else if(claim.eventLocation == ''){
        fail = 'eventLocation';
    } else if (claim.passingPercentage == '' && claim.passingLetter == 'N/A'){
        fail = 'passingPercentage';
    } else if(claim.description == ''){
        fail = 'description';
    } else if(claim.justification == ''){
        fail = 'justification';
    } else if(claim.hoursMissed == ''){
        fail = 'hoursMissed';
    } else if(claim.price == ''){
        fail = 'price';
    } 

    if(fail){
        let failElement = document.getElementById(fail);
        failElement.classList.add('failInput');

        if(fail == 'passingPercentage'){
            document.getElementById('passingLetter').classList.add('failInput');
        }
    } else {
        let response = await fetch(baseUrl + '/claims', {method : 'POST', body : JSON.stringify(claim)});
        if(response.status === 200){
            showClaims();
        } else {
            alert('Something went wrong')
            showClaim();
        }
    }
}

async function makeCommentRequest(index){
    let claim = claims[index];
    let description = document.getElementById('rfcQuestion');

    if(description.value == ''){
        description.classList.add('failInput');
    } else {
        let rfc = {
            claim : '' + claim.id,
            description : description.value
        };

        let response = await fetch(baseUrl + '/rfc', {method : 'POST', body : JSON.stringify(rfc)});
        if(response.status === 200){
            showClaims();
        } else {
            alert('Something went wrong');
            showClaims();
        }
    }
}

async function getRFCs(claimID){
    let response = await fetch(baseUrl + '/rfc/claims/' + claimID);
    if(response.status === 200){
        let rfcs = response.json();
        return rfcs;
    } else {
        alert('Unable to retrive comment requests');
    }
}

async function answerRFC(rfcIndex){
    let rfc = rfcList[rfcIndex];
    let textArea = document.getElementById('rfc' + rfc.id);
    
    let answerText = textArea.value;
    if(answerText == ''){
        textArea.classList.add('failInput');
        return;
    }
    
    let updateAnswer = {
        id:''+rfc.id,
        answer:answerText
    }

    let response = await fetch(baseUrl + '/rfc', {method : 'PUT', body : JSON.stringify(updateAnswer)});
    if(response.status === 200){
        showClaims();
    } else {
        alert("Something went wrong");
    }
}

 async function uploadFiles(claimIndex){
    //console.log('called file upload');
    //viewClaimDetails(claimIndex);

    let input = document.getElementById('fileInput');
    let files = input.files;

    if(files.length == 0){
        alert('No files selected.');
        return;
    }

    let formData = new FormData();
    
    for(let i in files){
        formData.append('files', files[i]);
    }

    let response = await fetch(baseUrl + '/attachment/' + claims[claimIndex].id, {
        method : 'POST',
        body : formData
    });

    if(response.status === 200){
        viewClaimDetails(claimIndex);
    } else {
        alert('unable to upload files');
    }
 }

 function calcEstimatedRembersment(){
    let priceInput = document.getElementById('price');
    let estimatedRembersmentHTML = document.getElementById('estimatedRembersment');
    let eventSelector = document.getElementById('eventOptions');
    let eventType = eventSelector.options[eventSelector.selectedIndex].text;
    let percentageCovered = eventType.split(':')[1];
    percentageCovered = percentageCovered.substring(0, percentageCovered.length-1);

    let price = priceInput.value;
    let currentTotal = user.amountClaimed;
    let estimate = price * percentageCovered / 100.0;

    if(estimate + currentTotal > 1000){
        estimate = 1000-currentTotal;
    }

    estimatedRembersmentHTML.innerHTML = 'Estimated rembersment: $' + estimate;
 }

 function editCRA(){
    let button = document.getElementById('updateCRA');
    let editArea = document.getElementById('amountCoveredArea');
    let currentEstimatedAmount = document.getElementById('estiamtedAmountText');
    currentEstimatedAmount = currentEstimatedAmount.innerHTML.substring(1);

    button.innerHTML = 'Save';
    button.removeEventListener('click', editCRA);
    button.addEventListener('click', updateCRA);

    editArea.innerHTML = `<input id="newEstimatedAmountInput" type="number" value="${currentEstimatedAmount}">`;
 }

 async function updateCRA(){
    let button = document.getElementById('updateCRA');
    let editArea = document.getElementById('amountCoveredArea');
    let newAmountInput = document.getElementById('newEstimatedAmountInput');

    button.innerHTML = 'Edit';
    button.removeEventListener('click', updateCRA);
    button.addEventListener('click', editCRA);

    if(newAmountInput.value != ''){
        let newAmount = newAmountInput.value;
        let claimID = editArea.getAttribute('claimID');
        let data = {
            id : claimID,
            amount : newAmount
        };
        let response = await fetch(baseUrl + '/claims/bc/', {
            method : 'PUT',
            body : JSON.stringify(data)
        });

        if(response.status === 200){
            editArea.innerHTML = `<h6 id="estiamtedAmountText">$${newAmount}</h6>`;
        } else {
            alert('Something went wrong');
        }
    }
 }

 async function cancelClaim(claimID){
    let denialReason = 'Canceled';
    let response = await fetch(baseUrl + '/claims/deny/' + claimID, {method: 'DELETE', body: denialReason});

    if(response.status === 200){
        showClaims();
    } else {
        alert('Something went wrong');
    }
 }