document.addEventListener("DOMContentLoaded", function () {
    let hasErrors = document.querySelector(".is-invalid") !== null;

    if (hasErrors) {
        let modal = document.getElementById("quickInquiryModal");
        if (modal) {
            modal.classList.add("show");
        }
    }

    document.getElementById("openModal").addEventListener("click", function () {
        document.getElementById("quickInquiryModal").classList.add("show");
    });

    document.getElementById("closeModal").addEventListener("click", function () {
        document.getElementById("quickInquiryModal").classList.remove("show");
    });

    window.addEventListener("click", function (event) {
        let modal = document.getElementById("quickInquiryModal");

        if (event.target === modal) {
            modal.classList.remove("show");
        }
    });
});