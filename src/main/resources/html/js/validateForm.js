function validateForm() {
    if (!validateTextFields() || !validateAge() || !validateCheckboxes()) || !validateRatings() {
        return false;
    }
    return true;
}

function validateTextFields() {
    const fields = ["fullNames", "email", "DOB", "contactNumb"];
    for (const field of fields) {
        if (document.getElementById(field).value.trim() === "") {
            alert("Please fill out all text fields.");
            return false;
        }
    }
    return true;
}

function validateAge() {
    const dob = document.getElementById("DOB").value.trim();
    const dobDate = new Date(dob);
    const currentDate = new Date();
    const age = currentDate.getFullYear() - dobDate.getFullYear();

    if (age < 5 || age > 120) {
        alert("Age must be between 5 and 120 years.");
        return false;
    }
    return true;
}

function validateCheckboxes() {
    if (document.querySelectorAll('input[name="food"]:checked').length === 0) {
        alert("Please select at least one favorite food.");
        return false;
    }
    return true;
}

function validateRatings() {
    const ratings = ["movies", "radio", "eat_out", "watch_tv"];
    for (const rating of ratings) {
        if (!document.querySelector(`input[name="${rating}"]:checked`)) {
            alert("Please select a rating for all questions.");
            return false;
        }
    }
    return true;
}
