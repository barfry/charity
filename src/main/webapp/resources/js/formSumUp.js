function formSumUp() {
    debugger;

    var categories = document.querySelectorAll('input[type="checkbox"]:checked');
    var input = "";
    for (var checkbox of categories) {
        input += checkbox.title + ' ';
    }
    var quantity = document.getElementById("quantity").value;
    var institution = document.querySelector('input[name="institution"]:checked').title;
    var street = document.getElementById("street").value;
    var city = document.getElementById("city").value;
    var zipCode = document.getElementById("zipCode").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var pickUpDate = document.getElementById("pickUpDate").value;
    var pickUpTime = document.getElementById("pickUpTime").value;
    var pickUpComment = document.getElementById("pickUpComment").value;

    document.getElementById("summary--quantity").innerHTML = quantity + " worki " + input;
    document.getElementById("summary--institution").innerHTML = institution;
    document.getElementById("summary--street").innerHTML = street;
    document.getElementById("summary--city").innerHTML = city;
    document.getElementById("summary--zipCode").innerHTML = zipCode;
    document.getElementById("summary--phoneNumber").innerHTML = phoneNumber;
    document.getElementById("summary--pickUpDate").innerHTML = pickUpDate;
    document.getElementById("summary--pickUpTime").innerHTML = pickUpTime;
    document.getElementById("summary--pickUpComment").innerHTML = pickUpComment;
}

function scrollToFormPart() {
    const formStepsSection = document.querySelector('.form--steps');
    const hasErrorsInput = document.querySelector('input[name="hasErrors"]');
    if (formStepsSection && hasErrorsInput && hasErrorsInput.value === 'true') {
        formStepsSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

// Scroll to the form part when the page loads
document.addEventListener('DOMContentLoaded', scrollToFormPart);