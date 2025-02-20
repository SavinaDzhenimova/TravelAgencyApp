document.querySelector("#datesCount").addEventListener("input", function () {
    let datesCount = parseInt(this.value);
    let datesContainer = document.querySelector("#datesContainer");

    datesContainer.innerHTML = "";

    if (!isNaN(datesCount) && datesCount > 0) {
        for (let i = 1; i <= datesCount; i++) {
            let div = document.createElement("div");
            div.classList.add("field");

            let label = document.createElement("label");
            label.setAttribute("for", `date${i}`);
            label.textContent = `Дата ${i}`;

            let input = document.createElement("input");
            input.setAttribute("type", "date");
            input.setAttribute("id", `date${i}`);
            input.setAttribute("name", "dates");
            input.setAttribute("required", "true");

            let small = document.createElement("small");
            small.classList.add("text-danger");

            div.appendChild(label);
            div.appendChild(input);
            div.appendChild(small);
            datesContainer.appendChild(div);
        }
    }
});