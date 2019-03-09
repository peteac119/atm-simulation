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

function submitClick(form){
    let dispensingAmount = form.dispensingAmount.value;
    fetch(url + "/" + dispensingAmount)
    .then((resp) => resp.json())
    .then(function(data){

        let resultSection = document.getElementById("result");
        removeAllChildren(resultSection);

        if (data.error != null ){
            let errorPara = createNode("p");
            errorPara.innerHTML = data.error;
            append(resultSection, errorPara);
            return
        }

        let availableBankNotes = data.availableCashReports;
        let dispensedBankNotes = data.dispensedCashReports;
        var totalAmount = 0;

        availableBankNotes.map(function(availableBankNote) {
            let tdNumOfBankNote = document.getElementById(availableBankNote.value);
            tdNumOfBankNote.innerHTML = availableBankNote.availableNotes;
            totalAmount += availableBankNote.value * availableBankNote.availableNotes
        })

        let totalPara = document.getElementById("totalAvailableAmount");
        totalPara.innerHTML = "Total of Available Amount: " + totalAmount;
//
//         let table = createNode('table');
//         let firstRow = createNode('tr');
//         let firstHeader = createNode('th');
//         let secondHeader = createNode('th');
//
//         firstHeader.innerHTML = "Bank Note";
//         secondHeader.innerHTML = "Dispensed"
//
//        dispensedBankNotes.map(function(dispensedBankNote) {
//            paragraph.innerHTML = dispensedBankNote.
//            resultSection.innerHTML =
//        })
    })
    .catch(function(error) {
        alert(JSON.stringify(error))
    })
}

const ul = document.getElementById("bankNoteTable");

fetch(url + "/allbanknote")
.then((resp) => resp.json())
.then(function(data){
    let availableBankNotes = data.availableCashReports;
    var totalAmount = 0;
    availableBankNotes.map(function(availableBankNote) {
        let tr = createNode('tr'),
        tdBankNote = createNode('td'),
        tdNumOfBankNote = createNode('td');

        tdNumOfBankNote.id = availableBankNote.value

        tdBankNote.innerHTML = availableBankNote.value;
        tdNumOfBankNote.innerHTML = availableBankNote.availableNotes;
        append(tr, tdBankNote);
        append(tr, tdNumOfBankNote);
        append(ul, tr);

        totalAmount += availableBankNote.value * availableBankNote.availableNotes;
    })
    let totalPara = document.getElementById("totalAvailableAmount");
    totalPara.innerHTML = "Total of Available Amount: " + totalAmount;
})
.catch(function(error) {
    alert(JSON.stringify(error))
})