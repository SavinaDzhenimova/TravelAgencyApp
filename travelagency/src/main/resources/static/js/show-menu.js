const hiddenDestinations = document.querySelector('.hidden-destinations');
const hiddenExcursions = document.querySelector('.hidden-excursions');

const destinations = document.querySelector('#destinations');
const excursions = document.querySelector('#excursions');

destinations.addEventListener('mouseover', () => {
    hiddenDestinations.style.display = 'block';
});

destinations.addEventListener('mouseout', () => {
    hiddenDestinations.style.display = 'none';
});

excursions.addEventListener('mouseover', () => {
    hiddenExcursions.style.display = 'block';
});

excursions.addEventListener('mouseout', () => {
    hiddenExcursions.style.display = 'none';
});