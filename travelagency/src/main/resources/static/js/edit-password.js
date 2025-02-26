document.addEventListener("DOMContentLoaded", function () {
    let editButton = document.querySelector(".edit-password-btn");
    let form = document.querySelector(".edit-buttons form");
    let inputs = form.querySelectorAll("input[type='password']");
    let saveButton = form.querySelector(".save-password-btn");

    editButton.addEventListener("click", function () {
        editButton.style.display = "none";
        form.style.display = "flex";

        inputs.forEach(input => {
            input.style.display = "block";
        });

        saveButton.disabled = true;
    });

    form.addEventListener("input", function () {
        let oldPassword = inputs[0].value.trim();
        let newPassword = inputs[1].value.trim();
        let confirmNewPassword = inputs[2].value.trim();

        saveButton.disabled = !(oldPassword && newPassword && confirmNewPassword && newPassword === confirmNewPassword);
    });
});