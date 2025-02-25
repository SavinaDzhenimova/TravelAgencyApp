document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-btn").forEach(button => {
        button.addEventListener("click", function () {
            let listItem = this.closest("li");
            let span = listItem.querySelector("span:nth-of-type(2)");
            let input = listItem.querySelector("input[name='updatedInfo']");
            let saveForm = listItem.querySelector("form");
            let saveButton = listItem.querySelector(".save-btn");
            let editButton = this;

            input.value = span.textContent.trim();

            input.style.display = "inline-block";
            saveForm.style.display = "flex";

            span.style.display = "none";
            editButton.style.display = "none";

            saveButton.disabled = true;

            input.addEventListener("input", function () {
                saveButton.disabled = input.value.trim() === span.textContent.trim();
            });
        });
    });
});