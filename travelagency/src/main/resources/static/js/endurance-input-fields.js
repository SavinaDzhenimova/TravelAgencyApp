document.querySelector("#endurance").addEventListener("input", function () {
    let endurance = parseInt(this.value);
    let container = document.querySelector("#daysContainer");

    container.innerHTML = "";

    if (!isNaN(endurance) && endurance > 0) {
        for (let i = 1; i <= endurance; i++) {
            let div = document.createElement("div");
            div.classList.add("field");

            let label = document.createElement("label");
            label.setAttribute("for", `day${i}`);
            label.textContent = `Програма за ден ${i}`;

            let textarea = document.createElement("textarea");
            textarea.setAttribute("id", `day${i}`);
            textarea.setAttribute("name", "days");
            textarea.setAttribute("required", "true");

            let small = document.createElement("small");
            small.classList.add("text-danger");

            div.appendChild(label);
            div.appendChild(textarea);
            div.appendChild(small);
            container.appendChild(div);
        }
    }
});