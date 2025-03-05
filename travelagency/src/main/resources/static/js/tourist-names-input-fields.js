window.addEventListener("DOMContentLoaded", function () {
    let touristsCountInput = document.querySelector("#touristsCount");
    if (touristsCountInput.value) {
        generateTouristFields(parseInt(touristsCountInput.value));
    }
});

document.querySelector("#touristsCount").addEventListener("input", function () {
    generateTouristFields(parseInt(this.value));
});

function generateTouristFields(touristsCount) {
    let touristsContainer = document.querySelector("#touristsContainer");
    touristsContainer.innerHTML = "";

    if (!isNaN(touristsCount) && touristsCount > 0) {
        for (let i = 1; i <= touristsCount; i++) {
            let div = document.createElement("div");
            div.classList.add("field");

            let label = document.createElement("label");
            label.setAttribute("for", `touristName${i}`);
            label.classList.add("required");
            label.textContent = `Три имена по паспорт/лична карта на турист ${i}: `;

            let input = document.createElement("input");
            input.setAttribute("type", "text");
            input.setAttribute("id", `touristName${i}`);
            input.setAttribute("name", "touristNames");

            let small = document.createElement("small");
            small.classList.add("text-danger");

            input.addEventListener("blur", function() {
                if (input.value.trim() === "") {
                    small.textContent = "Моля, попълнете името на туриста!";
                } else {
                    small.textContent = "";
                }
            });

            div.appendChild(label);
            div.appendChild(input);
            div.appendChild(small);
            touristsContainer.appendChild(div);
        }
    }
}