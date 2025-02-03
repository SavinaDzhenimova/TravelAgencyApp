const hiddenDestinations = document.querySelector('.hidden-destinations');
const hiddenExcursions = document.querySelector('.hidden-excursions');
const hiddenProfile = document.querySelector('.hidden-profile');

const destinations = document.querySelector('#destinations');
const excursions = document.querySelector('#excursions');
const profile = document.querySelector('#profile');

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

profile.addEventListener('mouseover', () => {
    hiddenProfile.style.display = 'block';
});

profile.addEventListener('mouseout', () => {
    hiddenProfile.style.display = 'none';
});