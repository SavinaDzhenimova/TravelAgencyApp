document.querySelector("#touristsCount").addEventListener("input", function () {
    let touristsCount = parseInt(this.value);
    let touristsContainer = document.querySelector("#touristsContainer");

    touristsContainer.innerHTML = "";

    if (!isNaN(touristsCount) && touristsCount > 0) {
        for (let i = 1; i <= touristsCount; i++) {
            let div = document.createElement("div");
            div.classList.add("field");

            let label = document.createElement("label");
            label.setAttribute("for", `touristName${i}`);
            label.setAttribute("class", `required`);
            label.textContent = `Три имена по паспорт/лична карта на турист ${i}:`;

            let input = document.createElement("input");
            input.setAttribute("type", "text");
            input.setAttribute("id", `touristName${i}`);
            input.setAttribute("name", "touristNames");
            input.setAttribute("required", "true");

            let small = document.createElement("small");
            small.classList.add("text-danger");

            div.appendChild(label);
            div.appendChild(input);
            div.appendChild(small);
            touristsContainer.appendChild(div);
        }
    }
});