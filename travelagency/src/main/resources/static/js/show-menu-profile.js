const hiddenProfile = document.querySelector('.hidden-profile');
const profile = document.querySelector('#profile');

profile.addEventListener('mouseover', () => {
    hiddenProfile.style.display = 'block';
});

profile.addEventListener('mouseout', () => {
    hiddenProfile.style.display = 'none';
});