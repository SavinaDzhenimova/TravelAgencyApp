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

            // Показваме input и формата за запазване
            input.value = span.textContent.trim();
            input.style.display = "inline-block";
            saveForm.style.display = "flex";

            // Скриваме span и бутона за редактиране
            span.style.display = "none";
            editButton.style.display = "none";

            // Деактивираме бутона за запазване, докато не има промяна
            saveButton.disabled = true;

            // Следене за промени в input полето
            input.addEventListener("input", function () {
                saveButton.disabled = input.value.trim() === span.textContent.trim();
            });

            // Задаваме текущия елемент като редактируем
            currentEditingItem = listItem;
        });
    });

    // Глобален обработчик за клик извън
    document.addEventListener("click", function (event) {
        if (currentEditingItem) {
            let listItem = currentEditingItem;
            let span = listItem.querySelector("span:nth-of-type(2)");
            let input = listItem.querySelector("input[name='updatedInfo']");
            let saveForm = listItem.querySelector("form");
            let saveButton = listItem.querySelector(".save-btn");
            let editButton = listItem.querySelector(".edit-btn");

            // Проверяваме дали кликът е извън текущия ред
            if (!listItem.contains(event.target)) {
                // Ако кликът е извън, възстановяваме състоянието
                span.style.display = "inline";
                editButton.style.display = "inline-block";
                input.style.display = "none";
                saveForm.style.display = "none";

                // Премахваме редактирания елемент
                currentEditingItem = null;
            }
        }
    });
});