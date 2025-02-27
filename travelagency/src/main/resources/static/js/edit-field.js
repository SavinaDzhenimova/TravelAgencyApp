document.addEventListener("DOMContentLoaded", function () {
    let currentEditingItem = null;

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

            currentEditingItem = listItem;
        });
    });

    document.addEventListener("click", function (event) {
        if (currentEditingItem) {
            let listItem = currentEditingItem;
            let span = listItem.querySelector("span:nth-of-type(2)");
            let input = listItem.querySelector("input[name='updatedInfo']");
            let saveForm = listItem.querySelector("form");
            let saveButton = listItem.querySelector(".save-btn");
            let editButton = listItem.querySelector(".edit-btn");

            if (!listItem.contains(event.target)) {
                span.style.display = "inline";
                editButton.style.display = "inline-block";
                input.style.display = "none";
                saveForm.style.display = "none";

                currentEditingItem = null;
            }
        }
    });
});