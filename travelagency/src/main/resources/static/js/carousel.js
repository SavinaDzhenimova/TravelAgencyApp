const container = document.querySelector('.carousel-container');
const items = document.querySelectorAll('.carousel-item');
const prevButton = document.querySelector('.prev');
const nextButton = document.querySelector('.next');

let currentIndex = 0;
const slideInterval = 10000;

function updateCarousel() {
    const offset = currentIndex * -100;
    container.style.transform = `translateX(${offset}%)`;
}

prevButton.addEventListener('click', () => {
    currentIndex = (currentIndex > 0) ? currentIndex - 1 : items.length - 1;
    updateCarousel();
});

nextButton.addEventListener('click', () => {
    currentIndex = (currentIndex < items.length - 1) ? currentIndex + 1 : 0;
    updateCarousel();
});

function startAutoSlide() {
    setInterval(() => {
        currentIndex = (currentIndex < items.length - 1) ? currentIndex + 1 : 0;
        updateCarousel();
    }, slideInterval);
}

startAutoSlide();