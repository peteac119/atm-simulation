const url = "http://localhost:8080/api/atm";

function createNode(element)
{
    return document.createElement(element);
}

function append(parent, element)
{
    return parent.appendChild(element);
}

function removeAllChildren(parent)
{
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function reset(){
    fetch(url + "/reset")
    .then((resp) => resp.json())
    .then(function(data){
        let resetReports= data.cashReports();
        var totalAmount = 0;
        resetReports.map(function(resetReport) {
            let tdNumOfBankNote = document.getElementById(resetReport.value);
            tdNumOfBankNote.innerHTML = resetReport.availableNotes;
            totalAmount += resetReport.value * resetReport.availableNotes
        })

        let totalPara = document.getElementById("totalAvailableAmount");
        totalPara.innerHTML = "Total of Available Amount: " + totalAmount;
    })
    .catch(function(error) {
        alert(JSON.stringify(error))
    })
}

function dispensing(amount){
    fetch(url + "/" + amount)
    .then((resp) => resp.json())
    .then(function(data){

        let resultSection = document.getElementById("result");
        resultSection.value = '';
        resultSection.style.color = "black";

        if (data.error != null ){
            resultSection.value = data.error
            resultSection.style.color = "red";
            hasError = true;
            return
        }

        let dispensedBankNotes = data.cashReports;

        dispensedBankNotes.forEach(function(dispensedBankNote){
            resultSection.value += dispensedBankNote.noteType;
            resultSection.value += " = ";
            resultSection.value += dispensedBankNote.availableNotes;
            resultSection.value += "\n";
        })

        getAllAvailableNotes(function(updatedAvailableBankNotes){
            var totalAmount = 0;
            updatedAvailableBankNotes.map(function(updatedAvailableBankNote) {
                let tdNumOfBankNote = document.getElementById(updatedAvailableBankNote.value);
                tdNumOfBankNote.innerHTML = updatedAvailableBankNote.availableNotes;
                totalAmount += updatedAvailableBankNote.value * updatedAvailableBankNote.availableNotes
            })

            let totalPara = document.getElementById("totalAvailableAmount");
            totalPara.innerHTML = "Total of Available Amount: " + totalAmount;
        });
    })
    .catch(function(error) {
        alert(JSON.stringify(error))
    })
}

function getAllAvailableNotes(handlerFunc){
    fetch(url + "/allbanknote")
    .then((resp) => resp.json())
    .then(function(data){
        let reports = data.cashReports;
        handlerFunc(reports);
    })
    .catch(function(error) {
        alert(JSON.stringify(error))
    })
}

function submitClick(form){
    let dispensingAmount = form.dispensingAmount.value;
    dispensing(dispensingAmount);
}

getAllAvailableNotes(function(availableBankNoteList){
    const ul = document.getElementById("bankNoteTable");
    var totalAmount = 0;

    availableBankNoteList.map(function(availableNote) {
        let tr = createNode('tr'),
        tdBankNote = createNode('td'),
        tdNumOfBankNote = createNode('td');

        tdNumOfBankNote.id = availableNote.value

        tdBankNote.innerHTML = availableNote.value;
        tdNumOfBankNote.innerHTML = availableNote.availableNotes;
        append(tr, tdBankNote);
        append(tr, tdNumOfBankNote);
        append(ul, tr);

        totalAmount += availableNote.value * availableNote.availableNotes;
    })
    let totalPara = document.getElementById("totalAvailableAmount");
    totalPara.innerHTML = "Total of Available Amount: " + totalAmount;
});